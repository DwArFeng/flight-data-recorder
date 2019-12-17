package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.PersistenceValueMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersistenceValueMaintainServiceImpl implements PersistenceValueMaintainService {

    @Autowired
    private PersistenceValueMaintainServiceDelegate delegate;

    @Override
    public PersistenceValue get(UuidKey key) throws ServiceException {
        return delegate.get(key);
    }

    @Override
    public UuidKey insert(PersistenceValue persistenceValue) throws ServiceException {
        return delegate.insert(persistenceValue);
    }

    @Override
    public UuidKey update(PersistenceValue persistenceValue) throws ServiceException {
        return delegate.update(persistenceValue);
    }

    @Override
    public void delete(UuidKey key) throws ServiceException {
        delegate.delete(key);
    }
}
