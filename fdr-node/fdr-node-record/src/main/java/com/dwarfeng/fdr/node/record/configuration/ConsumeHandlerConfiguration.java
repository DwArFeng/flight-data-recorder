package com.dwarfeng.fdr.node.record.configuration;

import com.dwarfeng.fdr.impl.handler.ConsumeHandlerImpl;
import com.dwarfeng.fdr.impl.handler.comsumer.*;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.handler.ConsumeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;

@Configuration
public class ConsumeHandlerConfiguration {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private FilteredEventConsumer filteredEventConsumer;
    @Value("${consume_manager.filtered_event.consumer_thread}")
    private int filteredEventConsumerThread;
    @Value("${consume_manager.filtered_event.buffer_size}")
    private int filteredEventBufferSize;
    @Value("${consume_manager.filtered_event.batch_size}")
    private int filteredEventBatchSize;
    @Value("${consume_manager.filtered_event.max_idle_time}")
    private long filteredEventMaxIdleTime;

    @Bean
    public ConsumeHandler<FilteredValue> filteredEventConsumeHandler() {
        ConsumeHandlerImpl<FilteredValue> consumeHandler = new ConsumeHandlerImpl<>(
                threadPoolTaskExecutor,
                new ArrayList<>(),
                new ArrayList<>(),
                filteredEventConsumer,
                filteredEventConsumerThread
        );
        consumeHandler.setBufferParameters(filteredEventBufferSize, filteredEventBatchSize, filteredEventMaxIdleTime);
        return consumeHandler;
    }

    @Autowired
    private FilteredValueConsumer filteredValueConsumer;
    @Value("${consume_manager.filtered_value.consumer_thread}")
    private int filteredValueConsumerThread;
    @Value("${consume_manager.filtered_value.buffer_size}")
    private int filteredValueBufferSize;
    @Value("${consume_manager.filtered_value.batch_size}")
    private int filteredValueBatchSize;
    @Value("${consume_manager.filtered_value.max_idle_time}")
    private long filteredValueMaxIdleTime;

    @Bean
    public ConsumeHandler<FilteredValue> filteredValueConsumeHandler() {
        ConsumeHandlerImpl<FilteredValue> consumeHandler = new ConsumeHandlerImpl<>(
                threadPoolTaskExecutor,
                new ArrayList<>(),
                new ArrayList<>(),
                filteredValueConsumer,
                filteredValueConsumerThread
        );
        consumeHandler.setBufferParameters(filteredValueBufferSize, filteredValueBatchSize, filteredValueMaxIdleTime);
        return consumeHandler;
    }

    @Autowired
    private TriggeredEventConsumer triggeredEventConsumer;
    @Value("${consume_manager.triggered_event.consumer_thread}")
    private int triggeredEventConsumerThread;
    @Value("${consume_manager.triggered_event.buffer_size}")
    private int triggeredEventBufferSize;
    @Value("${consume_manager.triggered_event.batch_size}")
    private int triggeredEventBatchSize;
    @Value("${consume_manager.triggered_event.max_idle_time}")
    private long triggeredEventMaxIdleTime;

    @Bean
    public ConsumeHandler<TriggeredValue> triggeredEventConsumeHandler() {
        ConsumeHandlerImpl<TriggeredValue> consumeHandler = new ConsumeHandlerImpl<>(
                threadPoolTaskExecutor,
                new ArrayList<>(),
                new ArrayList<>(),
                triggeredEventConsumer,
                triggeredEventConsumerThread
        );
        consumeHandler.setBufferParameters(triggeredEventBufferSize, triggeredEventBatchSize, triggeredEventMaxIdleTime);
        return consumeHandler;
    }

    @Autowired
    private TriggeredValueConsumer triggeredValueConsumer;
    @Value("${consume_manager.triggered_value.consumer_thread}")
    private int triggeredValueConsumerThread;
    @Value("${consume_manager.triggered_value.buffer_size}")
    private int triggeredValueBufferSize;
    @Value("${consume_manager.triggered_value.batch_size}")
    private int triggeredValueBatchSize;
    @Value("${consume_manager.triggered_value.max_idle_time}")
    private long triggeredValueMaxIdleTime;

    @Bean
    public ConsumeHandler<TriggeredValue> triggeredValueConsumeHandler() {
        ConsumeHandlerImpl<TriggeredValue> consumeHandler = new ConsumeHandlerImpl<>(
                threadPoolTaskExecutor,
                new ArrayList<>(),
                new ArrayList<>(),
                triggeredValueConsumer,
                triggeredValueConsumerThread
        );
        consumeHandler.setBufferParameters(triggeredValueBufferSize, triggeredValueBatchSize, triggeredValueMaxIdleTime);
        return consumeHandler;
    }

    @Autowired
    private RealtimeEventConsumer realtimeEventConsumer;
    @Value("${consume_manager.realtime_event.consumer_thread}")
    private int realtimeEventConsumerThread;
    @Value("${consume_manager.realtime_event.buffer_size}")
    private int realtimeEventBufferSize;
    @Value("${consume_manager.realtime_event.batch_size}")
    private int realtimeEventBatchSize;
    @Value("${consume_manager.realtime_event.max_idle_time}")
    private long realtimeEventMaxIdleTime;

    @Bean
    public ConsumeHandler<RealtimeValue> realtimeEventConsumeHandler() {
        ConsumeHandlerImpl<RealtimeValue> consumeHandler = new ConsumeHandlerImpl<>(
                threadPoolTaskExecutor,
                new ArrayList<>(),
                new ArrayList<>(),
                realtimeEventConsumer,
                realtimeEventConsumerThread
        );
        consumeHandler.setBufferParameters(realtimeEventBufferSize, realtimeEventBatchSize, realtimeEventMaxIdleTime);
        return consumeHandler;
    }

    @Autowired
    private RealtimeValueConsumer realtimeValueConsumer;
    @Value("${consume_manager.realtime_value.consumer_thread}")
    private int realtimeValueConsumerThread;
    @Value("${consume_manager.realtime_value.buffer_size}")
    private int realtimeValueBufferSize;
    @Value("${consume_manager.realtime_value.batch_size}")
    private int realtimeValueBatchSize;
    @Value("${consume_manager.realtime_value.max_idle_time}")
    private long realtimeValueMaxIdleTime;

    @Bean
    public ConsumeHandler<RealtimeValue> realtimeValueConsumeHandler() {
        ConsumeHandlerImpl<RealtimeValue> consumeHandler = new ConsumeHandlerImpl<>(
                threadPoolTaskExecutor,
                new ArrayList<>(),
                new ArrayList<>(),
                realtimeValueConsumer,
                realtimeValueConsumerThread
        );
        consumeHandler.setBufferParameters(realtimeValueBufferSize, realtimeValueBatchSize, realtimeValueMaxIdleTime);
        return consumeHandler;
    }

    @Autowired
    private PersistenceEventConsumer persistenceEventConsumer;
    @Value("${consume_manager.persistence_event.consumer_thread}")
    private int persistenceEventConsumerThread;
    @Value("${consume_manager.persistence_event.buffer_size}")
    private int persistenceEventBufferSize;
    @Value("${consume_manager.persistence_event.batch_size}")
    private int persistenceEventBatchSize;
    @Value("${consume_manager.persistence_event.max_idle_time}")
    private long persistenceEventMaxIdleTime;

    @Bean
    public ConsumeHandler<PersistenceValue> persistenceEventConsumeHandler() {
        ConsumeHandlerImpl<PersistenceValue> consumeHandler = new ConsumeHandlerImpl<>(
                threadPoolTaskExecutor,
                new ArrayList<>(),
                new ArrayList<>(),
                persistenceEventConsumer,
                persistenceEventConsumerThread
        );
        consumeHandler.setBufferParameters(persistenceEventBufferSize, persistenceEventBatchSize, persistenceEventMaxIdleTime);
        return consumeHandler;
    }

    @Autowired
    private PersistenceValueConsumer persistenceValueConsumer;
    @Value("${consume_manager.persistence_value.consumer_thread}")
    private int persistenceValueConsumerThread;
    @Value("${consume_manager.persistence_value.buffer_size}")
    private int persistenceValueBufferSize;
    @Value("${consume_manager.persistence_value.batch_size}")
    private int persistenceValueBatchSize;
    @Value("${consume_manager.persistence_value.max_idle_time}")
    private long persistenceValueMaxIdleTime;

    @Bean
    public ConsumeHandler<PersistenceValue> persistenceValueConsumeHandler() {
        ConsumeHandlerImpl<PersistenceValue> consumeHandler = new ConsumeHandlerImpl<>(
                threadPoolTaskExecutor,
                new ArrayList<>(),
                new ArrayList<>(),
                persistenceValueConsumer,
                persistenceValueConsumerThread
        );
        consumeHandler.setBufferParameters(persistenceValueBufferSize, persistenceValueBatchSize, persistenceValueMaxIdleTime);
        return consumeHandler;
    }
}
