package com.dwarfeng.fdr.impl.service.operation;

import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.dao.TriggeredValueDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.CrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TriggeredValueCrudOperation implements CrudOperation<LongIdKey, TriggeredValue> {

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
}
