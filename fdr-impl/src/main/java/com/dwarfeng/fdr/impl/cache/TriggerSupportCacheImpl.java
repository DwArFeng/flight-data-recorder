package com.dwarfeng.fdr.impl.cache;

import com.dwarfeng.fdr.sdk.bean.entity.FastJsonTriggerSupport;
import com.dwarfeng.fdr.stack.bean.entity.TriggerSupport;
import com.dwarfeng.fdr.stack.cache.TriggerSupportCache;
import com.dwarfeng.subgrade.impl.cache.RedisBaseCache;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TriggerSupportCacheImpl implements TriggerSupportCache {

    @Autowired
    private RedisBaseCache<StringIdKey, TriggerSupport, FastJsonTriggerSupport> baseCache;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean exists(StringIdKey key) throws CacheException {
        return baseCache.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public TriggerSupport get(StringIdKey key) throws CacheException {
        return baseCache.get(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void push(TriggerSupport value, long timeout) throws CacheException {
        baseCache.push(value, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void delete(StringIdKey key) throws CacheException {
        baseCache.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void clear() throws CacheException {
        baseCache.clear();
    }
}
