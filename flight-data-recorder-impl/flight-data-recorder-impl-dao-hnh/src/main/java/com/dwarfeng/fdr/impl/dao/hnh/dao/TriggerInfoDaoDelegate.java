package com.dwarfeng.fdr.impl.dao.hnh.dao;

import com.dwarfeng.fdr.impl.dao.hnh.bean.entity.HibernateTriggerInfo;
import com.dwarfeng.fdr.impl.dao.hnh.bean.key.HibernateGuidKey;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.DaoException;
import org.dozer.Mapper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Validated
public class TriggerInfoDaoDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggerInfoDaoDelegate.class);

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
        return Objects.nonNull(template.get(HibernateTriggerInfo.class, hibernateGuidKey));
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public TriggerInfo get(GuidKey key) throws DaoException {
        try {
            if (!internalExists(key)) {
                LOGGER.warn("指定的TriggerInfo " + key.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的GuidKey " + key.toString() + " 不存在");
            }

            HibernateGuidKey hibernateGuidKey = mapper.map(key, HibernateGuidKey.class);
            HibernateTriggerInfo hibernateTriggerInfo = template.get(HibernateTriggerInfo.class, hibernateGuidKey);
            return mapper.map(hibernateTriggerInfo, TriggerInfo.class);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey insert(@NotNull TriggerInfo triggerInfo) throws DaoException {
        try {
            if (internalExists(triggerInfo.getKey())) {
                LOGGER.warn("指定的TriggerInfo " + triggerInfo.toString() + " 已经存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的TriggerInfo " + triggerInfo.toString() + " 已经存在");
            }

            HibernateTriggerInfo hibernateTriggerInfo = mapper.map(triggerInfo, HibernateTriggerInfo.class);
            template.save(hibernateTriggerInfo);
            template.flush();
            return triggerInfo.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey update(@NotNull TriggerInfo triggerInfo) throws DaoException {
        try {
            if (!internalExists(triggerInfo.getKey())) {
                LOGGER.warn("指定的TriggerInfo " + triggerInfo.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的TriggerInfo " + triggerInfo.toString() + " 不存在");
            }

            HibernateGuidKey hibernateGuidKey = mapper.map(triggerInfo.getKey(), HibernateGuidKey.class);
            HibernateTriggerInfo hibernateTriggerInfo = template.get(HibernateTriggerInfo.class, hibernateGuidKey);
            assert hibernateTriggerInfo != null;
            mapper.map(triggerInfo, hibernateTriggerInfo);
            template.update(hibernateTriggerInfo);
            template.flush();
            return triggerInfo.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(@NotNull GuidKey key) throws DaoException {
        try {
            if (!internalExists(key)) {
                LOGGER.warn("指定的TriggerInfo " + key.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的GuidKey " + key.toString() + " 不存在");
            }

            HibernateGuidKey hibernateGuidKey = mapper.map(key, HibernateGuidKey.class);
            HibernateTriggerInfo hibernateTriggerInfo = template.get(HibernateTriggerInfo.class, hibernateGuidKey);
            assert hibernateTriggerInfo != null;
            template.delete(hibernateTriggerInfo);
            template.flush();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public List<TriggerInfo> getTriggerInfos(GuidKey pointGuidKey, @NotNull LookupPagingInfo lookupPagingInfo) throws DaoException {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(HibernateTriggerInfo.class);
            if (Objects.isNull(pointGuidKey)) {
                criteria.add(Restrictions.isNull("pointGuid"));
            } else {
                criteria.add(Restrictions.eq("pointGuid", pointGuidKey.getGuid()));
            }
            @SuppressWarnings("unchecked")
            List<HibernateTriggerInfo> hibernateResult = (List<HibernateTriggerInfo>) template.findByCriteria(
                    criteria,
                    lookupPagingInfo.isPaging() ? lookupPagingInfo.getPage() * lookupPagingInfo.getRows() : -1,
                    lookupPagingInfo.isPaging() ? lookupPagingInfo.getRows() : -1);
            List<TriggerInfo> result = new ArrayList<>();
            for (HibernateTriggerInfo hibernateTriggerInfo : hibernateResult) {
                result.add(mapper.map(hibernateTriggerInfo, TriggerInfo.class));
            }
            return result;
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public long getTriggerInfoCount(GuidKey pointGuidKey) throws DaoException {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(HibernateTriggerInfo.class);
            if (Objects.isNull(pointGuidKey)) {
                criteria.add(Restrictions.isNull("pointGuid"));
            } else {
                criteria.add(Restrictions.eq("pointGuid", pointGuidKey.getGuid()));
            }
            criteria.setProjection(Projections.rowCount());
            return template.findByCriteria(criteria).stream().findFirst().map(Long.class::cast).orElse(0L);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

}
