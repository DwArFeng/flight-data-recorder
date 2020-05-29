package com.dwarfeng.fdr.impl.dao;

import com.dwarfeng.fdr.impl.bean.entity.HibernateTriggeredValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.dao.TriggeredValueWriteDao;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchWriteDao;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class TriggeredValueWriteDaoImpl implements TriggeredValueWriteDao {

    @Autowired
    private HibernateBatchWriteDao<TriggeredValue, HibernateTriggeredValue> batchWriteDao;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void write(TriggeredValue element) throws DaoException {
        batchWriteDao.write(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchWrite(List<TriggeredValue> elements) throws DaoException {
        batchWriteDao.batchWrite(elements);
    }
}
