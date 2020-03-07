package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.sdk.bean.entity.FastJsonFilteredValue;
import com.dwarfeng.fdr.sdk.bean.entity.FastJsonPersistenceValue;
import com.dwarfeng.fdr.sdk.bean.entity.FastJsonRealtimeValue;
import com.dwarfeng.fdr.sdk.bean.entity.FastJsonTriggeredValue;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.handler.EventHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Component
public class EventHandlerImpl implements EventHandler {

    @Autowired
    private KafkaTemplate<String, Object> template;
    @Autowired
    private Mapper mapper;

    @Value("${kafka.topic.data_filtered}")
    private String dataFilteredTopic;
    @Value("${kafka.topic.data_triggered}")
    private String dataTriggeredTopic;
    @Value("${kafka.topic.realtime_updated}")
    private String realtimeUpdatedTopic;
    @Value("${kafka.topic.persistence_recorded}")
    private String persistenceRecordedTopic;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "kafkaTransactionManager")
    public void fireDataFiltered(FilteredValue filteredValue) {
        FastJsonFilteredValue fastJsonFilteredValue = mapper.map(filteredValue, FastJsonFilteredValue.class);
        template.send(dataFilteredTopic, fastJsonFilteredValue);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "kafkaTransactionManager")
    public void fireDataTriggered(@NotNull TriggeredValue triggeredValue) {
        FastJsonTriggeredValue fastJsonTriggeredValue = mapper.map(triggeredValue, FastJsonTriggeredValue.class);
        template.send(dataTriggeredTopic, fastJsonTriggeredValue);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "kafkaTransactionManager")
    public void fireRealtimeUpdated(RealtimeValue realtimeValue) {
        FastJsonRealtimeValue fastJsonRealtimeValue = mapper.map(realtimeValue, FastJsonRealtimeValue.class);
        template.send(realtimeUpdatedTopic, fastJsonRealtimeValue);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "kafkaTransactionManager")
    public void firePersistenceRecorded(PersistenceValue persistenceValue) {
        FastJsonPersistenceValue fastJsonPersistenceValue = mapper.map(persistenceValue, FastJsonPersistenceValue.class);
        template.send(persistenceRecordedTopic, fastJsonPersistenceValue);
    }
}
