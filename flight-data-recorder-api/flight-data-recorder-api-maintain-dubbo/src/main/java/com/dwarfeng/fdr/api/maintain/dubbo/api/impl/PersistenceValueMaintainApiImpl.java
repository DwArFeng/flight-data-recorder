package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.PersistenceValueMaintainApi;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersistenceValueMaintainApiImpl implements PersistenceValueMaintainApi {

    @Autowired
    private PersistenceValueMaintainApiDelegate delegate;

    @Override
    public PersistenceValue get(GuidKey guidKey) throws ServiceException {
        return delegate.get(guidKey);
    }

    @Override
    public GuidKey insert(PersistenceValue persistenceValue) throws ServiceException {
        return delegate.insert(persistenceValue);
    }

    @Override
    public GuidKey update(PersistenceValue persistenceValue) throws ServiceException {
        return delegate.update(persistenceValue);
    }

    @Override
    public void delete(GuidKey guidKey) throws ServiceException {
        delegate.delete(guidKey);
    }
}
