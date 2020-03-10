package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.handler.EventHandler;
import com.dwarfeng.fdr.stack.handler.RealtimeEventConsumeHandler;
import com.dwarfeng.fdr.stack.service.RealtimeValueMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class RealtimeEventConsumeHandlerImpl extends AbstractConsumeHandler<RealtimeValue>
        implements RealtimeEventConsumeHandler {

    @Autowired
    private ConsumerProcessor consumerProcessor;

    @Autowired
    public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @Value("${consume_manager.realtime_event.consumer_thread}")
    public void consumerThread(int consumerThread) {
        this.consumerThread = consumerThread;
    }

    @Value("${consume_manager.realtime_event.buffer_size}")
    public void bufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    @Value("${consume_manager.realtime_event.batch_size}")
    public void batchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    @Value("${consume_manager.realtime_event.max_idle_time}")
    public void maxIdleTime(long maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    @PostConstruct
    public void init() {
        start();
    }

    @PreDestroy
    public void dispose() {
        stop();
    }

    @Override
    protected void doConsume(List<RealtimeValue> list) {
        consumerProcessor.doConsume(list);
    }

    @Component
    public static class ConsumerProcessor {

        private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerProcessor.class);

        @Autowired
        private EventHandler eventHandler;
        @Autowired
        private RealtimeValueMaintainService realtimeValueMaintainService;

        public void doConsume(List<RealtimeValue> list) {
            TimeMeasurer tm = new TimeMeasurer();
            tm.start();
            try {
                Map<LongIdKey, RealtimeValue> realtimeValueMap = new HashMap<>();
                for (RealtimeValue realtimeValue : list) {
                    if (realtimeValueMap.containsKey(realtimeValue.getKey())) {
                        int compareResult = realtimeValue.getHappenedDate()
                                .compareTo(realtimeValueMap.get(realtimeValue.getKey()).getHappenedDate());
                        if (compareResult > 0) {
                            realtimeValueMap.put(realtimeValue.getKey(), realtimeValue);
                        }
                    } else {
                        realtimeValueMap.put(realtimeValue.getKey(), realtimeValue);
                    }
                }
                for (RealtimeValue realtimeValue : realtimeValueMap.values()) {
                    RealtimeValue ifExists = realtimeValueMaintainService.getIfExists(realtimeValue.getKey());
                    if (Objects.isNull(ifExists)) {
                        eventHandler.fireRealtimeUpdated(realtimeValue);
                    } else {
                        int compareResult = realtimeValue.getHappenedDate().compareTo(ifExists.getHappenedDate());
                        if (compareResult > 0) {
                            eventHandler.fireRealtimeUpdated(realtimeValue);
                        }
                    }
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
