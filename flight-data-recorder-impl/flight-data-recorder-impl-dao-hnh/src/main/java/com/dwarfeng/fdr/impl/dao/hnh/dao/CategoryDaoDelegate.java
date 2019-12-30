package com.dwarfeng.fdr.impl.dao.hnh.dao;

import com.dwarfeng.fdr.impl.dao.hnh.bean.entity.HibernateCategory;
import com.dwarfeng.fdr.impl.dao.hnh.bean.key.HibernateGuidKey;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Category;
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
public class CategoryDaoDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryDaoDelegate.class);

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
        return Objects.nonNull(template.get(HibernateCategory.class, hibernateGuidKey));
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public Category get(GuidKey key) throws DaoException {
        try {
            if (!internalExists(key)) {
                LOGGER.warn("指定的Category " + key.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的GuidKey " + key.toString() + " 不存在");
            }

            HibernateGuidKey hibernateGuidKey = mapper.map(key, HibernateGuidKey.class);
            HibernateCategory hibernateCategory = template.get(HibernateCategory.class, hibernateGuidKey);
            return mapper.map(hibernateCategory, Category.class);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey insert(@NotNull Category category) throws DaoException {
        try {
            if (internalExists(category.getKey())) {
                LOGGER.warn("指定的Category " + category.toString() + " 已经存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的Category " + category.toString() + " 已经存在");
            }

            HibernateCategory hibernateCategory = mapper.map(category, HibernateCategory.class);
            template.save(hibernateCategory);
            template.flush();
            return category.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey update(@NotNull Category category) throws DaoException {
        try {
            if (!internalExists(category.getKey())) {
                LOGGER.warn("指定的Category " + category.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的Category " + category.toString() + " 不存在");
            }

            HibernateGuidKey hibernateGuidKey = mapper.map(category.getKey(), HibernateGuidKey.class);
            HibernateCategory hibernateCategory = template.get(HibernateCategory.class, hibernateGuidKey);
            assert hibernateCategory != null;
            mapper.map(category, hibernateCategory);
            template.update(hibernateCategory);
            template.flush();
            return category.getKey();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(@NotNull GuidKey key) throws DaoException {
        try {
            if (!internalExists(key)) {
                LOGGER.warn("指定的Category " + key.toString() + " 不存在, 将抛出异常...");
                throw new IllegalArgumentException("指定的GuidKey " + key.toString() + " 不存在");
            }

            HibernateGuidKey hibernateGuidKey = mapper.map(key, HibernateGuidKey.class);
            HibernateCategory hibernateCategory = template.get(HibernateCategory.class, hibernateGuidKey);
            assert hibernateCategory != null;
            template.delete(hibernateCategory);
            template.flush();
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public List<Category> getChilds(GuidKey key, @NotNull LookupPagingInfo lookupPagingInfo) throws DaoException {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(HibernateCategory.class);
            if (Objects.isNull(key)) {
                criteria.add(Restrictions.isNull("parentGuid"));
            } else {
                criteria.add(Restrictions.eq("parentGuid", key.getGuid()));
            }
            criteria.addOrder(Order.asc("name"));
            @SuppressWarnings("unchecked")
            List<HibernateCategory> hibernateResult = (List<HibernateCategory>) template.findByCriteria(
                    criteria,
                    lookupPagingInfo.isPaging() ? lookupPagingInfo.getPage() * lookupPagingInfo.getRows() : -1,
                    lookupPagingInfo.isPaging() ? lookupPagingInfo.getRows() : -1);
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
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public long getChildCount(GuidKey key) throws DaoException {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(HibernateCategory.class);
            if (Objects.isNull(key)) {
                criteria.add(Restrictions.isNull("parentGuid"));
            } else {
                criteria.add(Restrictions.eq("parentGuid", key.getGuid()));
            }
            criteria.setProjection(Projections.rowCount());
            return template.findByCriteria(criteria).stream().findFirst().map(Long.class::cast).orElse(0L);
        } catch (Exception e) {
            throw new DaoException("数据访问发生异常", e);
        }
    }
}
