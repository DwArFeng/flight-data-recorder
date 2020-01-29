package com.dwarfeng.fdr.node.manager.fuh.launcher;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 启动器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class Launcher {

    private final Lock lock;

    private final ClassPathXmlApplicationContext ctx;
    private final Condition condition;
    private final Thread thread;
    private boolean exitFlag = false;
    private boolean startFlag = false;

    public Launcher() {
        ctx = new ClassPathXmlApplicationContext("classpath:spring/application-context*.xml");
        lock = new ReentrantLock();
        condition = lock.newCondition();
        thread = new Thread(new ProgramRunner(), "program-runner");
        thread.setDaemon(false);
    }

    public static void main(String[] args) throws InterruptedException {
        Launcher launcher = new Launcher();
        launcher.start();
        launcher.awaitFinish();
    }

    /**
     * 启动程序。
     */
    public void start() {
        lock.lock();
        try {
            if (startFlag) {
                throw new IllegalStateException("程序已经启动了");
            }
            startFlag = true;
            thread.start();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 告知启动器程序将要结束（可以结束）。
     */
    public void notify2Exit() {
        lock.lock();
        try {
            exitFlag = true;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 等待，直到服务端运行结束。
     *
     * <p>
     * 该方法会阻塞调用线程，直到服务端运行结束。
     *
     * @throws InterruptedException 调用线程被中断。
     */
    public void awaitFinish() throws InterruptedException {
        lock.lock();
        try {
            while (!exitFlag) {
                condition.await();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 等待，直到服务端运行结束或超过指定的时间。
     *
     * <p>
     * 如果服务端运行结束，则返回 <code>true</code>，如果超过了指定的时间，则返回 <code>false</code>。
     *
     * @param timeout 指定的时间。
     * @param unit    指定的时间单位。
     * @return 判断服务端运行结束还是超过了指定的时间。
     * @throws InterruptedException 调用线程被中断。
     */
    public boolean awaitFinish(long timeout, TimeUnit unit) throws InterruptedException {
        lock.lock();
        try {
            long nanosTimeout = unit.toNanos(timeout);
            while (!exitFlag) {
                if (nanosTimeout > 0)
                    nanosTimeout = condition.awaitNanos(nanosTimeout);
                else
                    return false;
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    private final class ProgramRunner implements Runnable {

        @Override
        public void run() {
            lock.lock();
            try {
                //程序启动。
                programStart();
                //检查程序是否允许退出。
                checkExitLoop();
                //程序退出
                programExit();
            } catch (Exception e) {
                System.exit(1);
            } finally {
                lock.unlock();
            }
        }

        private void programStart() {
            ctx.registerShutdownHook();
            ctx.start();
        }

        private void checkExitLoop() {
            while (!exitFlag) {
                try {
                    condition.await();
                } catch (InterruptedException ignored) {
                }
            }
        }

        private void programExit() {
            ctx.stop();
            ctx.close();
            System.exit(0);
        }

    }
}
