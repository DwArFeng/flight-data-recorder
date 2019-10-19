package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.stack.bean.entity.TriggerSetting;
import com.dwarfeng.fdr.stack.bean.key.NameKey;
import com.dwarfeng.fdr.stack.cache.TriggerSettingEntityCache;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
@Repository
public class TriggerEntitySettingCacheImpl extends AbstractBaseCache<NameKey, TriggerSetting> implements TriggerSettingEntityCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggerEntitySettingCacheImpl.class);

    @Autowired
    private Mapper mapper;
    @Value("${key_format.entity.trigger_setting}")
    private String keyFormat;

    @Override
    protected Object key2Object(NameKey key) {
        return String.format(keyFormat, key.getName());
    }

    @Override
    protected Object value2Object(TriggerSetting value) {
        return mapper.map(value, TriggerSetting.class);
    }

    @Override
    protected TriggerSetting object2Value(Object object) {
        return (TriggerSetting) object;
    }
}
