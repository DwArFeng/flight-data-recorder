package com.dwarfeng.fdr.impl.maintain.hibred.service;

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
    public boolean exists(UuidKey key) throws ServiceException {
        return delegate.exists(key);
    }

    @Override
    public Point get(UuidKey key) throws ServiceException {
        return delegate.get(key);
    }

    @Override
    public void add(Point point) throws ServiceException {
        delegate.add(point);
    }

    @Override
    public void remove(UuidKey key) throws ServiceException {
        delegate.remove(key);
    }
}
