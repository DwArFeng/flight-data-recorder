package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.TriggeredValueMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TriggeredValueMaintainServiceImpl implements TriggeredValueMaintainService {

    @Autowired
    private TriggeredValueMaintainServiceDelegate delegate;

    @Override
    public TriggeredValue get(UuidKey key) throws ServiceException {
        return delegate.get(key);
    }

    @Override
    public UuidKey insert(TriggeredValue triggeredValue) throws ServiceException {
        return delegate.insert(triggeredValue);
    }

    @Override
    public UuidKey update(TriggeredValue triggeredValue) throws ServiceException {
        return delegate.update(triggeredValue);
    }

    @Override
    public void delete(UuidKey key) throws ServiceException {
        delegate.delete(key);
    }
}
