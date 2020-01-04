package com.dwarfeng.fdr.sdk.crud;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.cache.FilterInfoCache;
import com.dwarfeng.fdr.stack.cache.FilteredValueCache;
import com.dwarfeng.fdr.stack.cache.PointHasFilterInfoCache;
import com.dwarfeng.fdr.stack.dao.FilterInfoDao;
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
public class FilterInfoCrudHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterInfoCrudHelper.class);

    @Autowired
    private FilterInfoDao filterInfoDao;
    @Autowired
    private FilterInfoCache filterInfoCache;
    @Autowired
    private PointHasFilterInfoCache pointHasFilterInfoCache;

    @Autowired
    private FilteredValueDao filteredValueDao;
    @Autowired
    private FilteredValueCache filteredValueCache;

    @Autowired
    private GuidApi guidApi;

    @Value("${cache.timeout.entity.filter_info}")
    private long filterInfoTimeout;

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public FilterInfo get(@NotNull GuidKey key) throws ServiceException {
        try {
            return noAdviseGet(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public FilterInfo noAdviseGet(GuidKey key) throws CacheException, DaoException {
        if (filterInfoCache.exists(key)) {
            LOGGER.debug("在缓存中发现了 " + key.toString() + " 对应的值，直接返回该值...");
            return filterInfoCache.get(key);
        } else {
            LOGGER.debug("未能在缓存中发现 " + key.toString() + " 对应的值，读取数据访问层...");
            FilterInfo filterInfo = filterInfoDao.get(key);
            LOGGER.debug("将读取到的值 " + filterInfo.toString() + " 回写到缓存中...");
            filterInfoCache.push(key, filterInfo, filterInfoTimeout);
            return filterInfo;
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey insert(@NotNull FilterInfo filterInfo) throws ServiceException {
        try {
            return noAdviseInsert(filterInfo);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public GuidKey noAdviseInsert(FilterInfo filterInfo) throws CacheException, DaoException, com.dwarfeng.sfds.stack.exception.ServiceException {
        //如果实体中没有主键，则向主键服务请求一个主键。
        maySetGuid(filterInfo);

        if (filterInfoCache.exists(filterInfo.getKey()) || filterInfoDao.exists(filterInfo.getKey())) {
            LOGGER.debug("指定的实体 " + filterInfo.toString() + " 已经存在，无法插入...");
            throw new IllegalStateException("指定的实体 " + filterInfo.toString() + " 已经存在，无法插入...");
        } else {
            LOGGER.debug("将指定的实体 " + filterInfo.toString() + " 插入数据访问层中...");
            filterInfoDao.insert(filterInfo);
            LOGGER.debug("将指定的实体 " + filterInfo.toString() + " 插入缓存中...");
            filterInfoCache.push(filterInfo.getKey(), filterInfo, filterInfoTimeout);
            if (Objects.nonNull(filterInfo.getPointKey())) {
                LOGGER.debug("清除实体 " + filterInfo.toString() + " 对应的父项缓存...");
                pointHasFilterInfoCache.delete(filterInfo.getPointKey());
            }
            return filterInfo.getKey();
        }
    }

    private void maySetGuid(FilterInfo filterInfo) throws com.dwarfeng.sfds.stack.exception.ServiceException {
        if (Objects.isNull(filterInfo.getKey())) {
            LOGGER.debug("实体 " + filterInfo.toString() + "没有主键，将从服务中获取GUID...");
            long guid = guidApi.nextGuid();
            LOGGER.debug("从服务中获取了guid: " + guid);
            filterInfo.setKey(new GuidKey(guid));
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey update(@NotNull FilterInfo filterInfo) throws ServiceException {
        try {
            return noAdviseUpdate(filterInfo);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public GuidKey noAdviseUpdate(FilterInfo filterInfo) throws CacheException, DaoException {
        if (!filterInfoCache.exists(filterInfo.getKey()) && !filterInfoDao.exists(filterInfo.getKey())) {
            LOGGER.debug("指定的实体 " + filterInfo.toString() + " 已经存在，无法更新...");
            throw new IllegalStateException("指定的实体 " + filterInfo.toString() + " 已经存在，无法更新...");
        } else {
            FilterInfo oldFilterInfo = noAdviseGet(filterInfo.getKey());
            if (Objects.nonNull(oldFilterInfo.getPointKey())) {
                LOGGER.debug("清除旧实体 " + oldFilterInfo.toString() + " 对应的父项缓存...");
                pointHasFilterInfoCache.delete(oldFilterInfo.getPointKey());
            }
            LOGGER.debug("将指定的实体 " + filterInfo.toString() + " 插入数据访问层中...");
            filterInfoDao.update(filterInfo);
            LOGGER.debug("将指定的实体 " + filterInfo.toString() + " 插入缓存中...");
            filterInfoCache.push(filterInfo.getKey(), filterInfo, filterInfoTimeout);
            if (Objects.nonNull(filterInfo.getPointKey())) {
                LOGGER.debug("清除新实体 " + filterInfo.toString() + " 对应的父项缓存...");
                pointHasFilterInfoCache.delete(filterInfo.getPointKey());
            }
            return filterInfo.getKey();
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(@NotNull GuidKey key) throws ServiceException {
        try {
            noAdviseDelete(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public void noAdviseDelete(GuidKey key) throws CacheException, DaoException {
        if (!filterInfoCache.exists(key) && !filterInfoDao.exists(key)) {
            LOGGER.debug("指定的键 " + key.toString() + " 不存在，无法删除...");
            throw new IllegalStateException("指定的键 " + key.toString() + " 不存在，无法删除...");
        } else {
            FilterInfo oldFilterInfo = noAdviseGet(key);
            if (Objects.nonNull(oldFilterInfo.getPointKey())) {
                LOGGER.debug("清除旧实体 " + oldFilterInfo.toString() + " 对应的父项缓存...");
                pointHasFilterInfoCache.delete(oldFilterInfo.getPointKey());
            }

            LOGGER.debug("删除过滤器信息 " + key.toString() + " 相关联的被过滤数据与相关缓存...");
            filteredValueDao.deleteAllByFilterInfo(key);
            filteredValueCache.deleteAllByFilterInfo(key);

            LOGGER.debug("删除过滤器信息 " + key.toString() + " 与缓存...");
            filterInfoCache.delete(key);
            filterInfoDao.delete(key);
        }
    }
}
