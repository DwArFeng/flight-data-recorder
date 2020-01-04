package com.dwarfeng.fdr.sdk.crud;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Component
@Validated
public class TriggerInfoCrudHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggerInfoCrudHelper.class);

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

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public TriggerInfo get(@NotNull GuidKey key) throws ServiceException {
        try {
            return noAdviseGet(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public TriggerInfo noAdviseGet(GuidKey key) throws CacheException, DaoException {
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
        try {
            return noAdviseInsert(triggerInfo);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public GuidKey noAdviseInsert(TriggerInfo triggerInfo) throws CacheException, DaoException, com.dwarfeng.sfds.stack.exception.ServiceException {
        //如果实体中没有主键，则向主键服务请求一个主键。
        maySetGuid(triggerInfo);

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
    }

    private void maySetGuid(TriggerInfo triggerInfo) throws com.dwarfeng.sfds.stack.exception.ServiceException {
        if (Objects.isNull(triggerInfo.getKey())) {
            LOGGER.debug("实体 " + triggerInfo.toString() + "没有主键，将从服务中获取GUID...");
            long guid = guidApi.nextGuid();
            LOGGER.debug("从服务中获取了guid: " + guid);
            triggerInfo.setKey(new GuidKey(guid));
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey update(@NotNull TriggerInfo triggerInfo) throws ServiceException {
        try {
            return noAdviseUpdate(triggerInfo);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public GuidKey noAdviseUpdate(TriggerInfo triggerInfo) throws CacheException, DaoException {
        if (!triggerInfoCache.exists(triggerInfo.getKey()) && !triggerInfoDao.exists(triggerInfo.getKey())) {
            LOGGER.debug("指定的实体 " + triggerInfo.toString() + " 已经存在，无法更新...");
            throw new IllegalStateException("指定的实体 " + triggerInfo.toString() + " 已经存在，无法更新...");
        } else {
            TriggerInfo oldTriggerInfo = noAdviseGet(triggerInfo.getKey());
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
        if (!triggerInfoCache.exists(key) && !triggerInfoDao.exists(key)) {
            LOGGER.debug("指定的键 " + key.toString() + " 不存在，无法删除...");
            throw new IllegalStateException("指定的键 " + key.toString() + " 不存在，无法删除...");
        } else {
            TriggerInfo oldTriggerInfo = noAdviseGet(key);
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
    }
}
