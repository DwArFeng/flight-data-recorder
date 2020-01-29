package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.cache.TriggeredValueCache;
import com.dwarfeng.fdr.stack.dao.TriggeredValueDao;
import com.dwarfeng.fdr.stack.service.TriggeredValueMaintainService;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.BehaviorAnalyse;
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
public class TriggeredValueMaintainServiceImpl implements TriggeredValueMaintainService {

    @Autowired
    private KeyFetcher<LongIdKey> keyFetcher;

    @Autowired
    private TriggeredValueDao triggeredValueDao;
    @Autowired
    private TriggeredValueCache triggeredValueCache;
    @Autowired
    private ServiceExceptionMapper sem;
    @Value("${cache.timeout.entity.triggered_value}")
    private long triggeredValueTimeout;

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
        return triggeredValueCache.exists(key) || triggeredValueDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public TriggeredValue get(LongIdKey key) throws ServiceException {
        try {
            if (triggeredValueCache.exists(key)) {
                return triggeredValueCache.get(key);
            } else {
                if (!triggeredValueDao.exists(key)) {
                    throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
                }
                TriggeredValue triggeredValue = triggeredValueDao.get(key);
                triggeredValueCache.push(triggeredValue, triggeredValueTimeout);
                return triggeredValue;
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public LongIdKey insert(TriggeredValue triggeredValue) throws ServiceException {
        try {
            if (Objects.nonNull(triggeredValue.getKey()) && internalExists(triggeredValue.getKey())) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_EXISTED);
            }
            if (Objects.isNull(triggeredValue.getKey())) {
                triggeredValue.setKey(keyFetcher.fetchKey());
            }
            triggeredValueCache.push(triggeredValue, triggeredValueTimeout);
            return triggeredValueDao.insert(triggeredValue);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void update(TriggeredValue triggeredValue) throws ServiceException {
        try {
            if (Objects.nonNull(triggeredValue.getKey()) && !internalExists(triggeredValue.getKey())) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            triggeredValueCache.push(triggeredValue, triggeredValueTimeout);
            triggeredValueDao.update(triggeredValue);
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
        triggeredValueDao.delete(key);
        triggeredValueCache.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<TriggeredValue> lookup(String preset, Object[] objs) throws ServiceException {
        try {
            return PagingUtil.pagedData(triggeredValueDao.lookup(preset, objs));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<TriggeredValue> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        try {
            return PagingUtil.pagedData(pagingInfo, triggeredValueDao.lookupCount(preset, objs), triggeredValueDao.lookup(preset, objs, pagingInfo));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void lookupDelete(String preset, Object[] objs) throws ServiceException {
        try {
            List<LongIdKey> longIdKeys = triggeredValueDao.lookupDelete(preset, objs);
            for (LongIdKey longIdKey : longIdKeys) {
                internalDelete(longIdKey);
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询并删除实体时发生异常", LogLevel.WARN, sem, e);
        }
    }
}
