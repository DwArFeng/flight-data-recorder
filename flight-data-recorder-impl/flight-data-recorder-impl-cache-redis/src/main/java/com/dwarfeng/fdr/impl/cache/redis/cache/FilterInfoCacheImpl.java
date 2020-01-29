package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.sdk.bean.entity.FastJsonFilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.cache.FilterInfoCache;
import com.dwarfeng.subgrade.impl.cache.RedisBatchBaseCache;
import com.dwarfeng.subgrade.sdk.interceptor.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class FilterInfoCacheImpl implements FilterInfoCache {

    @Autowired
    private RedisBatchBaseCache<LongIdKey, FilterInfo, FastJsonFilterInfo> filterInfoRedisBatchBaseDelegate;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public boolean exists(LongIdKey key) throws CacheException {
        return filterInfoRedisBatchBaseDelegate.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public FilterInfo get(LongIdKey key) throws CacheException {
        return filterInfoRedisBatchBaseDelegate.get(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void push(FilterInfo value, long timeout) throws CacheException {
        filterInfoRedisBatchBaseDelegate.push(value, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(LongIdKey key) throws CacheException {
        filterInfoRedisBatchBaseDelegate.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void clear() throws CacheException {
        filterInfoRedisBatchBaseDelegate.clear();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public boolean allExists(List<LongIdKey> keys) throws CacheException {
        return filterInfoRedisBatchBaseDelegate.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public boolean nonExists(List<LongIdKey> keys) throws CacheException {
        return filterInfoRedisBatchBaseDelegate.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public List<FilterInfo> batchGet(List<LongIdKey> keys) throws CacheException {
        return filterInfoRedisBatchBaseDelegate.batchGet(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void batchPush(List<FilterInfo> entities, long timeout) throws CacheException {
        filterInfoRedisBatchBaseDelegate.batchPush(entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void batchDelete(List<LongIdKey> keys) throws CacheException {
        filterInfoRedisBatchBaseDelegate.batchDelete(keys);
    }
}
