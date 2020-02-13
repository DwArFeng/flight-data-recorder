package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.service.TriggerInfoMaintainService;
import com.dwarfeng.subgrade.impl.service.CustomCrudService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyPresetLookupService;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TriggerInfoMaintainServiceImpl implements TriggerInfoMaintainService {

    @Autowired
    private CustomCrudService<LongIdKey, TriggerInfo> crudDelegate;
    @Autowired
    private DaoOnlyPresetLookupService<TriggerInfo> lookupDelegate;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public boolean exists(LongIdKey key) throws ServiceException {
        return crudDelegate.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public TriggerInfo get(LongIdKey key) throws ServiceException {
        return crudDelegate.get(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public LongIdKey insert(TriggerInfo element) throws ServiceException {
        return crudDelegate.insert(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void update(TriggerInfo element) throws ServiceException {
        crudDelegate.update(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(LongIdKey key) throws ServiceException {
        crudDelegate.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public TriggerInfo getIfExists(LongIdKey key) throws ServiceException {
        return crudDelegate.getIfExists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public LongIdKey insertIfNotExists(TriggerInfo element) throws ServiceException {
        return crudDelegate.insertIfNotExists(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void updateIfExists(TriggerInfo element) throws ServiceException {
        crudDelegate.updateIfExists(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void deleteIfExists(LongIdKey key) throws ServiceException {
        crudDelegate.deleteIfExists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public LongIdKey insertOrUpdate(TriggerInfo element) throws ServiceException {
        return crudDelegate.insertOrUpdate(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<TriggerInfo> lookup(String preset, Object[] objs) throws ServiceException {
        return lookupDelegate.lookup(preset, objs);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<TriggerInfo> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        return lookupDelegate.lookup(preset, objs, pagingInfo);
    }
}
