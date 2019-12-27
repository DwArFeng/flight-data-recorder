package com.dwarfeng.fdr.impl.dao.fuh.dao;

import com.dwarfeng.fdr.impl.dao.fuh.bean.entity.HibernateFilteredValue;
import com.dwarfeng.fdr.impl.dao.fuh.bean.key.HibernateUuidKey;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.DaoException;
import org.dozer.Mapper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Component
@Validated
public class FilteredValueDaoDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilteredValueDaoDelegate.class);

    @Autowired
    private HibernateTemplate template;
    @Autowired
    private Mapper mapper;

    @Value("${hibernate.batch_delete.filtered_value.size}")
    private int batchDeleteSize;

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public boolean exists(@NotNull UuidKey key) throws DaoException {
        try {
            return internalExists(key);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    private boolean internalExists(UuidKey key) {
        HibernateUuidKey hibernateUuidKey = mapper.map(key, HibernateUuidKey.class);
        return Objects.nonNull(template.get(HibernateFilteredValue.class, hibernateUuidKey));
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public FilteredValue get(UuidKey key) throws DaoException {
        try {
            if (!internalExists(key)) {
                LOGGER.warn("指定的FilteredValue " + key.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的UuidKey " + key.toString() + " 不存在");
            }

            HibernateUuidKey hibernateUuidKey = mapper.map(key, HibernateUuidKey.class);
            HibernateFilteredValue hibernateFilteredValue = template.get(HibernateFilteredValue.class, hibernateUuidKey);
            return mapper.map(hibernateFilteredValue, FilteredValue.class);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public UuidKey insert(@NotNull FilteredValue filteredValue) throws DaoException {
        try {
            if (internalExists(filteredValue.getKey())) {
                LOGGER.warn("指定的FilteredValue " + filteredValue.toString() + " 已经存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的FilteredValue " + filteredValue.toString() + " 已经存在");
            }

            HibernateFilteredValue hibernateFilteredValue = mapper.map(filteredValue, HibernateFilteredValue.class);
            template.save(hibernateFilteredValue);
            template.flush();
            return filteredValue.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public UuidKey update(@NotNull FilteredValue filteredValue) throws DaoException {
        try {
            if (!internalExists(filteredValue.getKey())) {
                LOGGER.warn("指定的FilteredValue " + filteredValue.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的FilteredValue " + filteredValue.toString() + " 不存在");
            }

            HibernateUuidKey hibernateUuidKey = mapper.map(filteredValue.getKey(), HibernateUuidKey.class);
            HibernateFilteredValue hibernateFilteredValue = template.get(HibernateFilteredValue.class, hibernateUuidKey);
            assert hibernateFilteredValue != null;
            mapper.map(filteredValue, hibernateFilteredValue);
            template.update(hibernateFilteredValue);
            template.flush();
            return filteredValue.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(@NotNull UuidKey key) throws DaoException {
        try {
            if (!internalExists(key)) {
                LOGGER.warn("指定的FilteredValue " + key.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的UuidKey " + key.toString() + " 不存在");
            }

            HibernateUuidKey hibernateUuidKey = mapper.map(key, HibernateUuidKey.class);
            HibernateFilteredValue hibernateFilteredValue = template.get(HibernateFilteredValue.class, hibernateUuidKey);
            assert hibernateFilteredValue != null;
            template.delete(hibernateFilteredValue);
            template.flush();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void deleteAllByPoint(UuidKey pointKey) throws DaoException {
        try {
            HibernateUuidKey hibernatePointKey = mapper.map(pointKey, HibernateUuidKey.class);

            DetachedCriteria criteria = DetachedCriteria.forClass(HibernateFilteredValue.class);
            criteria.add(Restrictions.eqOrIsNull("pointUuid", hibernatePointKey.getUuid()));
            List<?> list2Delete = template.findByCriteria(criteria, 0, batchDeleteSize);
            while (!list2Delete.isEmpty()) {
                template.deleteAll(list2Delete);
                template.flush();
                list2Delete = template.findByCriteria(criteria, 0, batchDeleteSize);
            }
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void deleteAllByFilterInfo(UuidKey filterInfoKey) throws DaoException {
        try {
            HibernateUuidKey hibernatePointKey = mapper.map(filterInfoKey, HibernateUuidKey.class);

            DetachedCriteria criteria = DetachedCriteria.forClass(HibernateFilteredValue.class);
            criteria.add(Restrictions.eqOrIsNull("filterUuid", hibernatePointKey.getUuid()));
            List<?> list2Delete = template.findByCriteria(criteria, 0, batchDeleteSize);
            while (!list2Delete.isEmpty()) {
                template.deleteAll(list2Delete);
                template.flush();
                list2Delete = template.findByCriteria(criteria, 0, batchDeleteSize);
            }
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }
}
