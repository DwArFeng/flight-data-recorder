package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.EnabledTriggerMeta;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.entity.TriggerSerialVersion;
import com.dwarfeng.fdr.stack.cache.EnabledTriggerInfoCache;
import com.dwarfeng.fdr.stack.dao.TriggerInfoDao;
import com.dwarfeng.fdr.stack.dao.TriggerSerialVersionDao;
import com.dwarfeng.fdr.stack.service.EnabledTriggerInfoLookupService;
import com.dwarfeng.fdr.stack.service.TriggerInfoMaintainService;
import com.dwarfeng.sfds.stack.service.LongIdService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnabledTriggerInfoLookupServiceImpl implements EnabledTriggerInfoLookupService {

    @Autowired
    private EnabledTriggerInfoCache enabledTriggerInfoCache;

    @Autowired
    private TriggerInfoDao triggerInfoDao;
    @Autowired
    private TriggerSerialVersionDao triggerSerialVersionDao;

    @Autowired
    @Qualifier("longIdService")
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private LongIdService longIdService;

    @Autowired
    private ServiceExceptionMapper sem;
    @Value("${cache.timeout.key_list.enabled_trigger_info}")
    private long timeout;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public boolean checkSerialVersion(LongIdKey pointKey, long serialVersion) throws ServiceException {
        try {
            if (!triggerSerialVersionDao.exists(pointKey)) {
                return false;
            } else {
                return triggerSerialVersionDao.get(pointKey).getSerialVersion() == serialVersion;
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("检查序列版本时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public EnabledTriggerMeta getEnabledTriggerInfos(LongIdKey pointKey) throws ServiceException {
        List<TriggerInfo> triggerInfos;
        long serialVersion;
        boolean serialVersionRenewFlag = false;

        try {
            if (!triggerSerialVersionDao.exists(pointKey)) {
                serialVersionRenewFlag = true;
            }

            if (enabledTriggerInfoCache.exists(pointKey)) {
                triggerInfos = enabledTriggerInfoCache.get(pointKey);
            } else {
                serialVersionRenewFlag = true;
                triggerInfos = triggerInfoDao.lookup(TriggerInfoMaintainService.ENABLED_CHILD_FOR_POINT, new Object[]{pointKey});
                enabledTriggerInfoCache.set(pointKey, triggerInfos, timeout);
            }

            if (serialVersionRenewFlag) {
                serialVersion = longIdService.nextLongId();
                TriggerSerialVersion triggerSerialVersion = new TriggerSerialVersion(pointKey, serialVersion);
                if (triggerSerialVersionDao.exists(pointKey)) {
                    triggerSerialVersionDao.update(triggerSerialVersion);
                } else {
                    triggerSerialVersionDao.insert(triggerSerialVersion);
                }
            } else {
                serialVersion = triggerSerialVersionDao.get(pointKey).getSerialVersion();
            }

            return new EnabledTriggerMeta(serialVersion, triggerInfos);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询有效的触发器信息时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }
}
