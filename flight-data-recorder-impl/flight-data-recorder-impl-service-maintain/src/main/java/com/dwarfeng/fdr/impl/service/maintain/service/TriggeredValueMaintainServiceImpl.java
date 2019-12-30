package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.TriggeredValueMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TriggeredValueMaintainServiceImpl implements TriggeredValueMaintainService {

    @Autowired
    private TriggeredValueMaintainServiceDelegate delegate;

    @Override
    public TriggeredValue get(GuidKey key) throws ServiceException {
        return delegate.get(key);
    }

    @Override
    public GuidKey insert(TriggeredValue triggeredValue) throws ServiceException {
        return delegate.insert(triggeredValue);
    }

    @Override
    public GuidKey update(TriggeredValue triggeredValue) throws ServiceException {
        return delegate.update(triggeredValue);
    }

    @Override
    public void delete(GuidKey key) throws ServiceException {
        delegate.delete(key);
    }
}
