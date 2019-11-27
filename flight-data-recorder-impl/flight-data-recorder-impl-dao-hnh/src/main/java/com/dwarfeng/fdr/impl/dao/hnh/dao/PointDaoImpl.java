package com.dwarfeng.fdr.impl.dao.hnh.dao;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
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
    public boolean exists(UuidKey key) throws DaoException {
        return delegate.exists(key);
    }

    @Override
    public Point get(UuidKey key) throws DaoException {
        return delegate.get(key);
    }

    @Override
    public UuidKey insert(Point point) throws DaoException {
        return delegate.insert(point);
    }

    @Override
    public UuidKey update(Point point) throws DaoException {
        return delegate.update(point);
    }

    @Override
    public void delete(UuidKey key) throws DaoException {
        delegate.delete(key);
    }

    @Override
    public List<Point> getPoints(UuidKey categoryUuidKey, LookupPagingInfo lookupPagingInfo) throws DaoException {
        return delegate.getPoints(categoryUuidKey, lookupPagingInfo);
    }

    @Override
    public long getChildCount(UuidKey categoryUuidKey) throws DaoException {
        return delegate.getChildCount(categoryUuidKey);
    }
}
