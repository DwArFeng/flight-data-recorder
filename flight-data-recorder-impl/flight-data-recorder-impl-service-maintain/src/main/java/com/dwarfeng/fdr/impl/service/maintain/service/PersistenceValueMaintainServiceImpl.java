package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.cache.PersistenceValueCache;
import com.dwarfeng.fdr.stack.dao.PersistenceValueDao;
import com.dwarfeng.fdr.stack.service.PersistenceValueMaintainService;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class PersistenceValueMaintainServiceImpl implements PersistenceValueMaintainService {

    @Autowired
    private KeyFetcher<LongIdKey> keyFetcher;

    @Autowired
    private PersistenceValueDao persistenceValueDao;
    @Autowired
    private PersistenceValueCache persistenceValueCache;
    @Autowired
    private ServiceExceptionMapper sem;
    @Value("${cache.timeout.entity.persistence_value}")
    private long persistenceValueTimeout;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public boolean exists(LongIdKey key) throws ServiceException {
        try {
            return internalExists(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("判断实体是否存在时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private boolean internalExists(LongIdKey key) throws Exception {
        return persistenceValueCache.exists(key) || persistenceValueDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PersistenceValue get(LongIdKey key) throws ServiceException {
        try {
            if (persistenceValueCache.exists(key)) {
                return persistenceValueCache.get(key);
            } else {
                if (!persistenceValueDao.exists(key)) {
                    throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
                }
                PersistenceValue persistenceValue = persistenceValueDao.get(key);
                persistenceValueCache.push(persistenceValue, persistenceValueTimeout);
                return persistenceValue;
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public LongIdKey insert(PersistenceValue persistenceValue) throws ServiceException {
        try {
            if (Objects.nonNull(persistenceValue.getKey()) && internalExists(persistenceValue.getKey())) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_EXISTED);
            }
            if (Objects.isNull(persistenceValue.getKey())) {
                persistenceValue.setKey(keyFetcher.fetchKey());
            }
            persistenceValueCache.push(persistenceValue, persistenceValueTimeout);
            return persistenceValueDao.insert(persistenceValue);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void update(PersistenceValue persistenceValue) throws ServiceException {
        try {
            if (Objects.nonNull(persistenceValue.getKey()) && !internalExists(persistenceValue.getKey())) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            persistenceValueCache.push(persistenceValue, persistenceValueTimeout);
            persistenceValueDao.update(persistenceValue);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("更新实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(LongIdKey key) throws ServiceException {
        try {
            if (!internalExists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            internalDelete(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("删除实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private void internalDelete(LongIdKey key) throws com.dwarfeng.subgrade.stack.exception.DaoException, com.dwarfeng.subgrade.stack.exception.CacheException {
        persistenceValueDao.delete(key);
        persistenceValueCache.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<PersistenceValue> lookup(String preset, Object[] objs) throws ServiceException {
        try {
            return PagingUtil.pagedData(persistenceValueDao.lookup(preset, objs));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<PersistenceValue> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        try {
            return PagingUtil.pagedData(pagingInfo, persistenceValueDao.lookupCount(preset, objs), persistenceValueDao.lookup(preset, objs, pagingInfo));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void lookupDelete(String preset, Object[] objs) throws ServiceException {
        try {
            List<LongIdKey> longIdKeys = persistenceValueDao.lookupDelete(preset, objs);
            for (LongIdKey longIdKey : longIdKeys) {
                internalDelete(longIdKey);
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询并删除实体时发生异常", LogLevel.WARN, sem, e);
        }
    }
}
