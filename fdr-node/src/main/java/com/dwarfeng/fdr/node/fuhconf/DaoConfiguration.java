package com.dwarfeng.fdr.node.fuhconf;

import com.dwarfeng.fdr.impl.fuhbean.entity.*;
import com.dwarfeng.fdr.impl.fuhdao.preset.*;
import com.dwarfeng.fdr.sdk.bean.entity.FastJsonRealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateEntireLookupDao;
import com.dwarfeng.subgrade.impl.dao.HibernatePresetLookupDao;
import com.dwarfeng.subgrade.impl.dao.RedisBatchBaseDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.redis.formatter.LongIdStringKeyFormatter;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;

@Configuration
public class DaoConfiguration {

    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Autowired
    private RedisTemplate<String, ?> redisTemplate;
    @Autowired
    private Mapper mapper;

    @Autowired
    private FilteredValuePresetCriteriaMaker filteredValuePresetCriteriaMaker;
    @Autowired
    private FilterInfoPresetCriteriaMaker filterInfoPresetCriteriaMaker;
    @Autowired
    private PersistenceValuePresetCriteriaMaker persistenceValuePresetCriteriaMaker;
    @Autowired
    private PointPresetCriteriaMaker pointPresetCriteriaMaker;
    @Autowired
    private TriggeredValuePresetCriteriaMaker triggeredValuePresetCriteriaMaker;
    @Autowired
    private TriggerInfoPresetCriteriaMaker triggerInfoPresetCriteriaMaker;

    @Value("${cache.prefix.entity.realtime_value}")
    private String realtimeValuePrefix;
    @Value("${redis.dbkey.realtime_value}")
    private String realtimeValueDbKey;

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, FilteredValue, HibernateFilteredValue> filteredValueHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(FilteredValue.class, HibernateFilteredValue.class, mapper),
                HibernateFilteredValue.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<FilteredValue, HibernateFilteredValue> filteredValueHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(FilteredValue.class, HibernateFilteredValue.class, mapper),
                HibernateFilteredValue.class,
                filteredValuePresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, FilterInfo, HibernateFilterInfo> filterInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(FilterInfo.class, HibernateFilterInfo.class, mapper),
                HibernateFilterInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<FilterInfo, HibernateFilterInfo> filterInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(FilterInfo.class, HibernateFilterInfo.class, mapper),
                HibernateFilterInfo.class,
                filterInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, PersistenceValue, HibernatePersistenceValue> persistenceValueHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(PersistenceValue.class, HibernatePersistenceValue.class, mapper),
                HibernatePersistenceValue.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<PersistenceValue, HibernatePersistenceValue> persistenceValueHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(PersistenceValue.class, HibernatePersistenceValue.class, mapper),
                HibernatePersistenceValue.class,
                persistenceValuePresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, Point, HibernatePoint> pointHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(Point.class, HibernatePoint.class, mapper),
                HibernatePoint.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<Point, HibernatePoint> pointHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(Point.class, HibernatePoint.class, mapper),
                HibernatePoint.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<Point, HibernatePoint> pointHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(Point.class, HibernatePoint.class, mapper),
                HibernatePoint.class,
                pointPresetCriteriaMaker
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseDao<LongIdKey, RealtimeValue, FastJsonRealtimeValue> realtimeValueRedisBatchBaseDao() {
        return new RedisBatchBaseDao<>(
                (RedisTemplate<String, FastJsonRealtimeValue>) redisTemplate,
                new LongIdStringKeyFormatter(realtimeValuePrefix),
                new DozerBeanTransformer<>(RealtimeValue.class, FastJsonRealtimeValue.class, mapper),
                realtimeValueDbKey
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, TriggeredValue, HibernateTriggeredValue> triggeredValueHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(TriggeredValue.class, HibernateTriggeredValue.class, mapper),
                HibernateTriggeredValue.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<TriggeredValue, HibernateTriggeredValue> triggeredValueHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(TriggeredValue.class, HibernateTriggeredValue.class, mapper),
                HibernateTriggeredValue.class,
                triggeredValuePresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, TriggerInfo, HibernateTriggerInfo> triggerInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(TriggerInfo.class, HibernateTriggerInfo.class, mapper),
                HibernateTriggerInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<TriggerInfo, HibernateTriggerInfo> triggerInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(TriggerInfo.class, HibernateTriggerInfo.class, mapper),
                HibernateTriggerInfo.class,
                triggerInfoPresetCriteriaMaker
        );
    }
}
