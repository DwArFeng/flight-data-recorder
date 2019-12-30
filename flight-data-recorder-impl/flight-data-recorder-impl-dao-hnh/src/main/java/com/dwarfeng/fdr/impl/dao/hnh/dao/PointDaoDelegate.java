package com.dwarfeng.fdr.impl.dao.hnh.dao;

import com.dwarfeng.fdr.impl.dao.hnh.bean.entity.HibernatePoint;
import com.dwarfeng.fdr.impl.dao.hnh.bean.key.HibernateGuidKey;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.DaoException;
import org.dozer.Mapper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
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
public class PointDaoDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointDaoDelegate.class);

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
        return Objects.nonNull(template.get(HibernatePoint.class, hibernateGuidKey));
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public Point get(GuidKey key) throws DaoException {
        try {
            if (!internalExists(key)) {
                LOGGER.warn("指定的Point " + key.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的GuidKey " + key.toString() + " 不存在");
            }

            HibernateGuidKey hibernateGuidKey = mapper.map(key, HibernateGuidKey.class);
            HibernatePoint hibernatePoint = template.get(HibernatePoint.class, hibernateGuidKey);
            return mapper.map(hibernatePoint, Point.class);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey insert(@NotNull Point point) throws DaoException {
        try {
            if (internalExists(point.getKey())) {
                LOGGER.warn("指定的Point " + point.toString() + " 已经存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的Point " + point.toString() + " 已经存在");
            }

            HibernatePoint hibernatePoint = mapper.map(point, HibernatePoint.class);
            template.save(hibernatePoint);
            template.flush();
            return point.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey update(@NotNull Point point) throws DaoException {
        try {
            if (!internalExists(point.getKey())) {
                LOGGER.warn("指定的Point " + point.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的Point " + point.toString() + " 不存在");
            }

            HibernateGuidKey hibernateGuidKey = mapper.map(point.getKey(), HibernateGuidKey.class);
            HibernatePoint hibernatePoint = template.get(HibernatePoint.class, hibernateGuidKey);
            assert hibernatePoint != null;
            mapper.map(point, hibernatePoint);
            template.update(hibernatePoint);
            template.flush();
            return point.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(@NotNull GuidKey key) throws DaoException {
        try {
            if (!internalExists(key)) {
                LOGGER.warn("指定的Point " + key.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的GuidKey " + key.toString() + " 不存在");
            }

            HibernateGuidKey hibernateGuidKey = mapper.map(key, HibernateGuidKey.class);
            HibernatePoint hibernatePoint = template.get(HibernatePoint.class, hibernateGuidKey);
            assert hibernatePoint != null;
            template.delete(hibernatePoint);
            template.flush();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public List<Point> getPoints(GuidKey categoryGuidKey, @NotNull LookupPagingInfo lookupPagingInfo) throws DaoException {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(HibernatePoint.class);
            if (Objects.isNull(categoryGuidKey)) {
                criteria.add(Restrictions.isNull("categoryGuid"));
            } else {
                criteria.add(Restrictions.eq("categoryGuid", categoryGuidKey.getGuid()));
            }
            criteria.addOrder(Order.asc("name"));
            @SuppressWarnings("unchecked")
            List<HibernatePoint> hibernateResult = (List<HibernatePoint>) template.findByCriteria(
                    criteria,
                    lookupPagingInfo.isPaging() ? lookupPagingInfo.getPage() * lookupPagingInfo.getRows() : -1,
                    lookupPagingInfo.isPaging() ? lookupPagingInfo.getRows() : -1);
            List<Point> result = new ArrayList<>();
            for (HibernatePoint hibernatePoint : hibernateResult) {
                result.add(mapper.map(hibernatePoint, Point.class));
            }
            return result;
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public long getPointCount(GuidKey categoryGuidKey) throws DaoException {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(HibernatePoint.class);
            if (Objects.isNull(categoryGuidKey)) {
                criteria.add(Restrictions.isNull("categoryGuid"));
            } else {
                criteria.add(Restrictions.eq("categoryGuid", categoryGuidKey.getGuid()));
            }
            criteria.setProjection(Projections.rowCount());
            return template.findByCriteria(criteria).stream().findFirst().map(Long.class::cast).orElse(0L);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

}
