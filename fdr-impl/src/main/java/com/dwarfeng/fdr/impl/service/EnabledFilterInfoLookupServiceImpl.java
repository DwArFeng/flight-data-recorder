package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.EnabledFilterMeta;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilterSerialVersion;
import com.dwarfeng.fdr.stack.cache.EnabledFilterInfoCache;
import com.dwarfeng.fdr.stack.dao.FilterInfoDao;
import com.dwarfeng.fdr.stack.dao.FilterSerialVersionDao;
import com.dwarfeng.fdr.stack.service.EnabledFilterInfoLookupService;
import com.dwarfeng.fdr.stack.service.FilterInfoMaintainService;
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
public class EnabledFilterInfoLookupServiceImpl implements EnabledFilterInfoLookupService {

    @Autowired
    private EnabledFilterInfoCache enabledFilterInfoCache;

    @Autowired
    private FilterInfoDao filterInfoDao;
    @Autowired
    private FilterSerialVersionDao filterSerialVersionDao;

    @Autowired
    @Qualifier("longIdService")
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private LongIdService longIdService;

    @Autowired
    private ServiceExceptionMapper sem;
    @Value("${cache.timeout.key_list.enabled_filter_info}")
    private long timeout;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public boolean checkSerialVersion(LongIdKey pointKey, long serialVersion) throws ServiceException {
        try {
            if (!filterSerialVersionDao.exists(pointKey)) {
                return false;
            } else {
                return filterSerialVersionDao.get(pointKey).getSerialVersion() == serialVersion;
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
    public EnabledFilterMeta getEnabledFilterInfos(LongIdKey pointKey) throws ServiceException {
        List<FilterInfo> filterInfos;
        long serialVersion;
        boolean serialVersionRenewFlag = false;

        try {
            if (!filterSerialVersionDao.exists(pointKey)) {
                serialVersionRenewFlag = true;
            }

            if (enabledFilterInfoCache.exists(pointKey)) {
                filterInfos = enabledFilterInfoCache.get(pointKey);
            } else {
                serialVersionRenewFlag = true;
                filterInfos = filterInfoDao.lookup(FilterInfoMaintainService.ENABLED_CHILD_FOR_POINT, new Object[]{pointKey});
                enabledFilterInfoCache.set(pointKey, filterInfos, timeout);
            }

            if (serialVersionRenewFlag) {
                serialVersion = longIdService.nextLongId();
                FilterSerialVersion filterSerialVersion = new FilterSerialVersion(pointKey, serialVersion);
                if (filterSerialVersionDao.exists(pointKey)) {
                    filterSerialVersionDao.update(filterSerialVersion);
                } else {
                    filterSerialVersionDao.insert(filterSerialVersion);
                }
            } else {
                serialVersion = filterSerialVersionDao.get(pointKey).getSerialVersion();
            }

            return new EnabledFilterMeta(serialVersion, filterInfos);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询有效的过滤器信息时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }
}
