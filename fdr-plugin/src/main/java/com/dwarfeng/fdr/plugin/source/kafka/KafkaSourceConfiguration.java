package com.dwarfeng.fdr.plugin.source.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaSourceConfiguration {

    @Value("${source.kafka.bootstrap_servers}")
    private String bootstrapServers;
    @Value("${source.kafka.group_id}")
    private String groupId;
    @Value("${source.kafka.auto_commit_interval_ms}")
    private int autoCommitIntervalMs;
    @Value("${source.kafka.session_timeout_ms}")
    private String sessionTimeoutMs;

    @Bean
    public Map<String, Object> properties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("group.id", groupId);
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", autoCommitIntervalMs);
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", sessionTimeoutMs);
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return properties;
    }

    @Bean
    public KafkaConsumerFactory kafkaConsumerFactory() {
        Map<String, Object> properties = properties();
        return new KafkaConsumerFactory(properties);
    }
}
