package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.PointMaintainApi;
import com.dwarfeng.fdr.api.maintain.dubbo.interceptor.DubboInvokeLog;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@DubboInvokeLog
public class PointMaintainApiImpl implements PointMaintainApi {

    @Autowired
    @Qualifier("pointMaintainService")
    private PointMaintainService delegate;

    @Override
    public PagedData<Point> getPoints(UuidKey categoryUuid, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        return delegate.getPoints(categoryUuid, lookupPagingInfo);
    }

    @Override
    public Point get(UuidKey key) throws ServiceException {
        return delegate.get(key);
    }

    @Override
    public UuidKey insert(Point element) throws ServiceException {
        return delegate.insert(element);
    }

    @Override
    public UuidKey update(Point element) throws ServiceException {
        return delegate.update(element);
    }

    @Override
    public void delete(UuidKey key) throws ServiceException {
        delegate.delete(key);
    }
}
