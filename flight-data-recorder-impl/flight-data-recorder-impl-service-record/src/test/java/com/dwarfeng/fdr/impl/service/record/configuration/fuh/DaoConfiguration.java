package com.dwarfeng.fdr.impl.service.record.configuration.fuh;

import com.dwarfeng.fdr.impl.dao.fuh.bean.entity.*;
import com.dwarfeng.fdr.impl.dao.fuh.dao.preset.*;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernatePresetDeleteDao;
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
    public HibernatePresetDeleteDao<LongIdKey, Category, HibernateCategory> categoryHibernatePresetDeleteDao() {
        return new HibernatePresetDeleteDao<>(
                template,
                new DozerBeanTransformer<>(Category.class, HibernateCategory.class, mapper),
                HibernateCategory.class,
                new CategoryPresetCriteriaMaker()
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
    public HibernatePresetDeleteDao<LongIdKey, FilteredValue, HibernateFilteredValue> filteredValueHibernatePresetDeleteDao() {
        return new HibernatePresetDeleteDao<>(
                template,
                new DozerBeanTransformer<>(FilteredValue.class, HibernateFilteredValue.class, mapper),
                HibernateFilteredValue.class,
                new FilteredValuePresetCriteriaMaker()
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
    public HibernatePresetDeleteDao<LongIdKey, FilterInfo, HibernateFilterInfo> filterInfoHibernatePresetDeleteDao() {
        return new HibernatePresetDeleteDao<>(
                template,
                new DozerBeanTransformer<>(FilterInfo.class, HibernateFilterInfo.class, mapper),
                HibernateFilterInfo.class,
                new FilterInfoPresetCriteriaMaker()
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
    public HibernatePresetDeleteDao<LongIdKey, PersistenceValue, HibernatePersistenceValue> persistenceValueHibernatePresetDeleteDao() {
        return new HibernatePresetDeleteDao<>(
                template,
                new DozerBeanTransformer<>(PersistenceValue.class, HibernatePersistenceValue.class, mapper),
                HibernatePersistenceValue.class,
                new PersistenceValuePresetCriteriaMaker()
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
    public HibernatePresetDeleteDao<LongIdKey, Point, HibernatePoint> pointHibernatePresetDeleteDao() {
        return new HibernatePresetDeleteDao<>(
                template,
                new DozerBeanTransformer<>(Point.class, HibernatePoint.class, mapper),
                HibernatePoint.class,
                new PointPresetCriteriaMaker()
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
    public HibernatePresetDeleteDao<LongIdKey, TriggeredValue, HibernateTriggeredValue> triggeredValueHibernatePresetDeleteDao() {
        return new HibernatePresetDeleteDao<>(
                template,
                new DozerBeanTransformer<>(TriggeredValue.class, HibernateTriggeredValue.class, mapper),
                HibernateTriggeredValue.class,
                new TriggeredValuePresetCriteriaMaker()
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
    public HibernatePresetDeleteDao<LongIdKey, TriggerInfo, HibernateTriggerInfo> triggerInfoHibernatePresetDeleteDao() {
        return new HibernatePresetDeleteDao<>(
                template,
                new DozerBeanTransformer<>(TriggerInfo.class, HibernateTriggerInfo.class, mapper),
                HibernateTriggerInfo.class,
                new TriggerInfoPresetCriteriaMaker()
        );
    }
}
