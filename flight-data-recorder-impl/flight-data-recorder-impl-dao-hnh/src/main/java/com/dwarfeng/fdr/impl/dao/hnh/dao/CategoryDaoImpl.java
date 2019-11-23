package com.dwarfeng.fdr.impl.dao.hnh.dao;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.dao.CategoryDao;
import com.dwarfeng.fdr.stack.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    private CategoryDaoDelegate delegate;

    @Override
    public boolean exists(UuidKey key) throws DaoException {
        return delegate.exists(key);
    }

    @Override
    public Category get(UuidKey key) throws DaoException {
        return delegate.get(key);
    }

    @Override
    public UuidKey insert(Category category) throws DaoException {
        return delegate.insert(category);
    }

    @Override
    public UuidKey update(Category category) throws DaoException {
        return delegate.update(category);
    }

    @Override
    public void delete(UuidKey key) throws DaoException {
        delegate.delete(key);
    }

    @Override
    public List<Category> getChilds(UuidKey uuidKey, LookupPagingInfo lookupPagingInfo) throws DaoException {
        return delegate.getChilds(uuidKey, lookupPagingInfo);
    }

    @Override
    public long getChildCount(UuidKey uuidKey) throws DaoException {
        return delegate.getChildCount(uuidKey);
    }
}
