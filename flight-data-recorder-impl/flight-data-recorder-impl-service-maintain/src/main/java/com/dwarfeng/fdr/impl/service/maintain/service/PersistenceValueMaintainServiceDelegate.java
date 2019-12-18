package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.cache.PersistenceValueCache;
import com.dwarfeng.fdr.stack.dao.PersistenceValueDao;
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
public class PersistenceValueMaintainServiceDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceValueMaintainServiceDelegate.class);

    @Autowired
    private PersistenceValueDao persistenceValueDao;
    @Autowired
    private PersistenceValueCache persistenceValueCache;
    @Autowired
    private ValidationHandler validationHandler;

    @Value("${cache.timeout.entity.persistence_value}")
    private long persistenceValueTimeout;

    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager", readOnly = true)
    public PersistenceValue get(@NotNull UuidKey key) throws ServiceException {
        try {
            validationHandler.uuidKeyValidation(key);
            return internalGet(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    private PersistenceValue internalGet(UuidKey key) throws CacheException, DaoException {
        if (persistenceValueCache.exists(key)) {
            LOGGER.debug("在缓存中发现了 " + key.toString() + " 对应的值，直接返回该值...");
            return persistenceValueCache.get(key);
        } else {
            LOGGER.debug("未能在缓存中发现 " + key.toString() + " 对应的值，读取数据访问层...");
            PersistenceValue persistenceValue = persistenceValueDao.get(key);
            LOGGER.debug("将读取到的值 " + persistenceValue.toString() + " 回写到缓存中...");
            persistenceValueCache.push(key, persistenceValue, persistenceValueTimeout);
            return persistenceValue;
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager")
    public UuidKey insert(@NotNull PersistenceValue persistenceValue) throws ServiceException {
        try {
            validationHandler.persistenceValueValidation(persistenceValue);

            if (persistenceValueCache.exists(persistenceValue.getKey()) || persistenceValueDao.exists(persistenceValue.getKey())) {
                LOGGER.debug("指定的实体 " + persistenceValue.toString() + " 已经存在，无法插入...");
                throw new IllegalStateException("指定的实体 " + persistenceValue.toString() + " 已经存在，无法插入...");
            } else {
                LOGGER.debug("将指定的实体 " + persistenceValue.toString() + " 插入数据访问层中...");
                persistenceValueDao.insert(persistenceValue);
                LOGGER.debug("将指定的实体 " + persistenceValue.toString() + " 插入缓存中...");
                persistenceValueCache.push(persistenceValue.getKey(), persistenceValue, persistenceValueTimeout);
                return persistenceValue.getKey();
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager")
    public UuidKey update(@NotNull PersistenceValue persistenceValue) throws ServiceException {
        try {
            validationHandler.persistenceValueValidation(persistenceValue);

            if (!persistenceValueCache.exists(persistenceValue.getKey()) && !persistenceValueDao.exists(persistenceValue.getKey())) {
                LOGGER.debug("指定的实体 " + persistenceValue.toString() + " 已经存在，无法更新...");
                throw new IllegalStateException("指定的实体 " + persistenceValue.toString() + " 已经存在，无法更新...");
            } else {
                PersistenceValue oldPersistenceValue = internalGet(persistenceValue.getKey());
                LOGGER.debug("将指定的实体 " + persistenceValue.toString() + " 插入数据访问层中...");
                persistenceValueDao.update(persistenceValue);
                LOGGER.debug("将指定的实体 " + persistenceValue.toString() + " 插入缓存中...");
                persistenceValueCache.push(persistenceValue.getKey(), persistenceValue, persistenceValueTimeout);
                return persistenceValue.getKey();
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

            if (!persistenceValueCache.exists(key) && !persistenceValueDao.exists(key)) {
                LOGGER.debug("指定的键 " + key.toString() + " 不存在，无法删除...");
                throw new IllegalStateException("指定的键 " + key.toString() + " 不存在，无法删除...");
            } else {
                PersistenceValue oldPersistenceValue = internalGet(key);
                LOGGER.debug("清除实体 " + key.toString() + " 对应的子项缓存...");
                LOGGER.debug("将指定的PersistenceValue从缓存中删除...");
                persistenceValueCache.delete(key);
                LOGGER.debug("将指定的PersistenceValue从数据访问层中删除...");
                persistenceValueDao.delete(key);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
