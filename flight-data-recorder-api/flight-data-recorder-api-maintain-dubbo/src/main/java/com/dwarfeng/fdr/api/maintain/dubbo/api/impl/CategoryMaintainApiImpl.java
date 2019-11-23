package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.CategoryMaintainApi;
import com.dwarfeng.fdr.api.maintain.dubbo.interceptor.DubboInvokeLog;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.CategoryMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@DubboInvokeLog
public class CategoryMaintainApiImpl implements CategoryMaintainApi {

    @Autowired
    @Qualifier("categoryMaintainService")
    private CategoryMaintainService delegate;

    @Override
    public PagedData<Category> getChilds(UuidKey uuidKey, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        return delegate.getChilds(uuidKey, lookupPagingInfo);
    }

    @Override
    public Category get(UuidKey key) throws ServiceException {
        return delegate.get(key);
    }

    @Override
    public UuidKey insert(Category element) throws ServiceException {
        return delegate.insert(element);
    }

    @Override
    public UuidKey update(Category element) throws ServiceException {
        return delegate.update(element);
    }

    @Override
    public void delete(UuidKey key) throws ServiceException {
        delegate.delete(key);
    }
}
