package com.dwarfeng.fdr.impl.dao.fuh.dao;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
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
    public boolean exists(GuidKey key) throws DaoException {
        return delegate.exists(key);
    }

    @Override
    public Category get(GuidKey key) throws DaoException {
        return delegate.get(key);
    }

    @Override
    public GuidKey insert(Category category) throws DaoException {
        return delegate.insert(category);
    }

    @Override
    public GuidKey update(Category category) throws DaoException {
        return delegate.update(category);
    }

    @Override
    public void delete(GuidKey key) throws DaoException {
        delegate.delete(key);
    }

    @Override
    public List<Category> getChilds(GuidKey guidKey, LookupPagingInfo lookupPagingInfo) throws DaoException {
        return delegate.getChilds(guidKey, lookupPagingInfo);
    }

    @Override
    public long getChildCount(GuidKey guidKey) throws DaoException {
        return delegate.getChildCount(guidKey);
    }
}
