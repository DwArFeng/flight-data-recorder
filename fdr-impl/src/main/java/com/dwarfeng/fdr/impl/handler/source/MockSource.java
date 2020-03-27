package com.dwarfeng.fdr.impl.handler.source;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.impl.handler.Source;
import com.dwarfeng.fdr.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.fdr.stack.service.RecordService;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MockSource implements Source {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockSource.class);

    @Autowired
    private ThreadPoolTaskScheduler scheduler;
    @Autowired
    private RecordService recordService;
    @Autowired
    @Qualifier("mockSource.mockBuffer")
    private MockBuffer mockBuffer;

    @Value("${source.mock.data_size_per_sec}")
    private int dataSizePerSec;
    @Value("${source.mock.point_id}")
    private long pointId;

    private final Lock lock = new ReentrantLock();
    private boolean startFlag = false;
    private MockRecordPlain mockRecordPlain = null;
    private ScheduledFuture<?> mockRecordPlainFuture = null;
    private MockMonitorPlain mockMonitorPlain = null;
    private ScheduledFuture<?> mockMonitorPlainFuture = null;
    private MockProvidePlain mockProvidePlain = null;
    private ScheduledFuture<?> mockProvidePlainFuture = null;

    @Override
    public boolean isOnline() {
        lock.lock();
        try {
            return startFlag;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void online() throws HandlerException {
        lock.lock();
        try {
            if (!startFlag) {
                LOGGER.info("Mock source 上线...");
                mockBuffer.block();
                mockRecordPlain = new MockRecordPlain(recordService, mockBuffer, dataSizePerSec);
                mockMonitorPlain = new MockMonitorPlain(mockBuffer);
                mockProvidePlain = new MockProvidePlain(mockBuffer, pointId, dataSizePerSec);
                mockRecordPlainFuture = scheduler.scheduleAtFixedRate(mockRecordPlain, 1000);
                mockMonitorPlainFuture = scheduler.scheduleAtFixedRate(mockMonitorPlain, 1000);
                mockProvidePlainFuture = scheduler.scheduleAtFixedRate(mockProvidePlain, 1000);
                startFlag = true;
            }
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void offline() throws HandlerException {
        lock.lock();
        try {
            if (startFlag) {
                LOGGER.info("Mock source 下线...");
                mockRecordPlain.shutdown();
                mockRecordPlainFuture.cancel(true);
                mockMonitorPlain.shutdown();
                mockMonitorPlainFuture.cancel(true);
                mockProvidePlain.shutdown();
                mockProvidePlainFuture.cancel(true);
                mockRecordPlain = null;
                mockRecordPlainFuture = null;
                mockMonitorPlain = null;
                mockMonitorPlainFuture = null;
                mockProvidePlain = null;
                mockProvidePlainFuture = null;
                startFlag = false;
            }
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    @Configuration
    public static class MockSourceConfiguration {

        private static final Logger LOGGER = LoggerFactory.getLogger(MockSourceConfiguration.class);

        @Value("${source.mock.buffer_size}")
        private int bufferSize;

        @Bean("mockSource.mockBuffer")
        public MockBuffer mockBuffer() {
            return new MockBuffer(bufferSize);
        }
    }

    private static class MockBuffer {

        private final Lock lock = new ReentrantLock();
        private final Condition provideCondition = lock.newCondition();
        private final Condition consumeCondition = lock.newCondition();
        private final List<DataInfo> buffer = new ArrayList<>();

        private final int bufferSize;

        private boolean blockEnabled = true;

        private MockBuffer(int bufferSize) {
            this.bufferSize = bufferSize;
        }

        public void accept(DataInfo dataInfo) {
            lock.lock();
            try {
                while (buffer.size() >= bufferSize && blockEnabled) {
                    try {
                        provideCondition.await();
                    } catch (InterruptedException ignored) {
                    }
                }

                if (!blockEnabled) {
                    return;
                }

                buffer.add(dataInfo);
                consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public List<DataInfo> poll(int size) {
            lock.lock();
            try {
                while (buffer.size() < size && blockEnabled) {
                    try {
                        consumeCondition.await();
                    } catch (InterruptedException ignored) {
                    }
                }

                if (!blockEnabled) {
                    return Collections.emptyList();
                }

                int processingElementSize = Math.min(size, buffer.size());
                List<DataInfo> subList = buffer.subList(0, processingElementSize);
                List<DataInfo> elements2Return = new ArrayList<>(subList);
                subList.clear();

                provideCondition.signalAll();
                return elements2Return;
            } finally {
                lock.unlock();
            }
        }

        public int bufferedSize() {
            lock.lock();
            try {
                return buffer.size();
            } finally {
                lock.unlock();
            }
        }

        public int bufferSize() {
            return bufferSize;
        }

        public void block() {
            lock.lock();
            try {
                this.blockEnabled = true;
                this.provideCondition.signalAll();
                this.consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public void unblock() {
            lock.lock();
            try {
                this.blockEnabled = false;
                this.provideCondition.signalAll();
                this.consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    private static class MockRecordPlain implements Runnable {

        private final RecordService recordService;
        private final MockBuffer mockBuffer;
        private final int size;

        private final Lock lock = new ReentrantLock();
        private boolean runningFlag = true;

        public MockRecordPlain(RecordService recordService, MockBuffer mockBuffer, int size) {
            this.recordService = recordService;
            this.mockBuffer = mockBuffer;
            this.size = size;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                if (!runningFlag) {
                    return;
                }

                for (DataInfo dataInfo : mockBuffer.poll(size)) {
                    try {
                        recordService.record(dataInfo);
                    } catch (ServiceException e) {
                        if (e.getCode().getCode() == ServiceExceptionCodes.RECORD_HANDLER_DISABLED.getCode()) {
                            LOGGER.warn("记录处理器被禁用， 消息 " + dataInfo + " 将会被忽略", e);
                        } else {
                            LOGGER.warn("记录处理器无法处理, 消息 " + dataInfo + " 将会被忽略", e);
                        }
                    }
                }
            } finally {
                lock.unlock();
            }
        }

        public void shutdown() {
            lock.lock();
            try {
                runningFlag = false;
            } finally {
                lock.unlock();
            }
        }
    }

    private static class MockProvidePlain implements Runnable {

        private final MockBuffer mockBuffer;
        private final long pointId;
        private final int size;

        private final Lock lock = new ReentrantLock();
        private final Random random = new Random();
        private boolean runningFlag = true;

        public MockProvidePlain(MockBuffer mockBuffer, long pointId, int size) {
            this.mockBuffer = mockBuffer;
            this.pointId = pointId;
            this.size = size;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                if (!runningFlag) {
                    return;
                }

                for (int i = 0; i < size; i++) {
                    mockBuffer.accept(new DataInfo(pointId, Long.toString(random.nextLong()), new Date()));
                }
            } finally {
                lock.unlock();
            }
        }

        public void shutdown() {
            lock.lock();
            try {
                runningFlag = false;
            } finally {
                lock.unlock();
            }
        }
    }

    private static class MockMonitorPlain implements Runnable {

        private static final Logger LOGGER = LoggerFactory.getLogger(MockMonitorPlain.class);

        private final MockBuffer mockBuffer;

        private final Lock lock = new ReentrantLock();
        private boolean runningFlag = true;

        public MockMonitorPlain(MockBuffer mockBuffer) {
            this.mockBuffer = mockBuffer;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                if (!runningFlag) {
                    return;
                }
                LOGGER.info("缓冲容量: " + mockBuffer.bufferedSize() + "/" + mockBuffer.bufferSize);
            } finally {
                lock.unlock();
            }
        }

        public void shutdown() {
            lock.lock();
            try {
                runningFlag = false;
            } finally {
                lock.unlock();
            }
        }
    }
}
