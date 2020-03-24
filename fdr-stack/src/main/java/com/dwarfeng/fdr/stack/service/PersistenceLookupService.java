package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.dto.MappedValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.Date;

/**
 * 持久数据查询服务。
 *
 * @author DwArFeng
 * @since 1.4.1
 */
public interface PersistenceLookupService extends Service {

    PagedData<PersistenceValue> lookup(LongIdKey pointKey, Date startDate, Date endDate) throws ServiceException;

    PagedData<PersistenceValue> lookup(
            LongIdKey pointKey, Date startDate, Date endDate, PagingInfo pagingInfo) throws ServiceException;

    PagedData<MappedValue> lookupMapping(
            LongIdKey pointKey, Date startDate, Date endDate, String mapperType, Object[] mapperArgs) throws ServiceException;

    PagedData<MappedValue> lookupMapping(
            LongIdKey pointKey, Date startDate, Date endDate, String mapperType, Object[] mapperArgs,
            PagingInfo pagingInfo) throws ServiceException;
}
