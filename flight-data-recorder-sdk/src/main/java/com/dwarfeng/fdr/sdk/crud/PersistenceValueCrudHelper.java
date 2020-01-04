package com.dwarfeng.fdr.sdk.crud;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.cache.PersistenceValueCache;
import com.dwarfeng.fdr.stack.dao.PersistenceValueDao;
import com.dwarfeng.fdr.stack.exception.CacheException;
import com.dwarfeng.fdr.stack.exception.DaoException;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.sfds.api.GuidApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Component
@Validated
public class PersistenceValueCrudHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceValueCrudHelper.class);

    @Autowired
    private PersistenceValueDao persistenceValueDao;
    @Autowired
    private PersistenceValueCache persistenceValueCache;

    @Autowired
    private GuidApi guidApi;

    @Value("${cache.timeout.entity.persistence_value}")
    private long persistenceValueTimeout;

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PersistenceValue get(@NotNull GuidKey key) throws ServiceException {
        try {
            return noAdviseGet(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public PersistenceValue noAdviseGet(GuidKey key) throws CacheException, DaoException {
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
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey insert(@NotNull PersistenceValue persistenceValue) throws ServiceException {
        try {
            return noAdviseInsert(persistenceValue);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public GuidKey noAdviseInsert(PersistenceValue persistenceValue) throws com.dwarfeng.sfds.stack.exception.ServiceException, CacheException, DaoException {
        //如果实体中没有主键，则向主键服务请求一个主键。
        maySetGuid(persistenceValue);

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
    }

    private void maySetGuid(PersistenceValue persistenceValue) throws com.dwarfeng.sfds.stack.exception.ServiceException {
        if (Objects.isNull(persistenceValue.getKey())) {
            LOGGER.debug("实体 " + persistenceValue.toString() + "没有主键，将从服务中获取GUID...");
            long guid = guidApi.nextGuid();
            LOGGER.debug("从服务中获取了guid: " + guid);
            persistenceValue.setKey(new GuidKey(guid));
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey update(@NotNull PersistenceValue persistenceValue) throws ServiceException {
        try {
            return noAdviseUpdate(persistenceValue);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public GuidKey noAdviseUpdate(PersistenceValue persistenceValue) throws CacheException, DaoException {
        if (!persistenceValueCache.exists(persistenceValue.getKey()) && !persistenceValueDao.exists(persistenceValue.getKey())) {
            LOGGER.debug("指定的实体 " + persistenceValue.toString() + " 已经存在，无法更新...");
            throw new IllegalStateException("指定的实体 " + persistenceValue.toString() + " 已经存在，无法更新...");
        } else {
            PersistenceValue oldPersistenceValue = noAdviseGet(persistenceValue.getKey());
            LOGGER.debug("将指定的实体 " + persistenceValue.toString() + " 插入数据访问层中...");
            persistenceValueDao.update(persistenceValue);
            LOGGER.debug("将指定的实体 " + persistenceValue.toString() + " 插入缓存中...");
            persistenceValueCache.push(persistenceValue.getKey(), persistenceValue, persistenceValueTimeout);
            return persistenceValue.getKey();
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(@NotNull GuidKey key) throws ServiceException {
        try {
            if (!persistenceValueCache.exists(key) && !persistenceValueDao.exists(key)) {
                LOGGER.debug("指定的键 " + key.toString() + " 不存在，无法删除...");
                throw new IllegalStateException("指定的键 " + key.toString() + " 不存在，无法删除...");
            } else {
                PersistenceValue oldPersistenceValue = noAdviseGet(key);
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

    public void noAdviseDelete(GuidKey key) throws CacheException, DaoException {
        if (!persistenceValueCache.exists(key) && !persistenceValueDao.exists(key)) {
            LOGGER.debug("指定的键 " + key.toString() + " 不存在，无法删除...");
            throw new IllegalStateException("指定的键 " + key.toString() + " 不存在，无法删除...");
        } else {
            PersistenceValue oldPersistenceValue = noAdviseGet(key);
            LOGGER.debug("清除实体 " + key.toString() + " 对应的子项缓存...");
            LOGGER.debug("将指定的PersistenceValue从缓存中删除...");
            persistenceValueCache.delete(key);
            LOGGER.debug("将指定的PersistenceValue从数据访问层中删除...");
            persistenceValueDao.delete(key);
        }
    }
}
