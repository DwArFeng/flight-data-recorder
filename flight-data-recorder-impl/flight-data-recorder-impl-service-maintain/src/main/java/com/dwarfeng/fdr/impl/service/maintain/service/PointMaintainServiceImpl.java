package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointMaintainServiceImpl implements PointMaintainService {

    @Autowired
    private PointMaintainServiceDelegate delegate;

    @Override
    public Point get(UuidKey key) throws ServiceException {
        return delegate.get(key);
    }

    @Override
    public UuidKey insert(Point point) throws ServiceException {
        return delegate.insert(point);
    }

    @Override
    public UuidKey update(Point point) throws ServiceException {
        return delegate.update(point);
    }

    @Override
    public void delete(UuidKey key) throws ServiceException {
        delegate.delete(key);
    }

    @Override
    public PagedData<Point> getPoints(UuidKey categoryUuid, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        return delegate.getPoints(categoryUuid, lookupPagingInfo);
    }
}
