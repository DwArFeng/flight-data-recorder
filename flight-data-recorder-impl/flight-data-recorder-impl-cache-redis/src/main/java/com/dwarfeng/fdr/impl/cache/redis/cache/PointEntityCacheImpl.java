package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.cache.PointEntityCache;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 数据点缓存的实现。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
@Repository
public class PointEntityCacheImpl implements PointEntityCache {

    @Autowired
    private PointEntityCacheDelegate delegate;

    @Override
    public boolean exists(UuidKey key) throws CacheException {
        return delegate.exists(key);
    }

    @Override
    public boolean existsAll(Collection<UuidKey> c) throws CacheException {
        return delegate.existsAll(c);
    }

    @Override
    public boolean existsNon(Collection<UuidKey> c) throws CacheException {
        return delegate.existsNon(c);
    }

    @Override
    public Point get(UuidKey key) throws CacheException {
        return delegate.get(key);
    }

    @Override
    public void push(UuidKey key, Point value, long timeout, TimeUnit timeUnit) throws CacheException {
        delegate.push(key, value, timeout, timeUnit);
    }

    @Override
    public void batchPush(Map<UuidKey, Point> map, long timeout, TimeUnit timeUnit) throws CacheException {
        delegate.batchPush(map, timeout, timeUnit);
    }

    @Override
    public void delete(UuidKey key) throws CacheException {
        delegate.delete(key);
    }

    @Override
    public void batchDelete(Collection<UuidKey> c) throws CacheException {
        delegate.batchDelete(c);
    }
}
