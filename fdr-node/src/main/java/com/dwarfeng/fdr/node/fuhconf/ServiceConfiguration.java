package com.dwarfeng.fdr.node.fuhconf;

import com.dwarfeng.fdr.impl.service.operation.*;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.fdr.stack.cache.FilterSupportCache;
import com.dwarfeng.fdr.stack.cache.TriggerSupportCache;
import com.dwarfeng.fdr.stack.dao.*;
import com.dwarfeng.sfds.api.integration.subgrade.SnowFlakeLongIdKeyFetcher;
import com.dwarfeng.subgrade.impl.bean.key.ExceptionKeyFetcher;
import com.dwarfeng.subgrade.impl.service.CustomCrudService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyEntireLookupService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyPresetLookupService;
import com.dwarfeng.subgrade.impl.service.GeneralCrudService;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Autowired
    private ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration;

    @Bean
    public KeyFetcher<LongIdKey> longIdKeyKeyFetcher() {
        return new SnowFlakeLongIdKeyFetcher();
    }

    @Autowired
    private FilteredValueCrudOperation filteredValueCrudOperation;
    @Autowired
    private FilteredValueDao filteredValueDao;
    @Autowired
    private FilterInfoCrudOperation filterInfoCrudOperation;
    @Autowired
    private FilterInfoDao filterInfoDao;
    @Autowired
    private PersistenceValueCrudOperation persistenceValueCrudOperation;
    @Autowired
    private PersistenceValueDao persistenceValueDao;
    @Autowired
    private PointCrudOperation pointCrudOperation;
    @Autowired
    private PointDao pointDao;
    @Autowired
    private RealtimeValueCrudOperation realtimeValueCrudOperation;
    @Autowired
    private TriggeredValueCrudOperation triggeredValueCrudOperation;
    @Autowired
    private TriggeredValueDao triggeredValueDao;
    @Autowired
    private TriggerInfoCrudOperation triggerInfoCrudOperation;
    @Autowired
    private TriggerInfoDao triggerInfoDao;
    @Autowired
    private FilterSupportCache filterSupportCache;
    @Autowired
    private FilterSupportDao filterSupportDao;
    @Autowired
    private TriggerSupportCache triggerSupportCache;
    @Autowired
    private TriggerSupportDao triggerSupportDao;

    @Value("${cache.timeout.entity.filter_support}")
    private long filterSupportTimeout;
    @Value("${cache.timeout.entity.trigger_support}")
    private long triggerSupportTimeout;

    @Bean
    public CustomCrudService<LongIdKey, FilteredValue> filteredValueCustomCrudService() {
        return new CustomCrudService<>(
                filteredValueCrudOperation,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<FilteredValue> filteredValueDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                filteredValueDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public CustomCrudService<LongIdKey, FilterInfo> filterInfoCustomCrudService() {
        return new CustomCrudService<>(
                filterInfoCrudOperation,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<FilterInfo> filterInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                filterInfoDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public CustomCrudService<LongIdKey, PersistenceValue> persistenceValueCustomCrudService() {
        return new CustomCrudService<>(
                persistenceValueCrudOperation,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<PersistenceValue> persistenceValueDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                persistenceValueDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public CustomCrudService<LongIdKey, Point> pointCustomCrudService() {
        return new CustomCrudService<>(
                pointCrudOperation,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<Point> pointDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                pointDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<Point> pointDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                pointDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public CustomCrudService<LongIdKey, RealtimeValue> realtimeValueCustomCrudService() {
        return new CustomCrudService<>(
                realtimeValueCrudOperation,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public CustomCrudService<LongIdKey, TriggeredValue> triggeredValueCustomCrudService() {
        return new CustomCrudService<>(
                triggeredValueCrudOperation,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<TriggeredValue> triggeredValueDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                triggeredValueDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public CustomCrudService<LongIdKey, TriggerInfo> triggerInfoCustomCrudService() {
        return new CustomCrudService<>(
                triggerInfoCrudOperation,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<TriggerInfo> triggerInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                triggerInfoDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public GeneralCrudService<StringIdKey, FilterSupport> filterSupportGeneralCrudService() {
        return new GeneralCrudService<>(
                filterSupportDao,
                filterSupportCache,
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                filterSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<FilterSupport> filterSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                filterSupportDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<FilterSupport> filterSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                filterSupportDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public GeneralCrudService<StringIdKey, TriggerSupport> triggerSupportGeneralCrudService() {
        return new GeneralCrudService<>(
                triggerSupportDao,
                triggerSupportCache,
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                triggerSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<TriggerSupport> triggerSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                triggerSupportDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<TriggerSupport> triggerSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                triggerSupportDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }
}
