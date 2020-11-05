package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.fdr.stack.exception.UnsupportedMapperTypeException;
import com.dwarfeng.fdr.stack.handler.MapLocalCacheHandler;
import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.fdr.stack.service.FilteredValueMaintainService;
import com.dwarfeng.fdr.stack.service.MappingLookupService;
import com.dwarfeng.fdr.stack.service.PersistenceValueMaintainService;
import com.dwarfeng.fdr.stack.service.TriggeredValueMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MappingLookupServiceImpl implements MappingLookupService {

    @Autowired
    private MapLocalCacheHandler mapLocalCacheHandler;
    @Autowired
    private PersistenceValueMaintainService persistenceValueMaintainService;
    @Autowired
    private FilteredValueMaintainService filteredValueMaintainService;
    @Autowired
    private TriggeredValueMaintainService triggeredValueMaintainService;

    @Autowired
    private ServiceExceptionMapper sem;

    @SuppressWarnings("DuplicatedCode")
    @Override
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<TimedValue> mappingPersistenceValue(
            String mapperType, LongIdKey pointKey, Date startDate, Date endDate, Object[] mapperArgs)
            throws ServiceException {
        try {
            Mapper mapper = checkAndGetMapper(mapperType);
            List<TimedValue> timedValues = persistenceValueMaintainService.lookup(
                    PersistenceValueMaintainService.CHILD_FOR_POINT_BETWEEN,
                    new Object[]{pointKey, startDate, endDate}).getData().stream()
                    .map(value -> new TimedValue(value.getValue(), value.getHappenedDate()))
                    .collect(Collectors.toList());
            TimedValue previous = Optional.ofNullable(persistenceValueMaintainService.previous(pointKey, startDate))
                    .map(value -> new TimedValue(value.getValue(), value.getHappenedDate())).orElse(null);
            return mapper.map(new Mapper.MapData(timedValues, previous, startDate, endDate, mapperArgs));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询并映射持久数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<TimedValue> mappingFilteredValue(
            String mapperType, LongIdKey pointKey, Date startDate, Date endDate, Object[] mapperArgs)
            throws ServiceException {
        try {
            Mapper mapper = checkAndGetMapper(mapperType);
            List<TimedValue> timedValues = filteredValueMaintainService.lookup(
                    FilteredValueMaintainService.CHILD_FOR_POINT_BETWEEN,
                    new Object[]{pointKey, startDate, endDate}).getData().stream()
                    .map(value -> new TimedValue(value.getValue(), value.getHappenedDate()))
                    .collect(Collectors.toList());
            TimedValue previous = Optional.ofNullable(filteredValueMaintainService.previous(pointKey, startDate))
                    .map(value -> new TimedValue(value.getValue(), value.getHappenedDate())).orElse(null);
            return mapper.map(new Mapper.MapData(timedValues, previous, startDate, endDate, mapperArgs));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询并映射被过滤数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<TimedValue> mappingTriggeredValue(
            String mapperType, LongIdKey pointKey, Date startDate, Date endDate, Object[] mapperArgs)
            throws ServiceException {
        try {
            Mapper mapper = checkAndGetMapper(mapperType);
            List<TimedValue> timedValues = triggeredValueMaintainService.lookup(
                    TriggeredValueMaintainService.CHILD_FOR_POINT_BETWEEN,
                    new Object[]{pointKey, startDate, endDate}).getData().stream()
                    .map(value -> new TimedValue(value.getValue(), value.getHappenedDate()))
                    .collect(Collectors.toList());
            TimedValue previous = Optional.ofNullable(triggeredValueMaintainService.previous(pointKey, startDate))
                    .map(value -> new TimedValue(value.getValue(), value.getHappenedDate())).orElse(null);
            return mapper.map(new Mapper.MapData(timedValues, previous, startDate, endDate, mapperArgs));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询并映射被触发数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    private Mapper checkAndGetMapper(String mapperType) throws HandlerException {
        Mapper mapper = mapLocalCacheHandler.getMapper(mapperType);
        if (Objects.isNull(mapper)) {
            throw new UnsupportedMapperTypeException(mapperType);
        }
        return mapper;
    }
}
