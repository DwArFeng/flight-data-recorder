package com.dwarfeng.fdr.impl.dao;

import com.dwarfeng.fdr.impl.bean.entity.FastJsonTriggerSerialVersion;
import com.dwarfeng.fdr.stack.bean.entity.TriggerSerialVersion;
import com.dwarfeng.fdr.stack.dao.TriggerSerialVersionDao;
import com.dwarfeng.subgrade.impl.dao.RedisBaseDao;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TriggerSerialVersionDaoImpl implements TriggerSerialVersionDao {

    @Autowired
    private RedisBaseDao<LongIdKey, TriggerSerialVersion, FastJsonTriggerSerialVersion> baseDao;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public LongIdKey insert(TriggerSerialVersion element) throws DaoException {
        return baseDao.insert(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void update(TriggerSerialVersion element) throws DaoException {
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
    public TriggerSerialVersion get(LongIdKey key) throws DaoException {
        return baseDao.get(key);
    }
}
