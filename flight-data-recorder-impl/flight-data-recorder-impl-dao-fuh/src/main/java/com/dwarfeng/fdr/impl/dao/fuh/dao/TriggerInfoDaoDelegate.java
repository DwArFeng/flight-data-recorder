package com.dwarfeng.fdr.impl.dao.fuh.dao;

import com.dwarfeng.fdr.impl.dao.fuh.bean.entity.HibernateTriggerInfo;
import com.dwarfeng.fdr.impl.dao.fuh.bean.key.HibernateUuidKey;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
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
        return Objects.nonNull(template.get(HibernateTriggerInfo.class, hibernateUuidKey));
    }

    @TimeAnalyse
    @Transactional(readOnly = true)
    public TriggerInfo get(UuidKey key) throws DaoException {
        try {
            HibernateUuidKey hibernateUuidKey = mapper.map(key, HibernateUuidKey.class);
            HibernateTriggerInfo hibernateTriggerInfo = template.get(HibernateTriggerInfo.class, hibernateUuidKey);
            return mapper.map(hibernateTriggerInfo, TriggerInfo.class);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional
    public UuidKey insert(@NotNull TriggerInfo triggerInfo) throws DaoException {
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
    @Transactional
    public UuidKey update(@NotNull TriggerInfo triggerInfo) throws DaoException {
        try {
            if (!internalExists(triggerInfo.getKey())) {
                LOGGER.warn("指定的TriggerInfo " + triggerInfo.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的TriggerInfo " + triggerInfo.toString() + " 不存在");
            }

            HibernateTriggerInfo hibernateTriggerInfo = mapper.map(triggerInfo, HibernateTriggerInfo.class);
            template.update(hibernateTriggerInfo);
            template.flush();
            return triggerInfo.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional
    public void delete(@NotNull UuidKey key) throws DaoException {
        try {
            if (!internalExists(key)) {
                LOGGER.warn("指定的TriggerInfo " + key.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的UuidKey " + key.toString() + " 不存在");
            }

            HibernateUuidKey hibernateUuidKey = mapper.map(key, HibernateUuidKey.class);
            HibernateTriggerInfo hibernateTriggerInfo = template.get(HibernateTriggerInfo.class, hibernateUuidKey);
//            //取消所有与该分类有关的自分类的父项关联。
//            DetachedCriteria criteria = DetachedCriteria.forClass(HibernateTriggerInfo.class)
//                    .add(Restrictions.eq("parentUuid", hibernateUuidKey.getUuid()));
//            for (Object child : hibernateTemplate.findByCriteria(criteria)) {
//                ((HibernateTriggerInfo) child).setParentUuid(null);
//            }
            assert hibernateTriggerInfo != null;
            template.delete(hibernateTriggerInfo);
            template.flush();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(readOnly = true)
    public List<TriggerInfo> getTriggerInfos(@NotNull UuidKey pointUuidKey, @NotNull LookupPagingInfo lookupPagingInfo) throws DaoException {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(HibernateTriggerInfo.class);
            if (Objects.isNull(pointUuidKey)) {
                criteria.add(Restrictions.isNull("pointUuid"));
            } else {
                criteria.add(Restrictions.eq("pointUuid", pointUuidKey.getUuid()));
            }
            @SuppressWarnings("unchecked")
            List<HibernateTriggerInfo> hibernateResult = (List<HibernateTriggerInfo>) template.findByCriteria(criteria, lookupPagingInfo.getPage() * lookupPagingInfo.getRows(), lookupPagingInfo.getRows());
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
    @Transactional(readOnly = true)
    public long getTriggerInfoCount(@NotNull UuidKey pointUuidKey) throws DaoException {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(HibernateTriggerInfo.class);
            if (Objects.isNull(pointUuidKey)) {
                criteria.add(Restrictions.isNull("pointUuid"));
            } else {
                criteria.add(Restrictions.eq("pointUuid", pointUuidKey.getUuid()));
            }
            criteria.setProjection(Projections.rowCount());
            return template.findByCriteria(criteria).stream().findFirst().map(Long.class::cast).orElse(0L);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

}
