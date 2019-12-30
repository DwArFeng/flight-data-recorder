package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.cache.FilteredValueCache;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FilteredValueCacheImpl implements FilteredValueCache {

    @Autowired
    private FilteredValueCacheDelegate delegate;

    @Override
    public boolean exists(GuidKey key) throws CacheException {
        return delegate.exists(key);
    }

    @Override
    public FilteredValue get(GuidKey key) throws CacheException {
        return delegate.get(key);
    }

    @Override
    public void push(GuidKey key, FilteredValue value, long timeout) throws CacheException {
        delegate.push(key, value, timeout);
    }

    @Override
    public void delete(GuidKey key) throws CacheException {
        delegate.delete(key);
    }

    @Override
    public void deleteAllByPoint(GuidKey pointKey) throws CacheException {
        delegate.deleteAllByPoint(pointKey);
    }

    @Override
    public void deleteAllByFilterInfo(GuidKey filterInfoKey) throws CacheException {
        delegate.deleteAllByFilterInfo(filterInfoKey);
    }
}
