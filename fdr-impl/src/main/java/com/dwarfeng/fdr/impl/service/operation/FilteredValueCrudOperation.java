package com.dwarfeng.fdr.impl.service.operation;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.dao.FilteredValueDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.CrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilteredValueCrudOperation implements CrudOperation<LongIdKey, FilteredValue> {

    @Autowired
    private FilteredValueDao filteredValueDao;

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return filteredValueDao.exists(key);
    }

    @Override
    public FilteredValue get(LongIdKey key) throws Exception {
        if (!filteredValueDao.exists(key)) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }
        return filteredValueDao.get(key);
    }

    @Override
    public LongIdKey insert(FilteredValue filteredValue) throws Exception {
        return filteredValueDao.insert(filteredValue);
    }

    @Override
    public void update(FilteredValue filteredValue) throws Exception {
        filteredValueDao.update(filteredValue);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        filteredValueDao.delete(key);
    }
}
