package com.dwarfeng.fdr.impl.dao.hnh.dao;

import com.dwarfeng.fdr.impl.dao.hnh.bean.entity.HibernatePoint;
import com.dwarfeng.fdr.impl.dao.hnh.bean.key.HibernateUuidKey;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
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
    @Transactional(readOnly = true)
    public boolean exists(@NotNull UuidKey key) throws DaoException {
        try {
            return innerExists(key);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    private boolean innerExists(UuidKey key) throws Exception {
        HibernateUuidKey hibernateUuidKey = mapper.map(key, HibernateUuidKey.class);
        return Objects.nonNull(template.get(HibernatePoint.class, hibernateUuidKey));
    }

    @TimeAnalyse
    @Transactional(readOnly = true)
    public Point get(UuidKey key) throws DaoException {
        try {
            HibernateUuidKey hibernateUuidKey = mapper.map(key, HibernateUuidKey.class);
            HibernatePoint hibernatePoint = template.get(HibernatePoint.class, hibernateUuidKey);
            return mapper.map(hibernatePoint, Point.class);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional
    public UuidKey insert(@NotNull Point point) throws DaoException {
        try {
            if (innerExists(point.getKey())) {
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
    @Transactional
    public UuidKey update(@NotNull Point point) throws DaoException {
        try {
            if (!innerExists(point.getKey())) {
                LOGGER.warn("指定的Point " + point.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的Point " + point.toString() + " 不存在");
            }

            HibernatePoint hibernatePoint = mapper.map(point, HibernatePoint.class);
            template.update(hibernatePoint);
            template.flush();
            return point.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional
    public void delete(@NotNull UuidKey key) throws DaoException {
        try {
            if (!innerExists(key)) {
                LOGGER.warn("指定的Point " + key.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的UuidKey " + key.toString() + " 不存在");
            }

            HibernateUuidKey hibernateUuidKey = mapper.map(key, HibernateUuidKey.class);
            HibernatePoint hibernatePoint = template.get(HibernatePoint.class, hibernateUuidKey);
//            //取消所有与该分类有关的自分类的父项关联。
//            DetachedCriteria criteria = DetachedCriteria.forClass(HibernatePoint.class)
//                    .add(Restrictions.eq("parentUuid", hibernateUuidKey.getUuid()));
//            for (Object child : hibernateTemplate.findByCriteria(criteria)) {
//                ((HibernatePoint) child).setParentUuid(null);
//            }
            assert hibernatePoint != null;
            template.delete(hibernatePoint);
            template.flush();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(readOnly = true)
    public List<Point> getPoints(@NotNull UuidKey categoryUuidKey, @NotNull LookupPagingInfo lookupPagingInfo) throws DaoException {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(HibernatePoint.class);
            if (Objects.isNull(categoryUuidKey)) {
                criteria.add(Restrictions.isNull("categoryUuid"));
            } else {
                criteria.add(Restrictions.eq("categoryUuid", categoryUuidKey.getUuid()));
            }
            criteria.addOrder(Order.asc("name"));
            @SuppressWarnings("unchecked")
            List<HibernatePoint> hibernateResult = (List<HibernatePoint>) template.findByCriteria(criteria, lookupPagingInfo.getPage() * lookupPagingInfo.getRows(), lookupPagingInfo.getRows());
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
    @Transactional(readOnly = true)
    public long getChildCount(@NotNull UuidKey categoryUuidKey) throws DaoException {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(HibernatePoint.class);
            if (Objects.isNull(categoryUuidKey)) {
                criteria.add(Restrictions.isNull("categoryUuid"));
            } else {
                criteria.add(Restrictions.eq("categoryUuid", categoryUuidKey.getUuid()));
            }
            criteria.setProjection(Projections.rowCount());
            return template.findByCriteria(criteria).stream().findFirst().map(Long.class::cast).orElse(0L);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

}
