package com.dwarfeng.fdr.impl.handler.event.kafka.handler;

import com.dwarfeng.fdr.impl.handler.event.kafka.bean.entity.KafkaTriggeredValue;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.exception.EventException;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Component
@Validated
public class EventHandlerDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventHandlerDelegate.class);

    @Autowired
    private KafkaTemplate<String, Object> template;
    @Autowired
    private Mapper mapper;

    @Value("${kafka.topic.dataTriggered}")
    private String dataTriggeredTopic;

    @TimeAnalyse
    @Transactional(transactionManager = "kafkaTransactionManager")
    public void fireDataTriggered(@NotNull TriggeredValue triggeredValue) throws EventException {
        LOGGER.debug("将TriggeredValue映射为KafkaTriggeredValue " + triggeredValue.toString() + "...");
        KafkaTriggeredValue kafkaTriggeredValue = mapper.map(triggeredValue, KafkaTriggeredValue.class);
        LOGGER.debug("向Kafka发送事件 " + kafkaTriggeredValue.toString() + "...");
        template.send(dataTriggeredTopic, kafkaTriggeredValue);
    }
}
