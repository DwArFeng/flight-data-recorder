package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.handler.RecordLocalCacheHandler.RecordContext;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 服务质量服务。
 *
 * @author DwArFeng
 * @since 1.2.0.a
 */
public interface RecordQosService extends Service {

    /**
     * 获取指定数据点的记录上下文。
     *
     * @param pointKey 指定数据点的记录上下文，或者是null。
     * @return 指定数据点的记录上下文。
     * @throws ServiceException 服务异常。
     */
    RecordContext getRecordContext(LongIdKey pointKey) throws ServiceException;

    /**
     * 清除本地缓存。
     *
     * @throws ServiceException 服务异常。
     */
    void clearLocalCache() throws ServiceException;

    /**
     * 获取指定消费者的消费者状态。
     *
     * @param consumerId 指定的消费者ID。
     * @return 消费者状态。
     * @throws ServiceException 服务异常。
     */
    ConsumerStatus getConsumerStatus(ConsumerId consumerId) throws ServiceException;

    /**
     * 设置指定消费之的参数。
     *
     * @param consumerId  指定的消费者ID。
     * @param bufferSize  缓冲器的大小。
     * @param batchSize   数据的批处理量。
     * @param maxIdleTime 最大空闲时间。
     * @param thread      消费者的线程数量。
     * @throws ServiceException 服务异常。
     */
    void setConsumerParameters(
            ConsumerId consumerId, Integer bufferSize, Integer batchSize, Long maxIdleTime, Integer thread)
            throws ServiceException;

    /**
     * 开启记录服务。
     *
     * @throws ServiceException 服务异常。
     */
    void startRecord() throws ServiceException;

    /**
     * 关闭记录服务。
     *
     * @throws ServiceException 服务异常。
     */
    void stopRecord() throws ServiceException;

    /**
     * 消费者ID。
     *
     * @author DwArFeng
     * @since 1.8.0
     */
    enum ConsumerId {

        EVENT_FILTERED("event", "filtered"),
        VALUE_FILTERED("value", "filtered"),
        EVENT_TRIGGERED("event", "triggered"),
        VALUE_TRIGGERED("value", "triggered"),
        EVENT_REALTIME("event", "realtime"),
        VALUE_REALTIME("value", "realtime"),
        EVENT_PERSISTENCE("event", "persistence"),
        VALUE_PERSISTENCE("value", "persistence"),

        ;
        private final String type;
        private final String label;

        ConsumerId(String type, String label) {
            this.type = type;
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return "ConsumerId{" +
                    "type='" + type + '\'' +
                    ", label='" + label + '\'' +
                    '}';
        }
    }

    /**
     * 消费者状态。
     *
     * @author DwArFeng
     * @since 1.8.0
     */
    class ConsumerStatus implements Bean {

        private static final long serialVersionUID = 5044732228551678000L;

        private int bufferSize;
        private int batchSize;
        private long maxIdleTime;
        private int thread;
        private boolean idle;

        public ConsumerStatus() {
        }

        public ConsumerStatus(int bufferSize, int batchSize, long maxIdleTime, int thread, boolean idle) {
            this.bufferSize = bufferSize;
            this.batchSize = batchSize;
            this.maxIdleTime = maxIdleTime;
            this.thread = thread;
            this.idle = idle;
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

        public int getThread() {
            return thread;
        }

        public void setThread(int thread) {
            this.thread = thread;
        }

        public boolean isIdle() {
            return idle;
        }

        public void setIdle(boolean idle) {
            this.idle = idle;
        }

        @Override
        public String toString() {
            return "ConsumerStatus{" +
                    "bufferSize=" + bufferSize +
                    ", batchSize=" + batchSize +
                    ", maxIdleTime=" + maxIdleTime +
                    ", thread=" + thread +
                    ", idle=" + idle +
                    '}';
        }
    }
}
