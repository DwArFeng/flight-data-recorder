package com.dwarfeng.fdr.impl.fuhdao;

import com.dwarfeng.fdr.impl.fuhbean.entity.HibernateTriggerSupport;
import com.dwarfeng.fdr.stack.bean.entity.TriggerSupport;
import com.dwarfeng.fdr.stack.dao.TriggerSupportDao;
import com.dwarfeng.subgrade.impl.dao.HibernateBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateEntireLookupDao;
import com.dwarfeng.subgrade.impl.dao.HibernatePresetLookupDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TriggerSupportDaoImpl implements TriggerSupportDao {

    @Autowired
    private HibernateBaseDao<StringIdKey, HibernateStringIdKey, TriggerSupport, HibernateTriggerSupport> baseDao;
    @Autowired
    private HibernateEntireLookupDao<TriggerSupport, HibernateTriggerSupport> entireLookupDao;
    @Autowired
    private HibernatePresetLookupDao<TriggerSupport, HibernateTriggerSupport> presetLookupDao;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public StringIdKey insert(TriggerSupport element) throws DaoException {
        return baseDao.insert(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void update(TriggerSupport element) throws DaoException {
        baseDao.update(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(StringIdKey key) throws DaoException {
        baseDao.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public boolean exists(StringIdKey key) throws DaoException {
        return baseDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public TriggerSupport get(StringIdKey key) throws DaoException {
        return baseDao.get(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public List<TriggerSupport> lookup() throws DaoException {
        return entireLookupDao.lookup();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public List<TriggerSupport> lookup(PagingInfo pagingInfo) throws DaoException {
        return entireLookupDao.lookup(pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public int lookupCount() throws DaoException {
        return entireLookupDao.lookupCount();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public List<TriggerSupport> lookup(String preset, Object[] objs) throws DaoException {
        return presetLookupDao.lookup(preset, objs);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public List<TriggerSupport> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws DaoException {
        return presetLookupDao.lookup(preset, objs, pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public int lookupCount(String preset, Object[] objs) throws DaoException {
        return presetLookupDao.lookupCount(preset, objs);
    }
}
