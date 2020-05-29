package com.dwarfeng.fdr.impl.dao;

import com.dwarfeng.fdr.impl.bean.entity.HibernateFilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.dao.FilteredValueWriteDao;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchWriteDao;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class FilteredValueWriteDaoImpl implements FilteredValueWriteDao {

    @Autowired
    private HibernateBatchWriteDao<FilteredValue, HibernateFilteredValue> batchWriteDao;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void write(FilteredValue element) throws DaoException {
        batchWriteDao.write(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchWrite(List<FilteredValue> elements) throws DaoException {
        batchWriteDao.batchWrite(elements);
    }
}
