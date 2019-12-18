package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.cache.FilteredValueCache;
import com.dwarfeng.fdr.stack.dao.FilteredValueDao;
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
public class FilteredValueMaintainServiceDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilteredValueMaintainServiceDelegate.class);

    @Autowired
    private FilteredValueDao filteredValueDao;
    @Autowired
    private FilteredValueCache filteredValueCache;
    @Autowired
    private ValidationHandler validationHandler;

    @Value("${cache.timeout.entity.filtered_value}")
    private long filteredValueTimeout;

    @TimeAnalyse
    @Transactional(readOnly = true)
    public FilteredValue get(@NotNull UuidKey key) throws ServiceException {
        try {
            validationHandler.uuidKeyValidation(key);
            return internalGet(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    private FilteredValue internalGet(UuidKey key) throws CacheException, DaoException {
        if (filteredValueCache.exists(key)) {
            LOGGER.debug("在缓存中发现了 " + key.toString() + " 对应的值，直接返回该值...");
            return filteredValueCache.get(key);
        } else {
            LOGGER.debug("未能在缓存中发现 " + key.toString() + " 对应的值，读取数据访问层...");
            FilteredValue filteredValue = filteredValueDao.get(key);
            LOGGER.debug("将读取到的值 " + filteredValue.toString() + " 回写到缓存中...");
            filteredValueCache.push(key, filteredValue, filteredValueTimeout);
            return filteredValue;
        }
    }

    @TimeAnalyse
    @Transactional
    public UuidKey insert(@NotNull FilteredValue filteredValue) throws ServiceException {
        try {
            validationHandler.filteredValueValidation(filteredValue);

            if (filteredValueCache.exists(filteredValue.getKey()) || filteredValueDao.exists(filteredValue.getKey())) {
                LOGGER.debug("指定的实体 " + filteredValue.toString() + " 已经存在，无法插入...");
                throw new IllegalStateException("指定的实体 " + filteredValue.toString() + " 已经存在，无法插入...");
            } else {
                LOGGER.debug("将指定的实体 " + filteredValue.toString() + " 插入数据访问层中...");
                filteredValueDao.insert(filteredValue);
                LOGGER.debug("将指定的实体 " + filteredValue.toString() + " 插入缓存中...");
                filteredValueCache.push(filteredValue.getKey(), filteredValue, filteredValueTimeout);
                return filteredValue.getKey();
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional
    public UuidKey update(@NotNull FilteredValue filteredValue) throws ServiceException {
        try {
            validationHandler.filteredValueValidation(filteredValue);

            if (!filteredValueCache.exists(filteredValue.getKey()) && !filteredValueDao.exists(filteredValue.getKey())) {
                LOGGER.debug("指定的实体 " + filteredValue.toString() + " 已经存在，无法更新...");
                throw new IllegalStateException("指定的实体 " + filteredValue.toString() + " 已经存在，无法更新...");
            } else {
                FilteredValue oldFilteredValue = internalGet(filteredValue.getKey());
                LOGGER.debug("将指定的实体 " + filteredValue.toString() + " 插入数据访问层中...");
                filteredValueDao.update(filteredValue);
                LOGGER.debug("将指定的实体 " + filteredValue.toString() + " 插入缓存中...");
                filteredValueCache.push(filteredValue.getKey(), filteredValue, filteredValueTimeout);
                return filteredValue.getKey();
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional
    public void delete(@NotNull UuidKey key) throws ServiceException {
        try {
            validationHandler.uuidKeyValidation(key);

            if (!filteredValueCache.exists(key) && !filteredValueDao.exists(key)) {
                LOGGER.debug("指定的键 " + key.toString() + " 不存在，无法删除...");
                throw new IllegalStateException("指定的键 " + key.toString() + " 不存在，无法删除...");
            } else {
                FilteredValue oldFilteredValue = internalGet(key);
                LOGGER.debug("清除实体 " + key.toString() + " 对应的子项缓存...");
                LOGGER.debug("将指定的FilteredValue从缓存中删除...");
                filteredValueCache.delete(key);
                LOGGER.debug("将指定的FilteredValue从数据访问层中删除...");
                filteredValueDao.delete(key);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}