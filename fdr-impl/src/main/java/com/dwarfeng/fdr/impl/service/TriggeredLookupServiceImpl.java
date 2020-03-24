package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.MappedValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.fdr.stack.handler.MapperHandler;
import com.dwarfeng.fdr.stack.service.TriggeredLookupService;
import com.dwarfeng.fdr.stack.service.TriggeredValueMaintainService;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TriggeredLookupServiceImpl implements TriggeredLookupService {

    @Autowired
    private TriggeredValueMaintainService maintainService;
    @Autowired
    private MapperHandler mapperHandler;

    @Autowired
    private ServiceExceptionMapper sem;

    @Override
    public PagedData<TriggeredValue> lookupForPoint(
            LongIdKey pointKey, Date startDate, Date endDate) throws ServiceException {
        try {
            return maintainService.lookup(
                    TriggeredValueMaintainService.CHILD_FOR_POINT_BETWEEN, new Object[]{pointKey, startDate, endDate});
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询被过滤数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    public PagedData<TriggeredValue> lookupForPoint(
            LongIdKey pointKey, Date startDate, Date endDate, PagingInfo pagingInfo) throws ServiceException {
        try {
            return maintainService.lookup(
                    TriggeredValueMaintainService.CHILD_FOR_POINT_BETWEEN, new Object[]{pointKey, startDate, endDate},
                    pagingInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询被过滤数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    public PagedData<MappedValue> lookupMappingForPoint(
            LongIdKey pointKey, Date startDate, Date endDate, String mapperType, Object[] mapperArgs) throws ServiceException {
        try {
            Mapper mapper = mapperHandler.make(mapperType, mapperArgs);
            if (!mapper.supportTriggeredValue()) {
                throw new MapperException("Mapper " + mapperType + " 不支持被过滤数据值");
            }
            PagedData<TriggeredValue> lookup = maintainService.lookup(
                    TriggeredValueMaintainService.CHILD_FOR_POINT_BETWEEN, new Object[]{pointKey, startDate, endDate});
            List<MappedValue> mappedValues = mapper.mapTriggeredValue(lookup.getData());
            return PagingUtil.pagedData(mappedValues);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询被过滤数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    public PagedData<MappedValue> lookupMappingForPoint(
            LongIdKey pointKey, Date startDate, Date endDate, String mapperType, Object[] mapperArgs,
            PagingInfo pagingInfo) throws ServiceException {
        try {
            Mapper mapper = mapperHandler.make(mapperType, mapperArgs);
            if (!mapper.supportTriggeredValue()) {
                throw new MapperException("Mapper " + mapperType + " 不支持被过滤数据值");
            }
            PagedData<TriggeredValue> lookup = maintainService.lookup(
                    TriggeredValueMaintainService.CHILD_FOR_POINT_BETWEEN, new Object[]{pointKey, startDate, endDate});
            List<MappedValue> mappedValues = mapper.mapTriggeredValue(lookup.getData());
            return PagingUtil.pagedData(pagingInfo, mappedValues.size(), PagingUtil.subList(mappedValues, pagingInfo));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询被过滤数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    public PagedData<TriggeredValue> lookupForTrigger(LongIdKey triggerKey, Date startDate, Date endDate) throws ServiceException {
        try {
            return maintainService.lookup(
                    TriggeredValueMaintainService.CHILD_FOR_TRIGGER_BETWEEN, new Object[]{triggerKey, startDate, endDate});
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询被过滤数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    public PagedData<TriggeredValue> lookupForTrigger(
            LongIdKey triggerKey, Date startDate, Date endDate, PagingInfo pagingInfo) throws ServiceException {
        try {
            return maintainService.lookup(
                    TriggeredValueMaintainService.CHILD_FOR_TRIGGER_BETWEEN, new Object[]{triggerKey, startDate, endDate},
                    pagingInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询被过滤数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    public PagedData<MappedValue> lookupMappingForTrigger(
            LongIdKey triggerKey, Date startDate, Date endDate, String mapperType, Object[] mapperArgs) throws ServiceException {
        try {
            Mapper mapper = mapperHandler.make(mapperType, mapperArgs);
            if (!mapper.supportTriggeredValue()) {
                throw new MapperException("Mapper " + mapperType + " 不支持被过滤数据值");
            }
            PagedData<TriggeredValue> lookup = maintainService.lookup(
                    TriggeredValueMaintainService.CHILD_FOR_TRIGGER_BETWEEN, new Object[]{triggerKey, startDate, endDate});
            List<MappedValue> mappedValues = mapper.mapTriggeredValue(lookup.getData());
            return PagingUtil.pagedData(mappedValues);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询被过滤数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    public PagedData<MappedValue> lookupMappingForTrigger(
            LongIdKey triggerKey, Date startDate, Date endDate, String mapperType, Object[] mapperArgs,
            PagingInfo pagingInfo) throws ServiceException {
        try {
            Mapper mapper = mapperHandler.make(mapperType, mapperArgs);
            if (!mapper.supportTriggeredValue()) {
                throw new MapperException("Mapper " + mapperType + " 不支持被过滤数据值");
            }
            PagedData<TriggeredValue> lookup = maintainService.lookup(
                    TriggeredValueMaintainService.CHILD_FOR_TRIGGER_BETWEEN, new Object[]{triggerKey, startDate, endDate});
            List<MappedValue> mappedValues = mapper.mapTriggeredValue(lookup.getData());
            return PagingUtil.pagedData(pagingInfo, mappedValues.size(), PagingUtil.subList(mappedValues, pagingInfo));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询被过滤数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }
}
