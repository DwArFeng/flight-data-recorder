package com.dwarfeng.fdr.impl.dao.hnh.dao;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.dao.TriggerInfoDao;
import com.dwarfeng.fdr.stack.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public class TriggerInfoDaoImpl implements TriggerInfoDao {

    @Autowired
    private TriggerInfoDaoDelegate delegate;

    @Override
    @Transactional(transactionManager = "daoTransactionManager", readOnly = true)
    @TimeAnalyse
    public boolean exists(@NotNull UuidKey key) throws DaoException {
        return delegate.exists(key);
    }

    @Override
    @Transactional(transactionManager = "daoTransactionManager", readOnly = true)
    @TimeAnalyse
    public TriggerInfo get(UuidKey key) throws DaoException {
        return delegate.get(key);
    }

    @Transactional(transactionManager = "daoTransactionManager")
    @TimeAnalyse
    public UuidKey insert(@NotNull TriggerInfo triggerInfo) throws DaoException {
        return delegate.insert(triggerInfo);
    }

    @Transactional(transactionManager = "daoTransactionManager")
    @TimeAnalyse
    public UuidKey update(@NotNull TriggerInfo triggerInfo) throws DaoException {
        return delegate.update(triggerInfo);
    }

    @Override
    @Transactional(transactionManager = "daoTransactionManager")
    @TimeAnalyse
    public void delete(@NotNull UuidKey key) throws DaoException {
        delegate.delete(key);
    }

    @Override
    @Transactional(transactionManager = "daoTransactionManager", readOnly = true)
    @TimeAnalyse
    public List<TriggerInfo> getTriggerInfos(@NotNull UuidKey pointUuidKey, @NotNull LookupPagingInfo lookupPagingInfo) throws DaoException {
        return delegate.getTriggerInfos(pointUuidKey, lookupPagingInfo);
    }

    @Override
    @Transactional(transactionManager = "daoTransactionManager", readOnly = true)
    @TimeAnalyse
    public long getTriggerInfoCount(@NotNull UuidKey pointUuidKey) throws DaoException {
        return delegate.getTriggerInfoCount(pointUuidKey);
    }
}
