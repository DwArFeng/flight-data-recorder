package com.dwarfeng.fdr.impl.fuhconf;

import com.dwarfeng.fdr.sdk.bean.entity.*;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.impl.cache.RedisBatchBaseCache;
import com.dwarfeng.subgrade.impl.cache.RedisKeyListCache;
import com.dwarfeng.subgrade.sdk.redis.formatter.LongIdStringKeyFormatter;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class CacheConfiguration {

    @Autowired
    private RedisTemplate<String, ?> template;
    @Autowired
    private Mapper mapper;

    @Value("${cache.prefix.entity.filtered_value}")
    private String filteredValuePrefix;
    @Value("${cache.prefix.entity.filter_info}")
    private String filterInfoPrefix;
    @Value("${cache.prefix.entity.persistence_value}")
    private String persistenceValuePrefix;
    @Value("${cache.prefix.entity.point}")
    private String pointPrefix;
    @Value("${cache.prefix.entity.realtime_value}")
    private String realtimeValuePrefix;
    @Value("${cache.prefix.entity.triggered_value}")
    private String triggeredValuePrefix;
    @Value("${cache.prefix.entity.trigger_info}")
    private String triggerInfoPrefix;

    @Bean
    public RedisBatchBaseCache<LongIdKey, FilteredValue, FastJsonFilteredValue> filteredValueRedisBatchBaseCache() {
        //noinspection unchecked
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonFilteredValue>) template,
                new LongIdStringKeyFormatter(filteredValuePrefix),
                new DozerBeanTransformer<>(FilteredValue.class, FastJsonFilteredValue.class, mapper)
        );
    }

    @Bean
    public RedisBatchBaseCache<LongIdKey, FilterInfo, FastJsonFilterInfo> filterInfoRedisBatchBaseCache() {
        //noinspection unchecked
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonFilterInfo>) template,
                new LongIdStringKeyFormatter(filterInfoPrefix),
                new DozerBeanTransformer<>(FilterInfo.class, FastJsonFilterInfo.class, mapper)
        );
    }

    @Bean
    public RedisBatchBaseCache<LongIdKey, PersistenceValue, FastJsonPersistenceValue> persistenceValueRedisBatchBaseCache() {
        //noinspection unchecked
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonPersistenceValue>) template,
                new LongIdStringKeyFormatter(persistenceValuePrefix),
                new DozerBeanTransformer<>(PersistenceValue.class, FastJsonPersistenceValue.class, mapper)
        );
    }

    @Bean
    public RedisBatchBaseCache<LongIdKey, Point, FastJsonPoint> pointRedisBatchBaseCache() {
        //noinspection unchecked
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonPoint>) template,
                new LongIdStringKeyFormatter(pointPrefix),
                new DozerBeanTransformer<>(Point.class, FastJsonPoint.class, mapper)
        );
    }

    @Bean
    public RedisBatchBaseCache<LongIdKey, RealtimeValue, FastJsonRealtimeValue> realtimeValueRedisBatchBaseCache() {
        //noinspection unchecked
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonRealtimeValue>) template,
                new LongIdStringKeyFormatter(realtimeValuePrefix),
                new DozerBeanTransformer<>(RealtimeValue.class, FastJsonRealtimeValue.class, mapper)
        );
    }

    @Bean
    public RedisBatchBaseCache<LongIdKey, TriggeredValue, FastJsonTriggeredValue> triggeredValueRedisBatchBaseCache() {
        //noinspection unchecked
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonTriggeredValue>) template,
                new LongIdStringKeyFormatter(triggeredValuePrefix),
                new DozerBeanTransformer<>(TriggeredValue.class, FastJsonTriggeredValue.class, mapper)
        );
    }

    @Bean
    public RedisBatchBaseCache<LongIdKey, TriggerInfo, FastJsonTriggerInfo> triggerInfoRedisBatchBaseCache() {
        //noinspection unchecked
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonTriggerInfo>) template,
                new LongIdStringKeyFormatter(triggerInfoPrefix),
                new DozerBeanTransformer<>(TriggerInfo.class, FastJsonTriggerInfo.class, mapper)
        );
    }

    @Bean
    public RedisKeyListCache<LongIdKey, FilterInfo, FastJsonFilterInfo> filterInfoEnabledRedisKeyListCache() {
        //noinspection unchecked
        return new RedisKeyListCache<>(
                (RedisTemplate<String, FastJsonFilterInfo>) template,
                new LongIdStringKeyFormatter(filterInfoPrefix),
                new DozerBeanTransformer<>(FilterInfo.class, FastJsonFilterInfo.class, mapper)
        );
    }

    @Bean
    public RedisKeyListCache<LongIdKey, TriggerInfo, FastJsonTriggerInfo> triggerInfoEnabledRedisKeyListCache() {
        //noinspection unchecked
        return new RedisKeyListCache<>(
                (RedisTemplate<String, FastJsonTriggerInfo>) template,
                new LongIdStringKeyFormatter(triggerInfoPrefix),
                new DozerBeanTransformer<>(TriggerInfo.class, FastJsonTriggerInfo.class, mapper)
        );
    }
}
