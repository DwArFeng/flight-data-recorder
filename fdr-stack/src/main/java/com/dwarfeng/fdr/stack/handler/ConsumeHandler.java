package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 消费处理器。
 *
 * @author DwArFeng
 * @since 1.2.0.a
 */
public interface ConsumeHandler<E> extends Handler {

    /**
     * 使消费处理器接受指定的元素。
     *
     * @param element 指定的元素。
     * @throws HandlerException 处理器异常。
     */
    void accept(E element) throws HandlerException;

    /**
     * 获取消费状态。
     *
     * @return 消费状态。
     * @throws HandlerException 处理器异常。
     */
    ConsumeStatus getConsumeStatus() throws HandlerException;

    /**
     * 设置消费者的各项参数。
     *
     * @param consumerThread 消费者的线程数。
     * @param bufferSize     缓冲队列的数量。
     * @param batchSize      批处理数量。
     * @param maxIdleTime    最大空闲时间。
     * @throws HandlerException 处理器异常。
     */
    void setConsumeParameter(int consumerThread, int bufferSize, int batchSize, long maxIdleTime) throws HandlerException;

    /**
     * 消费状态。
     *
     * @author DwArFeng
     * @since 1.2.0.a
     */
    class ConsumeStatus implements Bean {

        private static final long serialVersionUID = 4200797487098414748L;

        private int consumerThread;
        private int bufferSize;
        private int batchSize;
        private long maxIdleTime;
        private int usedBufferSize;
        private int processingElementSize;

        public ConsumeStatus() {
        }

        public ConsumeStatus(
                int consumerThread, int bufferSize, int batchSize, long maxIdleTime, int usedBufferSize,
                int processingElementSize) {
            this.consumerThread = consumerThread;
            this.bufferSize = bufferSize;
            this.batchSize = batchSize;
            this.maxIdleTime = maxIdleTime;
            this.usedBufferSize = usedBufferSize;
            this.processingElementSize = processingElementSize;
        }

        public int getConsumerThread() {
            return consumerThread;
        }

        public void setConsumerThread(int consumerThread) {
            this.consumerThread = consumerThread;
        }

        public int getBufferSize() {
            return bufferSize;
        }

        public void setBufferSize(int bufferSize) {
            this.bufferSize = bufferSize;
        }

        public int getBatchSize() {
            return batchSize;
        }

        public void setBatchSize(int batchSize) {
            this.batchSize = batchSize;
        }

        public long getMaxIdleTime() {
            return maxIdleTime;
        }

        public void setMaxIdleTime(long maxIdleTime) {
            this.maxIdleTime = maxIdleTime;
        }

        public int getUsedBufferSize() {
            return usedBufferSize;
        }

        public void setUsedBufferSize(int usedBufferSize) {
            this.usedBufferSize = usedBufferSize;
        }

        public int getProcessingElementSize() {
            return processingElementSize;
        }

        public void setProcessingElementSize(int processingElementSize) {
            this.processingElementSize = processingElementSize;
        }

        @Override
        public String toString() {
            return "ConsumerStatus{" +
                    "consumerThread=" + consumerThread +
                    ", bufferSize=" + bufferSize +
                    ", batchSize=" + batchSize +
                    ", maxIdleTime=" + maxIdleTime +
                    ", usedBufferSize=" + usedBufferSize +
                    ", processingElementSize=" + processingElementSize +
                    '}';
        }
    }
}
