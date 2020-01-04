package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.sdk.crud.FilterInfoCrudHelper;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.cache.FilterInfoCache;
import com.dwarfeng.fdr.stack.cache.PointHasFilterInfoCache;
import com.dwarfeng.fdr.stack.dao.FilterInfoDao;
import com.dwarfeng.fdr.stack.exception.ServiceException;
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

@Component
@Validated
public class FilterInfoMaintainServiceDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterInfoMaintainServiceDelegate.class);

    @Autowired
    private FilterInfoCrudHelper helper;
    @Autowired
    private AsyncBean asyncBean;

    @Autowired
    private FilterInfoDao filterInfoDao;
    @Autowired
    private FilterInfoCache filterInfoCache;
    @Autowired
    private PointHasFilterInfoCache pointHasFilterInfoCache;

    @Value("${cache.timeout.entity.filter_info}")
    private long filterInfoTimeout;

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public FilterInfo get(@NotNull GuidKey key) throws ServiceException {
        try {
            return helper.noAdviseGet(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey insert(@NotNull FilterInfo filterInfo) throws ServiceException {
        try {
            return helper.noAdviseInsert(filterInfo);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey update(@NotNull FilterInfo filterInfo) throws ServiceException {
        try {
            return helper.noAdviseUpdate(filterInfo);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(@NotNull GuidKey key) throws ServiceException {
        try {
            helper.noAdviseDelete(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<FilterInfo> getFilterInfos(GuidKey pointGuid, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        try {
            //定义中间变量。
            List<FilterInfo> filterInfos;
            long count;

            if (pointHasFilterInfoCache.exists(pointGuid)) {
                LOGGER.debug("在缓存中发现了 " + pointGuid.toString() + " 对应的子项列表，直接返回缓存中的值...");
                filterInfos = pointHasFilterInfoCache.get(pointGuid, lookupPagingInfo);
                count = pointHasFilterInfoCache.size(pointGuid);
            } else {
                LOGGER.debug("查询指定的FilterInfo对应的子项...");
                filterInfos = filterInfoDao.getFilterInfos(pointGuid, lookupPagingInfo);
                count = filterInfoDao.getFilterInfoCount(pointGuid);
                if (count > 0) {
                    for (FilterInfo filterInfo : filterInfos) {
                        LOGGER.debug("将查询到的的实体 " + filterInfo.toString() + " 插入缓存中...");
                        filterInfoCache.push(filterInfo.getKey(), filterInfo, filterInfoTimeout);
                    }
                    LOGGER.debug("抓取实体 " + pointGuid.toString() + " 对应的子项并插入缓存...");
                    asyncBean.fetchFilterInfo2Cache(pointGuid);
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

    @Component
    public static class AsyncBean {

        @Autowired
        private FilterInfoDao filterInfoDao;
        @Autowired
        private PointHasFilterInfoCache pointHasFilterInfoCache;

        @Value("${cache.timeout.one_to_many.point_has_filter_info}")
        private long pointHasFilterInfoTimeout;
        @Value("${cache.batch_fetch_size.filter_info}")
        private int filterInfoFetchSize;

        @Transactional(transactionManager = "hibernateTransactionManager")
        @TimeAnalyse
        @Async
        public void fetchFilterInfo2Cache(GuidKey guidKey) {
            try {
                int totlePage;
                int currPage;
                long count = filterInfoDao.getFilterInfoCount(guidKey);
                totlePage = Math.max((int) Math.ceil((double) count / filterInfoFetchSize), 1);
                currPage = 0;
                pointHasFilterInfoCache.delete(guidKey);
                do {
                    LookupPagingInfo lookupPagingInfo = new LookupPagingInfo(true, currPage++, filterInfoFetchSize);
                    List<FilterInfo> childs = filterInfoDao.getFilterInfos(guidKey, lookupPagingInfo);
                    if (childs.size() > 0) {
                        pointHasFilterInfoCache.push(guidKey, childs, pointHasFilterInfoTimeout);
                    }
                } while (currPage < totlePage);
            } catch (Exception e) {
                LOGGER.warn("将分类 " + guidKey.toString() + " 的子项添加进入缓存时发生异常，异常信息如下", e);
            }
        }
    }
}
