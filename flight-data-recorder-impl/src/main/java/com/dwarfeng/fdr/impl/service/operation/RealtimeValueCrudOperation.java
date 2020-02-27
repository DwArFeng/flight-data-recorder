package com.dwarfeng.fdr.impl.service.operation;

import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.cache.RealtimeValueCache;
import com.dwarfeng.fdr.stack.dao.RealtimeValueDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.CrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RealtimeValueCrudOperation implements CrudOperation<LongIdKey, RealtimeValue> {

    @Autowired
    private RealtimeValueDao realtimeValueDao;
    @Autowired
    private RealtimeValueCache realtimeValueCache;
    @Value("${cache.timeout.entity.realtime_value}")
    private long realtimeValueTimeout;

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return realtimeValueCache.exists(key) || realtimeValueDao.exists(key);
    }

    @Override
    public RealtimeValue get(LongIdKey key) throws Exception {
        if (realtimeValueCache.exists(key)) {
            return realtimeValueCache.get(key);
        } else {
            if (!realtimeValueDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            RealtimeValue realtimeValue = realtimeValueDao.get(key);
            realtimeValueCache.push(realtimeValue, realtimeValueTimeout);
            return realtimeValue;
        }
    }

    @Override
    public LongIdKey insert(RealtimeValue realtimeValue) throws Exception {
        realtimeValueCache.push(realtimeValue, realtimeValueTimeout);
        return realtimeValueDao.insert(realtimeValue);
    }

    @Override
    public void update(RealtimeValue realtimeValue) throws Exception {
        realtimeValueCache.push(realtimeValue, realtimeValueTimeout);
        realtimeValueDao.update(realtimeValue);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        realtimeValueDao.delete(key);
        realtimeValueCache.delete(key);
    }
}
