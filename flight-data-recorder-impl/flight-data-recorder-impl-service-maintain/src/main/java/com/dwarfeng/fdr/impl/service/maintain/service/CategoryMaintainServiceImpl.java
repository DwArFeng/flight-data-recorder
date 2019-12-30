package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.CategoryMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryMaintainServiceImpl implements CategoryMaintainService {

    @Autowired
    private CategoryMaintainServiceDelegate delegate;

    @Override
    public Category get(GuidKey key) throws ServiceException {
        return delegate.get(key);
    }

    @Override
    public GuidKey insert(Category category) throws ServiceException {
        return delegate.insert(category);
    }

    @Override
    public GuidKey update(Category category) throws ServiceException {
        return delegate.update(category);
    }

    @Override
    public void delete(GuidKey key) throws ServiceException {
        delegate.delete(key);
    }

    @Override
    public PagedData<Category> getChilds(GuidKey guidKey, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        return delegate.getChilds(guidKey, lookupPagingInfo);
    }
}
