package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.cache.RealtimeValueCache;
import com.dwarfeng.fdr.stack.dao.RealtimeValueDao;
import com.dwarfeng.fdr.stack.service.RealtimeValueMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class RealtimeValueMaintainServiceImpl implements RealtimeValueMaintainService {

    @Autowired
    private KeyFetcher<LongIdKey> keyFetcher;

    @Autowired
    private RealtimeValueDao realtimeValueDao;
    @Autowired
    private RealtimeValueCache realtimeValueCache;
    @Autowired
    private ServiceExceptionMapper sem;
    @Value("${cache.timeout.entity.realtime_value}")
    private long realtimeValueTimeout;

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
        return realtimeValueCache.exists(key) || realtimeValueDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public RealtimeValue get(LongIdKey key) throws ServiceException {
        try {
            if (realtimeValueCache.exists(key)) {
                return realtimeValueCache.get(key);
            } else {
                if (!realtimeValueDao.exists(key)) {
                    throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
                }
                RealtimeValue realtimeValue = realtimeValueDao.get(key);
                realtimeValueCache.push(realtimeValue, realtimeValueTimeout);
                return realtimeValue;
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public LongIdKey insert(RealtimeValue realtimeValue) throws ServiceException {
        try {
            if (Objects.nonNull(realtimeValue.getKey()) && internalExists(realtimeValue.getKey())) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_EXISTED);
            }
            if (Objects.isNull(realtimeValue.getKey())) {
                realtimeValue.setKey(keyFetcher.fetchKey());
            }
            realtimeValueCache.push(realtimeValue, realtimeValueTimeout);
            return realtimeValueDao.insert(realtimeValue);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void update(RealtimeValue realtimeValue) throws ServiceException {
        try {
            if (Objects.nonNull(realtimeValue.getKey()) && !internalExists(realtimeValue.getKey())) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            realtimeValueCache.push(realtimeValue, realtimeValueTimeout);
            realtimeValueDao.update(realtimeValue);
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
        realtimeValueDao.delete(key);
        realtimeValueCache.delete(key);
    }
}
