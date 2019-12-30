package com.dwarfeng.fdr.impl.dao.fuh.dao;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.dao.PersistenceValueDao;
import com.dwarfeng.fdr.stack.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PersistenceValueDaoImpl implements PersistenceValueDao {

    @Autowired
    private PersistenceValueDaoDelegate delegate;

    @Override
    public boolean exists(GuidKey key) throws DaoException {
        return delegate.exists(key);
    }

    @Override
    public PersistenceValue get(GuidKey key) throws DaoException {
        return delegate.get(key);
    }

    @Override
    public GuidKey insert(PersistenceValue persistenceValue) throws DaoException {
        return delegate.insert(persistenceValue);
    }

    @Override
    public GuidKey update(PersistenceValue persistenceValue) throws DaoException {
        return delegate.update(persistenceValue);
    }

    @Override
    public void delete(GuidKey key) throws DaoException {
        delegate.delete(key);
    }

    @Override
    public void deleteAll(GuidKey pointKey) throws DaoException {
        delegate.deleteAll(pointKey);
    }
}
