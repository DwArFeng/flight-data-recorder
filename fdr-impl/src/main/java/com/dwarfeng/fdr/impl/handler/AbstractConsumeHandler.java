package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.dutil.develop.backgr.AbstractTask;
import com.dwarfeng.dutil.develop.backgr.Task;
import com.dwarfeng.fdr.stack.handler.ConsumeHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    private Queue<E> queue = null;
    private long lastIdleCheckDate = System.currentTimeMillis();
    private Set<Task> tasks = new HashSet<>();
    private boolean runningFlag = true;

    @Override
    public void accept(E element) {
        lock.lock();
        try {
            while (queue.size() >= bufferSize && runningFlag) {
                try {
                    provideCondition.await();
                } catch (InterruptedException ignored) {
                }
            }
            if (!runningFlag) {
                return;
            }
            queue.add(element);
            consumerCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int usedBufferSize() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int totalBufferSize() {
        return bufferSize;
    }

    @Override
    public boolean canGracefulShutdown() {
        lock.lock();
        try {
            if (!queue.isEmpty()) {
                return false;
            }
            for (Task task : tasks) {
                if (!task.isFinished()) {
                    return false;
                }
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    protected void start() {
        lock.lock();
        try {
            runningFlag = true;
            queue = new ArrayDeque<>(bufferSize);
            for (int i = 0; i < consumerThread; i++) {
                Task task = new AbstractTask() {
                    @Override
                    protected void todo() {
                        loopConsume();
                    }
                };
                tasks.add(task);
                threadPoolTaskExecutor.execute(task);
            }
        } finally {
            lock.unlock();
        }
    }

    protected void stop() {
        lock.lock();
        try {
            runningFlag = false;
            provideCondition.signalAll();
            consumerCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    abstract protected void doConsume(List<E> list);

    @SuppressWarnings("StatementWithEmptyBody")
    private void loopConsume() {
        while (consume()) ;
    }

    private boolean consume() {
        long currentTimeMillis = System.currentTimeMillis();
        List<E> list = new ArrayList<>();
        long timeOffset = 0;
        lock.lock();
        try {
            while ((queue.isEmpty() || queue.size() < batchSize)
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
                return false;
            }
            int processBatch = Math.min(batchSize, queue.size());
            for (int i = 0; i < processBatch; i++) {
                list.add(queue.remove());
            }
            provideCondition.signalAll();
        } finally {
            lock.unlock();
        }
        if (!list.isEmpty()) {
            doConsume(list);
        }
        return true;
    }
}
