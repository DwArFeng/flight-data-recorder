package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.cache.CategoryHasPointCache;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryHasPointCacheImpl implements CategoryHasPointCache {

    @Autowired
    private CategoryHasPointCacheDelegate delegate;

    @Override
    public boolean exists(GuidKey key) throws CacheException {
        return delegate.exists(key);
    }

    @Override
    public long size(GuidKey key) throws CacheException {
        return delegate.size(key);
    }

    @Override
    public List<Point> get(GuidKey key, LookupPagingInfo lookupPagingInfo) throws CacheException {
        return delegate.get(key, lookupPagingInfo);
    }

    @Override
    public void set(GuidKey key, List<? extends Point> value, long timeout) throws CacheException {
        delegate.set(key, value, timeout);
    }

    @Override
    public void push(GuidKey key, List<? extends Point> value, long timeout) throws CacheException {
        delegate.push(key, value, timeout);
    }

    @Override
    public void delete(GuidKey key) throws CacheException {
        delegate.delete(key);
    }
}
