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
                    tasks.stream().map(ConsumeTask::processingElementSize).reduce(0, Integer::sum)
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
                collect.forEach(ConsumeTask::shutdown);
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
                tasks.forEach(ConsumeTask::shutdown);
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

        public void shutdown() {
            this.runningFlag = false;
        }

        public int processingElementSize() {
            return processingElementSize;
        }

        private void consume() {
            long currentTimeMillis = System.currentTimeMillis();
            List<E> list = new ArrayList<>();
            long timeOffset = 0;
            AbstractConsumeHandler.this.lock.lock();
            try {
                /*
                 * 线程阻塞的逻辑。
                 *   最终的效果是达到如下的目的。
                 *     [最大空闲时间]或者[批处理]这两个参数有一个小于等于0，意思是只要[buffer]中有至少一个元素，就立即处理。
                 *     除此之外([最大空闲时间]和[批处理]两个参数都大于0)
                 *       当[buffer]的数量小于[批处理]的数且([当前时间]-[最近检查日期] < [最大空闲时间])时，一直阻塞。
                 *     以上条件发生的前提是 [runningFlag] 必须为 true，一旦 [runningFlag] 为 false，则其余参数为任何值都
                 *     不能够阻塞。
                 * 三者要同时满足:
                 *   1. [buffer]中没有任何元素，或者[批处理]大于0并且[buffer]中的元素小于[批处理]中的元素。
                 *      解释: [buffer]中没有任何元素，肯定是要阻塞的。
                 *            [批处理]小于等于0的意思是只要[buffer]中有一个元素，就不能阻塞，因为"buffer.isEmpty()"的结果
                 *            为false已经判断了[buffer]中至少含有一个元素，因此该处逻辑只需判断[批处理]小于等于0。
                 *            [批处理]大于0，且[buffer]中元素的数量小于[批处理]的数量的话，是需要阻塞的。由于前条语句
                 *            "buffer.isEmpty() || batchSize <= 0"的结果为false已经判断了[buffer]中至少含有一个元素且[批处理]
                 *            大于0，此时需要判断[buffer]是否小于[批处理]，如果小于批处理，则阻塞。
                 *   2. [空闲时间]小于[最大空闲时间]，或者[最大空闲时间]小于等于0且buffer中没有任何元素。
                 *      解释: 定义[空闲时间] = [当前时间] - [最近检查日期]
                 *            这部分逻辑比较简单，依照定义给出逻辑判断式。
                 *   3. [runningFlag] 必须为 true。
                 *      解释: 这部分逻辑比较简单，依照定义给出逻辑判断式。
                 */
                while ((buffer.isEmpty() || batchSize <= 0 || buffer.size() < batchSize)
                        //注意: 以下两行是一句
                        && (maxIdleTime <= 0 && buffer.isEmpty() || maxIdleTime > 0
                        && (timeOffset = maxIdleTime - currentTimeMillis + lastIdleCheckDate) > 0)
                        //注意: 以上两行是一句
                        && runningFlag
                ) {
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
