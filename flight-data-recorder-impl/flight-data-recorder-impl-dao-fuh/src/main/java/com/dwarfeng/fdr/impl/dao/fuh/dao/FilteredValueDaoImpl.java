package com.dwarfeng.fdr.impl.dao.fuh.dao;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.dao.FilteredValueDao;
import com.dwarfeng.fdr.stack.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FilteredValueDaoImpl implements FilteredValueDao {

    @Autowired
    private FilteredValueDaoDelegate delegate;

    @Override
    public boolean exists(GuidKey key) throws DaoException {
        return delegate.exists(key);
    }

    @Override
    public FilteredValue get(GuidKey key) throws DaoException {
        return delegate.get(key);
    }

    @Override
    public GuidKey insert(FilteredValue filteredValue) throws DaoException {
        return delegate.insert(filteredValue);
    }

    @Override
    public GuidKey update(FilteredValue filteredValue) throws DaoException {
        return delegate.update(filteredValue);
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
    public void deleteAllByFilterInfo(GuidKey filterInfoKey) throws DaoException {
        delegate.deleteAllByFilterInfo(filterInfoKey);
    }
}
