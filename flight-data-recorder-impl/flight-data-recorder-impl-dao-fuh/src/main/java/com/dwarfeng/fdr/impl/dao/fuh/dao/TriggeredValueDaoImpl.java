package com.dwarfeng.fdr.impl.dao.fuh.dao;

import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.dao.TriggeredValueDao;
import com.dwarfeng.fdr.stack.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TriggeredValueDaoImpl implements TriggeredValueDao {

    @Autowired
    private TriggeredValueDaoDelegate delegate;

    @Override
    public boolean exists(GuidKey key) throws DaoException {
        return delegate.exists(key);
    }

    @Override
    public TriggeredValue get(GuidKey key) throws DaoException {
        return delegate.get(key);
    }

    @Override
    public GuidKey insert(TriggeredValue triggeredValue) throws DaoException {
        return delegate.insert(triggeredValue);
    }

    @Override
    public GuidKey update(TriggeredValue triggeredValue) throws DaoException {
        return delegate.update(triggeredValue);
    }

    @Override
    public void delete(GuidKey key) throws DaoException {
        delegate.delete(key);
    }

    @Override
    public void deleteAllByPoint(GuidKey pointKey) throws DaoException {
        delegate.deleteAllByPoint(pointKey);
    }

    @Override
    public void deleteAllByTriggerInfo(GuidKey triggerInfoKey) throws DaoException {
        delegate.deleteAllByTriggerInfo(triggerInfoKey);
    }
}
