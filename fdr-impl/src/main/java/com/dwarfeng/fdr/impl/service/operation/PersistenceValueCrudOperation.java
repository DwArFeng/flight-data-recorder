package com.dwarfeng.fdr.impl.service.operation;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.dao.PersistenceValueDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersistenceValueCrudOperation implements BatchCrudOperation<LongIdKey, PersistenceValue> {

    @Autowired
    private PersistenceValueDao persistenceValueDao;

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return persistenceValueDao.exists(key);
    }

    @Override
    public PersistenceValue get(LongIdKey key) throws Exception {
        if (!persistenceValueDao.exists(key)) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }
        return persistenceValueDao.get(key);
    }

    @Override
    public LongIdKey insert(PersistenceValue persistenceValue) throws Exception {
        return persistenceValueDao.insert(persistenceValue);
    }

    @Override
    public void update(PersistenceValue persistenceValue) throws Exception {
        persistenceValueDao.update(persistenceValue);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        persistenceValueDao.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return persistenceValueDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return persistenceValueDao.nonExists(keys);
    }

    @Override
    public List<PersistenceValue> batchGet(List<LongIdKey> keys) throws Exception {
        if (!persistenceValueDao.allExists(keys)) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }
        return persistenceValueDao.batchGet(keys);
    }

    @Override
    public List<LongIdKey> batchInsert(List<PersistenceValue> persistenceValues) throws Exception {
        return persistenceValueDao.batchInsert(persistenceValues);
    }

    @Override
    public void batchUpdate(List<PersistenceValue> persistenceValues) throws Exception {
        persistenceValueDao.batchUpdate(persistenceValues);
    }

    @Override
    public void batchDelete(List<LongIdKey> persistenceValues) throws Exception {
        persistenceValueDao.batchDelete(persistenceValues);
    }
}
