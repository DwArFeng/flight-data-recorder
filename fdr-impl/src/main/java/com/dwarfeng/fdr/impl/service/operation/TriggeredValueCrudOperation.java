package com.dwarfeng.fdr.impl.service.operation;

import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.dao.TriggeredValueDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TriggeredValueCrudOperation implements BatchCrudOperation<LongIdKey, TriggeredValue> {

    @Autowired
    private TriggeredValueDao triggeredValueDao;

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return triggeredValueDao.exists(key);
    }

    @Override
    public TriggeredValue get(LongIdKey key) throws Exception {
        if (!triggeredValueDao.exists(key)) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }
        return triggeredValueDao.get(key);
    }

    @Override
    public LongIdKey insert(TriggeredValue triggeredValue) throws Exception {
        return triggeredValueDao.insert(triggeredValue);
    }

    @Override
    public void update(TriggeredValue triggeredValue) throws Exception {
        triggeredValueDao.update(triggeredValue);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        triggeredValueDao.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return triggeredValueDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return triggeredValueDao.nonExists(keys);
    }

    @Override
    public List<TriggeredValue> batchGet(List<LongIdKey> keys) throws Exception {
        if (!triggeredValueDao.allExists(keys)) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }
        return triggeredValueDao.batchGet(keys);
    }

    @Override
    public List<LongIdKey> batchInsert(List<TriggeredValue> triggeredValues) throws Exception {
        return triggeredValueDao.batchInsert(triggeredValues);
    }

    @Override
    public void batchUpdate(List<TriggeredValue> triggeredValues) throws Exception {
        triggeredValueDao.batchUpdate(triggeredValues);
    }

    @Override
    public void batchDelete(List<LongIdKey> triggeredValues) throws Exception {
        triggeredValueDao.batchDelete(triggeredValues);
    }
}
