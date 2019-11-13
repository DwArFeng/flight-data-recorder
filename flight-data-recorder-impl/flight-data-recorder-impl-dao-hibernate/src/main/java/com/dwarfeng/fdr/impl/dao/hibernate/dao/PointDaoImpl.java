package com.dwarfeng.fdr.impl.dao.hibernate.dao;

import com.dwarfeng.fdr.stack.bean.constraint.PointConstraint;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.dao.PointDao;
import com.dwarfeng.fdr.stack.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
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
    public boolean existsAll(Collection<UuidKey> c) throws DaoException {
        return delegate.existsAll(c);
    }

    @Override
    public boolean existsNon(Collection<UuidKey> c) throws DaoException {
        return delegate.existsNon(c);
    }

    @Override
    public Point get(UuidKey key) throws DaoException {
        return delegate.get(key);
    }

    @Override
    public UuidKey insert(Point element) throws DaoException {
        return delegate.insert(element);
    }

    @Override
    public void batchInsert(Collection<Point> c) throws DaoException {
        delegate.batchInsert(c);
    }

    @Override
    public UuidKey update(Point element) throws DaoException {
        return delegate.update(element);
    }

    @Override
    public void batchUpdate(Collection<Point> c) throws DaoException {
        delegate.batchUpdate(c);
    }

    @Override
    public void delete(UuidKey key) throws DaoException {
        delegate.delete(key);
    }

    @Override
    public void batchDelete(Collection<UuidKey> c) throws DaoException {
        delegate.batchDelete(c);
    }

    @Override
    public List<Point> select(PointConstraint constraint, LookupPagingInfo lookupPagingInfo) throws DaoException {
        return delegate.select(constraint, lookupPagingInfo);
    }

    @Override
    public int selectCount(PointConstraint constraint) throws DaoException {
        return delegate.selectCount(constraint);
    }
}
