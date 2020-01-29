package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.cache.EnabledFilterInfoCache;
import com.dwarfeng.fdr.stack.cache.FilterInfoCache;
import com.dwarfeng.fdr.stack.cache.FilteredValueCache;
import com.dwarfeng.fdr.stack.dao.FilterInfoDao;
import com.dwarfeng.fdr.stack.dao.FilteredValueDao;
import com.dwarfeng.fdr.stack.service.FilterInfoMaintainService;
import com.dwarfeng.fdr.stack.service.FilteredValueMaintainService;
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
public class FilterInfoMaintainServiceImpl implements FilterInfoMaintainService {

    @Autowired
    private KeyFetcher<LongIdKey> keyFetcher;

    @Autowired
    private FilterInfoDao filterInfoDao;
    @Autowired
    private FilteredValueDao filteredValueDao;

    @Autowired
    private FilterInfoCache filterInfoCache;
    @Autowired
    private FilteredValueCache filteredValueCache;
    @Autowired
    private EnabledFilterInfoCache enabledFilterInfoCache;

    @Autowired
    private ServiceExceptionMapper sem;

    @Value("${cache.timeout.entity.filter_info}")
    private long filterInfoTimeout;

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
        return filterInfoCache.exists(key) || filterInfoDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public FilterInfo get(LongIdKey key) throws ServiceException {
        try {
            return internalGet(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private FilterInfo internalGet(LongIdKey key) throws Exception {
        if (filterInfoCache.exists(key)) {
            return filterInfoCache.get(key);
        } else {
            if (!filterInfoDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            FilterInfo filterInfo = filterInfoDao.get(key);
            filterInfoCache.push(filterInfo, filterInfoTimeout);
            return filterInfo;
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public LongIdKey insert(FilterInfo filterInfo) throws ServiceException {
        try {
            if (Objects.nonNull(filterInfo.getKey()) && internalExists(filterInfo.getKey())) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_EXISTED);
            }
            if (Objects.isNull(filterInfo.getKey())) {
                filterInfo.setKey(keyFetcher.fetchKey());
            }

            if (Objects.nonNull(filterInfo.getPointKey())) {
                enabledFilterInfoCache.delete(filterInfo.getPointKey());
            }

            filterInfoCache.push(filterInfo, filterInfoTimeout);
            return filterInfoDao.insert(filterInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void update(FilterInfo filterInfo) throws ServiceException {
        try {
            if (Objects.nonNull(filterInfo.getKey()) && !internalExists(filterInfo.getKey())) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }

            FilterInfo oldFilterInfo = internalGet(filterInfo.getKey());
            if (Objects.nonNull(oldFilterInfo.getPointKey())) {
                enabledFilterInfoCache.delete(oldFilterInfo.getPointKey());
            }

            if (Objects.nonNull(filterInfo.getPointKey())) {
                enabledFilterInfoCache.delete(filterInfo.getPointKey());
            }

            filterInfoCache.push(filterInfo, filterInfoTimeout);
            filterInfoDao.update(filterInfo);
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
        FilterInfo oldFilterInfo = internalGet(key);
        if (Objects.nonNull(oldFilterInfo.getPointKey())) {
            enabledFilterInfoCache.delete(oldFilterInfo.getPointKey());
        }

        List<LongIdKey> filteredValueKeys = filteredValueDao.lookup(FilteredValueMaintainService.CHILD_FOR_FILTER, new Object[]{key})
                .stream().map(FilteredValue::getKey).collect(Collectors.toList());
        filteredValueDao.batchDelete(filteredValueKeys);
        filteredValueCache.batchDelete(filteredValueKeys);

        filterInfoDao.delete(key);
        filterInfoCache.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<FilterInfo> lookup(String preset, Object[] objs) throws ServiceException {
        try {
            return PagingUtil.pagedData(filterInfoDao.lookup(preset, objs));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<FilterInfo> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        try {
            return PagingUtil.pagedData(pagingInfo, filterInfoDao.lookupCount(preset, objs), filterInfoDao.lookup(preset, objs, pagingInfo));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void lookupDelete(String preset, Object[] objs) throws ServiceException {
        try {
            List<LongIdKey> longIdKeys = filterInfoDao.lookupDelete(preset, objs);
            for (LongIdKey longIdKey : longIdKeys) {
                internalDelete(longIdKey);
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询并删除实体时发生异常", LogLevel.WARN, sem, e);
        }
    }
}
