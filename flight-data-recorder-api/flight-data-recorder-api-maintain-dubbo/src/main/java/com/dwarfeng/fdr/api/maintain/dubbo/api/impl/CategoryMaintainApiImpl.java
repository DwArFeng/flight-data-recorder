package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.CategoryMaintainApi;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMaintainApiImpl implements CategoryMaintainApi {

    @Autowired
    private CategoryMaintainApiDelegate delegate;

    @Override
    public PagedData<Category> getChilds(GuidKey guidKey, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        return delegate.getChilds(guidKey, lookupPagingInfo);
    }

    @Override
    public Category get(GuidKey guidKey) throws ServiceException {
        return delegate.get(guidKey);
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
    public void delete(GuidKey guidKey) throws ServiceException {
        delegate.delete(guidKey);
    }
}
