package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.impl.cache.redis.bean.entity.PointImpl;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.NameKey;
import com.dwarfeng.fdr.stack.cache.PointEntityCache;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * 数据点缓存的实现。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
@Repository
public class PointEntityCacheImpl extends AbstractBaseCache<NameKey, Point> implements PointEntityCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointEntityCacheImpl.class);

    @Autowired
    private Mapper mapper;
    @Value("${key_format.entity.point}")
    private String keyFormat;

    @Override
    protected Object key2Object(NameKey key) {
        return String.format(keyFormat, key.getName());
    }

    @Override
    protected Object value2Object(Point value) {
        return mapper.map(value, PointImpl.class);
    }

    @Override
    protected Point object2Value(Object object) {
        return (Point) object;
    }
}
