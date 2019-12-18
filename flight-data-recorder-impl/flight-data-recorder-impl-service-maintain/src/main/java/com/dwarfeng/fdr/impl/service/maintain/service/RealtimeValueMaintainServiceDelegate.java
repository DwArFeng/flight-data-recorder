package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.cache.RealtimeValueCache;
import com.dwarfeng.fdr.stack.dao.RealtimeValueDao;
import com.dwarfeng.fdr.stack.exception.CacheException;
import com.dwarfeng.fdr.stack.exception.DaoException;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.handler.ValidationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Component
@Validated
public class RealtimeValueMaintainServiceDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(RealtimeValueMaintainServiceDelegate.class);

    @Autowired
    private RealtimeValueDao realtimeValueDao;
    @Autowired
    private RealtimeValueCache realtimeValueCache;
    @Autowired
    private ValidationHandler validationHandler;

    @Value("${cache.timeout.entity.realtime_value}")
    private long realtimeValueTimeout;

    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager", readOnly = true)
    public RealtimeValue get(@NotNull UuidKey key) throws ServiceException {
        try {
            validationHandler.uuidKeyValidation(key);
            return internalGet(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    private RealtimeValue internalGet(UuidKey key) throws CacheException, DaoException {
        if (realtimeValueCache.exists(key)) {
            LOGGER.debug("在缓存中发现了 " + key.toString() + " 对应的值，直接返回该值...");
            return realtimeValueCache.get(key);
        } else {
            LOGGER.debug("未能在缓存中发现 " + key.toString() + " 对应的值，读取数据访问层...");
            RealtimeValue realtimeValue = realtimeValueDao.get(key);
            LOGGER.debug("将读取到的值 " + realtimeValue.toString() + " 回写到缓存中...");
            realtimeValueCache.push(key, realtimeValue, realtimeValueTimeout);
            return realtimeValue;
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager")
    public UuidKey insert(@NotNull RealtimeValue realtimeValue) throws ServiceException {
        try {
            validationHandler.realtimeValueValidation(realtimeValue);

            if (realtimeValueCache.exists(realtimeValue.getKey()) || realtimeValueDao.exists(realtimeValue.getKey())) {
                LOGGER.debug("指定的实体 " + realtimeValue.toString() + " 已经存在，无法插入...");
                throw new IllegalStateException("指定的实体 " + realtimeValue.toString() + " 已经存在，无法插入...");
            } else {
                LOGGER.debug("将指定的实体 " + realtimeValue.toString() + " 插入数据访问层中...");
                realtimeValueDao.insert(realtimeValue);
                LOGGER.debug("将指定的实体 " + realtimeValue.toString() + " 插入缓存中...");
                realtimeValueCache.push(realtimeValue.getKey(), realtimeValue, realtimeValueTimeout);
                return realtimeValue.getKey();
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager")
    public UuidKey update(@NotNull RealtimeValue realtimeValue) throws ServiceException {
        try {
            validationHandler.realtimeValueValidation(realtimeValue);

            if (!realtimeValueCache.exists(realtimeValue.getKey()) && !realtimeValueDao.exists(realtimeValue.getKey())) {
                LOGGER.debug("指定的实体 " + realtimeValue.toString() + " 已经存在，无法更新...");
                throw new IllegalStateException("指定的实体 " + realtimeValue.toString() + " 已经存在，无法更新...");
            } else {
                RealtimeValue oldRealtimeValue = internalGet(realtimeValue.getKey());
                LOGGER.debug("将指定的实体 " + realtimeValue.toString() + " 插入数据访问层中...");
                realtimeValueDao.update(realtimeValue);
                LOGGER.debug("将指定的实体 " + realtimeValue.toString() + " 插入缓存中...");
                realtimeValueCache.push(realtimeValue.getKey(), realtimeValue, realtimeValueTimeout);
                return realtimeValue.getKey();
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager")
    public void delete(@NotNull UuidKey key) throws ServiceException {
        try {
            validationHandler.uuidKeyValidation(key);

            if (!realtimeValueCache.exists(key) && !realtimeValueDao.exists(key)) {
                LOGGER.debug("指定的键 " + key.toString() + " 不存在，无法删除...");
                throw new IllegalStateException("指定的键 " + key.toString() + " 不存在，无法删除...");
            } else {
                RealtimeValue oldRealtimeValue = internalGet(key);
                LOGGER.debug("清除实体 " + key.toString() + " 对应的子项缓存...");
                LOGGER.debug("将指定的RealtimeValue从缓存中删除...");
                realtimeValueCache.delete(key);
                LOGGER.debug("将指定的RealtimeValue从数据访问层中删除...");
                realtimeValueDao.delete(key);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
