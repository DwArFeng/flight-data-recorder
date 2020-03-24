package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.dto.MappedValue;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.Date;

/**
 * 被过滤数据查询服务。
 *
 * @author DwArFeng
 * @since 1.4.1
 */
public interface FilteredLookupService extends Service {

    PagedData<FilteredValue> lookupForPoint(LongIdKey pointKey, Date startDate, Date endDate) throws ServiceException;

    PagedData<FilteredValue> lookupForPoint(
            LongIdKey pointKey, Date startDate, Date endDate, PagingInfo pagingInfo) throws ServiceException;

    PagedData<MappedValue> lookupMappingForPoint(
            LongIdKey pointKey, Date startDate, Date endDate, String mapperType, Object[] mapperArgs) throws ServiceException;

    PagedData<MappedValue> lookupMappingForPoint(
            LongIdKey pointKey, Date startDate, Date endDate, String mapperType, Object[] mapperArgs,
            PagingInfo pagingInfo) throws ServiceException;

    PagedData<FilteredValue> lookupForFilter(LongIdKey filterKey, Date startDate, Date endDate) throws ServiceException;

    PagedData<FilteredValue> lookupForFilter(
            LongIdKey filterKey, Date startDate, Date endDate, PagingInfo pagingInfo) throws ServiceException;

    PagedData<MappedValue> lookupMappingForFilter(
            LongIdKey filterKey, Date startDate, Date endDate, String mapperType, Object[] mapperArgs) throws ServiceException;

    PagedData<MappedValue> lookupMappingForFilter(
            LongIdKey filterKey, Date startDate, Date endDate, String mapperType, Object[] mapperArgs,
            PagingInfo pagingInfo) throws ServiceException;
}
