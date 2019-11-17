package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.cache.CategoryCache;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class CategoryCacheImpl implements CategoryCache {

    @Autowired
    private CategoryCacheDelegate delegate;

    @Override
    public boolean exists(UuidKey key) throws CacheException {
        return delegate.exists(key);
    }

    @Override
    public Category get(UuidKey key) throws CacheException {
        return delegate.get(key);
    }

    @Override
    public void push(UuidKey key, Category value, long timeout, TimeUnit timeUnit) throws CacheException {
        delegate.push(key, value, timeout, timeUnit);
    }


    @Override
    public void delete(UuidKey key) throws CacheException {
        delegate.delete(key);
    }
}
