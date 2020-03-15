package com.dwarfeng.fdr.plugin.source.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Map;

/**
 * Kafka消费者工厂。
 *
 * @author DwArFeng
 * @since 1.2.1
 */
public class KafkaConsumerFactory {

    private Map<String, Object> properties;

    public KafkaConsumerFactory(Map<String, Object> properties) {
        this.properties = properties;
    }

    public KafkaConsumer<String, String> newKafkaConsumer() {
        return new KafkaConsumer<>(properties);
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
