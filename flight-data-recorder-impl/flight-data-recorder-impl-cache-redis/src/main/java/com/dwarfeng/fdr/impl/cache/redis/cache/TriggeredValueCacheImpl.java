package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.cache.TriggeredValueCache;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TriggeredValueCacheImpl implements TriggeredValueCache {

    @Autowired
    private TriggeredValueCacheDelegate delegate;

    @Override
    public boolean exists(GuidKey key) throws CacheException {
        return delegate.exists(key);
    }

    @Override
    public TriggeredValue get(GuidKey key) throws CacheException {
        return delegate.get(key);
    }

    @Override
    public void push(GuidKey key, TriggeredValue value, long timeout) throws CacheException {
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
    public void deleteAllByTriggerInfo(GuidKey triggerInfoKey) throws CacheException {
        delegate.deleteAllByTriggerInfo(triggerInfoKey);
    }
}
