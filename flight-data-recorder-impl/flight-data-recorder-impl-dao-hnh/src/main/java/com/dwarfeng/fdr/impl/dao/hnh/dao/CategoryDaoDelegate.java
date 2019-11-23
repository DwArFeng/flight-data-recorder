package com.dwarfeng.fdr.impl.dao.hnh.dao;

import com.dwarfeng.fdr.impl.dao.hnh.bean.entity.HibernateCategory;
import com.dwarfeng.fdr.impl.dao.hnh.bean.key.HibernateUuidKey;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Category;
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
public class CategoryDaoDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryDaoDelegate.class);

    @Autowired
    private HibernateTemplate hibernateTemplate;
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
        return Objects.nonNull(hibernateTemplate.get(HibernateCategory.class, hibernateUuidKey));
    }

    @TimeAnalyse
    @Transactional(readOnly = true)
    public Category get(UuidKey key) throws DaoException {
        try {
            HibernateUuidKey hibernateUuidKey = mapper.map(key, HibernateUuidKey.class);
            HibernateCategory hibernateCategory = hibernateTemplate.get(HibernateCategory.class, hibernateUuidKey);
            return mapper.map(hibernateCategory, Category.class);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional
    public UuidKey insert(@NotNull Category category) throws DaoException {
        try {
            if (Objects.isNull(category.getKey())) {
                LOGGER.warn("指定的Category " + category.toString() + " 没有主键, 将抛出异常...");
                throw new IllegalArgumentException("指定的Category " + category.toString() + " 没有主键");
            }
            if (innerExists(category.getKey())) {
                LOGGER.warn("指定的Category " + category.toString() + " 已经存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的Category " + category.toString() + " 已经存在");
            }

            HibernateCategory hibernateCategory = mapper.map(category, HibernateCategory.class);
            hibernateTemplate.save(hibernateCategory);
            hibernateTemplate.flush();
            return category.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional
    public UuidKey update(@NotNull Category category) throws DaoException {
        try {
            if (Objects.isNull(category.getKey())) {
                LOGGER.warn("指定的Category " + category.toString() + " 没有主键, 将抛出异常...");
                throw new IllegalArgumentException("指定的Category " + category.toString() + " 没有主键");
            }
            if (!innerExists(category.getKey())) {
                LOGGER.warn("指定的Category " + category.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的Category " + category.toString() + " 不存在");
            }

            HibernateCategory hibernateCategory = mapper.map(category, HibernateCategory.class);
            hibernateTemplate.update(hibernateCategory);
            hibernateTemplate.flush();
            return category.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional
    public void delete(@NotNull UuidKey key) throws DaoException {
        try {
            if (!innerExists(key)) {
                LOGGER.warn("指定的Category " + key.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的UuidKey " + key.toString() + " 不存在");
            }

            HibernateUuidKey hibernateUuidKey = mapper.map(key, HibernateUuidKey.class);
            HibernateCategory hibernateCategory = hibernateTemplate.get(HibernateCategory.class, hibernateUuidKey);
            //取消所有与该分类有关的自分类的父项关联。
            DetachedCriteria criteria = DetachedCriteria.forClass(HibernateCategory.class)
                    .add(Restrictions.eq("parentUuid", hibernateUuidKey.getUuid()));
            for (Object child : hibernateTemplate.findByCriteria(criteria)) {
                ((HibernateCategory) child).setParentUuid(null);
            }
            assert hibernateCategory != null;
            hibernateTemplate.delete(hibernateCategory);
            hibernateTemplate.flush();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(readOnly = true)
    public List<Category> getChilds(UuidKey key, @NotNull LookupPagingInfo lookupPagingInfo) throws DaoException {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(HibernateCategory.class);
            if (Objects.isNull(key)) {
                criteria.add(Restrictions.isNull("parentUuid"));
            } else {
                criteria.add(Restrictions.eq("parentUuid", key.getUuid()));
            }
            criteria.addOrder(Order.asc("name"));
            @SuppressWarnings("unchecked")
            List<HibernateCategory> hibernateResult = (List<HibernateCategory>) hibernateTemplate.findByCriteria(criteria, lookupPagingInfo.getPage() * lookupPagingInfo.getRows(), lookupPagingInfo.getRows());
            List<Category> result = new ArrayList<>();
            for (HibernateCategory hibernateCategory : hibernateResult) {
                result.add(mapper.map(hibernateCategory, Category.class));
            }
            return result;
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(readOnly = true)
    public long getChildCount(UuidKey key) throws DaoException {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(HibernateCategory.class);
            if (Objects.isNull(key)) {
                criteria.add(Restrictions.isNull("parentUuid"));
            } else {
                criteria.add(Restrictions.eq("parentUuid", key.getUuid()));
            }
            criteria.setProjection(Projections.rowCount());
            return hibernateTemplate.findByCriteria(criteria).stream().findFirst().map(Long.class::cast).orElse(0L);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }
}
