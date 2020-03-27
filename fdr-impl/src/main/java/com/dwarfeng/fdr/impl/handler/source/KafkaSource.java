package com.dwarfeng.fdr.impl.handler.source;

import com.dwarfeng.fdr.impl.handler.Source;
import com.dwarfeng.fdr.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.fdr.stack.service.RecordService;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class KafkaSource implements Source {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSource.class);

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private KafkaListenerEndpointRegistry registry;
    @Autowired
    private RecordService recordService;

    @Value("${source.kafka.listener_id}")
    private String listenerId;

    private final Lock lock = new ReentrantLock();

    @Override
    public boolean isOnline() throws HandlerException {
        lock.lock();
        try {
            MessageListenerContainer listenerContainer = registry.getListenerContainer(listenerId);
            if (Objects.isNull(listenerContainer)) {
                throw new HandlerException("找不到 kafka listener container " + listenerId);
            }
            if (!listenerContainer.isRunning()) {
                return false;
            }
            return !listenerContainer.isContainerPaused();
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void online() throws HandlerException {
        lock.lock();
        try {
            LOGGER.info("Kafka source 上线...");
            MessageListenerContainer listenerContainer = registry.getListenerContainer(listenerId);
            if (Objects.isNull(listenerContainer)) {
                throw new HandlerException("找不到 kafka listener container " + listenerId);
            }
            //判断监听容器是否启动，未启动则将其启动
            if (!listenerContainer.isRunning()) {
                listenerContainer.start();
            }
            listenerContainer.resume();
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void offline() throws HandlerException {
        lock.lock();
        try {
            LOGGER.info("Kafka source 下线...");
            MessageListenerContainer listenerContainer = registry.getListenerContainer(listenerId);
            if (Objects.isNull(listenerContainer)) {
                throw new HandlerException("找不到 kafka listener container " + listenerId);
            }
            listenerContainer.pause();
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    @KafkaListener(id = "${source.kafka.listener_id}", containerFactory = "kafkaSource.kafkaListenerContainerFactory",
            topics = "${source.kafka.listener_topic}")
    public void handleDataInfo(String dataInfo, Acknowledgment ack) {
        try {
            recordService.record(dataInfo);
            ack.acknowledge();
        } catch (ServiceException e) {
            if (e.getCode().getCode() == ServiceExceptionCodes.RECORD_HANDLER_DISABLED.getCode()) {
                LOGGER.warn("记录处理器被禁用， 消息 " + dataInfo + " 不会被提交", e);
            } else {
                LOGGER.warn("记录处理器无法处理, 消息 " + dataInfo + " 将会被忽略", e);
                ack.acknowledge();
            }
        }
    }

    @Configuration
    @EnableKafka
    public static class KafkaSourceConfiguration {

        private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSourceConfiguration.class);

        @Value("${source.kafka.bootstrap_servers}")
        private String consumerBootstrapServers;
        @Value("${source.kafka.session_timeout_ms}")
        private int sessionTimeoutMs;
        @Value("${source.kafka.group}")
        private String group;
        @Value("${source.kafka.auto_offset_reset}")
        private String autoOffsetReset;
        @Value("${source.kafka.concurrency}")
        private int concurrency;
        @Value("${source.kafka.poll_timeout}")
        private int pollTimeout;
        @Value("${source.kafka.auto_startup}")
        private boolean autoStartup;

        @Bean("kafkaSource.consumerProperties")
        public Map<String, Object> consumerProperties() {
            LOGGER.info("配置Kafka消费者属性...");
            Map<String, Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerBootstrapServers);
            // 本实例使用ack手动提交，因此禁止自动提交的功能。
            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
            props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);
            props.put(ConsumerConfig.GROUP_ID_CONFIG, group);
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
            LOGGER.debug("Kafka消费者属性配置完成...");
            return props;
        }

        @Bean("kafkaSource.consumerFactory")
        public ConsumerFactory<String, String> consumerFactory() {
            LOGGER.info("配置Kafka消费者工厂...");
            Map<String, Object> properties = consumerProperties();
            DefaultKafkaConsumerFactory<String, String> factory = new DefaultKafkaConsumerFactory<>(properties);
            factory.setKeyDeserializer(new StringDeserializer());
            factory.setValueDeserializer(new StringDeserializer());
            LOGGER.debug("Kafka消费者工厂配置完成");
            return factory;
        }

        @Bean("kafkaSource.kafkaListenerContainerFactory")
        public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
            LOGGER.info("配置Kafka侦听容器工厂...");
            ConsumerFactory<String, String> consumerFactory = consumerFactory();
            ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory);
            factory.setConcurrency(concurrency);
            factory.getContainerProperties().setPollTimeout(pollTimeout);
            factory.setAutoStartup(autoStartup);
            // 配置ACK模式为手动立即提交。
            factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
            LOGGER.info("配置Kafka侦听容器工厂...");
            return factory;
        }
    }
}
