package com.dwarfeng.fdr.sdk.crud;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.cache.FilteredValueCache;
import com.dwarfeng.fdr.stack.dao.FilteredValueDao;
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
public class FilteredValueCrudHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilteredValueCrudHelper.class);

    @Autowired
    private FilteredValueDao filteredValueDao;
    @Autowired
    private FilteredValueCache filteredValueCache;

    @Autowired
    private GuidApi guidApi;

    @Value("${cache.timeout.entity.filtered_value}")
    private long filteredValueTimeout;

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public FilteredValue get(@NotNull GuidKey key) throws ServiceException {
        try {
            return noAdviseGet(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public FilteredValue noAdviseGet(GuidKey key) throws CacheException, DaoException {
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
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey insert(@NotNull FilteredValue filteredValue) throws ServiceException {
        try {
            return noAdviseInsert(filteredValue);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public GuidKey noAdviseInsert(FilteredValue filteredValue) throws com.dwarfeng.sfds.stack.exception.ServiceException, CacheException, DaoException {
        //如果实体中没有主键，则向主键服务请求一个主键。
        maySetGuid(filteredValue);

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
    }

    private void maySetGuid(FilteredValue filteredValue) throws com.dwarfeng.sfds.stack.exception.ServiceException {
        if (Objects.isNull(filteredValue.getKey())) {
            LOGGER.debug("实体 " + filteredValue.toString() + "没有主键，将从服务中获取GUID...");
            long guid = guidApi.nextGuid();
            LOGGER.debug("从服务中获取了guid: " + guid);
            filteredValue.setKey(new GuidKey(guid));
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey update(@NotNull FilteredValue filteredValue) throws ServiceException {
        try {
            return noAdviseUpdate(filteredValue);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public GuidKey noAdviseUpdate(FilteredValue filteredValue) throws CacheException, DaoException {
        if (!filteredValueCache.exists(filteredValue.getKey()) && !filteredValueDao.exists(filteredValue.getKey())) {
            LOGGER.debug("指定的实体 " + filteredValue.toString() + " 已经存在，无法更新...");
            throw new IllegalStateException("指定的实体 " + filteredValue.toString() + " 已经存在，无法更新...");
        } else {
            FilteredValue oldFilteredValue = noAdviseGet(filteredValue.getKey());
            LOGGER.debug("将指定的实体 " + filteredValue.toString() + " 插入数据访问层中...");
            filteredValueDao.update(filteredValue);
            LOGGER.debug("将指定的实体 " + filteredValue.toString() + " 插入缓存中...");
            filteredValueCache.push(filteredValue.getKey(), filteredValue, filteredValueTimeout);
            return filteredValue.getKey();
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(@NotNull GuidKey key) throws ServiceException {
        try {
            if (!filteredValueCache.exists(key) && !filteredValueDao.exists(key)) {
                LOGGER.debug("指定的键 " + key.toString() + " 不存在，无法删除...");
                throw new IllegalStateException("指定的键 " + key.toString() + " 不存在，无法删除...");
            } else {
                FilteredValue oldFilteredValue = noAdviseGet(key);
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

    public void noAdviseDelete(GuidKey key) throws CacheException, DaoException {
        if (!filteredValueCache.exists(key) && !filteredValueDao.exists(key)) {
            LOGGER.debug("指定的键 " + key.toString() + " 不存在，无法删除...");
            throw new IllegalStateException("指定的键 " + key.toString() + " 不存在，无法删除...");
        } else {
            FilteredValue oldFilteredValue = noAdviseGet(key);
            LOGGER.debug("清除实体 " + key.toString() + " 对应的子项缓存...");
            LOGGER.debug("将指定的FilteredValue从缓存中删除...");
            filteredValueCache.delete(key);
            LOGGER.debug("将指定的FilteredValue从数据访问层中删除...");
            filteredValueDao.delete(key);
        }
    }
}
