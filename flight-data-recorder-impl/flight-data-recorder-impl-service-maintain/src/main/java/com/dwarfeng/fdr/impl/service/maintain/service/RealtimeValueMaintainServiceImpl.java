package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.RealtimeValueMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RealtimeValueMaintainServiceImpl implements RealtimeValueMaintainService {

    @Autowired
    private RealtimeValueMaintainServiceDelegate delegate;

    @Override
    public RealtimeValue get(UuidKey key) throws ServiceException {
        return delegate.get(key);
    }

    @Override
    public UuidKey insert(RealtimeValue realtimeValue) throws ServiceException {
        return delegate.insert(realtimeValue);
    }

    @Override
    public UuidKey update(RealtimeValue realtimeValue) throws ServiceException {
        return delegate.update(realtimeValue);
    }

    @Override
    public void delete(UuidKey key) throws ServiceException {
        delegate.delete(key);
    }
}
