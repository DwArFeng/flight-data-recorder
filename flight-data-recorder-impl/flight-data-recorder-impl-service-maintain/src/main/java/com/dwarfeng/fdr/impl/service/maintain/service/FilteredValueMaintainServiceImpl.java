package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.FilteredValueMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilteredValueMaintainServiceImpl implements FilteredValueMaintainService {

    @Autowired
    private FilteredValueMaintainServiceDelegate delegate;

    @Override
    public FilteredValue get(GuidKey key) throws ServiceException {
        return delegate.get(key);
    }

    @Override
    public GuidKey insert(FilteredValue filteredValue) throws ServiceException {
        return delegate.insert(filteredValue);
    }

    @Override
    public GuidKey update(FilteredValue filteredValue) throws ServiceException {
        return delegate.update(filteredValue);
    }

    @Override
    public void delete(GuidKey key) throws ServiceException {
        delegate.delete(key);
    }
}
