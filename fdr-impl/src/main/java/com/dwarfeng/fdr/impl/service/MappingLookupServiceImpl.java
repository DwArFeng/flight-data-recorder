package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.exception.PersistenceDisabledException;
import com.dwarfeng.fdr.stack.exception.RealtimeDisabledException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.fdr.stack.handler.MapperHandler;
import com.dwarfeng.fdr.stack.service.MappingLookupService;
import com.dwarfeng.fdr.stack.service.PersistenceValueMaintainService;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.fdr.stack.service.RealtimeValueMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MappingLookupServiceImpl implements MappingLookupService {

    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private RealtimeValueMaintainService realtimeValueMaintainService;
    @Autowired
    private PersistenceValueMaintainService persistenceValueMaintainService;
    @Autowired
    private MapperHandler mapperHandler;

    @Autowired
    private ServiceExceptionMapper sem;

    @Override
    public TimedValue lookupRealtime(LongIdKey pointKey) throws ServiceException {
        try {
            return internalLookupRealtime(pointKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询并映射实时数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    public List<TimedValue> mappingRealtime(
            LongIdKey pointKey,
            String mapperType, Object[] mapperArgs) throws ServiceException {
        try {
            TimedValue timedValue = internalLookupRealtime(pointKey);
            return makeMapper(mapperType, mapperArgs).map(Collections.singletonList(timedValue));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询并映射实时数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    private TimedValue internalLookupRealtime(LongIdKey pointKey) throws Exception {
        Point point = pointMaintainService.get(pointKey);
        if (!point.isRealtimeEnabled()) {
            throw new RealtimeDisabledException(pointKey);
        }
        RealtimeValue realtimeValue = realtimeValueMaintainService.get(pointKey);
        return new TimedValue(realtimeValue.getValue(), realtimeValue.getHappenedDate());
    }

    @Override
    public List<TimedValue> lookupPersistence(
            LongIdKey pointKey, Date startDate, Date endDate) throws ServiceException {
        try {
            return internalLookupPersistence(pointKey, startDate, endDate);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询并映射持久数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    public List<TimedValue> mappingPersistence(
            LongIdKey pointKey, Date startDate, Date endDate,
            String mapperType, Object[] mapperArgs) throws ServiceException {
        try {
            List<TimedValue> timedValues = internalLookupPersistence(pointKey, startDate, endDate);
            return makeMapper(mapperType, mapperArgs).map(timedValues);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询并映射持久数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    private List<TimedValue> internalLookupPersistence(
            LongIdKey pointKey, Date startDate, Date endDate) throws Exception {
        Point point = pointMaintainService.get(pointKey);
        if (!point.isPersistenceEnabled()) {
            throw new PersistenceDisabledException(pointKey);
        }
        List<PersistenceValue> persistenceValues = persistenceValueMaintainService.lookup(
                PersistenceValueMaintainService.CHILD_FOR_POINT_BETWEEN,
                new Object[]{pointKey, startDate, endDate}).getData();
        return persistenceValues.stream()
                .map(p -> new TimedValue(p.getValue(), p.getHappenedDate())).collect(Collectors.toList());
    }

    private Mapper makeMapper(String mapperType, Object[] mapperArgs) throws Exception {
        return mapperHandler.make(mapperType, mapperArgs);
    }
}
