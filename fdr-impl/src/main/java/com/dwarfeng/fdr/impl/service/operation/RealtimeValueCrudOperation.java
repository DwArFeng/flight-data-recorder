package com.dwarfeng.fdr.impl.service.operation;

import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.dao.RealtimeValueDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.CrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RealtimeValueCrudOperation implements CrudOperation<LongIdKey, RealtimeValue> {

    @Autowired
    private RealtimeValueDao realtimeValueDao;

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return realtimeValueDao.exists(key);
    }

    @Override
    public RealtimeValue get(LongIdKey key) throws Exception {
        if (!realtimeValueDao.exists(key)) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }
        return realtimeValueDao.get(key);
    }

    @Override
    public LongIdKey insert(RealtimeValue realtimeValue) throws Exception {
        return realtimeValueDao.insert(realtimeValue);
    }

    @Override
    public void update(RealtimeValue realtimeValue) throws Exception {
        realtimeValueDao.update(realtimeValue);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        realtimeValueDao.delete(key);
    }
}
