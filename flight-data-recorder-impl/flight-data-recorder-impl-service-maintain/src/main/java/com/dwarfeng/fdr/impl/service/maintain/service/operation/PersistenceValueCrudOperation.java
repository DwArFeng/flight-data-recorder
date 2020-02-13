package com.dwarfeng.fdr.impl.service.maintain.service.operation;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.cache.PersistenceValueCache;
import com.dwarfeng.fdr.stack.dao.PersistenceValueDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.CrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PersistenceValueCrudOperation implements CrudOperation<LongIdKey, PersistenceValue> {

    @Autowired
    private PersistenceValueDao persistenceValueDao;
    @Autowired
    private PersistenceValueCache persistenceValueCache;
    @Value("${cache.timeout.entity.persistence_value}")
    private long persistenceValueTimeout;

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return persistenceValueCache.exists(key) || persistenceValueDao.exists(key);
    }

    @Override
    public PersistenceValue get(LongIdKey key) throws Exception {
        if (persistenceValueCache.exists(key)) {
            return persistenceValueCache.get(key);
        } else {
            if (!persistenceValueDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            PersistenceValue persistenceValue = persistenceValueDao.get(key);
            persistenceValueCache.push(persistenceValue, persistenceValueTimeout);
            return persistenceValue;
        }
    }

    @Override
    public LongIdKey insert(PersistenceValue persistenceValue) throws Exception {
        persistenceValueCache.push(persistenceValue, persistenceValueTimeout);
        return persistenceValueDao.insert(persistenceValue);
    }

    @Override
    public void update(PersistenceValue persistenceValue) throws Exception {
        persistenceValueCache.push(persistenceValue, persistenceValueTimeout);
        persistenceValueDao.update(persistenceValue);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        persistenceValueDao.delete(key);
        persistenceValueCache.delete(key);
    }
}
