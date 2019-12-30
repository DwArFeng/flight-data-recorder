package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.TriggerInfoMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TriggerInfoMaintainServiceImpl implements TriggerInfoMaintainService {

    @Autowired
    private TriggerInfoMaintainServiceDelegate delegate;

    @Override
    public TriggerInfo get(GuidKey key) throws ServiceException {
        return delegate.get(key);
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
    public void delete(GuidKey key) throws ServiceException {
        delegate.delete(key);
    }

    @Override
    public PagedData<TriggerInfo> getTriggerInfos(GuidKey categoryGuid, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        return delegate.getTriggerInfos(categoryGuid, lookupPagingInfo);
    }
}
