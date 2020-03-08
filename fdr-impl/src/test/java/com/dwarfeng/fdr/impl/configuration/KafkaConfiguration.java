package com.dwarfeng.fdr.impl.configuration;

import com.dwarfeng.subgrade.sdk.kafka.serialize.FastJsonKafkaDeserializer;
import com.dwarfeng.subgrade.sdk.kafka.serialize.FastJsonKafkaSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@EnableTransactionManagement
public class KafkaConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConfiguration.class);

    @Value("${kafka.producer.bootstrap_servers}")
    private String producerBootstrapServers;
    @Value("${kafka.producer.retries}")
    private int retries;
    @Value("${kafka.producer.linger}")
    private long linger;
    @Value("${kafka.producer.buffer_memory}")
    private long bufferMemory;
    @Value("${kafka.producer.batch_size}")
    private int batchSize;
    @Value("${kafka.producer.acks}")
    private String acks;
    @Value("${kafka.producer.transaction_prefix}")
    private String transactionPrefix;

    @Bean("producerProperties")
    public Map<String, Object> producerProperties() {
        LOGGER.info("配置Kafka生产者属性...");
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerBootstrapServers);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        props.put(ProducerConfig.ACKS_CONFIG, acks);
        LOGGER.debug("Kafka生产者属性配置完成...");
        return props;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        LOGGER.info("配置Kafka生产者工厂...");
        Map<String, Object> properties = producerProperties();
        DefaultKafkaProducerFactory<String, Object> factory = new DefaultKafkaProducerFactory<>(properties);
        factory.setTransactionIdPrefix(transactionPrefix);
        factory.setKeySerializer(new StringSerializer());
        factory.setValueSerializer(new FastJsonKafkaSerializer<>());
        LOGGER.debug("Kafka生产者工厂配置完成");
        return factory;
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        LOGGER.info("生成KafkaTemplate...");
        ProducerFactory<String, Object> producerFactory = producerFactory();
        KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<>(producerFactory, true);
        LOGGER.debug("KafkaTemplate生成完成...");
        return kafkaTemplate;
    }

    @Bean("kafkaTransactionManager")
    public KafkaTransactionManager<String, Object> kafkaTransactionManager() {
        LOGGER.info("生成KafkaTransactionManager...");
        ProducerFactory<String, Object> producerFactory = producerFactory();
        LOGGER.debug("KafkaTransactionManager生成完成...");
        return new KafkaTransactionManager<>(producerFactory);
    }

    @Value("${kafka.consumer.bootstrap_servers}")
    private String consumerBootstrapServers;
    @Value("${kafka.consumer.enable_auto_commit}")
    private boolean enableAutoCommit;
    @Value("${kafka.consumer.auto_commit_interval_ms}")
    private int autoCommitIntervalMs;
    @Value("${kafka.consumer.session_timeout_ms}")
    private int sessionTimeoutMs;
    @Value("${kafka.consumer.group}")
    private String group;
    @Value("${kafka.consumer.auto_offset_reset}")
    private String autoOffsetReset;

    @Bean("consumerProperties")
    public Map<String, Object> consumerProperties() {
        LOGGER.info("配置Kafka消费者属性...");
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerBootstrapServers);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitIntervalMs);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, group);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        LOGGER.debug("Kafka消费者属性配置完成...");
        return props;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        LOGGER.info("配置Kafka消费者工厂...");
        Map<String, Object> properties = consumerProperties();
        DefaultKafkaConsumerFactory<String, Object> factory = new DefaultKafkaConsumerFactory<>(properties);
        factory.setKeyDeserializer(new StringDeserializer());
        factory.setValueDeserializer(new FastJsonKafkaDeserializer<>(Object.class));
        LOGGER.debug("Kafka消费者工厂配置完成");
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory() {
        LOGGER.info("配置Kafka侦听容器工厂...");
        ConsumerFactory<String, Object> consumerFactory = consumerFactory();
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(2);
        factory.getContainerProperties().setPollTimeout(3000);
        LOGGER.info("配置Kafka侦听容器工厂...");
        return factory;
    }
}