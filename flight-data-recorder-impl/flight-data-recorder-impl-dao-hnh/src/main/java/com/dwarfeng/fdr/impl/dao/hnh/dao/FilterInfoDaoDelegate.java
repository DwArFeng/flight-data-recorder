package com.dwarfeng.fdr.impl.dao.hnh.dao;

import com.dwarfeng.fdr.impl.dao.hnh.bean.entity.HibernateFilterInfo;
import com.dwarfeng.fdr.impl.dao.hnh.bean.key.HibernateUuidKey;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
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
public class FilterInfoDaoDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterInfoDaoDelegate.class);

    @Autowired
    private HibernateTemplate template;
    @Autowired
    private Mapper mapper;

    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager", readOnly = true)
    public boolean exists(@NotNull UuidKey key) throws DaoException {
        try {
            return internalExists(key);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    private boolean internalExists(UuidKey key) {
        HibernateUuidKey hibernateUuidKey = mapper.map(key, HibernateUuidKey.class);
        return Objects.nonNull(template.get(HibernateFilterInfo.class, hibernateUuidKey));
    }

    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager", readOnly = true)
    public FilterInfo get(UuidKey key) throws DaoException {
        try {
            if (!internalExists(key)) {
                LOGGER.warn("指定的FilterInfo " + key.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的UuidKey " + key.toString() + " 不存在");
            }

            HibernateUuidKey hibernateUuidKey = mapper.map(key, HibernateUuidKey.class);
            HibernateFilterInfo hibernateFilterInfo = template.get(HibernateFilterInfo.class, hibernateUuidKey);
            return mapper.map(hibernateFilterInfo, FilterInfo.class);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager")
    public UuidKey insert(@NotNull FilterInfo filterInfo) throws DaoException {
        try {
            if (internalExists(filterInfo.getKey())) {
                LOGGER.warn("指定的FilterInfo " + filterInfo.toString() + " 已经存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的FilterInfo " + filterInfo.toString() + " 已经存在");
            }

            HibernateFilterInfo hibernateFilterInfo = mapper.map(filterInfo, HibernateFilterInfo.class);
            template.save(hibernateFilterInfo);
            template.flush();
            return filterInfo.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager")
    public UuidKey update(@NotNull FilterInfo filterInfo) throws DaoException {
        try {
            if (!internalExists(filterInfo.getKey())) {
                LOGGER.warn("指定的FilterInfo " + filterInfo.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的FilterInfo " + filterInfo.toString() + " 不存在");
            }

            HibernateUuidKey hibernateUuidKey = mapper.map(filterInfo.getKey(), HibernateUuidKey.class);
            HibernateFilterInfo hibernateFilterInfo = template.get(HibernateFilterInfo.class, hibernateUuidKey);
            assert hibernateFilterInfo != null;
            mapper.map(filterInfo, hibernateFilterInfo);
            template.update(hibernateFilterInfo);
            template.flush();
            return filterInfo.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager")
    public void delete(@NotNull UuidKey key) throws DaoException {
        try {
            if (!internalExists(key)) {
                LOGGER.warn("指定的FilterInfo " + key.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的UuidKey " + key.toString() + " 不存在");
            }

            HibernateUuidKey hibernateUuidKey = mapper.map(key, HibernateUuidKey.class);
            HibernateFilterInfo hibernateFilterInfo = template.get(HibernateFilterInfo.class, hibernateUuidKey);
            assert hibernateFilterInfo != null;
            template.delete(hibernateFilterInfo);
            template.flush();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager", readOnly = true)
    public List<FilterInfo> getFilterInfos(UuidKey pointUuidKey, @NotNull LookupPagingInfo lookupPagingInfo) throws DaoException {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(HibernateFilterInfo.class);
            if (Objects.isNull(pointUuidKey)) {
                criteria.add(Restrictions.isNull("pointUuid"));
            } else {
                criteria.add(Restrictions.eq("pointUuid", pointUuidKey.getUuid()));
            }
            @SuppressWarnings("unchecked")
            List<HibernateFilterInfo> hibernateResult = (List<HibernateFilterInfo>) template.findByCriteria(criteria, lookupPagingInfo.getPage() * lookupPagingInfo.getRows(), lookupPagingInfo.getRows());
            List<FilterInfo> result = new ArrayList<>();
            for (HibernateFilterInfo hibernateFilterInfo : hibernateResult) {
                result.add(mapper.map(hibernateFilterInfo, FilterInfo.class));
            }
            return result;
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager", readOnly = true)
    public long getFilterInfoCount(UuidKey pointUuidKey) throws DaoException {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(HibernateFilterInfo.class);
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
