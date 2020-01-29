package com.dwarfeng.fdr.impl.handler.event.kafka.handler;

import com.dwarfeng.fdr.sdk.bean.entity.FastJsonTriggeredValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.exception.EventException;
import com.dwarfeng.fdr.stack.handler.EventHandler;
import com.dwarfeng.subgrade.sdk.interceptor.BehaviorAnalyse;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
public class EventHandlerImpl implements EventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventHandlerImpl.class);

    @Autowired
    private KafkaTemplate<String, Object> template;
    @Autowired
    private Mapper mapper;

    @Value("${kafka.topic.dataTriggered}")
    private String dataTriggeredTopic;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "kafkaTransactionManager")
    public void fireDataTriggered(@NotNull TriggeredValue triggeredValue) throws EventException {
        FastJsonTriggeredValue fastJsonTriggeredValue = mapper.map(triggeredValue, FastJsonTriggeredValue.class);
        template.send(dataTriggeredTopic, fastJsonTriggeredValue);
    }
}