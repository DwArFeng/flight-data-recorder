package com.dwarfeng.fdr.impl.service.operation;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.cache.FilteredValueCache;
import com.dwarfeng.fdr.stack.dao.FilteredValueDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.CrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FilteredValueCrudOperation implements CrudOperation<LongIdKey, FilteredValue> {

    @Autowired
    private FilteredValueDao filteredValueDao;
    @Autowired
    private FilteredValueCache filteredValueCache;
    @Value("${cache.timeout.entity.filtered_value}")
    private long filteredValueTimeout;

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return filteredValueCache.exists(key) || filteredValueDao.exists(key);
    }

    @Override
    public FilteredValue get(LongIdKey key) throws Exception {
        if (filteredValueCache.exists(key)) {
            return filteredValueCache.get(key);
        } else {
            if (!filteredValueDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            FilteredValue filteredValue = filteredValueDao.get(key);
            filteredValueCache.push(filteredValue, filteredValueTimeout);
            return filteredValue;
        }
    }

    @Override
    public LongIdKey insert(FilteredValue filteredValue) throws Exception {
        filteredValueCache.push(filteredValue, filteredValueTimeout);
        return filteredValueDao.insert(filteredValue);
    }

    @Override
    public void update(FilteredValue filteredValue) throws Exception {
        filteredValueCache.push(filteredValue, filteredValueTimeout);
        filteredValueDao.update(filteredValue);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        filteredValueDao.delete(key);
        filteredValueCache.delete(key);
    }
}
