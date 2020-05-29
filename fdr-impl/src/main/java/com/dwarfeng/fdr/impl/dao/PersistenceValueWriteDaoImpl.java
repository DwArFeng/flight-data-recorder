package com.dwarfeng.fdr.impl.dao;

import com.dwarfeng.fdr.impl.bean.entity.HibernatePersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.dao.PersistenceValueWriteDao;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchWriteDao;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PersistenceValueWriteDaoImpl implements PersistenceValueWriteDao {

    @Autowired
    private HibernateBatchWriteDao<PersistenceValue, HibernatePersistenceValue> batchWriteDao;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void write(PersistenceValue element) throws DaoException {
        batchWriteDao.write(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchWrite(List<PersistenceValue> elements) throws DaoException {
        batchWriteDao.batchWrite(elements);
    }
}
