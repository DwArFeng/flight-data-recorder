package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.dutil.develop.backgr.AbstractTask;
import com.dwarfeng.fdr.stack.handler.ConsumeHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * 消费处理器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.2.0.a
 */
public abstract class AbstractConsumeHandler<E> implements ConsumeHandler<E> {

    protected ThreadPoolTaskExecutor threadPoolTaskExecutor;
    protected int consumerThread;
    protected int bufferSize;
    protected int batchSize;
    protected long maxIdleTime;

    private Lock lock = new ReentrantLock();
    private Condition provideCondition = lock.newCondition();
    private Condition consumerCondition = lock.newCondition();
    private List<E> buffer = null;
    private long lastIdleCheckDate = System.currentTimeMillis();
    private Set<ConsumeTask> tasks = new HashSet<>();
    private boolean startFlag = false;

    @Override
    public void accept(E element) {
        lock.lock();
        try {
            while (buffer.size() >= bufferSize) {
                try {
                    provideCondition.await();
                } catch (InterruptedException ignored) {
                }
            }
            buffer.add(element);
            consumerCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public ConsumeStatus getConsumeStatus() {
        lock.lock();
        try {
            return new ConsumeStatus(
                    this.consumerThread,
                    this.bufferSize,
                    this.batchSize,
                    this.maxIdleTime,
                    this.buffer.size(),
                    tasks.stream().map(ConsumeTask::getProcessingElementSize).reduce(0, Integer::sum)
            );
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setConsumeParameter(int consumerThread, int bufferSize, int batchSize, long maxIdleTime) {
        lock.lock();
        try {
            this.consumerThread = Math.max(1, consumerThread);
            this.bufferSize = Math.max(1, bufferSize);
            this.batchSize = batchSize;
            this.maxIdleTime = maxIdleTime;
            int threadDelta = this.consumerThread - tasks.size();
            if (threadDelta > 0) {
                for (int i = 0; i < threadDelta; i++) {
                    ConsumeTask task = new ConsumeTask();
                    tasks.add(task);
                    threadPoolTaskExecutor.execute(task);
                }
            } else if (threadDelta < 0) {
                Set<ConsumeTask> collect = tasks.stream().limit(-threadDelta).collect(Collectors.toSet());
                collect.forEach(task -> task.setRunningFlag(false));
                tasks.removeAll(collect);
            }
            provideCondition.signalAll();
            consumerCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    protected void start() {
        lock.lock();
        try {
            if (!startFlag) {
                buffer = new ArrayList<>();
                for (int i = 0; i < consumerThread; i++) {
                    ConsumeTask task = new ConsumeTask();
                    tasks.add(task);
                    threadPoolTaskExecutor.execute(task);
                }
                startFlag = true;
            }
        } finally {
            lock.unlock();
        }
    }

    protected void stop() {
        lock.lock();
        try {
            if (startFlag) {
                tasks.forEach(task -> task.setRunningFlag(false));
                tasks.clear();
                provideCondition.signalAll();
                consumerCondition.signalAll();
                startFlag = false;
            }
        } finally {
            lock.unlock();
        }
    }

    abstract protected void doConsume(List<E> list);

    private class ConsumeTask extends AbstractTask {

        private boolean runningFlag = true;
        private int processingElementSize = 0;

        @Override
        protected void todo() {
            while (runningFlag) {
                consume();
            }
        }

        public boolean isRunningFlag() {
            return runningFlag;
        }

        public void setRunningFlag(boolean runningFlag) {
            this.runningFlag = runningFlag;
        }

        public int getProcessingElementSize() {
            return processingElementSize;
        }

        public void setProcessingElementSize(int processingElementSize) {
            this.processingElementSize = processingElementSize;
        }

        private void consume() {
            long currentTimeMillis = System.currentTimeMillis();
            List<E> list = new ArrayList<>();
            long timeOffset = 0;
            AbstractConsumeHandler.this.lock.lock();
            try {
                while ((buffer.isEmpty() || buffer.size() < batchSize)
                        && (maxIdleTime <= 0 || (timeOffset = maxIdleTime - currentTimeMillis + lastIdleCheckDate) > 0)
                        && runningFlag) {
                    try {
                        if (batchSize <= 1 || maxIdleTime <= 0) {
                            consumerCondition.await();
                        } else {
                            consumerCondition.await(timeOffset, TimeUnit.MILLISECONDS);
                        }
                    } catch (InterruptedException ignored) {
                    }
                    currentTimeMillis = System.currentTimeMillis();
                }
                lastIdleCheckDate = currentTimeMillis;
                if (!runningFlag) {
                    return;
                }
                processingElementSize = Math.min(batchSize, buffer.size());
                List<E> subList = buffer.subList(0, processingElementSize);
                list.addAll(subList);
                subList.clear();
                provideCondition.signalAll();
            } finally {
                AbstractConsumeHandler.this.lock.unlock();
            }
            if (!list.isEmpty()) {
                doConsume(list);
            }
            AbstractConsumeHandler.this.lock.lock();
            try {
                processingElementSize = 0;
            } finally {
                AbstractConsumeHandler.this.lock.unlock();
            }
        }
    }
}
