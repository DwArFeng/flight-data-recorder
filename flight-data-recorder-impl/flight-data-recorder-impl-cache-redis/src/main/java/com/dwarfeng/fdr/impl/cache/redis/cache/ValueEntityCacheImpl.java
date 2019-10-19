package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.stack.bean.entity.Value;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.cache.ValueEntityCache;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class ValueEntityCacheImpl extends AbstractBaseCache<UuidKey, Value> implements ValueEntityCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValueEntityCacheImpl.class);

    @Autowired
    private Mapper mapper;
    @org.springframework.beans.factory.annotation.Value("${key_format.entity.value}")
    private String keyFormat;

    @Override
    protected Object key2Object(UuidKey key) {
        return String.format(keyFormat, key.getUuid());
    }

    @Override
    protected Object value2Object(Value value) {
        return mapper.map(value, Value.class);
    }

    @Override
    protected Value object2Value(Object object) {
        return (Value) object;
    }
}
