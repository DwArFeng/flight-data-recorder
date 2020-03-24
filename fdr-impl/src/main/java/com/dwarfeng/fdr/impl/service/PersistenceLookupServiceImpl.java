package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.MappedValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.dao.PersistenceValueDao;
import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.fdr.stack.handler.MapperHandler;
import com.dwarfeng.fdr.stack.service.PersistenceLookupService;
import com.dwarfeng.fdr.stack.service.PersistenceValueMaintainService;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PersistenceLookupServiceImpl implements PersistenceLookupService {

    @Autowired
    private PersistenceValueDao dao;
    @Autowired
    private MapperHandler mapperHandler;

    @Autowired
    private ServiceExceptionMapper sem;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<PersistenceValue> lookup(LongIdKey pointKey, Date startDate, Date endDate) throws ServiceException {
        try {
            return PagingUtil.pagedData(dao.lookup(PersistenceValueMaintainService.CHILD_FOR_POINT_BETWEEN,
                    new Object[]{pointKey, startDate, endDate}));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询持久数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<PersistenceValue> lookup(
            LongIdKey pointKey, Date startDate, Date endDate, PagingInfo pagingInfo) throws ServiceException {
        try {
            return PagingUtil.pagedData(pagingInfo,
                    dao.lookupCount(PersistenceValueMaintainService.CHILD_FOR_POINT_BETWEEN,
                            new Object[]{pointKey, startDate, endDate}),
                    dao.lookup(PersistenceValueMaintainService.CHILD_FOR_POINT_BETWEEN,
                            new Object[]{pointKey, startDate, endDate}, pagingInfo));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询持久数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<MappedValue> lookupMapping(
            LongIdKey pointKey, Date startDate, Date endDate, String mapperType, Object[] mapperArgs) throws ServiceException {
        try {
            Mapper mapper = mapperHandler.make(mapperType, mapperArgs);
            if (!mapper.supportPersistenceValue()) {
                throw new MapperException("Mapper " + mapperType + " 不支持持久数据值");
            }
            List<PersistenceValue> persistenceValues = dao.lookup(PersistenceValueMaintainService.CHILD_FOR_POINT_BETWEEN,
                    new Object[]{pointKey, startDate, endDate});
            List<MappedValue> mappedValues = mapper.mapPersistenceValue(persistenceValues);
            return PagingUtil.pagedData(mappedValues);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询持久数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<MappedValue> lookupMapping(
            LongIdKey pointKey, Date startDate, Date endDate, String mapperType, Object[] mapperArgs,
            PagingInfo pagingInfo) throws ServiceException {
        try {
            Mapper mapper = mapperHandler.make(mapperType, mapperArgs);
            if (!mapper.supportPersistenceValue()) {
                throw new MapperException("Mapper " + mapperType + " 不支持持久数据值");
            }
            List<PersistenceValue> persistenceValues = dao.lookup(PersistenceValueMaintainService.CHILD_FOR_POINT_BETWEEN,
                    new Object[]{pointKey, startDate, endDate});
            List<MappedValue> mappedValues = mapper.mapPersistenceValue(persistenceValues);
            return PagingUtil.pagedData(pagingInfo, mappedValues.size(), PagingUtil.subList(mappedValues, pagingInfo));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询持久数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }
}
