package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.TriggeredValueMaintainApi;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TriggeredValueMaintainApiImpl implements TriggeredValueMaintainApi {

    @Autowired
    private TriggeredValueMaintainApiDelegate delegate;

    @Override
    public TriggeredValue get(GuidKey guidKey) throws ServiceException {
        return delegate.get(guidKey);
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
    public void delete(GuidKey guidKey) throws ServiceException {
        delegate.delete(guidKey);
    }
}
