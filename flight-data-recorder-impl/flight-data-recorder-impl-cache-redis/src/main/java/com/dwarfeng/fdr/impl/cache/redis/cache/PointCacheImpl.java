package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.cache.PointCache;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PointCacheImpl implements PointCache {

    @Autowired
    private PointCacheDelegate delegate;

    @Override
    public boolean exists(GuidKey key) throws CacheException {
        return delegate.exists(key);
    }

    @Override
    public Point get(GuidKey key) throws CacheException {
        return delegate.get(key);
    }

    @Override
    public void push(GuidKey key, Point value, long timeout) throws CacheException {
        delegate.push(key, value, timeout);
    }


    @Override
    public void delete(GuidKey key) throws CacheException {
        delegate.delete(key);
    }
}
