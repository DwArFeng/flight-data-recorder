package com.dwarfeng.fdr.impl.dao;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.springframework.lang.NonNull;

import java.sql.Connection;
import java.util.List;

/**
 * 本地SQL生成器。
 *
 * @author DwArFeng
 * @since 1.4.1
 */
public interface NSQLGenerator {

    boolean supportType(String type);

    List<PersistenceValue> lookupPersistence(@NonNull Connection connection, Object[] objs) throws DaoException;

    List<PersistenceValue> lookupPersistence(@NonNull Connection connection, Object[] objs, PagingInfo pagingInfo) throws DaoException;

    Integer lookupPersistenceCount(@NonNull Connection connection, Object[] objs) throws DaoException;

    List<FilteredValue> lookupFilteredForPoint(@NonNull Connection connection, Object[] objs) throws DaoException;

    List<FilteredValue> lookupFilteredForPoint(@NonNull Connection connection, Object[] objs, PagingInfo pagingInfo) throws DaoException;

    Integer lookupFilteredCountForPoint(@NonNull Connection connection, Object[] objs) throws DaoException;

    List<FilteredValue> lookupFilteredForFilter(@NonNull Connection connection, Object[] objs) throws DaoException;

    List<FilteredValue> lookupFilteredForFilter(@NonNull Connection connection, Object[] objs, PagingInfo pagingInfo) throws DaoException;

    Integer lookupFilteredCountForFilter(@NonNull Connection connection, Object[] objs) throws DaoException;

    List<TriggeredValue> lookupTriggeredForPoint(@NonNull Connection connection, Object[] objs) throws DaoException;

    List<TriggeredValue> lookupTriggeredForPoint(@NonNull Connection connection, Object[] objs, PagingInfo pagingInfo) throws DaoException;

    Integer lookupTriggeredCountForPoint(@NonNull Connection connection, Object[] objs) throws DaoException;

    List<TriggeredValue> lookupTriggeredForTrigger(@NonNull Connection connection, Object[] objs) throws DaoException;

    List<TriggeredValue> lookupTriggeredForTrigger(@NonNull Connection connection, Object[] objs, PagingInfo pagingInfo) throws DaoException;

    Integer lookupTriggeredCountForTrigger(@NonNull Connection connection, Object[] objs) throws DaoException;
}
