package com.dwarfeng.fdr.impl.dao.fuh.dao;

import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.dao.RealtimeValueDao;
import com.dwarfeng.fdr.stack.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RealtimeValueDaoImpl implements RealtimeValueDao {

    @Autowired
    private RealtimeValueDaoDelegate delegate;

    @Override
    public boolean exists(UuidKey key) throws DaoException {
        return delegate.exists(key);
    }

    @Override
    public RealtimeValue get(UuidKey key) throws DaoException {
        return delegate.get(key);
    }

    @Override
    public UuidKey insert(RealtimeValue realtimeValue) throws DaoException {
        return delegate.insert(realtimeValue);
    }

    @Override
    public UuidKey update(RealtimeValue realtimeValue) throws DaoException {
        return delegate.update(realtimeValue);
    }

    @Override
    public void delete(UuidKey key) throws DaoException {
        delegate.delete(key);
    }
}
