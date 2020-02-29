package com.dwarfeng.fdr.impl.service.operation;

import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.fdr.stack.cache.*;
import com.dwarfeng.fdr.stack.dao.*;
import com.dwarfeng.fdr.stack.service.*;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.CrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PointCrudOperation implements CrudOperation<LongIdKey, Point> {

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

    @Value("${cache.timeout.entity.point}")
    private long pointTimeout;

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return pointCache.exists(key) || pointDao.exists(key);
    }

    @Override
    public Point get(LongIdKey key) throws Exception {
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
    }

    @Override
    public LongIdKey insert(Point point) throws Exception {
        pointCache.push(point, pointTimeout);
        return pointDao.insert(point);
    }

    @Override
    public void update(Point point) throws Exception {
        pointCache.push(point, pointTimeout);
        pointDao.update(point);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        //删除与点位直接相关的数据值。
        {
            if (realtimeValueCache.exists(key) || realtimeValueDao.exists(key)) {
                realtimeValueDao.delete(key);
                realtimeValueCache.delete(key);
            }

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
}
