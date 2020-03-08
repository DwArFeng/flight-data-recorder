package com.dwarfeng.fdr.impl.service.operation;

import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.cache.EnabledFilterInfoCache;
import com.dwarfeng.fdr.stack.cache.FilterInfoCache;
import com.dwarfeng.fdr.stack.dao.FilterInfoDao;
import com.dwarfeng.fdr.stack.dao.FilteredValueDao;
import com.dwarfeng.fdr.stack.service.FilteredValueMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.CrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FilterInfoCrudOperation implements CrudOperation<LongIdKey, FilterInfo> {

    @Autowired
    private FilterInfoDao filterInfoDao;
    @Autowired
    private FilteredValueDao filteredValueDao;

    @Autowired
    private FilterInfoCache filterInfoCache;
    @Autowired
    private EnabledFilterInfoCache enabledFilterInfoCache;

    @Value("${cache.timeout.entity.filter_info}")
    private long filterInfoTimeout;

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return filterInfoCache.exists(key) || filterInfoDao.exists(key);
    }

    @Override
    public FilterInfo get(LongIdKey key) throws Exception {
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
    public LongIdKey insert(FilterInfo filterInfo) throws Exception {
        if (Objects.nonNull(filterInfo.getPointKey())) {
            enabledFilterInfoCache.delete(filterInfo.getPointKey());
        }

        filterInfoCache.push(filterInfo, filterInfoTimeout);
        return filterInfoDao.insert(filterInfo);
    }

    @Override
    public void update(FilterInfo filterInfo) throws Exception {
        FilterInfo oldFilterInfo = get(filterInfo.getKey());
        if (Objects.nonNull(oldFilterInfo.getPointKey())) {
            enabledFilterInfoCache.delete(oldFilterInfo.getPointKey());
        }

        if (Objects.nonNull(filterInfo.getPointKey())) {
            enabledFilterInfoCache.delete(filterInfo.getPointKey());
        }

        filterInfoCache.push(filterInfo, filterInfoTimeout);
        filterInfoDao.update(filterInfo);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        FilterInfo oldFilterInfo = get(key);
        if (Objects.nonNull(oldFilterInfo.getPointKey())) {
            enabledFilterInfoCache.delete(oldFilterInfo.getPointKey());
        }

        List<LongIdKey> filteredValueKeys = filteredValueDao.lookup(FilteredValueMaintainService.CHILD_FOR_FILTER, new Object[]{key})
                .stream().map(FilteredValue::getKey).collect(Collectors.toList());
        filteredValueDao.batchDelete(filteredValueKeys);

        filterInfoDao.delete(key);
        filterInfoCache.delete(key);
    }
}
