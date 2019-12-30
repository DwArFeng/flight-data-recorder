package com.dwarfeng.fdr.impl.dao.hnh.dao;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.dao.PointDao;
import com.dwarfeng.fdr.stack.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PointDaoImpl implements PointDao {

    @Autowired
    private PointDaoDelegate delegate;

    @Override
    public boolean exists(GuidKey key) throws DaoException {
        return delegate.exists(key);
    }

    @Override
    public Point get(GuidKey key) throws DaoException {
        return delegate.get(key);
    }

    @Override
    public GuidKey insert(Point point) throws DaoException {
        return delegate.insert(point);
    }

    @Override
    public GuidKey update(Point point) throws DaoException {
        return delegate.update(point);
    }

    @Override
    public void delete(GuidKey key) throws DaoException {
        delegate.delete(key);
    }

    @Override
    public List<Point> getPoints(GuidKey categoryGuidKey, LookupPagingInfo lookupPagingInfo) throws DaoException {
        return delegate.getPoints(categoryGuidKey, lookupPagingInfo);
    }

    @Override
    public long getPointCount(GuidKey categoryGuidKey) throws DaoException {
        return delegate.getPointCount(categoryGuidKey);
    }
}
