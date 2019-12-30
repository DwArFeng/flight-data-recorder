package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.FilterInfoMaintainApi;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilterInfoMaintainApiImpl implements FilterInfoMaintainApi {

    @Autowired
    private FilterInfoMaintainApiDelegate delegate;

    @Override
    public PagedData<FilterInfo> getFilterInfos(GuidKey pointGuidKey, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        return delegate.getFilterInfos(pointGuidKey, lookupPagingInfo);
    }

    @Override
    public FilterInfo get(GuidKey guidKey) throws ServiceException {
        return delegate.get(guidKey);
    }

    @Override
    public GuidKey insert(FilterInfo filterInfo) throws ServiceException {
        return delegate.insert(filterInfo);
    }

    @Override
    public GuidKey update(FilterInfo filterInfo) throws ServiceException {
        return delegate.update(filterInfo);
    }

    @Override
    public void delete(GuidKey guidKey) throws ServiceException {
        delegate.delete(guidKey);
    }
}
