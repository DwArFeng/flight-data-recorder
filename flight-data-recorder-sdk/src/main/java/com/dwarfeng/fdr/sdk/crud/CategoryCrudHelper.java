package com.dwarfeng.fdr.sdk.crud;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.cache.CategoryCache;
import com.dwarfeng.fdr.stack.cache.CategoryHasChildCache;
import com.dwarfeng.fdr.stack.cache.CategoryHasPointCache;
import com.dwarfeng.fdr.stack.cache.PointCache;
import com.dwarfeng.fdr.stack.dao.CategoryDao;
import com.dwarfeng.fdr.stack.dao.PointDao;
import com.dwarfeng.fdr.stack.exception.CacheException;
import com.dwarfeng.fdr.stack.exception.DaoException;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.sfds.api.GuidApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Component
@Validated
public class CategoryCrudHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryCrudHelper.class);

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CategoryCache categoryCache;
    @Autowired
    private CategoryHasChildCache categoryHasChildCache;
    @Autowired
    private CategoryHasPointCache categoryHasPointCache;
    @Autowired
    private PointDao pointDao;
    @Autowired
    private PointCache pointCache;

    @Autowired
    private GuidApi guidApi;

    @Value("${cache.timeout.entity.category}")
    private long categoryTimeout;
    @Value("${cache.timeout.one_to_many.category_has_child}")
    private long categoryHasChildTimeout;
    @Value("${cache.batch_fetch_size.category}")
    private int categoryFetchSize;

    @Value("${cache.timeout.entity.point}")
    private long pointTimeout;

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public Category get(@NotNull GuidKey key) throws ServiceException {
        try {
            return noAdviseGet(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Category noAdviseGet(GuidKey key) throws CacheException, DaoException {
        if (categoryCache.exists(key)) {
            LOGGER.debug("在缓存中发现了 " + key.toString() + " 对应的值，直接返回该值...");
            return categoryCache.get(key);
        } else {
            LOGGER.debug("未能在缓存中发现 " + key.toString() + " 对应的值，读取数据访问层...");
            Category category = categoryDao.get(key);
            LOGGER.debug("将读取到的值 " + category.toString() + " 回写到缓存中...");
            categoryCache.push(key, category, categoryTimeout);
            return category;
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey insert(@NotNull Category category) throws ServiceException {
        try {
            return noAdviseInsert(category);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public GuidKey noAdviseInsert(Category category) throws com.dwarfeng.sfds.stack.exception.ServiceException, CacheException, DaoException {
        //如果实体中没有主键，则向主键服务请求一个主键。
        maySetGuid(category);

        if (categoryCache.exists(category.getKey()) || categoryDao.exists(category.getKey())) {
            LOGGER.debug("指定的实体 " + category.toString() + " 已经存在，无法插入...");
            throw new IllegalStateException("指定的实体 " + category.toString() + " 已经存在，无法插入...");
        } else {
            LOGGER.debug("将指定的实体 " + category.toString() + " 插入数据访问层中...");
            categoryDao.insert(category);
            LOGGER.debug("将指定的实体 " + category.toString() + " 插入缓存中...");
            categoryCache.push(category.getKey(), category, categoryTimeout);
            if (Objects.nonNull(category.getParentKey())) {
                LOGGER.debug("清除实体 " + category.toString() + " 对应的父项缓存...");
                categoryHasChildCache.delete(category.getParentKey());
            }
            return category.getKey();
        }
    }

    private void maySetGuid(Category category) throws com.dwarfeng.sfds.stack.exception.ServiceException {
        if (Objects.isNull(category.getKey())) {
            LOGGER.debug("实体 " + category.toString() + "没有主键，将从服务中获取GUID...");
            long guid = guidApi.nextGuid();
            LOGGER.debug("从服务中获取了guid: " + guid);
            category.setKey(new GuidKey(guid));
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey update(@NotNull Category category) throws ServiceException {
        try {
            return noAdviseUpdate(category);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public GuidKey noAdviseUpdate(Category category) throws CacheException, DaoException {
        if (!categoryCache.exists(category.getKey()) && !categoryDao.exists(category.getKey())) {
            LOGGER.debug("指定的实体 " + category.toString() + " 已经存在，无法更新...");
            throw new IllegalStateException("指定的实体 " + category.toString() + " 已经存在，无法更新...");
        } else {
            Category oldCategory = noAdviseGet(category.getKey());
            if (Objects.nonNull(oldCategory.getParentKey())) {
                LOGGER.debug("清除旧实体 " + oldCategory.toString() + " 对应的父项缓存...");
                categoryHasChildCache.delete(oldCategory.getParentKey());
            }
            LOGGER.debug("将指定的实体 " + category.toString() + " 插入数据访问层中...");
            categoryDao.update(category);
            LOGGER.debug("将指定的实体 " + category.toString() + " 插入缓存中...");
            categoryCache.push(category.getKey(), category, categoryTimeout);
            if (Objects.nonNull(category.getParentKey())) {
                LOGGER.debug("清除新实体 " + category.toString() + " 对应的父项缓存...");
                categoryHasChildCache.delete(category.getParentKey());
            }
            return category.getKey();
        }
    }


    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(@NotNull GuidKey key) throws ServiceException {
        try {
            noAdviseDelete(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public void noAdviseDelete(GuidKey key) throws CacheException, DaoException {
        if (!categoryCache.exists(key) && !categoryDao.exists(key)) {
            LOGGER.debug("指定的键 " + key.toString() + " 不存在，无法删除...");
            throw new IllegalStateException("指定的键 " + key.toString() + " 不存在，无法删除...");
        } else {
            Category oldCategory = noAdviseGet(key);
            if (Objects.nonNull(oldCategory.getParentKey())) {
                LOGGER.debug("清除旧实体 " + oldCategory.toString() + " 对应的父项缓存...");
                categoryHasChildCache.delete(oldCategory.getParentKey());
            }

            List<Category> childCategories = categoryDao.getChilds(key, LookupPagingInfo.LOOKUP_ALL);
            List<Point> childPoints = pointDao.getPoints(key, LookupPagingInfo.LOOKUP_ALL);

            LOGGER.debug("更新分类 " + key.toString() + " 对应的子项缓存与信息...");
            for (Category category : childCategories) {
                category.setParentKey(null);
                categoryCache.push(category.getKey(), category, categoryTimeout);
                categoryDao.update(category);
            }
            LOGGER.debug("更新分类 " + key.toString() + " 对应的点位缓存与信息...");
            for (Point point : childPoints) {
                point.setCategoryKey(null);
                pointCache.push(point.getKey(), point, pointTimeout);
                pointDao.update(point);
            }

            LOGGER.debug("清除实体 " + key.toString() + " 对应的子项缓存...");
            categoryHasChildCache.delete(key);
            categoryHasPointCache.delete(key);
            LOGGER.debug("将指定的Category从缓存中删除...");
            categoryCache.delete(key);
            LOGGER.debug("将指定的Category从数据访问层中删除...");
            categoryDao.delete(key);
        }
    }
}
