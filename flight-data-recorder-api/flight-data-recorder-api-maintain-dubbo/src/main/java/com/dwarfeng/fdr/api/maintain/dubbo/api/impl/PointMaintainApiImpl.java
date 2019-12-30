package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.PointMaintainApi;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PointMaintainApiImpl implements PointMaintainApi {

    @Autowired
    private PointMaintainApiDelegate delegate;

    @Override
    public PagedData<Point> getPoints(GuidKey pointGuidKey, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        return delegate.getPoints(pointGuidKey, lookupPagingInfo);
    }

    @Override
    public Point get(GuidKey guidKey) throws ServiceException {
        return delegate.get(guidKey);
    }

    @Override
    public GuidKey insert(Point point) throws ServiceException {
        return delegate.insert(point);
    }

    @Override
    public GuidKey update(Point point) throws ServiceException {
        return delegate.update(point);
    }

    @Override
    public void delete(GuidKey guidKey) throws ServiceException {
        delegate.delete(guidKey);
    }
}
