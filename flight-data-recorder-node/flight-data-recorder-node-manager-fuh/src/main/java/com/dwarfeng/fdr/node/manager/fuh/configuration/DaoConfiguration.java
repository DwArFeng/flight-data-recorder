package com.dwarfeng.fdr.node.manager.fuh.configuration;

import com.dwarfeng.fdr.impl.dao.fuh.bean.entity.*;
import com.dwarfeng.fdr.impl.dao.fuh.dao.preset.*;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernatePresetLookupDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;

@Configuration
public class DaoConfiguration {

    @Autowired
    private HibernateTemplate template;
    @Autowired
    private Mapper mapper;

    @Autowired
    private CategoryPresetCriteriaMaker categoryPresetCriteriaMaker;
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

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, Category, HibernateCategory> categoryHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(Category.class, HibernateCategory.class, mapper),
                HibernateCategory.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<Category, HibernateCategory> categoryHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new DozerBeanTransformer<>(Category.class, HibernateCategory.class, mapper),
                HibernateCategory.class,
                categoryPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, FilteredValue, HibernateFilteredValue> filteredValueHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(FilteredValue.class, HibernateFilteredValue.class, mapper),
                HibernateFilteredValue.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<FilteredValue, HibernateFilteredValue> filteredValueHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new DozerBeanTransformer<>(FilteredValue.class, HibernateFilteredValue.class, mapper),
                HibernateFilteredValue.class,
                filteredValuePresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, FilterInfo, HibernateFilterInfo> filterInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(FilterInfo.class, HibernateFilterInfo.class, mapper),
                HibernateFilterInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<FilterInfo, HibernateFilterInfo> filterInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new DozerBeanTransformer<>(FilterInfo.class, HibernateFilterInfo.class, mapper),
                HibernateFilterInfo.class,
                filterInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, PersistenceValue, HibernatePersistenceValue> persistenceValueHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(PersistenceValue.class, HibernatePersistenceValue.class, mapper),
                HibernatePersistenceValue.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<PersistenceValue, HibernatePersistenceValue> persistenceValueHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new DozerBeanTransformer<>(PersistenceValue.class, HibernatePersistenceValue.class, mapper),
                HibernatePersistenceValue.class,
                persistenceValuePresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, Point, HibernatePoint> pointHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(Point.class, HibernatePoint.class, mapper),
                HibernatePoint.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<Point, HibernatePoint> pointHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new DozerBeanTransformer<>(Point.class, HibernatePoint.class, mapper),
                HibernatePoint.class,
                pointPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, RealtimeValue, HibernateRealtimeValue> realtimeValueHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(RealtimeValue.class, HibernateRealtimeValue.class, mapper),
                HibernateRealtimeValue.class
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, TriggeredValue, HibernateTriggeredValue> triggeredValueHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(TriggeredValue.class, HibernateTriggeredValue.class, mapper),
                HibernateTriggeredValue.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<TriggeredValue, HibernateTriggeredValue> triggeredValueHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new DozerBeanTransformer<>(TriggeredValue.class, HibernateTriggeredValue.class, mapper),
                HibernateTriggeredValue.class,
                triggeredValuePresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, TriggerInfo, HibernateTriggerInfo> triggerInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(TriggerInfo.class, HibernateTriggerInfo.class, mapper),
                HibernateTriggerInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<TriggerInfo, HibernateTriggerInfo> triggerInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new DozerBeanTransformer<>(TriggerInfo.class, HibernateTriggerInfo.class, mapper),
                HibernateTriggerInfo.class,
                triggerInfoPresetCriteriaMaker
        );
    }
}
