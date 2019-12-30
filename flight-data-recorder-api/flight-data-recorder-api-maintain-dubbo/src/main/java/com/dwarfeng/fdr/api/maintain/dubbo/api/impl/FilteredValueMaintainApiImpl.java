package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.FilteredValueMaintainApi;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilteredValueMaintainApiImpl implements FilteredValueMaintainApi {

    @Autowired
    private FilteredValueMaintainApiDelegate delegate;

    @Override
    public FilteredValue get(GuidKey guidKey) throws ServiceException {
        return delegate.get(guidKey);
    }

    public GuidKey insert(FilteredValue filteredValue) throws ServiceException {
        return delegate.insert(filteredValue);
    }

    public GuidKey update(FilteredValue filteredValue) throws ServiceException {
        return delegate.update(filteredValue);
    }

    @Override
    public void delete(GuidKey guidKey) throws ServiceException {
        delegate.delete(guidKey);
    }
}
