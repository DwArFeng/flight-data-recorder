package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.TriggerInfoMaintainApi;
import com.dwarfeng.fdr.api.maintain.dubbo.interceptor.DubboInvokeLog;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.TriggerInfoMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@DubboInvokeLog
public class TriggerInfoMaintainApiImpl implements TriggerInfoMaintainApi {

    @Autowired
    @Qualifier("triggerInfoMaintainService")
    private TriggerInfoMaintainService delegate;

    @Override
    public PagedData<TriggerInfo> getTriggerInfos(UuidKey pointUuid, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        return delegate.getTriggerInfos(pointUuid, lookupPagingInfo);
    }

    @Override
    public TriggerInfo get(UuidKey key) throws ServiceException {
        return delegate.get(key);
    }

    @Override
    public UuidKey insert(TriggerInfo element) throws ServiceException {
        return delegate.insert(element);
    }

    @Override
    public UuidKey update(TriggerInfo element) throws ServiceException {
        return delegate.update(element);
    }

    @Override
    public void delete(UuidKey key) throws ServiceException {
        delegate.delete(key);
    }
}
