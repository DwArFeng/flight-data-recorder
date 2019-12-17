package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.TriggeredValueMaintainApi;
import com.dwarfeng.fdr.api.maintain.dubbo.interceptor.DubboInvokeLog;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.TriggeredValueMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@DubboInvokeLog
public class TriggeredValueMaintainApiImpl implements TriggeredValueMaintainApi {

    @Autowired
    @Qualifier("triggeredValueMaintainService")
    private TriggeredValueMaintainService delegate;

    @Override
    public TriggeredValue get(UuidKey key) throws ServiceException {
        return delegate.get(key);
    }

    @Override
    public UuidKey insert(TriggeredValue element) throws ServiceException {
        return delegate.insert(element);
    }

    @Override
    public UuidKey update(TriggeredValue element) throws ServiceException {
        return delegate.update(element);
    }

    @Override
    public void delete(UuidKey key) throws ServiceException {
        delegate.delete(key);
    }
}
