package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.cache.PersistenceValueCache;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PersistenceValueCacheImpl implements PersistenceValueCache {

    @Autowired
    private PersistenceValueCacheDelegate delegate;

    @Override
    public boolean exists(GuidKey key) throws CacheException {
        return delegate.exists(key);
    }

    @Override
    public PersistenceValue get(GuidKey key) throws CacheException {
        return delegate.get(key);
    }

    @Override
    public void push(GuidKey key, PersistenceValue value, long timeout) throws CacheException {
        delegate.push(key, value, timeout);
    }

    @Override
    public void delete(GuidKey key) throws CacheException {
        delegate.delete(key);
    }

    @Override
    public void deleteAll(GuidKey pointKey) throws CacheException {
        delegate.deleteAll(pointKey);
    }
}
