package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.handler.EventHandler;
import com.dwarfeng.fdr.stack.handler.TriggeredEventConsumeHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Component
public class TriggeredEventConsumeHandlerImpl extends AbstractConsumeHandler<TriggeredValue>
        implements TriggeredEventConsumeHandler {

    @Autowired
    private ConsumerProcessor consumerProcessor;

    @Autowired
    public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @Value("${consume_manager.triggered_event.consumer_thread}")
    public void consumerThread(int consumerThread) {
        this.consumerThread = consumerThread;
    }

    @Value("${consume_manager.triggered_event.buffer_size}")
    public void bufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    @Value("${consume_manager.triggered_event.batch_size}")
    public void batchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    @Value("${consume_manager.triggered_event.max_idle_time}")
    public void maxIdleTime(long maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    @PostConstruct
    public void init() throws HandlerException {
        start();
    }

    @PreDestroy
    public void dispose() throws HandlerException {
        stop();
    }

    @Override
    protected void doConsume(List<TriggeredValue> list) {
        consumerProcessor.doConsume(list);
    }

    @Component
    public static class ConsumerProcessor {

        private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerProcessor.class);

        @Autowired
        private EventHandler eventHandler;

        public void doConsume(List<TriggeredValue> list) {
            TimeMeasurer tm = new TimeMeasurer();
            tm.start();
            try {
                for (TriggeredValue triggeredValue : list) {
                    eventHandler.fireDataTriggered(triggeredValue);
                }
            } catch (Exception e) {
                LOGGER.error("记录实时数据时发生异常, 最多 " + list.size() + " 个数据信息丢失", e);
                list.forEach(realtimeValue -> LOGGER.debug(realtimeValue + ""));
            } finally {
                tm.stop();
                LOGGER.info("消费者处理了 " + list.size() + " 条数据, 共用时 " + tm.getTimeMs() + " 毫秒");
            }
        }
    }
}
