package com.dwarfeng.fdr.sdk.crud;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.cache.*;
import com.dwarfeng.fdr.stack.dao.*;
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
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Validated
public class PointCrudHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointCrudHelper.class);

    @Autowired
    private PointDao pointDao;
    @Autowired
    private PointCache pointCache;
    @Autowired
    private PointHasFilterInfoCache pointHasFilterInfoCache;
    @Autowired
    private PointHasTriggerInfoCache pointHasTriggerInfoCache;

    @Autowired
    private FilterInfoDao filterInfoDao;
    @Autowired
    private FilterInfoCache filterInfoCache;
    @Autowired
    private TriggerInfoDao triggerInfoDao;
    @Autowired
    private TriggerInfoCache triggerInfoCache;
    @Autowired
    private PersistenceValueDao persistenceValueDao;
    @Autowired
    private PersistenceValueCache persistenceValueCache;
    @Autowired
    private RealtimeValueDao realtimeValueDao;
    @Autowired
    private RealtimeValueCache realtimeValueCache;
    @Autowired
    private FilteredValueDao filteredValueDao;
    @Autowired
    private FilteredValueCache filteredValueCache;
    @Autowired
    private TriggeredValueDao triggeredValueDao;
    @Autowired
    private TriggeredValueCache triggeredValueCache;

    @Autowired
    private GuidApi guidApi;

    @Value("${cache.timeout.entity.point}")
    private long pointTimeout;

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public Point get(@NotNull GuidKey key) throws ServiceException {
        try {
            return noAdviseGet(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Point noAdviseGet(GuidKey key) throws CacheException, DaoException {
        if (pointCache.exists(key)) {
            LOGGER.debug("在缓存中发现了 " + key.toString() + " 对应的值，直接返回该值...");
            return pointCache.get(key);
        } else {
            LOGGER.debug("未能在缓存中发现 " + key.toString() + " 对应的值，读取数据访问层...");
            Point point = pointDao.get(key);
            LOGGER.debug("将读取到的值 " + point.toString() + " 回写到缓存中...");
            pointCache.push(key, point, pointTimeout);
            return point;
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey insert(@NotNull Point point) throws ServiceException {
        try {
            return noAdviseInsert(point);
        } catch (Exception e) {
            throw new ServiceException(ServiceExceptionCodes.UNDEFINE, e);
        }
    }

    public GuidKey noAdviseInsert(Point point) throws CacheException, DaoException, com.dwarfeng.sfds.stack.exception.ServiceException {
        //如果实体中没有主键，则向主键服务请求一个主键。
        maySetGuid(point);

        if (pointCache.exists(point.getKey()) || pointDao.exists(point.getKey())) {
            LOGGER.debug("指定的实体 " + point.toString() + " 已经存在，无法插入...");
            throw new IllegalStateException("指定的实体 " + point.toString() + " 已经存在，无法插入...");
        } else {
            LOGGER.debug("将指定的实体 " + point.toString() + " 插入数据访问层中...");
            pointDao.insert(point);
            LOGGER.debug("将指定的实体 " + point.toString() + " 插入缓存中...");
            pointCache.push(point.getKey(), point, pointTimeout);
            return point.getKey();
        }
    }

    private void maySetGuid(Point point) throws com.dwarfeng.sfds.stack.exception.ServiceException {
        if (Objects.isNull(point.getKey())) {
            LOGGER.debug("实体 " + point.toString() + "没有主键，将从服务中获取GUID...");
            long guid = guidApi.nextGuid();
            LOGGER.debug("从服务中获取了guid: " + guid);
            point.setKey(new GuidKey(guid));
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey update(@NotNull Point point) throws ServiceException {
        try {
            return noAdviseUpdate(point);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public GuidKey noAdviseUpdate(Point point) throws CacheException, DaoException {
        if (!pointCache.exists(point.getKey()) && !pointDao.exists(point.getKey())) {
            LOGGER.debug("指定的实体 " + point.toString() + " 已经存在，无法更新...");
            throw new IllegalStateException("指定的实体 " + point.toString() + " 已经存在，无法更新...");
        } else {
            LOGGER.debug("将指定的实体 " + point.toString() + " 插入数据访问层中...");
            pointDao.update(point);
            LOGGER.debug("将指定的实体 " + point.toString() + " 插入缓存中...");
            pointCache.push(point.getKey(), point, pointTimeout);
            return point.getKey();
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
        if (!pointCache.exists(key) && !pointDao.exists(key)) {
            LOGGER.debug("指定的键 " + key.toString() + " 不存在，无法删除...");
            throw new IllegalStateException("指定的键 " + key.toString() + " 不存在，无法删除...");
        } else {
            LOGGER.debug("查询数据点 " + key.toString() + " 对应的子项过滤器信息...");
            Set<GuidKey> filterInfos2Delete = filterInfoDao.getFilterInfos(key, LookupPagingInfo.LOOKUP_ALL)
                    .stream().map(FilterInfo::getKey).collect(Collectors.toSet());
            LOGGER.debug("查询数据点 " + key.toString() + " 对应的子项触发器信息...");
            Set<GuidKey> triggerInfos2Delete = triggerInfoDao.getTriggerInfos(key, LookupPagingInfo.LOOKUP_ALL)
                    .stream().map(TriggerInfo::getKey).collect(Collectors.toSet());

            LOGGER.debug("清除数据点 " + key.toString() + " 对应的子项缓存...");
            pointHasFilterInfoCache.delete(key);
            pointHasTriggerInfoCache.delete(key);

            LOGGER.debug("删除数据点 " + key.toString() + " 相关联的被过滤数据与相关缓存...");
            for (GuidKey filterInfoKey : filterInfos2Delete) {
                filteredValueCache.deleteAllByFilterInfo(filterInfoKey);
                filteredValueDao.deleteAllByFilterInfo(filterInfoKey);
            }
            filteredValueCache.deleteAllByPoint(key);
            filteredValueDao.deleteAllByPoint(key);
            LOGGER.debug("删除数据点 " + key.toString() + " 相关联的被触发数据与相关缓存...");
            for (GuidKey triggerInfoKey : triggerInfos2Delete) {
                triggeredValueCache.deleteAllByTriggerInfo(triggerInfoKey);
                triggeredValueDao.deleteAllByTriggerInfo(triggerInfoKey);
            }
            triggeredValueCache.deleteAllByPoint(key);
            triggeredValueDao.deleteAllByPoint(key);
            LOGGER.debug("删除数据点 " + key.toString() + " 相关联的持久化数据与相关缓存...");
            persistenceValueCache.deleteAll(key);
            persistenceValueDao.deleteAll(key);
            LOGGER.debug("删除数据点 " + key.toString() + " 相关联的实时数据与相关缓存...");
            if (realtimeValueDao.exists(key)) {
                realtimeValueCache.delete(key);
                realtimeValueDao.delete(key);
            }

            LOGGER.debug("删除数据点 " + key.toString() + " 相关联的过滤器信息与缓存...");
            for (GuidKey filterInfoKey : filterInfos2Delete) {
                filterInfoCache.delete(filterInfoKey);
                filterInfoDao.delete(filterInfoKey);
            }
            LOGGER.debug("删除数据点 " + key.toString() + " 相关联的触发器信息与缓存...");
            for (GuidKey triggerInfoKey : triggerInfos2Delete) {
                triggerInfoCache.delete(triggerInfoKey);
                triggerInfoDao.delete(triggerInfoKey);
            }

            LOGGER.debug("删除数据点 " + key.toString() + " 与缓存...");
            pointCache.delete(key);
            pointDao.delete(key);
        }
    }
}
