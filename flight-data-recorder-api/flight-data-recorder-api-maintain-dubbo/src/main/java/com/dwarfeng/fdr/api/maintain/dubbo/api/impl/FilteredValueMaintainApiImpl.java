package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.FilteredValueMaintainApi;
import com.dwarfeng.fdr.api.maintain.dubbo.interceptor.DubboInvokeLog;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.FilteredValueMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@DubboInvokeLog
public class FilteredValueMaintainApiImpl implements FilteredValueMaintainApi {

    @Autowired
    @Qualifier("filteredValueMaintainService")
    private FilteredValueMaintainService delegate;

    @Override
    public FilteredValue get(UuidKey key) throws ServiceException {
        return delegate.get(key);
    }

    public UuidKey insert(FilteredValue element) throws ServiceException {
        return delegate.insert(element);
    }

    public UuidKey update(FilteredValue element) throws ServiceException {
        return delegate.update(element);
    }

    @Override
    public void delete(UuidKey key) throws ServiceException {
        delegate.delete(key);
    }
}
