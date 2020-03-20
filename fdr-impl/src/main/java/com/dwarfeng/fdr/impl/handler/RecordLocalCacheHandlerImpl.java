package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.handler.*;
import com.dwarfeng.fdr.stack.service.EnabledFilterInfoLookupService;
import com.dwarfeng.fdr.stack.service.EnabledTriggerInfoLookupService;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class RecordLocalCacheHandlerImpl implements RecordLocalCacheHandler {

    @Autowired
    private RecordContextFetcher recordContextFetcher;

    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Map<LongIdKey, RecordContext> contextMap = new HashMap<>();
    private Set<LongIdKey> notExistPoints = new HashSet<>();

    @Override
    public boolean existsPoint(LongIdKey pointKey) throws HandlerException {
        try {
            lock.readLock().lock();
            try {
                if (contextMap.containsKey(pointKey)) {
                    return true;
                }
                if (notExistPoints.contains(pointKey)) {
                    return false;
                }
            } finally {
                lock.readLock().unlock();
            }
            lock.writeLock().lock();
            try {
                if (contextMap.containsKey(pointKey)) {
                    return true;
                }
                if (notExistPoints.contains(pointKey)) {
                    return false;
                }
                RecordContext recordContext = recordContextFetcher.fetchContext(pointKey);
                if (Objects.isNull(recordContext)) {
                    notExistPoints.add(pointKey);
                    return false;
                }
                contextMap.put(pointKey, recordContext);
                return true;
            } finally {
                lock.writeLock().unlock();
            }
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public RecordContext getRecordContext(LongIdKey pointKey) throws HandlerException {
        try {
            lock.readLock().lock();
            try {
                if (contextMap.containsKey(pointKey)) {
                    return contextMap.get(pointKey);
                }
                if (notExistPoints.contains(pointKey)) {
                    return null;
                }
            } finally {
                lock.readLock().unlock();
            }
            lock.writeLock().lock();
            try {
                if (contextMap.containsKey(pointKey)) {
                    return contextMap.get(pointKey);
                }
                if (notExistPoints.contains(pointKey)) {
                    return null;
                }
                RecordContext recordContext = recordContextFetcher.fetchContext(pointKey);
                if (Objects.nonNull(recordContext)) {
                    contextMap.put(pointKey, recordContext);
                    return recordContext;
                }
                notExistPoints.add(pointKey);
                return null;
            } finally {
                lock.writeLock().unlock();
            }
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void clear() {
        lock.writeLock().lock();
        try {
            contextMap.clear();
            notExistPoints.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Component
    public static class RecordContextFetcher {

        @Autowired
        private PointMaintainService pointMaintainService;
        @Autowired
        private EnabledFilterInfoLookupService enabledFilterInfoLookupService;
        @Autowired
        private EnabledTriggerInfoLookupService enabledTriggerInfoLookupService;

        @Autowired
        private FilterHandler filterHandler;
        @Autowired
        private TriggerHandler triggerHandler;

        @BehaviorAnalyse
        @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
        public RecordLocalCacheHandler.RecordContext fetchContext(LongIdKey pointKey) throws Exception {
            if (!pointMaintainService.exists(pointKey)) {
                return null;
            }

            Point point = pointMaintainService.get(pointKey);
            List<FilterInfo> filterInfos = enabledFilterInfoLookupService.getEnabledFilterInfos(pointKey);
            List<TriggerInfo> triggerInfos = enabledTriggerInfoLookupService.getEnabledTriggerInfos(pointKey);

            List<Filter> filters = new ArrayList<>();
            List<Trigger> triggers = new ArrayList<>();

            for (FilterInfo filterInfo : filterInfos) {
                filters.add(filterHandler.make(filterInfo));
            }
            for (TriggerInfo triggerInfo : triggerInfos) {
                triggers.add(triggerHandler.make(triggerInfo));
            }

            return new RecordLocalCacheHandler.RecordContext(
                    point,
                    filters,
                    triggers
            );
        }
    }
}
