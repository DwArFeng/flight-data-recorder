package com.dwarfeng.fdr.impl.dao.fuh.dao;

import com.dwarfeng.fdr.impl.dao.fuh.bean.entity.HibernatePersistenceValue;
import com.dwarfeng.fdr.impl.dao.fuh.bean.key.HibernateGuidKey;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
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
public class PersistenceValueDaoDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceValueDaoDelegate.class);

    @Autowired
    private HibernateTemplate template;
    @Autowired
    private Mapper mapper;

    @Value("${hibernate.batch_delete.persistence_value.size}")
    private int batchDeleteSize;

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public boolean exists(@NotNull GuidKey key) throws DaoException {
        try {
            return internalExists(key);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    private boolean internalExists(GuidKey key) {
        HibernateGuidKey hibernateGuidKey = mapper.map(key, HibernateGuidKey.class);
        return Objects.nonNull(template.get(HibernatePersistenceValue.class, hibernateGuidKey));
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PersistenceValue get(GuidKey key) throws DaoException {
        try {
            if (!internalExists(key)) {
                LOGGER.warn("指定的PersistenceValue " + key.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的GuidKey " + key.toString() + " 不存在");
            }

            HibernateGuidKey hibernateGuidKey = mapper.map(key, HibernateGuidKey.class);
            HibernatePersistenceValue hibernatePersistenceValue = template.get(HibernatePersistenceValue.class, hibernateGuidKey);
            return mapper.map(hibernatePersistenceValue, PersistenceValue.class);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey insert(@NotNull PersistenceValue persistenceValue) throws DaoException {
        try {
            if (internalExists(persistenceValue.getKey())) {
                LOGGER.warn("指定的PersistenceValue " + persistenceValue.toString() + " 已经存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的PersistenceValue " + persistenceValue.toString() + " 已经存在");
            }

            HibernatePersistenceValue hibernatePersistenceValue = mapper.map(persistenceValue, HibernatePersistenceValue.class);
            template.save(hibernatePersistenceValue);
            template.flush();
            return persistenceValue.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey update(@NotNull PersistenceValue persistenceValue) throws DaoException {
        try {
            if (!internalExists(persistenceValue.getKey())) {
                LOGGER.warn("指定的PersistenceValue " + persistenceValue.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的PersistenceValue " + persistenceValue.toString() + " 不存在");
            }

            HibernateGuidKey hibernateGuidKey = mapper.map(persistenceValue.getKey(), HibernateGuidKey.class);
            HibernatePersistenceValue hibernatePersistenceValue = template.get(HibernatePersistenceValue.class, hibernateGuidKey);
            assert hibernatePersistenceValue != null;
            mapper.map(persistenceValue, hibernatePersistenceValue);
            template.update(hibernatePersistenceValue);
            template.flush();
            return persistenceValue.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(@NotNull GuidKey key) throws DaoException {
        try {
            if (!internalExists(key)) {
                LOGGER.warn("指定的PersistenceValue " + key.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的GuidKey " + key.toString() + " 不存在");
            }

            HibernateGuidKey hibernateGuidKey = mapper.map(key, HibernateGuidKey.class);
            HibernatePersistenceValue hibernatePersistenceValue = template.get(HibernatePersistenceValue.class, hibernateGuidKey);
            assert hibernatePersistenceValue != null;
            template.delete(hibernatePersistenceValue);
            template.flush();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void deleteAll(GuidKey pointKey) throws DaoException {
        try {
            HibernateGuidKey hibernatePointKey = mapper.map(pointKey, HibernateGuidKey.class);

            DetachedCriteria criteria = DetachedCriteria.forClass(HibernatePersistenceValue.class);
            criteria.add(Restrictions.eqOrIsNull("pointGuid", hibernatePointKey.getGuid()));
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
