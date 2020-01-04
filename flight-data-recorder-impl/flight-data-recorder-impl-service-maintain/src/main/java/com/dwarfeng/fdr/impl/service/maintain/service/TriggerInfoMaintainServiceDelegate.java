package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.sdk.crud.TriggerInfoCrudHelper;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.cache.PointHasTriggerInfoCache;
import com.dwarfeng.fdr.stack.cache.TriggerInfoCache;
import com.dwarfeng.fdr.stack.dao.TriggerInfoDao;
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
public class TriggerInfoMaintainServiceDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggerInfoMaintainServiceDelegate.class);

    @Autowired
    private TriggerInfoCrudHelper helper;
    @Autowired
    private AsyncBean asyncBean;

    @Autowired
    private TriggerInfoDao triggerInfoDao;
    @Autowired
    private TriggerInfoCache triggerInfoCache;
    @Autowired
    private PointHasTriggerInfoCache pointHasTriggerInfoCache;

    @Value("${cache.timeout.entity.trigger_info}")
    private long triggerInfoTimeout;

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public TriggerInfo get(@NotNull GuidKey key) throws ServiceException {
        try {
            return helper.noAdviseGet(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey insert(@NotNull TriggerInfo triggerInfo) throws ServiceException {
        try {
            return helper.noAdviseInsert(triggerInfo);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey update(@NotNull TriggerInfo triggerInfo) throws ServiceException {
        try {
            return helper.noAdviseUpdate(triggerInfo);
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
    public PagedData<TriggerInfo> getTriggerInfos(GuidKey pointGuid, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        try {
            //定义中间变量。
            List<TriggerInfo> triggerInfos;
            long count;

            if (pointHasTriggerInfoCache.exists(pointGuid)) {
                LOGGER.debug("在缓存中发现了 " + pointGuid.toString() + " 对应的子项列表，直接返回缓存中的值...");
                triggerInfos = pointHasTriggerInfoCache.get(pointGuid, lookupPagingInfo);
                count = pointHasTriggerInfoCache.size(pointGuid);
            } else {
                LOGGER.debug("查询指定的TriggerInfo对应的子项...");
                triggerInfos = triggerInfoDao.getTriggerInfos(pointGuid, lookupPagingInfo);
                count = triggerInfoDao.getTriggerInfoCount(pointGuid);
                if (count > 0) {
                    for (TriggerInfo triggerInfo : triggerInfos) {
                        LOGGER.debug("将查询到的的实体 " + triggerInfo.toString() + " 插入缓存中...");
                        triggerInfoCache.push(triggerInfo.getKey(), triggerInfo, triggerInfoTimeout);
                    }
                    LOGGER.debug("抓取实体 " + pointGuid.toString() + " 对应的子项并插入缓存...");
                    asyncBean.fetchTriggerInfo2Cache(pointGuid);
                }
            }
            return new PagedData<>(
                    lookupPagingInfo.getPage(),
                    Math.max((int) Math.ceil((double) count / lookupPagingInfo.getRows()), 1),
                    lookupPagingInfo.getRows(),
                    count,
                    triggerInfos
            );
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Component
    public static class AsyncBean {

        @Autowired
        private TriggerInfoDao triggerInfoDao;
        @Autowired
        private PointHasTriggerInfoCache pointHasTriggerInfoCache;

        @Value("${cache.timeout.one_to_many.point_has_trigger_info}")
        private long pointHasTriggerInfoTimeout;
        @Value("${cache.batch_fetch_size.trigger_info}")
        private int triggerInfoFetchSize;

        @Transactional(transactionManager = "hibernateTransactionManager")
        @TimeAnalyse
        @Async
        public void fetchTriggerInfo2Cache(GuidKey guidKey) {
            try {
                int totlePage;
                int currPage;
                long count = triggerInfoDao.getTriggerInfoCount(guidKey);
                totlePage = Math.max((int) Math.ceil((double) count / triggerInfoFetchSize), 1);
                currPage = 0;
                pointHasTriggerInfoCache.delete(guidKey);
                do {
                    LookupPagingInfo lookupPagingInfo = new LookupPagingInfo(true, currPage++, triggerInfoFetchSize);
                    List<TriggerInfo> childs = triggerInfoDao.getTriggerInfos(guidKey, lookupPagingInfo);
                    if (childs.size() > 0) {
                        pointHasTriggerInfoCache.push(guidKey, childs, pointHasTriggerInfoTimeout);
                    }
                } while (currPage < totlePage);
            } catch (Exception e) {
                LOGGER.warn("将分类 " + guidKey.toString() + " 的子项添加进入缓存时发生异常，异常信息如下", e);
            }
        }
    }
}
