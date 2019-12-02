package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.FilterInfoMaintainApi;
import com.dwarfeng.fdr.api.maintain.dubbo.interceptor.DubboInvokeLog;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.FilterInfoMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@DubboInvokeLog
public class FilterInfoMaintainApiImpl implements FilterInfoMaintainApi {

    @Autowired
    @Qualifier("filterInfoMaintainService")
    private FilterInfoMaintainService delegate;

    @Override
    public PagedData<FilterInfo> getFilterInfos(UuidKey categoryUuid, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        return delegate.getFilterInfos(categoryUuid, lookupPagingInfo);
    }

    @Override
    public FilterInfo get(UuidKey key) throws ServiceException {
        return delegate.get(key);
    }

    @Override
    public UuidKey insert(FilterInfo element) throws ServiceException {
        return delegate.insert(element);
    }

    @Override
    public UuidKey update(FilterInfo element) throws ServiceException {
        return delegate.update(element);
    }

    @Override
    public void delete(UuidKey key) throws ServiceException {
        delegate.delete(key);
    }
}
