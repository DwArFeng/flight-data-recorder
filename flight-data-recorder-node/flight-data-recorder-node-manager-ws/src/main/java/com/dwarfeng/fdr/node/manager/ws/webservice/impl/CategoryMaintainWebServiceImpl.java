package com.dwarfeng.fdr.node.manager.ws.webservice.impl;

import com.dwarfeng.fdr.node.manager.ws.bean.entity.WsCategory;
import com.dwarfeng.fdr.node.manager.ws.bean.key.WsUuidKey;
import com.dwarfeng.fdr.node.manager.ws.webservice.CategoryMaintainWebService;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.Collection;
import java.util.List;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
@WebService
@Component("categoryMaintainWebService")
public class CategoryMaintainWebServiceImpl implements CategoryMaintainWebService {

    @Autowired
    private CategoryMaintainWebServiceDelegate delegate;

    @Override
    public WsCategory get(WsUuidKey key) throws ServiceException {
        return delegate.get(key);
    }

    @Override
    public WsUuidKey insert(WsCategory category) throws ServiceException {
        return delegate.insert(category);
    }

    @Override
    public List<? extends WsUuidKey> batchInsert(Collection<? extends WsCategory> categories) throws ServiceException {
        return delegate.batchInsert(categories);
    }

    @Override
    public WsUuidKey update(WsCategory category) throws ServiceException {
        return delegate.update(category);
    }

    @Override
    public List<? extends WsUuidKey> batchUpdate(Collection<? extends WsCategory> categories) throws ServiceException {
        return delegate.batchUpdate(categories);
    }

    @Override
    public void delete(WsUuidKey key) throws ServiceException {
        delegate.delete(key);
    }

    @Override
    public void batchDelete(Collection<? extends WsUuidKey> keys) throws ServiceException {
        delegate.batchDelete(keys);
    }
}

