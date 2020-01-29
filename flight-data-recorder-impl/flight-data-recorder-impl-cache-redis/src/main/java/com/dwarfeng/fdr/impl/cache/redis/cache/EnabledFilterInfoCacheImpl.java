package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.sdk.bean.entity.FastJsonFilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.cache.EnabledFilterInfoCache;
import com.dwarfeng.subgrade.impl.cache.RedisKeyListCache;
import com.dwarfeng.subgrade.sdk.interceptor.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public class EnabledFilterInfoCacheImpl implements EnabledFilterInfoCache {

    @Autowired
    @Qualifier("filterInfoEnabledRedisKeyListCache")
    private RedisKeyListCache<LongIdKey, FilterInfo, FastJsonFilterInfo> delegate;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public boolean exists(LongIdKey key) throws CacheException {
        return delegate.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public int size(LongIdKey key) throws CacheException {
        return delegate.size(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public List<FilterInfo> get(LongIdKey key) throws CacheException {
        return delegate.get(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public List<FilterInfo> get(LongIdKey key, int beginIndex, int maxEntity) throws CacheException {
        return delegate.get(key, beginIndex, maxEntity);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public List<FilterInfo> get(LongIdKey key, PagingInfo pagingInfo) throws CacheException {
        return delegate.get(key, pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void set(LongIdKey key, Collection<FilterInfo> entities, long timeout) throws CacheException {
        delegate.set(key, entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void leftPush(LongIdKey key, Collection<FilterInfo> entities, long timeout) throws CacheException {
        delegate.leftPush(key, entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void rightPush(LongIdKey key, Collection<FilterInfo> entities, long timeout) throws CacheException {
        delegate.rightPush(key, entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(LongIdKey key) throws CacheException {
        delegate.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void clear() throws CacheException {
        delegate.clear();
    }
}
