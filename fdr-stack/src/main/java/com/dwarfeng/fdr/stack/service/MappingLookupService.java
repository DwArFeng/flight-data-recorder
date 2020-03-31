package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.dto.TimedValue;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.Date;
import java.util.List;

/**
 * 映射查询服务。
 *
 * @author DwArFeng
 * @since 1.5.0
 */
public interface MappingLookupService extends Service {

    /**
     * 查询指定数据点的实时数据。
     *
     * @param pointKey 指定的数据点。
     * @return 指定数据点的实时数据。
     * @throws ServiceException 服务异常。
     */
    TimedValue lookupRealtime(LongIdKey pointKey) throws ServiceException;

    /**
     * 查询并映射指定数据点的实时数据。
     *
     * @param pointKey   指定的数据点。
     * @param mapperType 映射器的类型。
     * @param mapperArgs 映射器的参数。
     * @return 被映射的指定数据点的实时数据。
     * @throws ServiceException 服务异常。
     */
    List<TimedValue> mappingRealtime(
            LongIdKey pointKey,
            String mapperType, Object[] mapperArgs) throws ServiceException;

    /**
     * 查询指定数据点的持久化数据。
     *
     * @param pointKey  指定的数据点。
     * @param startDate 持久数据的开始时间。
     * @param endDate   持久数据的结束时间。
     * @return 指定数据点的持久数据。
     * @throws ServiceException 服务异常。
     */
    List<TimedValue> lookupPersistence(LongIdKey pointKey, Date startDate, Date endDate) throws ServiceException;

    /**
     * 查询并映射指定数据点的持久化数据。
     *
     * @param pointKey   指定的数据点。
     * @param startDate  持久数据的开始时间。
     * @param endDate    持久数据的结束时间。
     * @param mapperType 映射器的类型。
     * @param mapperArgs 映射器的参数。
     * @return 被映射的指定数据的持久化数据。
     * @throws ServiceException 服务异常。
     */
    List<TimedValue> mappingPersistence(
            LongIdKey pointKey, Date startDate, Date endDate,
            String mapperType, Object[] mapperArgs) throws ServiceException;
}
