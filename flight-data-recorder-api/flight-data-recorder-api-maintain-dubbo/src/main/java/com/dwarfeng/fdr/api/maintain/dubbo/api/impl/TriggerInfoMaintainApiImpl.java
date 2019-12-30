package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.TriggerInfoMaintainApi;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TriggerInfoMaintainApiImpl implements TriggerInfoMaintainApi {

    @Autowired
    private TriggerInfoMaintainApiDelegate delegate;

    @Override
    public PagedData<TriggerInfo> getTriggerInfos(GuidKey pointGuid, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        return delegate.getTriggerInfos(pointGuid, lookupPagingInfo);
    }

    @Override
    public TriggerInfo get(GuidKey guidKey) throws ServiceException {
        return delegate.get(guidKey);
    }

    @Override
    public GuidKey insert(TriggerInfo triggerInfo) throws ServiceException {
        return delegate.insert(triggerInfo);
    }

    @Override
    public GuidKey update(TriggerInfo triggerInfo) throws ServiceException {
        return delegate.update(triggerInfo);
    }

    @Override
    public void delete(GuidKey guidKey) throws ServiceException {
        delegate.delete(guidKey);
    }
}
