package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.cache.EnabledTriggerInfoCache;
import com.dwarfeng.fdr.stack.cache.TriggerInfoCache;
import com.dwarfeng.fdr.stack.cache.TriggeredValueCache;
import com.dwarfeng.fdr.stack.dao.TriggerInfoDao;
import com.dwarfeng.fdr.stack.dao.TriggeredValueDao;
import com.dwarfeng.fdr.stack.service.TriggerInfoMaintainService;
import com.dwarfeng.fdr.stack.service.TriggeredValueMaintainService;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TriggerInfoMaintainServiceImpl implements TriggerInfoMaintainService {

    @Autowired
    private KeyFetcher<LongIdKey> keyFetcher;

    @Autowired
    private TriggerInfoDao triggerInfoDao;
    @Autowired
    private TriggeredValueDao triggeredValueDao;

    @Autowired
    private TriggerInfoCache triggerInfoCache;
    @Autowired
    private TriggeredValueCache triggeredValueCache;
    @Autowired
    private EnabledTriggerInfoCache enabledTriggerInfoCache;

    @Autowired
    private ServiceExceptionMapper sem;

    @Value("${cache.timeout.entity.trigger_info}")
    private long triggerInfoTimeout;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public boolean exists(LongIdKey key) throws ServiceException {
        try {
            return internalExists(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("判断实体是否存在时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private boolean internalExists(LongIdKey key) throws Exception {
        return triggerInfoCache.exists(key) || triggerInfoDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public TriggerInfo get(LongIdKey key) throws ServiceException {
        try {
            return internalGet(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private TriggerInfo internalGet(LongIdKey key) throws Exception {
        if (triggerInfoCache.exists(key)) {
            return triggerInfoCache.get(key);
        } else {
            if (!triggerInfoDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            TriggerInfo triggerInfo = triggerInfoDao.get(key);
            triggerInfoCache.push(triggerInfo, triggerInfoTimeout);
            return triggerInfo;
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public LongIdKey insert(TriggerInfo triggerInfo) throws ServiceException {
        try {
            if (Objects.nonNull(triggerInfo.getKey()) && internalExists(triggerInfo.getKey())) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_EXISTED);
            }
            if (Objects.isNull(triggerInfo.getKey())) {
                triggerInfo.setKey(keyFetcher.fetchKey());
            }

            if (Objects.nonNull(triggerInfo.getPointKey())) {
                enabledTriggerInfoCache.delete(triggerInfo.getPointKey());
            }

            triggerInfoCache.push(triggerInfo, triggerInfoTimeout);
            return triggerInfoDao.insert(triggerInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void update(TriggerInfo triggerInfo) throws ServiceException {
        try {
            if (Objects.nonNull(triggerInfo.getKey()) && !internalExists(triggerInfo.getKey())) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }

            TriggerInfo oldTriggerInfo = internalGet(triggerInfo.getKey());
            if (Objects.nonNull(oldTriggerInfo.getPointKey())) {
                enabledTriggerInfoCache.delete(oldTriggerInfo.getPointKey());
            }

            if (Objects.nonNull(triggerInfo.getPointKey())) {
                enabledTriggerInfoCache.delete(triggerInfo.getPointKey());
            }

            triggerInfoCache.push(triggerInfo, triggerInfoTimeout);
            triggerInfoDao.update(triggerInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("更新实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(LongIdKey key) throws ServiceException {
        try {
            if (!internalExists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            internalDelete(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("删除实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private void internalDelete(LongIdKey key) throws Exception {
        TriggerInfo oldTriggerInfo = internalGet(key);
        if (Objects.nonNull(oldTriggerInfo.getPointKey())) {
            enabledTriggerInfoCache.delete(oldTriggerInfo.getPointKey());
        }

        List<LongIdKey> triggeredValueKeys = triggeredValueDao.lookup(TriggeredValueMaintainService.CHILD_FOR_TRIGGER, new Object[]{key})
                .stream().map(TriggeredValue::getKey).collect(Collectors.toList());
        triggeredValueDao.batchDelete(triggeredValueKeys);
        triggeredValueCache.batchDelete(triggeredValueKeys);

        triggerInfoDao.delete(key);
        triggerInfoCache.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<TriggerInfo> lookup(String preset, Object[] objs) throws ServiceException {
        try {
            return PagingUtil.pagedData(triggerInfoDao.lookup(preset, objs));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<TriggerInfo> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        try {
            return PagingUtil.pagedData(pagingInfo, triggerInfoDao.lookupCount(preset, objs), triggerInfoDao.lookup(preset, objs, pagingInfo));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void lookupDelete(String preset, Object[] objs) throws ServiceException {
        try {
            List<LongIdKey> longIdKeys = triggerInfoDao.lookupDelete(preset, objs);
            for (LongIdKey longIdKey : longIdKeys) {
                internalDelete(longIdKey);
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询并删除实体时发生异常", LogLevel.WARN, sem, e);
        }
    }
}
