package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.cache.PointHasTriggerInfoCache;
import com.dwarfeng.fdr.stack.cache.TriggerInfoCache;
import com.dwarfeng.fdr.stack.dao.TriggerInfoDao;
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
public class TriggerInfoMaintainServiceDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggerInfoMaintainServiceDelegate.class);

    @Autowired
    private TriggerInfoDao triggerInfoDao;
    @Autowired
    private TriggerInfoCache triggerInfoCache;
    @Autowired
    private PointHasTriggerInfoCache pointHasTriggerInfoCache;
    @Autowired
    private ValidationHandler validationHandler;

    @Value("${cache.timeout.entity.trigger_info}")
    private long triggerInfoTimeout;
    @Value("${cache.timeout.one_to_many.point_has_trigger_info}")
    private long pointHasTriggerInfoTimeout;
    @Value("${cache.batch_fetch_size.trigger_info}")
    private int triggerInfoFetchSize;

    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager", readOnly = true)
    public TriggerInfo get(@NotNull UuidKey key) throws ServiceException {
        try {
            validationHandler.uuidKeyValidation(key);
            return internalGet(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    private TriggerInfo internalGet(UuidKey key) throws CacheException, DaoException {
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
    @Transactional(transactionManager = "daoTransactionManager")
    public UuidKey insert(@NotNull TriggerInfo triggerInfo) throws ServiceException {
        try {
            validationHandler.triggerInfoValidation(triggerInfo);

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

    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager")
    public UuidKey update(@NotNull TriggerInfo triggerInfo) throws ServiceException {
        try {
            validationHandler.triggerInfoValidation(triggerInfo);

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
    @Transactional(transactionManager = "daoTransactionManager")
    public void delete(@NotNull UuidKey key) throws ServiceException {
        try {
            validationHandler.uuidKeyValidation(key);

            if (!triggerInfoCache.exists(key) && !triggerInfoDao.exists(key)) {
                LOGGER.debug("指定的键 " + key.toString() + " 不存在，无法删除...");
                throw new IllegalStateException("指定的键 " + key.toString() + " 不存在，无法删除...");
            } else {
                TriggerInfo oldTriggerInfo = internalGet(key);
                if (Objects.nonNull(oldTriggerInfo.getPointKey())) {
                    LOGGER.debug("清除旧实体 " + oldTriggerInfo.toString() + " 对应的父项缓存...");
                    pointHasTriggerInfoCache.delete(oldTriggerInfo.getPointKey());
                }
                LOGGER.debug("将指定的TriggerInfo从缓存中删除...");
                triggerInfoCache.delete(key);
                LOGGER.debug("将指定的TriggerInfo从数据访问层中删除...");
                triggerInfoDao.delete(key);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }


    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager", readOnly = true)
    public PagedData<TriggerInfo> getTriggerInfos(UuidKey pointUuid, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        try {
            validationHandler.uuidKeyValidation(pointUuid);
            validationHandler.lookupPagingInfoValidation(lookupPagingInfo);

            //定义中间变量。
            List<TriggerInfo> triggerInfos;
            long count;

            if (pointHasTriggerInfoCache.exists(pointUuid)) {
                LOGGER.debug("在缓存中发现了 " + pointUuid.toString() + " 对应的子项列表，直接返回缓存中的值...");
                triggerInfos = pointHasTriggerInfoCache.get(pointUuid, lookupPagingInfo.getPage() * lookupPagingInfo.getRows(), lookupPagingInfo.getRows());
                count = pointHasTriggerInfoCache.size(pointUuid);
            } else {
                LOGGER.debug("查询指定的TriggerInfo对应的子项...");
                triggerInfos = triggerInfoDao.getTriggerInfos(pointUuid, lookupPagingInfo);
                count = triggerInfoDao.getTriggerInfoCount(pointUuid);
                if (count > 0) {
                    for (TriggerInfo triggerInfo : triggerInfos) {
                        LOGGER.debug("将查询到的的实体 " + triggerInfo.toString() + " 插入缓存中...");
                        triggerInfoCache.push(triggerInfo.getKey(), triggerInfo, triggerInfoTimeout);
                    }
                    LOGGER.debug("抓取实体 " + pointUuid.toString() + " 对应的子项并插入缓存...");
                    fetchTriggerInfo2Cache(pointUuid);
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

    @Transactional(transactionManager = "daoTransactionManager")
    @TimeAnalyse
    @Async
    public void fetchTriggerInfo2Cache(UuidKey uuidKey) {
        try {
            int totlePage;
            int currPage;
            long count = triggerInfoDao.getTriggerInfoCount(uuidKey);
            totlePage = Math.max((int) Math.ceil((double) count / triggerInfoFetchSize), 1);
            currPage = 0;
            pointHasTriggerInfoCache.delete(uuidKey);
            do {
                LookupPagingInfo lookupPagingInfo = new LookupPagingInfo(currPage++, triggerInfoFetchSize);
                List<TriggerInfo> childs = triggerInfoDao.getTriggerInfos(uuidKey, lookupPagingInfo);
                if (childs.size() > 0) {
                    pointHasTriggerInfoCache.push(uuidKey, childs, pointHasTriggerInfoTimeout);
                }
            } while (currPage < totlePage);
        } catch (Exception e) {
            LOGGER.warn("将分类 " + uuidKey.toString() + " 的子项添加进入缓存时发生异常，异常信息如下", e);
        }
    }
}
