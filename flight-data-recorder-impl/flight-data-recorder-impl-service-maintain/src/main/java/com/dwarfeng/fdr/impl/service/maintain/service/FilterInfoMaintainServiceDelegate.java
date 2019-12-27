package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.cache.FilterInfoCache;
import com.dwarfeng.fdr.stack.cache.FilteredValueCache;
import com.dwarfeng.fdr.stack.cache.PointHasFilterInfoCache;
import com.dwarfeng.fdr.stack.dao.FilterInfoDao;
import com.dwarfeng.fdr.stack.dao.FilteredValueDao;
import com.dwarfeng.fdr.stack.exception.CacheException;
import com.dwarfeng.fdr.stack.exception.DaoException;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.handler.ValidationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Component
@Validated
public class FilterInfoMaintainServiceDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterInfoMaintainServiceDelegate.class);

    @Autowired
    private FilterInfoDao filterInfoDao;
    @Autowired
    private FilterInfoCache filterInfoCache;
    @Autowired
    private PointHasFilterInfoCache pointHasFilterInfoCache;
    @Autowired
    private ValidationHandler validationHandler;

    @Autowired
    private FilteredValueDao filteredValueDao;
    @Autowired
    private FilteredValueCache filteredValueCache;

    @Value("${cache.timeout.entity.filter_info}")
    private long filterInfoTimeout;
    @Value("${cache.timeout.one_to_many.point_has_filter_info}")
    private long pointHasFilterInfoTimeout;
    @Value("${cache.batch_fetch_size.filter_info}")
    private int filterInfoFetchSize;

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public FilterInfo get(@NotNull UuidKey key) throws ServiceException {
        try {
            validationHandler.uuidKeyValidation(key);
            return internalGet(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    private FilterInfo internalGet(UuidKey key) throws CacheException, DaoException {
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
    public UuidKey insert(@NotNull FilterInfo filterInfo) throws ServiceException {
        try {
            validationHandler.filterInfoValidation(filterInfo);

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
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public UuidKey update(@NotNull FilterInfo filterInfo) throws ServiceException {
        try {
            validationHandler.filterInfoValidation(filterInfo);

            if (!filterInfoCache.exists(filterInfo.getKey()) && !filterInfoDao.exists(filterInfo.getKey())) {
                LOGGER.debug("指定的实体 " + filterInfo.toString() + " 已经存在，无法更新...");
                throw new IllegalStateException("指定的实体 " + filterInfo.toString() + " 已经存在，无法更新...");
            } else {
                FilterInfo oldFilterInfo = internalGet(filterInfo.getKey());
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
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(@NotNull UuidKey key) throws ServiceException {
        try {
            validationHandler.uuidKeyValidation(key);

            if (!filterInfoCache.exists(key) && !filterInfoDao.exists(key)) {
                LOGGER.debug("指定的键 " + key.toString() + " 不存在，无法删除...");
                throw new IllegalStateException("指定的键 " + key.toString() + " 不存在，无法删除...");
            } else {
                FilterInfo oldFilterInfo = internalGet(key);
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
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }


    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<FilterInfo> getFilterInfos(UuidKey pointUuid, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        try {
            validationHandler.uuidKeyValidation(pointUuid);
            validationHandler.lookupPagingInfoValidation(lookupPagingInfo);

            //定义中间变量。
            List<FilterInfo> filterInfos;
            long count;

            if (pointHasFilterInfoCache.exists(pointUuid)) {
                LOGGER.debug("在缓存中发现了 " + pointUuid.toString() + " 对应的子项列表，直接返回缓存中的值...");
                filterInfos = pointHasFilterInfoCache.get(pointUuid, lookupPagingInfo.getPage() * lookupPagingInfo.getRows(), lookupPagingInfo.getRows());
                count = pointHasFilterInfoCache.size(pointUuid);
            } else {
                LOGGER.debug("查询指定的FilterInfo对应的子项...");
                filterInfos = filterInfoDao.getFilterInfos(pointUuid, lookupPagingInfo);
                count = filterInfoDao.getFilterInfoCount(pointUuid);
                if (count > 0) {
                    for (FilterInfo filterInfo : filterInfos) {
                        LOGGER.debug("将查询到的的实体 " + filterInfo.toString() + " 插入缓存中...");
                        filterInfoCache.push(filterInfo.getKey(), filterInfo, filterInfoTimeout);
                    }
                    LOGGER.debug("抓取实体 " + pointUuid.toString() + " 对应的子项并插入缓存...");
                    fetchFilterInfo2Cache(pointUuid);
                }
            }
            return new PagedData<>(
                    lookupPagingInfo.getPage(),
                    Math.max((int) Math.ceil((double) count / lookupPagingInfo.getRows()), 1),
                    lookupPagingInfo.getRows(),
                    count,
                    filterInfos
            );
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }


    @Transactional(transactionManager = "hibernateTransactionManager")
    @TimeAnalyse
    @Async
    public void fetchFilterInfo2Cache(UuidKey uuidKey) {
        try {
            int totlePage;
            int currPage;
            long count = filterInfoDao.getFilterInfoCount(uuidKey);
            totlePage = Math.max((int) Math.ceil((double) count / filterInfoFetchSize), 1);
            currPage = 0;
            pointHasFilterInfoCache.delete(uuidKey);
            do {
                LookupPagingInfo lookupPagingInfo = new LookupPagingInfo(true, currPage++, filterInfoFetchSize);
                List<FilterInfo> childs = filterInfoDao.getFilterInfos(uuidKey, lookupPagingInfo);
                if (childs.size() > 0) {
                    pointHasFilterInfoCache.push(uuidKey, childs, pointHasFilterInfoTimeout);
                }
            } while (currPage < totlePage);
        } catch (Exception e) {
            LOGGER.warn("将分类 " + uuidKey.toString() + " 的子项添加进入缓存时发生异常，异常信息如下", e);
        }
    }
}
