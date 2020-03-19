package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.handler.PersistenceValueConsumeHandler;
import com.dwarfeng.fdr.stack.service.PersistenceValueMaintainService;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersistenceValueConsumeHandlerImpl extends AbstractConsumeHandler<PersistenceValue>
        implements PersistenceValueConsumeHandler {

    @Autowired
    private ConsumerProcessor consumerProcessor;

    @Autowired
    public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @Value("${consume_manager.persistence_value.consumer_thread}")
    public void consumerThread(int consumerThread) {
        this.consumerThread = consumerThread;
    }

    @Value("${consume_manager.persistence_value.buffer_size}")
    public void bufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    @Value("${consume_manager.persistence_value.batch_size}")
    public void batchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    @Value("${consume_manager.persistence_value.max_idle_time}")
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
    protected void doConsume(List<PersistenceValue> list) {
        consumerProcessor.doConsume(list);
    }

    @Component
    public static class ConsumerProcessor {

        private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerProcessor.class);

        @Autowired
        private PersistenceValueMaintainService realtimeValueMaintainService;

        public void doConsume(List<PersistenceValue> list) {
            TimeMeasurer tm = new TimeMeasurer();
            tm.start();
            try {
                try {
                    realtimeValueMaintainService.batchInsert(list);
                    return;
                } catch (Exception e) {
                    LOGGER.warn("数据插入失败, 试图使用不同的策略进行插入: 插入或更新", e);
                }

                try {
                    realtimeValueMaintainService.batchInsertOrUpdate(list);
                    return;
                } catch (Exception e) {
                    LOGGER.warn("数据插入失败, 试图使用不同的策略进行插入: 逐条插入", e);
                }

                List<PersistenceValue> failedList = new ArrayList<>();

                for (PersistenceValue persistenceValue : list) {
                    try {
                        realtimeValueMaintainService.insertOrUpdate(persistenceValue);
                    } catch (Exception e) {
                        LOGGER.error("数据插入失败, 放弃对数据的插入: " + persistenceValue, e);
                        failedList.add(persistenceValue);
                    }
                }

                if (!failedList.isEmpty()) {
                    LOGGER.error("记录实时数据时发生异常, 最多 " + failedList.size() + " 个数据信息丢失");
                    failedList.forEach(realtimeValue -> LOGGER.debug(realtimeValue + ""));
                }
            } finally {
                tm.stop();
                LOGGER.info("消费者处理了 " + list.size() + " 条数据, 共用时 " + tm.getTimeMs() + " 毫秒");
            }
        }
    }
}
