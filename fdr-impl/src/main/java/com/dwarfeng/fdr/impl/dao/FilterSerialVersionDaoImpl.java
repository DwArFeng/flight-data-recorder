package com.dwarfeng.fdr.impl.dao;

import com.dwarfeng.fdr.impl.bean.entity.FastJsonFilterSerialVersion;
import com.dwarfeng.fdr.stack.bean.entity.FilterSerialVersion;
import com.dwarfeng.fdr.stack.dao.FilterSerialVersionDao;
import com.dwarfeng.subgrade.impl.dao.RedisBaseDao;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FilterSerialVersionDaoImpl implements FilterSerialVersionDao {

    @Autowired
    private RedisBaseDao<LongIdKey, FilterSerialVersion, FastJsonFilterSerialVersion> baseDao;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public LongIdKey insert(FilterSerialVersion element) throws DaoException {
        return baseDao.insert(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void update(FilterSerialVersion element) throws DaoException {
        baseDao.update(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(LongIdKey key) throws DaoException {
        baseDao.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public boolean exists(LongIdKey key) throws DaoException {
        return baseDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public FilterSerialVersion get(LongIdKey key) throws DaoException {
        return baseDao.get(key);
    }
}
