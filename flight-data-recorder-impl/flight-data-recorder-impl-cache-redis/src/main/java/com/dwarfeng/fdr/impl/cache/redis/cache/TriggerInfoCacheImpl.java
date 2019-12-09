package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.cache.TriggerInfoCache;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TriggerInfoCacheImpl implements TriggerInfoCache {

    @Autowired
    private TriggerInfoCacheDelegate delegate;

    @Override
    public boolean exists(UuidKey key) throws CacheException {
        return delegate.exists(key);
    }

    @Override
    public TriggerInfo get(UuidKey key) throws CacheException {
        return delegate.get(key);
    }

    @Override
    public void push(UuidKey key, TriggerInfo value, long timeout) throws CacheException {
        delegate.push(key, value, timeout);
    }


    @Override
    public void delete(UuidKey key) throws CacheException {
        delegate.delete(key);
    }
}
