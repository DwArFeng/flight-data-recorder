package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.cache.TriggeredValueCache;
import com.dwarfeng.fdr.stack.dao.TriggeredValueDao;
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
public class TriggeredValueMaintainServiceDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggeredValueMaintainServiceDelegate.class);

    @Autowired
    private TriggeredValueDao triggeredValueDao;
    @Autowired
    private TriggeredValueCache triggeredValueCache;

    @Autowired
    private GuidApi guidApi;

    @Value("${cache.timeout.entity.triggered_value}")
    private long triggeredValueTimeout;

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public TriggeredValue get(@NotNull GuidKey key) throws ServiceException {
        try {
            return internalGet(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    private TriggeredValue internalGet(GuidKey key) throws CacheException, DaoException {
        if (triggeredValueCache.exists(key)) {
            LOGGER.debug("在缓存中发现了 " + key.toString() + " 对应的值，直接返回该值...");
            return triggeredValueCache.get(key);
        } else {
            LOGGER.debug("未能在缓存中发现 " + key.toString() + " 对应的值，读取数据访问层...");
            TriggeredValue triggeredValue = triggeredValueDao.get(key);
            LOGGER.debug("将读取到的值 " + triggeredValue.toString() + " 回写到缓存中...");
            triggeredValueCache.push(key, triggeredValue, triggeredValueTimeout);
            return triggeredValue;
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey insert(@NotNull TriggeredValue triggeredValue) throws ServiceException {
        //如果实体中没有主键，则向主键服务请求一个主键。
        maySetGuid(triggeredValue);

        try {
            if (triggeredValueCache.exists(triggeredValue.getKey()) || triggeredValueDao.exists(triggeredValue.getKey())) {
                LOGGER.debug("指定的实体 " + triggeredValue.toString() + " 已经存在，无法插入...");
                throw new IllegalStateException("指定的实体 " + triggeredValue.toString() + " 已经存在，无法插入...");
            } else {
                LOGGER.debug("将指定的实体 " + triggeredValue.toString() + " 插入数据访问层中...");
                triggeredValueDao.insert(triggeredValue);
                LOGGER.debug("将指定的实体 " + triggeredValue.toString() + " 插入缓存中...");
                triggeredValueCache.push(triggeredValue.getKey(), triggeredValue, triggeredValueTimeout);
                return triggeredValue.getKey();
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    private void maySetGuid(TriggeredValue triggeredValue) throws ServiceException {
        if (Objects.isNull(triggeredValue.getKey())) {
            LOGGER.debug("实体 " + triggeredValue.toString() + "没有主键，将从服务中获取GUID...");
            try {
                long guid = guidApi.nextGuid();
                LOGGER.debug("从服务中获取了guid: " + guid);
                triggeredValue.setKey(new GuidKey(guid));
            } catch (Exception e) {
                LOGGER.warn("主键获取失败，将抛出异常...", e);
                throw new ServiceException(ServiceExceptionCodes.GUID_FETCH_FAILED);
            }
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey update(@NotNull TriggeredValue triggeredValue) throws ServiceException {
        try {
            if (!triggeredValueCache.exists(triggeredValue.getKey()) && !triggeredValueDao.exists(triggeredValue.getKey())) {
                LOGGER.debug("指定的实体 " + triggeredValue.toString() + " 已经存在，无法更新...");
                throw new IllegalStateException("指定的实体 " + triggeredValue.toString() + " 已经存在，无法更新...");
            } else {
                TriggeredValue oldTriggeredValue = internalGet(triggeredValue.getKey());
                LOGGER.debug("将指定的实体 " + triggeredValue.toString() + " 插入数据访问层中...");
                triggeredValueDao.update(triggeredValue);
                LOGGER.debug("将指定的实体 " + triggeredValue.toString() + " 插入缓存中...");
                triggeredValueCache.push(triggeredValue.getKey(), triggeredValue, triggeredValueTimeout);
                return triggeredValue.getKey();
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(@NotNull GuidKey key) throws ServiceException {
        try {
            if (!triggeredValueCache.exists(key) && !triggeredValueDao.exists(key)) {
                LOGGER.debug("指定的键 " + key.toString() + " 不存在，无法删除...");
                throw new IllegalStateException("指定的键 " + key.toString() + " 不存在，无法删除...");
            } else {
                TriggeredValue oldTriggeredValue = internalGet(key);
                LOGGER.debug("清除实体 " + key.toString() + " 对应的子项缓存...");
                LOGGER.debug("将指定的TriggeredValue从缓存中删除...");
                triggeredValueCache.delete(key);
                LOGGER.debug("将指定的TriggeredValue从数据访问层中删除...");
                triggeredValueDao.delete(key);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
