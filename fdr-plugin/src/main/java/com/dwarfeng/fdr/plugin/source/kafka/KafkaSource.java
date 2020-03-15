package com.dwarfeng.fdr.plugin.source.kafka;

import com.dwarfeng.dutil.develop.backgr.AbstractTask;
import com.dwarfeng.fdr.stack.handler.RecordControlHandler;
import com.dwarfeng.fdr.stack.service.RecordService;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Kafka Source
 *
 * @author DwArFeng
 * @since 1.3.1
 */
@Component
public class KafkaSource implements RecordControlHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PollTask.class);

    @Autowired
    private KafkaConsumerFactory kafkaConsumerFactory;
    @Autowired
    private ThreadPoolTaskExecutor executor;
    @Autowired
    AsyncManager asyncManager;

    @Value("${source.kafka.topic}")
    private String dataInfoTopic;
    @Value("${source.kafka.consumer_poll_duration}")
    private long consumerPollDuration;

    private final Lock lock = new ReentrantLock();
    private PollTask pollTask = null;
    private boolean onlineFlag = false;

    @PostConstruct
    public void init() {
        online();
    }

    @PreDestroy
    public void dispose() {
        offline();
    }


    @Override
    public void online() {
        lock.lock();
        try {
            if (!onlineFlag) {
                pollTask = new PollTask();
                executor.execute(pollTask);
                onlineFlag = true;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void offline() {
        lock.lock();
        try {
            if (onlineFlag) {
                pollTask.shutdown();
                pollTask = null;
                onlineFlag = false;
            }
        } finally {
            lock.unlock();
        }
    }


    @Component
    public static class AsyncManager {

        private static final Logger LOGGER = LoggerFactory.getLogger(AsyncManager.class);

        @Autowired
        RecordService recordService;

        @Async
        @BehaviorAnalyse
        public void record(String message) {
            try {
                recordService.record(message);
            } catch (Exception e) {
                LOGGER.warn("记录数据信息时发生异常: " + message);
                LOGGER.warn("异常信息如下", e);
            }
        }
    }

    private class PollTask extends AbstractTask {

        KafkaConsumer<String, String> kafkaConsumer;
        private boolean runningFlag = true;

        @Override
        protected void todo() {
            try {
                kafkaConsumer = kafkaConsumerFactory.newKafkaConsumer();
                kafkaConsumer.subscribe(Collections.singleton(dataInfoTopic));
                while (isRunning()) {
                    ConsumerRecords<String, String> consumerRecords =
                            kafkaConsumer.poll(Duration.ofMillis(consumerPollDuration));
                    for (ConsumerRecord<String, String> record : consumerRecords.records(dataInfoTopic)) {
                        asyncManager.record(record.value());
                    }
                }
                kafkaConsumer.unsubscribe();
            } catch (Exception e) {
                LOGGER.warn("PollTask执行过程中出现异常, 任务将退出, KafkaSource插件将失效, 异常信息如下:", e);
            }
        }

        public void shutdown() {
            runningFlag = false;
        }

        private boolean isRunning() {
            KafkaSource.this.lock.lock();
            try {
                return runningFlag;
            } finally {
                KafkaSource.this.lock.unlock();
            }
        }
    }
}
