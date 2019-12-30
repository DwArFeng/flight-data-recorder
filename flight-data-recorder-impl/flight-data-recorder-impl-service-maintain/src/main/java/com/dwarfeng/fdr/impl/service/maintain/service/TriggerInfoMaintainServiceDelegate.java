package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.cache.PointHasTriggerInfoCache;
import com.dwarfeng.fdr.stack.cache.TriggerInfoCache;
import com.dwarfeng.fdr.stack.cache.TriggeredValueCache;
import com.dwarfeng.fdr.stack.dao.TriggerInfoDao;
import com.dwarfeng.fdr.stack.dao.TriggeredValueDao;
import com.dwarfeng.fdr.stack.exception.CacheException;
import com.dwarfeng.fdr.stack.exception.DaoException;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.sfds.api.GuidApi;
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
public class TriggerInfoMaintainServiceDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggerInfoMaintainServiceDelegate.class);

    @Autowired
    private TriggerInfoDao triggerInfoDao;
    @Autowired
    private TriggerInfoCache triggerInfoCache;
    @Autowired
    private PointHasTriggerInfoCache pointHasTriggerInfoCache;

    @Autowired
    private TriggeredValueDao triggeredValueDao;
    @Autowired
    private TriggeredValueCache triggeredValueCache;

    @Autowired
    private GuidApi guidApi;

    @Value("${cache.timeout.entity.trigger_info}")
    private long triggerInfoTimeout;
    @Value("${cache.timeout.one_to_many.point_has_trigger_info}")
    private long pointHasTriggerInfoTimeout;
    @Value("${cache.batch_fetch_size.trigger_info}")
    private int triggerInfoFetchSize;

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public TriggerInfo get(@NotNull GuidKey key) throws ServiceException {
        try {
            return internalGet(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    private TriggerInfo internalGet(GuidKey key) throws CacheException, DaoException {
        if (triggerInfoCache.exists(key)) {
            LOGGER.debug("在缓存中发现了 " + key.toString() + " 对应的值，直接返回该值...");
            return triggerInfoCache.get(key);
        } else {
            LOGGER.debug("未能在缓存中发现 " + key.toString() + " 对应的值，读取数据访问层...");
            TriggerInfo triggerInfo = triggerInfoDao.get(key);
            LOGGER.debug("将读取到的值 " + triggerInfo.toString() + " 回写到缓存中...");
            triggerInfoCache.push(key, triggerInfo, triggerInfoTimeout);
            return triggerInfo;
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey insert(@NotNull TriggerInfo triggerInfo) throws ServiceException {
        //如果实体中没有主键，则向主键服务请求一个主键。
        maySetGuid(triggerInfo);

        try {
            if (triggerInfoCache.exists(triggerInfo.getKey()) || triggerInfoDao.exists(triggerInfo.getKey())) {
                LOGGER.debug("指定的实体 " + triggerInfo.toString() + " 已经存在，无法插入...");
                throw new IllegalStateException("指定的实体 " + triggerInfo.toString() + " 已经存在，无法插入...");
            } else {
                LOGGER.debug("将指定的实体 " + triggerInfo.toString() + " 插入数据访问层中...");
                triggerInfoDao.insert(triggerInfo);
                LOGGER.debug("将指定的实体 " + triggerInfo.toString() + " 插入缓存中...");
                triggerInfoCache.push(triggerInfo.getKey(), triggerInfo, triggerInfoTimeout);
                if (Objects.nonNull(triggerInfo.getPointKey())) {
                    LOGGER.debug("清除实体 " + triggerInfo.toString() + " 对应的父项缓存...");
                    pointHasTriggerInfoCache.delete(triggerInfo.getPointKey());
                }
                return triggerInfo.getKey();
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    private void maySetGuid(TriggerInfo triggerInfo) throws ServiceException {
        if (Objects.isNull(triggerInfo.getKey())) {
            LOGGER.debug("实体 " + triggerInfo.toString() + "没有主键，将从服务中获取GUID...");
            try {
                long guid = guidApi.nextGuid();
                LOGGER.debug("从服务中获取了guid: " + guid);
                triggerInfo.setKey(new GuidKey(guid));
            } catch (Exception e) {
                LOGGER.warn("主键获取失败，将抛出异常...", e);
                throw new ServiceException(ServiceExceptionCodes.GUID_FETCH_FAILED);
            }
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey update(@NotNull TriggerInfo triggerInfo) throws ServiceException {
        try {
            if (!triggerInfoCache.exists(triggerInfo.getKey()) && !triggerInfoDao.exists(triggerInfo.getKey())) {
                LOGGER.debug("指定的实体 " + triggerInfo.toString() + " 已经存在，无法更新...");
                throw new IllegalStateException("指定的实体 " + triggerInfo.toString() + " 已经存在，无法更新...");
            } else {
                TriggerInfo oldTriggerInfo = internalGet(triggerInfo.getKey());
                if (Objects.nonNull(oldTriggerInfo.getPointKey())) {
                    LOGGER.debug("清除旧实体 " + oldTriggerInfo.toString() + " 对应的父项缓存...");
                    pointHasTriggerInfoCache.delete(oldTriggerInfo.getPointKey());
                }
                LOGGER.debug("将指定的实体 " + triggerInfo.toString() + " 插入数据访问层中...");
                triggerInfoDao.update(triggerInfo);
                LOGGER.debug("将指定的实体 " + triggerInfo.toString() + " 插入缓存中...");
                triggerInfoCache.push(triggerInfo.getKey(), triggerInfo, triggerInfoTimeout);
                if (Objects.nonNull(triggerInfo.getPointKey())) {
                    LOGGER.debug("清除新实体 " + triggerInfo.toString() + " 对应的父项缓存...");
                    pointHasTriggerInfoCache.delete(triggerInfo.getPointKey());
                }
                return triggerInfo.getKey();
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(@NotNull GuidKey key) throws ServiceException {
        try {
            if (!triggerInfoCache.exists(key) && !triggerInfoDao.exists(key)) {
                LOGGER.debug("指定的键 " + key.toString() + " 不存在，无法删除...");
                throw new IllegalStateException("指定的键 " + key.toString() + " 不存在，无法删除...");
            } else {
                TriggerInfo oldTriggerInfo = internalGet(key);
                if (Objects.nonNull(oldTriggerInfo.getPointKey())) {
                    LOGGER.debug("清除旧实体 " + oldTriggerInfo.toString() + " 对应的父项缓存...");
                    pointHasTriggerInfoCache.delete(oldTriggerInfo.getPointKey());
                }

                LOGGER.debug("删除触发器信息 " + key.toString() + " 相关联的被过滤数据与相关缓存...");
                triggeredValueDao.deleteAllByTriggerInfo(key);
                triggeredValueCache.deleteAllByTriggerInfo(key);

                LOGGER.debug("删除触发器信息 " + key.toString() + " 与缓存...");
                triggerInfoCache.delete(key);
                triggerInfoDao.delete(key);
            }
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
                triggerInfos = pointHasTriggerInfoCache.get(pointGuid, lookupPagingInfo.getPage() * lookupPagingInfo.getRows(), lookupPagingInfo.getRows());
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
                    fetchTriggerInfo2Cache(pointGuid);
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
