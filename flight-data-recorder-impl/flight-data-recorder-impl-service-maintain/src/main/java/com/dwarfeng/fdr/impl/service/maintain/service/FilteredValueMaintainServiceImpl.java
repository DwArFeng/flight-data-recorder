package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.FilteredValueMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilteredValueMaintainServiceImpl implements FilteredValueMaintainService {

    @Autowired
    private FilteredValueMaintainServiceDelegate delegate;

    @Override
    public FilteredValue get(UuidKey key) throws ServiceException {
        return delegate.get(key);
    }

    @Override
    public UuidKey insert(FilteredValue filteredValue) throws ServiceException {
        return delegate.insert(filteredValue);
    }

    @Override
    public UuidKey update(FilteredValue filteredValue) throws ServiceException {
        return delegate.update(filteredValue);
    }

    @Override
    public void delete(UuidKey key) throws ServiceException {
        delegate.delete(key);
    }
}
