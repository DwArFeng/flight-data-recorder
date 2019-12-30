package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.RealtimeValueMaintainApi;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RealtimeValueMaintainApiImpl implements RealtimeValueMaintainApi {

    @Autowired
    private RealtimeValueMaintainApiDelegate delegate;

    @Override
    public RealtimeValue get(GuidKey guidKey) throws ServiceException {
        return delegate.get(guidKey);
    }

    @Override
    public GuidKey insert(RealtimeValue realtimeValue) throws ServiceException {
        return delegate.insert(realtimeValue);
    }

    @Override
    public GuidKey update(RealtimeValue realtimeValue) throws ServiceException {
        return delegate.update(realtimeValue);
    }

    @Override
    public void delete(GuidKey guidKey) throws ServiceException {
        delegate.delete(guidKey);
    }
}
