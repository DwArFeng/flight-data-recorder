package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.FilterInfoMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilterInfoMaintainServiceImpl implements FilterInfoMaintainService {

    @Autowired
    private FilterInfoMaintainServiceDelegate delegate;

    @Override
    public FilterInfo get(GuidKey key) throws ServiceException {
        return delegate.get(key);
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
    public void delete(GuidKey key) throws ServiceException {
        delegate.delete(key);
    }

    @Override
    public PagedData<FilterInfo> getFilterInfos(GuidKey categoryGuid, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        return delegate.getFilterInfos(categoryGuid, lookupPagingInfo);
    }
}
