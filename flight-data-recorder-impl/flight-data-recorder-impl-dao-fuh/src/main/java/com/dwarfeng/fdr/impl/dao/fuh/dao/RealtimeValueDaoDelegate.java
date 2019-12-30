package com.dwarfeng.fdr.impl.dao.fuh.dao;

import com.dwarfeng.fdr.impl.dao.fuh.bean.entity.HibernateRealtimeValue;
import com.dwarfeng.fdr.impl.dao.fuh.bean.key.HibernateGuidKey;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
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
public class RealtimeValueDaoDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(RealtimeValueDaoDelegate.class);

    @Autowired
    private HibernateTemplate template;
    @Autowired
    private Mapper mapper;

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
        return Objects.nonNull(template.get(HibernateRealtimeValue.class, hibernateGuidKey));
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public RealtimeValue get(GuidKey key) throws DaoException {
        try {
            if (!internalExists(key)) {
                LOGGER.warn("指定的RealtimeValue " + key.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的GuidKey " + key.toString() + " 不存在");
            }

            HibernateGuidKey hibernateGuidKey = mapper.map(key, HibernateGuidKey.class);
            HibernateRealtimeValue hibernateRealtimeValue = template.get(HibernateRealtimeValue.class, hibernateGuidKey);
            return mapper.map(hibernateRealtimeValue, RealtimeValue.class);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey insert(@NotNull RealtimeValue realtimeValue) throws DaoException {
        try {
            if (internalExists(realtimeValue.getKey())) {
                LOGGER.warn("指定的RealtimeValue " + realtimeValue.toString() + " 已经存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的RealtimeValue " + realtimeValue.toString() + " 已经存在");
            }

            HibernateRealtimeValue hibernateRealtimeValue = mapper.map(realtimeValue, HibernateRealtimeValue.class);
            template.save(hibernateRealtimeValue);
            template.flush();
            return realtimeValue.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey update(@NotNull RealtimeValue realtimeValue) throws DaoException {
        try {
            if (!internalExists(realtimeValue.getKey())) {
                LOGGER.warn("指定的RealtimeValue " + realtimeValue.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的RealtimeValue " + realtimeValue.toString() + " 不存在");
            }

            HibernateGuidKey hibernateGuidKey = mapper.map(realtimeValue.getKey(), HibernateGuidKey.class);
            HibernateRealtimeValue hibernateRealtimeValue = template.get(HibernateRealtimeValue.class, hibernateGuidKey);
            assert hibernateRealtimeValue != null;
            mapper.map(realtimeValue, hibernateRealtimeValue);
            template.update(hibernateRealtimeValue);
            template.flush();
            return realtimeValue.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(@NotNull GuidKey key) throws DaoException {
        try {
            if (!internalExists(key)) {
                LOGGER.warn("指定的RealtimeValue " + key.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的GuidKey " + key.toString() + " 不存在");
            }

            HibernateGuidKey hibernateGuidKey = mapper.map(key, HibernateGuidKey.class);
            HibernateRealtimeValue hibernateRealtimeValue = template.get(HibernateRealtimeValue.class, hibernateGuidKey);
            assert hibernateRealtimeValue != null;
            template.delete(hibernateRealtimeValue);
            template.flush();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public RealtimeValue getRealtimeValue(GuidKey pointGuidKey) throws DaoException {
        throw new IllegalStateException("not implemented yet."); //TODO
    }
}
