package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.FilterInfoMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilterInfoMaintainServiceImpl implements FilterInfoMaintainService {

    @Autowired
    private FilterInfoMaintainServiceDelegate delegate;

    @Override
    public FilterInfo get(UuidKey key) throws ServiceException {
        return delegate.get(key);
    }

    @Override
    public UuidKey insert(FilterInfo point) throws ServiceException {
        return delegate.insert(point);
    }

    @Override
    public UuidKey update(FilterInfo point) throws ServiceException {
        return delegate.update(point);
    }

    @Override
    public void delete(UuidKey key) throws ServiceException {
        delegate.delete(key);
    }

    @Override
    public PagedData<FilterInfo> getFilterInfos(UuidKey categoryUuid, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        return delegate.getFilterInfos(categoryUuid, lookupPagingInfo);
    }
}
