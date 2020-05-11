package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.service.RealtimeValueMaintainService;
import com.dwarfeng.subgrade.impl.service.CustomCrudService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyEntireLookupService;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RealtimeValueMaintainServiceImpl implements RealtimeValueMaintainService {

    @Autowired
    private CustomCrudService<LongIdKey, RealtimeValue> crudService;
    @Autowired
    private DaoOnlyEntireLookupService<RealtimeValue> entireLookupService;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean exists(LongIdKey key) throws ServiceException {
        return crudService.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public RealtimeValue get(LongIdKey key) throws ServiceException {
        return crudService.get(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public LongIdKey insert(RealtimeValue element) throws ServiceException {
        return crudService.insert(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void update(RealtimeValue element) throws ServiceException {
        crudService.update(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void delete(LongIdKey key) throws ServiceException {
        crudService.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public RealtimeValue getIfExists(LongIdKey key) throws ServiceException {
        return crudService.getIfExists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public LongIdKey insertIfNotExists(RealtimeValue element) throws ServiceException {
        return crudService.insertIfNotExists(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void updateIfExists(RealtimeValue element) throws ServiceException {
        crudService.updateIfExists(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void deleteIfExists(LongIdKey key) throws ServiceException {
        crudService.deleteIfExists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public LongIdKey insertOrUpdate(RealtimeValue element) throws ServiceException {
        return crudService.insertOrUpdate(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public PagedData<RealtimeValue> lookup() throws ServiceException {
        return entireLookupService.lookup();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public PagedData<RealtimeValue> lookup(PagingInfo pagingInfo) throws ServiceException {
        return entireLookupService.lookup(pagingInfo);
    }
}
