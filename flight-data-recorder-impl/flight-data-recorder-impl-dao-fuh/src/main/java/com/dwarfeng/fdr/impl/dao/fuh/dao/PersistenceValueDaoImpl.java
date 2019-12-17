package com.dwarfeng.fdr.impl.dao.fuh.dao;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.dao.PersistenceValueDao;
import com.dwarfeng.fdr.stack.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PersistenceValueDaoImpl implements PersistenceValueDao {

    @Autowired
    private PersistenceValueDaoDelegate delegate;

    @Override
    public boolean exists(UuidKey key) throws DaoException {
        return delegate.exists(key);
    }

    @Override
    public PersistenceValue get(UuidKey key) throws DaoException {
        return delegate.get(key);
    }

    @Override
    public UuidKey insert(PersistenceValue persistenceValue) throws DaoException {
        return delegate.insert(persistenceValue);
    }

    @Override
    public UuidKey update(PersistenceValue persistenceValue) throws DaoException {
        return delegate.update(persistenceValue);
    }

    @Override
    public void delete(UuidKey key) throws DaoException {
        delegate.delete(key);
    }
}
