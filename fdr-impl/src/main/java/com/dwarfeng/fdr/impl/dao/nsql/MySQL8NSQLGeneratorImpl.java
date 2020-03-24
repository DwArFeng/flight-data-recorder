package com.dwarfeng.fdr.impl.dao.nsql;

import com.dwarfeng.fdr.impl.dao.NSQLGenerator;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;

@Component
public class MySQL8NSQLGeneratorImpl<E extends Entity> implements NSQLGenerator {

    public static final String SUPPORT_TYPE = "org.hibernate.dialect.MySQL8Dialect";

    @Override
    public boolean supportType(String type) {
        return Objects.equals(SUPPORT_TYPE, type);
    }

    @Override
    public List<PersistenceValue> lookupPersistence(@NonNull Connection connection, Object[] objs) throws DaoException {
        try {
            LongIdKey pointKey;
            Date startDate;
            Date endDate;
            try {
                pointKey = (LongIdKey) objs[0];
                startDate = Optional.ofNullable((Date) objs[1]).orElseThrow(NullPointerException::new);
                endDate = Optional.ofNullable((Date) objs[2]).orElseThrow(NullPointerException::new);
            } catch (Exception e) {
                throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
            }

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT ");
            {
                sqlBuilder.append("tbl.id,");
                sqlBuilder.append("tbl.happened_date,");
                sqlBuilder.append("tbl.value ");
            }
            sqlBuilder.append("FROM ");
            {
                sqlBuilder.append("tbl_persistence_value AS tbl ");
            }
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(pointKey)) {
                    sqlBuilder.append("tbl.point_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.point_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }
            sqlBuilder.append("ORDER BY ");
            {
                sqlBuilder.append("tbl.id ASC ");
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            if (Objects.isNull(pointKey)) {
                preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
            } else {
                preparedStatement.setLong(1, pointKey.getLongId());
                preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            List<PersistenceValue> persistenceValues = new ArrayList<>();
            while (resultSet.next()) {
                persistenceValues.add(new PersistenceValue(
                        new LongIdKey(resultSet.getLong(1)),
                        pointKey,
                        new Date(resultSet.getTimestamp(2).getTime()),
                        resultSet.getString(3)
                ));
            }
            return persistenceValues;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<PersistenceValue> lookupPersistence(@NonNull Connection connection, Object[] objs, PagingInfo pagingInfo) throws DaoException {
        try {
            LongIdKey pointKey;
            Date startDate;
            Date endDate;
            try {
                pointKey = (LongIdKey) objs[0];
                startDate = Optional.ofNullable((Date) objs[1]).orElseThrow(NullPointerException::new);
                endDate = Optional.ofNullable((Date) objs[2]).orElseThrow(NullPointerException::new);
            } catch (Exception e) {
                throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
            }

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT ");
            {
                sqlBuilder.append("tbl.id,");
                sqlBuilder.append("tbl.happened_date,");
                sqlBuilder.append("tbl.value ");
            }
            sqlBuilder.append("FROM ");
            {
                sqlBuilder.append("tbl_persistence_value AS tbl ");
            }
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(pointKey)) {
                    sqlBuilder.append("tbl.point_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.point_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }
            sqlBuilder.append("ORDER BY ");
            {
                sqlBuilder.append("tbl.id ASC ");
            }
            sqlBuilder.append("LIMIT ?, ?");

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            if (Objects.isNull(pointKey)) {
                preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
                preparedStatement.setInt(3, pagingInfo.getRows() * pagingInfo.getPage());
                preparedStatement.setInt(4, pagingInfo.getRows());
            } else {
                preparedStatement.setLong(1, pointKey.getLongId());
                preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
                preparedStatement.setInt(4, pagingInfo.getRows() * pagingInfo.getPage());
                preparedStatement.setInt(5, pagingInfo.getRows());
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            List<PersistenceValue> persistenceValues = new ArrayList<>();
            while (resultSet.next()) {
                persistenceValues.add(new PersistenceValue(
                        new LongIdKey(resultSet.getLong(1)),
                        pointKey,
                        new Date(resultSet.getTimestamp(2).getTime()),
                        resultSet.getString(3)
                ));
            }
            return persistenceValues;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Integer lookupPersistenceCount(@NonNull Connection connection, Object[] objs) throws DaoException {
        try {
            LongIdKey pointKey;
            Date startDate;
            Date endDate;
            try {
                pointKey = (LongIdKey) objs[0];
                startDate = Optional.ofNullable((Date) objs[1]).orElseThrow(NullPointerException::new);
                endDate = Optional.ofNullable((Date) objs[2]).orElseThrow(NullPointerException::new);
            } catch (Exception e) {
                throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
            }

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT ");
            {
                sqlBuilder.append("COUNT(tbl.id) ");
            }
            sqlBuilder.append("FROM ");
            {
                sqlBuilder.append("tbl_persistence_value AS tbl ");
            }
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(pointKey)) {
                    sqlBuilder.append("tbl.point_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.point_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            if (Objects.isNull(pointKey)) {
                preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
            } else {
                preparedStatement.setLong(1, pointKey.getLongId());
                preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Long.valueOf(resultSet.getLong(1)).intValue();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<FilteredValue> lookupFilteredForPoint(@NonNull Connection connection, Object[] objs) throws DaoException {
        try {
            LongIdKey pointKey;
            Date startDate;
            Date endDate;
            try {
                pointKey = (LongIdKey) objs[0];
                startDate = Optional.ofNullable((Date) objs[1]).orElseThrow(NullPointerException::new);
                endDate = Optional.ofNullable((Date) objs[2]).orElseThrow(NullPointerException::new);
            } catch (Exception e) {
                throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
            }

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT ");
            {
                sqlBuilder.append("tbl.id,");
                sqlBuilder.append("tbl.filter_id,");
                sqlBuilder.append("tbl.happened_date, ");
                sqlBuilder.append("tbl.value, ");
                sqlBuilder.append("tbl.message ");
            }
            sqlBuilder.append("FROM ");
            {
                sqlBuilder.append("tbl_filtered_value AS tbl ");
            }
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(pointKey)) {
                    sqlBuilder.append("tbl.point_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.point_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }
            sqlBuilder.append("ORDER BY ");
            {
                sqlBuilder.append("tbl.id ASC ");
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            if (Objects.isNull(pointKey)) {
                preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
            } else {
                preparedStatement.setLong(1, pointKey.getLongId());
                preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            List<FilteredValue> filteredValues = new ArrayList<>();
            while (resultSet.next()) {
                filteredValues.add(new FilteredValue(
                        new LongIdKey(resultSet.getLong(1)),
                        pointKey,
                        new LongIdKey(resultSet.getLong(2)),
                        new Date(resultSet.getTimestamp(3).getTime()),
                        resultSet.getString(4),
                        resultSet.getString(5)
                ));
            }
            return filteredValues;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<FilteredValue> lookupFilteredForPoint(@NonNull Connection connection, Object[] objs, PagingInfo pagingInfo) throws DaoException {
        try {
            LongIdKey pointKey;
            Date startDate;
            Date endDate;
            try {
                pointKey = (LongIdKey) objs[0];
                startDate = Optional.ofNullable((Date) objs[1]).orElseThrow(NullPointerException::new);
                endDate = Optional.ofNullable((Date) objs[2]).orElseThrow(NullPointerException::new);
            } catch (Exception e) {
                throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
            }

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT ");
            {
                sqlBuilder.append("tbl.id,");
                sqlBuilder.append("tbl.filter_id,");
                sqlBuilder.append("tbl.happened_date, ");
                sqlBuilder.append("tbl.value, ");
                sqlBuilder.append("tbl.message ");
            }
            sqlBuilder.append("FROM ");
            {
                sqlBuilder.append("tbl_filtered_value AS tbl ");
            }
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(pointKey)) {
                    sqlBuilder.append("tbl.point_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.point_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }
            sqlBuilder.append("ORDER BY ");
            {
                sqlBuilder.append("tbl.id ASC ");
            }
            sqlBuilder.append("LIMIT ?, ?");

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            if (Objects.isNull(pointKey)) {
                preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
                preparedStatement.setInt(3, pagingInfo.getRows() * pagingInfo.getPage());
                preparedStatement.setInt(4, pagingInfo.getRows());
            } else {
                preparedStatement.setLong(1, pointKey.getLongId());
                preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
                preparedStatement.setInt(4, pagingInfo.getRows() * pagingInfo.getPage());
                preparedStatement.setInt(5, pagingInfo.getRows());
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            List<FilteredValue> filteredValues = new ArrayList<>();
            while (resultSet.next()) {
                filteredValues.add(new FilteredValue(
                        new LongIdKey(resultSet.getLong(1)),
                        pointKey,
                        new LongIdKey(resultSet.getLong(2)),
                        new Date(resultSet.getTimestamp(3).getTime()),
                        resultSet.getString(4),
                        resultSet.getString(5)
                ));
            }
            return filteredValues;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Integer lookupFilteredCountForPoint(@NonNull Connection connection, Object[] objs) throws DaoException {
        try {
            LongIdKey pointKey;
            Date startDate;
            Date endDate;
            try {
                pointKey = (LongIdKey) objs[0];
                startDate = Optional.ofNullable((Date) objs[1]).orElseThrow(NullPointerException::new);
                endDate = Optional.ofNullable((Date) objs[2]).orElseThrow(NullPointerException::new);
            } catch (Exception e) {
                throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
            }

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT ");
            {
                sqlBuilder.append("COUNT(tbl.id) ");
            }
            sqlBuilder.append("FROM ");
            {
                sqlBuilder.append("tbl_filtered_value AS tbl ");
            }
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(pointKey)) {
                    sqlBuilder.append("tbl.point_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.point_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            if (Objects.isNull(pointKey)) {
                preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
            } else {
                preparedStatement.setLong(1, pointKey.getLongId());
                preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Long.valueOf(resultSet.getLong(1)).intValue();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<FilteredValue> lookupFilteredForFilter(@NonNull Connection connection, Object[] objs) throws DaoException {
        try {
            LongIdKey filterKey;
            Date startDate;
            Date endDate;
            try {
                filterKey = (LongIdKey) objs[0];
                startDate = Optional.ofNullable((Date) objs[1]).orElseThrow(NullPointerException::new);
                endDate = Optional.ofNullable((Date) objs[2]).orElseThrow(NullPointerException::new);
            } catch (Exception e) {
                throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
            }

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT ");
            {
                sqlBuilder.append("tbl.id,");
                sqlBuilder.append("tbl.point_id,");
                sqlBuilder.append("tbl.happened_date, ");
                sqlBuilder.append("tbl.value, ");
                sqlBuilder.append("tbl.message ");
            }
            sqlBuilder.append("FROM ");
            {
                sqlBuilder.append("tbl_filtered_value AS tbl ");
            }
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(filterKey)) {
                    sqlBuilder.append("tbl.filter_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.filter_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }
            sqlBuilder.append("ORDER BY ");
            {
                sqlBuilder.append("tbl.id ASC ");
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            if (Objects.isNull(filterKey)) {
                preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
            } else {
                preparedStatement.setLong(1, filterKey.getLongId());
                preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            List<FilteredValue> filteredValues = new ArrayList<>();
            while (resultSet.next()) {
                filteredValues.add(new FilteredValue(
                        new LongIdKey(resultSet.getLong(1)),
                        new LongIdKey(resultSet.getLong(2)),
                        filterKey,
                        new Date(resultSet.getTimestamp(3).getTime()),
                        resultSet.getString(4),
                        resultSet.getString(5)
                ));
            }
            return filteredValues;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<FilteredValue> lookupFilteredForFilter(@NonNull Connection connection, Object[] objs, PagingInfo pagingInfo) throws DaoException {
        try {
            LongIdKey filterKey;
            Date startDate;
            Date endDate;
            try {
                filterKey = (LongIdKey) objs[0];
                startDate = Optional.ofNullable((Date) objs[1]).orElseThrow(NullPointerException::new);
                endDate = Optional.ofNullable((Date) objs[2]).orElseThrow(NullPointerException::new);
            } catch (Exception e) {
                throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
            }

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT ");
            {
                sqlBuilder.append("tbl.id,");
                sqlBuilder.append("tbl.point_id,");
                sqlBuilder.append("tbl.happened_date, ");
                sqlBuilder.append("tbl.value, ");
                sqlBuilder.append("tbl.message ");
            }
            sqlBuilder.append("FROM ");
            {
                sqlBuilder.append("tbl_filtered_value AS tbl ");
            }
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(filterKey)) {
                    sqlBuilder.append("tbl.filter_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.filter_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }
            sqlBuilder.append("ORDER BY ");
            {
                sqlBuilder.append("tbl.id ASC ");
            }
            sqlBuilder.append("LIMIT ?, ?");

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            if (Objects.isNull(filterKey)) {
                preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
                preparedStatement.setInt(3, pagingInfo.getRows() * pagingInfo.getPage());
                preparedStatement.setInt(4, pagingInfo.getRows());
            } else {
                preparedStatement.setLong(1, filterKey.getLongId());
                preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
                preparedStatement.setInt(4, pagingInfo.getRows() * pagingInfo.getPage());
                preparedStatement.setInt(5, pagingInfo.getRows());
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            List<FilteredValue> filteredValues = new ArrayList<>();
            while (resultSet.next()) {
                filteredValues.add(new FilteredValue(
                        new LongIdKey(resultSet.getLong(1)),
                        new LongIdKey(resultSet.getLong(2)),
                        filterKey,
                        new Date(resultSet.getTimestamp(3).getTime()),
                        resultSet.getString(4),
                        resultSet.getString(5)
                ));
            }
            return filteredValues;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Integer lookupFilteredCountForFilter(@NonNull Connection connection, Object[] objs) throws DaoException {
        try {
            LongIdKey filterKey;
            Date startDate;
            Date endDate;
            try {
                filterKey = (LongIdKey) objs[0];
                startDate = Optional.ofNullable((Date) objs[1]).orElseThrow(NullPointerException::new);
                endDate = Optional.ofNullable((Date) objs[2]).orElseThrow(NullPointerException::new);
            } catch (Exception e) {
                throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
            }

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT ");
            {
                sqlBuilder.append("COUNT(tbl.id) ");
            }
            sqlBuilder.append("FROM ");
            {
                sqlBuilder.append("tbl_filtered_value AS tbl ");
            }
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(filterKey)) {
                    sqlBuilder.append("tbl.filter_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.filter_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            if (Objects.isNull(filterKey)) {
                preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
            } else {
                preparedStatement.setLong(1, filterKey.getLongId());
                preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Long.valueOf(resultSet.getLong(1)).intValue();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<TriggeredValue> lookupTriggeredForPoint(@NonNull Connection connection, Object[] objs) throws DaoException {
        try {
            LongIdKey pointKey;
            Date startDate;
            Date endDate;
            try {
                pointKey = (LongIdKey) objs[0];
                startDate = Optional.ofNullable((Date) objs[1]).orElseThrow(NullPointerException::new);
                endDate = Optional.ofNullable((Date) objs[2]).orElseThrow(NullPointerException::new);
            } catch (Exception e) {
                throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
            }

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT ");
            {
                sqlBuilder.append("tbl.id,");
                sqlBuilder.append("tbl.trigger_id,");
                sqlBuilder.append("tbl.happened_date, ");
                sqlBuilder.append("tbl.value, ");
                sqlBuilder.append("tbl.message ");
            }
            sqlBuilder.append("FROM ");
            {
                sqlBuilder.append("tbl_triggered_value AS tbl ");
            }
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(pointKey)) {
                    sqlBuilder.append("tbl.point_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.point_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }
            sqlBuilder.append("ORDER BY ");
            {
                sqlBuilder.append("tbl.id ASC ");
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            if (Objects.isNull(pointKey)) {
                preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
            } else {
                preparedStatement.setLong(1, pointKey.getLongId());
                preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            List<TriggeredValue> triggeredValues = new ArrayList<>();
            while (resultSet.next()) {
                triggeredValues.add(new TriggeredValue(
                        new LongIdKey(resultSet.getLong(1)),
                        pointKey,
                        new LongIdKey(resultSet.getLong(2)),
                        new Date(resultSet.getTimestamp(3).getTime()),
                        resultSet.getString(4),
                        resultSet.getString(5)
                ));
            }
            return triggeredValues;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<TriggeredValue> lookupTriggeredForPoint(@NonNull Connection connection, Object[] objs, PagingInfo pagingInfo) throws DaoException {
        try {
            LongIdKey pointKey;
            Date startDate;
            Date endDate;
            try {
                pointKey = (LongIdKey) objs[0];
                startDate = Optional.ofNullable((Date) objs[1]).orElseThrow(NullPointerException::new);
                endDate = Optional.ofNullable((Date) objs[2]).orElseThrow(NullPointerException::new);
            } catch (Exception e) {
                throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
            }

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT ");
            {
                sqlBuilder.append("tbl.id,");
                sqlBuilder.append("tbl.trigger_id,");
                sqlBuilder.append("tbl.happened_date, ");
                sqlBuilder.append("tbl.value, ");
                sqlBuilder.append("tbl.message ");
            }
            sqlBuilder.append("FROM ");
            {
                sqlBuilder.append("tbl_triggered_value AS tbl ");
            }
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(pointKey)) {
                    sqlBuilder.append("tbl.point_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.point_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }
            sqlBuilder.append("ORDER BY ");
            {
                sqlBuilder.append("tbl.id ASC ");
            }
            sqlBuilder.append("LIMIT ?, ?");

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            if (Objects.isNull(pointKey)) {
                preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
                preparedStatement.setInt(3, pagingInfo.getRows() * pagingInfo.getPage());
                preparedStatement.setInt(4, pagingInfo.getRows());
            } else {
                preparedStatement.setLong(1, pointKey.getLongId());
                preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
                preparedStatement.setInt(4, pagingInfo.getRows() * pagingInfo.getPage());
                preparedStatement.setInt(5, pagingInfo.getRows());
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            List<TriggeredValue> triggeredValues = new ArrayList<>();
            while (resultSet.next()) {
                triggeredValues.add(new TriggeredValue(
                        new LongIdKey(resultSet.getLong(1)),
                        pointKey,
                        new LongIdKey(resultSet.getLong(2)),
                        new Date(resultSet.getTimestamp(3).getTime()),
                        resultSet.getString(4),
                        resultSet.getString(5)
                ));
            }
            return triggeredValues;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Integer lookupTriggeredCountForPoint(@NonNull Connection connection, Object[] objs) throws DaoException {
        try {
            LongIdKey pointKey;
            Date startDate;
            Date endDate;
            try {
                pointKey = (LongIdKey) objs[0];
                startDate = Optional.ofNullable((Date) objs[1]).orElseThrow(NullPointerException::new);
                endDate = Optional.ofNullable((Date) objs[2]).orElseThrow(NullPointerException::new);
            } catch (Exception e) {
                throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
            }

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT ");
            {
                sqlBuilder.append("COUNT(tbl.id) ");
            }
            sqlBuilder.append("FROM ");
            {
                sqlBuilder.append("tbl_triggered_value AS tbl ");
            }
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(pointKey)) {
                    sqlBuilder.append("tbl.point_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.point_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            if (Objects.isNull(pointKey)) {
                preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
            } else {
                preparedStatement.setLong(1, pointKey.getLongId());
                preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Long.valueOf(resultSet.getLong(1)).intValue();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<TriggeredValue> lookupTriggeredForTrigger(@NonNull Connection connection, Object[] objs) throws DaoException {
        try {
            LongIdKey triggerKey;
            Date startDate;
            Date endDate;
            try {
                triggerKey = (LongIdKey) objs[0];
                startDate = Optional.ofNullable((Date) objs[1]).orElseThrow(NullPointerException::new);
                endDate = Optional.ofNullable((Date) objs[2]).orElseThrow(NullPointerException::new);
            } catch (Exception e) {
                throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
            }

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT ");
            {
                sqlBuilder.append("tbl.id,");
                sqlBuilder.append("tbl.point_id,");
                sqlBuilder.append("tbl.happened_date, ");
                sqlBuilder.append("tbl.value, ");
                sqlBuilder.append("tbl.message ");
            }
            sqlBuilder.append("FROM ");
            {
                sqlBuilder.append("tbl_triggered_value AS tbl ");
            }
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(triggerKey)) {
                    sqlBuilder.append("tbl.trigger_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.trigger_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }
            sqlBuilder.append("ORDER BY ");
            {
                sqlBuilder.append("tbl.id ASC ");
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            if (Objects.isNull(triggerKey)) {
                preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
            } else {
                preparedStatement.setLong(1, triggerKey.getLongId());
                preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            List<TriggeredValue> triggeredValues = new ArrayList<>();
            while (resultSet.next()) {
                triggeredValues.add(new TriggeredValue(
                        new LongIdKey(resultSet.getLong(1)),
                        new LongIdKey(resultSet.getLong(2)),
                        triggerKey,
                        new Date(resultSet.getTimestamp(3).getTime()),
                        resultSet.getString(4),
                        resultSet.getString(5)
                ));
            }
            return triggeredValues;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<TriggeredValue> lookupTriggeredForTrigger(@NonNull Connection connection, Object[] objs, PagingInfo pagingInfo) throws DaoException {
        try {
            LongIdKey triggerKey;
            Date startDate;
            Date endDate;
            try {
                triggerKey = (LongIdKey) objs[0];
                startDate = Optional.ofNullable((Date) objs[1]).orElseThrow(NullPointerException::new);
                endDate = Optional.ofNullable((Date) objs[2]).orElseThrow(NullPointerException::new);
            } catch (Exception e) {
                throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
            }

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT ");
            {
                sqlBuilder.append("tbl.id,");
                sqlBuilder.append("tbl.point_id,");
                sqlBuilder.append("tbl.happened_date, ");
                sqlBuilder.append("tbl.value, ");
                sqlBuilder.append("tbl.message ");
            }
            sqlBuilder.append("FROM ");
            {
                sqlBuilder.append("tbl_triggered_value AS tbl ");
            }
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(triggerKey)) {
                    sqlBuilder.append("tbl.trigger_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.trigger_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }
            sqlBuilder.append("ORDER BY ");
            {
                sqlBuilder.append("tbl.id ASC ");
            }
            sqlBuilder.append("LIMIT ?, ?");

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            if (Objects.isNull(triggerKey)) {
                preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
                preparedStatement.setInt(3, pagingInfo.getRows() * pagingInfo.getPage());
                preparedStatement.setInt(4, pagingInfo.getRows());
            } else {
                preparedStatement.setLong(1, triggerKey.getLongId());
                preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
                preparedStatement.setInt(4, pagingInfo.getRows() * pagingInfo.getPage());
                preparedStatement.setInt(5, pagingInfo.getRows());
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            List<TriggeredValue> triggeredValues = new ArrayList<>();
            while (resultSet.next()) {
                triggeredValues.add(new TriggeredValue(
                        new LongIdKey(resultSet.getLong(1)),
                        new LongIdKey(resultSet.getLong(2)),
                        triggerKey,
                        new Date(resultSet.getTimestamp(3).getTime()),
                        resultSet.getString(4),
                        resultSet.getString(5)
                ));
            }
            return triggeredValues;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Integer lookupTriggeredCountForTrigger(@NonNull Connection connection, Object[] objs) throws DaoException {
        try {
            LongIdKey triggerKey;
            Date startDate;
            Date endDate;
            try {
                triggerKey = (LongIdKey) objs[0];
                startDate = Optional.ofNullable((Date) objs[1]).orElseThrow(NullPointerException::new);
                endDate = Optional.ofNullable((Date) objs[2]).orElseThrow(NullPointerException::new);
            } catch (Exception e) {
                throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
            }

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT ");
            {
                sqlBuilder.append("COUNT(tbl.id) ");
            }
            sqlBuilder.append("FROM ");
            {
                sqlBuilder.append("tbl_triggered_value AS tbl ");
            }
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(triggerKey)) {
                    sqlBuilder.append("tbl.trigger_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.trigger_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            if (Objects.isNull(triggerKey)) {
                preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
            } else {
                preparedStatement.setLong(1, triggerKey.getLongId());
                preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
                preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Long.valueOf(resultSet.getLong(1)).intValue();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
