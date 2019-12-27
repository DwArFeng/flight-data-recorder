package com.dwarfeng.fdr.impl.dao.fuh.dao;

import com.dwarfeng.fdr.impl.dao.fuh.bean.entity.HibernateTriggeredValue;
import com.dwarfeng.fdr.impl.dao.fuh.bean.key.HibernateUuidKey;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
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
public class TriggeredValueDaoDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggeredValueDaoDelegate.class);

    @Autowired
    private HibernateTemplate template;
    @Autowired
    private Mapper mapper;

    @Value("${hibernate.batch_delete.triggered_value.size}")
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
        return Objects.nonNull(template.get(HibernateTriggeredValue.class, hibernateUuidKey));
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public TriggeredValue get(UuidKey key) throws DaoException {
        try {
            if (!internalExists(key)) {
                LOGGER.warn("指定的TriggeredValue " + key.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的UuidKey " + key.toString() + " 不存在");
            }

            HibernateUuidKey hibernateUuidKey = mapper.map(key, HibernateUuidKey.class);
            HibernateTriggeredValue hibernateTriggeredValue = template.get(HibernateTriggeredValue.class, hibernateUuidKey);
            return mapper.map(hibernateTriggeredValue, TriggeredValue.class);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public UuidKey insert(@NotNull TriggeredValue triggeredValue) throws DaoException {
        try {
            if (internalExists(triggeredValue.getKey())) {
                LOGGER.warn("指定的TriggeredValue " + triggeredValue.toString() + " 已经存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的TriggeredValue " + triggeredValue.toString() + " 已经存在");
            }

            HibernateTriggeredValue hibernateTriggeredValue = mapper.map(triggeredValue, HibernateTriggeredValue.class);
            template.save(hibernateTriggeredValue);
            template.flush();
            return triggeredValue.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public UuidKey update(@NotNull TriggeredValue triggeredValue) throws DaoException {
        try {
            if (!internalExists(triggeredValue.getKey())) {
                LOGGER.warn("指定的TriggeredValue " + triggeredValue.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的TriggeredValue " + triggeredValue.toString() + " 不存在");
            }

            HibernateUuidKey hibernateUuidKey = mapper.map(triggeredValue.getKey(), HibernateUuidKey.class);
            HibernateTriggeredValue hibernateTriggeredValue = template.get(HibernateTriggeredValue.class, hibernateUuidKey);
            assert hibernateTriggeredValue != null;
            mapper.map(triggeredValue, hibernateTriggeredValue);
            template.update(hibernateTriggeredValue);
            template.flush();
            return triggeredValue.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(@NotNull UuidKey key) throws DaoException {
        try {
            if (!internalExists(key)) {
                LOGGER.warn("指定的TriggeredValue " + key.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的UuidKey " + key.toString() + " 不存在");
            }

            HibernateUuidKey hibernateUuidKey = mapper.map(key, HibernateUuidKey.class);
            HibernateTriggeredValue hibernateTriggeredValue = template.get(HibernateTriggeredValue.class, hibernateUuidKey);
            assert hibernateTriggeredValue != null;
            template.delete(hibernateTriggeredValue);
            template.flush();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public void deleteAllByPoint(UuidKey pointKey) throws DaoException {
        try {
            HibernateUuidKey hibernatePointKey = mapper.map(pointKey, HibernateUuidKey.class);

            DetachedCriteria criteria = DetachedCriteria.forClass(HibernateTriggeredValue.class);
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
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public void deleteAllByTriggerInfo(UuidKey triggerInfoKey) throws DaoException {
        try {
            HibernateUuidKey hibernatePointKey = mapper.map(triggerInfoKey, HibernateUuidKey.class);

            DetachedCriteria criteria = DetachedCriteria.forClass(HibernateTriggeredValue.class);
            criteria.add(Restrictions.eqOrIsNull("triggerUuid", hibernatePointKey.getUuid()));
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
