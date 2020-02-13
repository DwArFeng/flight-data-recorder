package com.dwarfeng.fdr.impl.service.maintain.service.operation;

import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.cache.EnabledTriggerInfoCache;
import com.dwarfeng.fdr.stack.cache.TriggerInfoCache;
import com.dwarfeng.fdr.stack.cache.TriggeredValueCache;
import com.dwarfeng.fdr.stack.dao.TriggerInfoDao;
import com.dwarfeng.fdr.stack.dao.TriggeredValueDao;
import com.dwarfeng.fdr.stack.service.TriggeredValueMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.CrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TriggerInfoCrudOperation implements CrudOperation<LongIdKey, TriggerInfo> {

    @Autowired
    private TriggerInfoDao triggerInfoDao;
    @Autowired
    private TriggeredValueDao triggeredValueDao;

    @Autowired
    private TriggerInfoCache triggerInfoCache;
    @Autowired
    private TriggeredValueCache triggeredValueCache;
    @Autowired
    private EnabledTriggerInfoCache enabledTriggerInfoCache;

    @Value("${cache.timeout.entity.trigger_info}")
    private long triggerInfoTimeout;

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return triggerInfoCache.exists(key) || triggerInfoDao.exists(key);
    }

    @Override
    public TriggerInfo get(LongIdKey key) throws Exception {
        if (triggerInfoCache.exists(key)) {
            return triggerInfoCache.get(key);
        } else {
            if (!triggerInfoDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            TriggerInfo triggerInfo = triggerInfoDao.get(key);
            triggerInfoCache.push(triggerInfo, triggerInfoTimeout);
            return triggerInfo;
        }
    }

    @Override
    public LongIdKey insert(TriggerInfo triggerInfo) throws Exception {
        if (Objects.nonNull(triggerInfo.getPointKey())) {
            enabledTriggerInfoCache.delete(triggerInfo.getPointKey());
        }

        triggerInfoCache.push(triggerInfo, triggerInfoTimeout);
        return triggerInfoDao.insert(triggerInfo);
    }

    @Override
    public void update(TriggerInfo triggerInfo) throws Exception {
        TriggerInfo oldTriggerInfo = get(triggerInfo.getKey());
        if (Objects.nonNull(oldTriggerInfo.getPointKey())) {
            enabledTriggerInfoCache.delete(oldTriggerInfo.getPointKey());
        }

        if (Objects.nonNull(triggerInfo.getPointKey())) {
            enabledTriggerInfoCache.delete(triggerInfo.getPointKey());
        }

        triggerInfoCache.push(triggerInfo, triggerInfoTimeout);
        triggerInfoDao.update(triggerInfo);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        TriggerInfo oldTriggerInfo = get(key);
        if (Objects.nonNull(oldTriggerInfo.getPointKey())) {
            enabledTriggerInfoCache.delete(oldTriggerInfo.getPointKey());
        }

        List<LongIdKey> triggeredValueKeys = triggeredValueDao.lookup(TriggeredValueMaintainService.CHILD_FOR_TRIGGER, new Object[]{key})
                .stream().map(TriggeredValue::getKey).collect(Collectors.toList());
        triggeredValueDao.batchDelete(triggeredValueKeys);
        triggeredValueCache.batchDelete(triggeredValueKeys);

        triggerInfoDao.delete(key);
        triggerInfoCache.delete(key);
    }
}
