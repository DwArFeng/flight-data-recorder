package com.dwarfeng.fdr.impl.service.operation;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.dao.PersistenceValueDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.CrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersistenceValueCrudOperation implements CrudOperation<LongIdKey, PersistenceValue> {

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
}
