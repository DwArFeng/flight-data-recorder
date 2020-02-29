package com.dwarfeng.fdr.node.fuhconf;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
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
    private String bootstrapServers;
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
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.dwarfeng.fdr.sdk.serialize.FastJsonKafkaSerializer");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
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
        DefaultKafkaProducerFactory<String, Object> defaultKafkaProducerFactory = new DefaultKafkaProducerFactory<>(properties);
        defaultKafkaProducerFactory.setTransactionIdPrefix(transactionPrefix);
        LOGGER.debug("Kafka生产者工厂配置完成");
        return defaultKafkaProducerFactory;
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
}