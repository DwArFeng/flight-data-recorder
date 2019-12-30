package com.dwarfeng.fdr.impl.dao.hnh.dao;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.dao.FilterInfoDao;
import com.dwarfeng.fdr.stack.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public class FilterInfoDaoImpl implements FilterInfoDao {

    @Autowired
    private FilterInfoDaoDelegate delegate;

    @Override
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    @TimeAnalyse
    public boolean exists(@NotNull GuidKey key) throws DaoException {
        return delegate.exists(key);
    }

    @Override
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    @TimeAnalyse
    public FilterInfo get(GuidKey key) throws DaoException {
        return delegate.get(key);
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    @TimeAnalyse
    public GuidKey insert(@NotNull FilterInfo filterInfo) throws DaoException {
        return delegate.insert(filterInfo);
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    @TimeAnalyse
    public GuidKey update(@NotNull FilterInfo filterInfo) throws DaoException {
        return delegate.update(filterInfo);
    }

    @Override
    @Transactional(transactionManager = "hibernateTransactionManager")
    @TimeAnalyse
    public void delete(@NotNull GuidKey key) throws DaoException {
        delegate.delete(key);
    }

    @Override
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    @TimeAnalyse
    public List<FilterInfo> getFilterInfos(@NotNull GuidKey pointGuidKey, @NotNull LookupPagingInfo lookupPagingInfo) throws DaoException {
        return delegate.getFilterInfos(pointGuidKey, lookupPagingInfo);
    }

    @Override
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    @TimeAnalyse
    public long getFilterInfoCount(@NotNull GuidKey pointGuidKey) throws DaoException {
        return delegate.getFilterInfoCount(pointGuidKey);
    }
}
