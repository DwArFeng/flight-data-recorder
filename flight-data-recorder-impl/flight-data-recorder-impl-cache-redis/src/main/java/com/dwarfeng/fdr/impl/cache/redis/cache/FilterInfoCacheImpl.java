package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.cache.FilterInfoCache;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FilterInfoCacheImpl implements FilterInfoCache {

    @Autowired
    private FilterInfoCacheDelegate delegate;

    @Override
    public boolean exists(GuidKey key) throws CacheException {
        return delegate.exists(key);
    }

    @Override
    public FilterInfo get(GuidKey key) throws CacheException {
        return delegate.get(key);
    }

    @Override
    public void push(GuidKey key, FilterInfo value, long timeout) throws CacheException {
        delegate.push(key, value, timeout);
    }


    @Override
    public void delete(GuidKey key) throws CacheException {
        delegate.delete(key);
    }
}
