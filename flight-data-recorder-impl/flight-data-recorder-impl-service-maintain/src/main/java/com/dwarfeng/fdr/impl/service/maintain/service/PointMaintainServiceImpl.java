package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.fdr.stack.cache.*;
import com.dwarfeng.fdr.stack.dao.*;
import com.dwarfeng.fdr.stack.service.*;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.BehaviorAnalyse;
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
public class PointMaintainServiceImpl implements PointMaintainService {

    @Autowired
    private KeyFetcher<LongIdKey> keyFetcher;

    @Autowired
    private PointDao pointDao;
    @Autowired
    private FilterInfoDao filterInfoDao;
    @Autowired
    private TriggerInfoDao triggerInfoDao;
    @Autowired
    private FilteredValueDao filteredValueDao;
    @Autowired
    private TriggeredValueDao triggeredValueDao;
    @Autowired
    private PersistenceValueDao persistenceValueDao;
    @Autowired
    private RealtimeValueDao realtimeValueDao;

    @Autowired
    private PointCache pointCache;
    @Autowired
    private FilterInfoCache filterInfoCache;
    @Autowired
    private TriggerInfoCache triggerInfoCache;
    @Autowired
    private FilteredValueCache filteredValueCache;
    @Autowired
    private TriggeredValueCache triggeredValueCache;
    @Autowired
    private PersistenceValueCache persistenceValueCache;
    @Autowired
    private RealtimeValueCache realtimeValueCache;

    @Autowired
    private EnabledFilterInfoCache enabledFilterInfoCache;
    @Autowired
    private EnabledTriggerInfoCache enabledTriggerInfoCache;

    @Autowired
    private ServiceExceptionMapper sem;

    @Value("${cache.timeout.entity.point}")
    private long pointTimeout;
    @Value("${cache.timeout.entity.filter_info}")
    private long filterInfoTimeout;
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
        return pointCache.exists(key) || pointDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public Point get(LongIdKey key) throws ServiceException {
        try {
            if (pointCache.exists(key)) {
                return pointCache.get(key);
            } else {
                if (!pointDao.exists(key)) {
                    throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
                }
                Point point = pointDao.get(key);
                pointCache.push(point, pointTimeout);
                return point;
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public LongIdKey insert(Point point) throws ServiceException {
        try {
            if (Objects.nonNull(point.getKey()) && internalExists(point.getKey())) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_EXISTED);
            }
            if (Objects.isNull(point.getKey())) {
                point.setKey(keyFetcher.fetchKey());
            }
            pointCache.push(point, pointTimeout);
            return pointDao.insert(point);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void update(Point point) throws ServiceException {
        try {
            if (Objects.nonNull(point.getKey()) && !internalExists(point.getKey())) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            pointCache.push(point, pointTimeout);
            pointDao.update(point);
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
        //删除与点位直接相关的数据值。
        {
            List<LongIdKey> filteredValueKeys = filteredValueDao.lookup(FilteredValueMaintainService.CHILD_FOR_POINT, new Object[]{key})
                    .stream().map(FilteredValue::getKey).collect(Collectors.toList());
            filteredValueDao.batchDelete(filteredValueKeys);
            filteredValueCache.batchDelete(filteredValueKeys);

            List<LongIdKey> triggeredValueKeys = triggeredValueDao.lookup(TriggeredValueMaintainService.CHILD_FOR_POINT, new Object[]{key})
                    .stream().map(TriggeredValue::getKey).collect(Collectors.toList());
            triggeredValueDao.batchDelete(triggeredValueKeys);
            triggeredValueCache.batchDelete(triggeredValueKeys);

            List<LongIdKey> persistenceValueKeys = persistenceValueDao.lookup(PersistenceValueMaintainService.CHILD_FOR_POINT, new Object[]{key})
                    .stream().map(PersistenceValue::getKey).collect(Collectors.toList());
            persistenceValueDao.batchDelete(persistenceValueKeys);
            persistenceValueCache.batchDelete(persistenceValueKeys);
        }

        //删除与点位拥有的过滤器与触发器相关的点位以及其过滤器触发器本身。
        {
            //查找点位拥有的过滤器与触发器。
            List<LongIdKey> filterInfoKeys = filterInfoDao.lookup(FilterInfoMaintainService.CHILD_FOR_POINT, new Object[]{key})
                    .stream().map(FilterInfo::getKey).collect(Collectors.toList());
            List<LongIdKey> triggerInfoKeys = triggerInfoDao.lookup(TriggerInfoMaintainService.CHILD_FOR_POINT, new Object[]{key})
                    .stream().map(TriggerInfo::getKey).collect(Collectors.toList());

            //查找点位拥有的过滤器与触发器相关的数据点。
            List<LongIdKey> filteredValueKeys = filteredValueDao.lookup(FilteredValueMaintainService.CHILD_FOR_FILTER_SET, new Object[]{filterInfoKeys})
                    .stream().map(FilteredValue::getKey).collect(Collectors.toList());
            List<LongIdKey> triggeredValueKeys = triggeredValueDao.lookup(TriggeredValueMaintainService.CHILD_FOR_TRIGGER_SET, new Object[]{triggerInfoKeys})
                    .stream().map(TriggeredValue::getKey).collect(Collectors.toList());

            //删除点位拥有的过滤器与触发器相关的数据点。
            filteredValueDao.batchDelete(filteredValueKeys);
            filteredValueCache.batchDelete(filteredValueKeys);
            triggeredValueDao.batchDelete(triggeredValueKeys);
            triggeredValueCache.batchDelete(triggeredValueKeys);

            //删除点位拥有的过滤器与触发器。
            filterInfoDao.batchDelete(filterInfoKeys);
            filterInfoCache.batchDelete(filterInfoKeys);
            triggerInfoDao.batchDelete(triggerInfoKeys);
            triggerInfoCache.batchDelete(triggerInfoKeys);
        }

        //使能过滤器信息和使能触发器缓存信息删除。
        {
            enabledFilterInfoCache.delete(key);
            enabledTriggerInfoCache.delete(key);
        }

        pointDao.delete(key);
        pointCache.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<Point> lookup(String preset, Object[] objs) throws ServiceException {
        try {
            return PagingUtil.pagedData(pointDao.lookup(preset, objs));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<Point> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        try {
            return PagingUtil.pagedData(pagingInfo, pointDao.lookupCount(preset, objs), pointDao.lookup(preset, objs, pagingInfo));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void lookupDelete(String preset, Object[] objs) throws ServiceException {
        try {
            List<LongIdKey> longIdKeys = pointDao.lookupDelete(preset, objs);
            for (LongIdKey longIdKey : longIdKeys) {
                internalDelete(longIdKey);
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询并删除实体时发生异常", LogLevel.WARN, sem, e);
        }
    }
}
