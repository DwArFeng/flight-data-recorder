package com.dwarfeng.fdr.impl.dao.fuh.dao;

import com.dwarfeng.fdr.impl.dao.fuh.bean.entity.HibernatePersistenceValue;
import com.dwarfeng.fdr.impl.dao.fuh.bean.key.HibernateUuidKey;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.DaoException;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Component
@Validated
public class PersistenceValueDaoDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceValueDaoDelegate.class);

    @Autowired
    private HibernateTemplate template;
    @Autowired
    private Mapper mapper;

    @TimeAnalyse
    @Transactional(readOnly = true)
    public boolean exists(@NotNull UuidKey key) throws DaoException {
        try {
            return internalExists(key);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    private boolean internalExists(UuidKey key) {
        HibernateUuidKey hibernateUuidKey = mapper.map(key, HibernateUuidKey.class);
        return Objects.nonNull(template.get(HibernatePersistenceValue.class, hibernateUuidKey));
    }

    @TimeAnalyse
    @Transactional(readOnly = true)
    public PersistenceValue get(UuidKey key) throws DaoException {
        try {
            HibernateUuidKey hibernateUuidKey = mapper.map(key, HibernateUuidKey.class);
            HibernatePersistenceValue hibernatePersistenceValue = template.get(HibernatePersistenceValue.class, hibernateUuidKey);
            return mapper.map(hibernatePersistenceValue, PersistenceValue.class);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional
    public UuidKey insert(@NotNull PersistenceValue persistenceValue) throws DaoException {
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
    @Transactional
    public UuidKey update(@NotNull PersistenceValue persistenceValue) throws DaoException {
        try {
            if (!internalExists(persistenceValue.getKey())) {
                LOGGER.warn("指定的PersistenceValue " + persistenceValue.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的PersistenceValue " + persistenceValue.toString() + " 不存在");
            }

            HibernatePersistenceValue hibernatePersistenceValue = mapper.map(persistenceValue, HibernatePersistenceValue.class);
            template.update(hibernatePersistenceValue);
            template.flush();
            return persistenceValue.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional
    public void delete(@NotNull UuidKey key) throws DaoException {
        try {
            if (!internalExists(key)) {
                LOGGER.warn("指定的PersistenceValue " + key.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的UuidKey " + key.toString() + " 不存在");
            }

            HibernateUuidKey hibernateUuidKey = mapper.map(key, HibernateUuidKey.class);
            HibernatePersistenceValue hibernatePersistenceValue = template.get(HibernatePersistenceValue.class, hibernateUuidKey);
            assert hibernatePersistenceValue != null;
            template.delete(hibernatePersistenceValue);
            template.flush();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }
}