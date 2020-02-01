package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.cache.CategoryCache;
import com.dwarfeng.fdr.stack.cache.PointCache;
import com.dwarfeng.fdr.stack.dao.CategoryDao;
import com.dwarfeng.fdr.stack.dao.PointDao;
import com.dwarfeng.fdr.stack.service.CategoryMaintainService;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryMaintainServiceImpl implements CategoryMaintainService {

    @Autowired
    private KeyFetcher<LongIdKey> keyFetcher;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private PointDao pointDao;
    @Autowired
    private CategoryCache categoryCache;
    @Autowired
    private PointCache pointCache;
    @Autowired
    private ServiceExceptionMapper sem;
    @Value("${cache.timeout.entity.category}")
    private long categoryTimeout;
    @Value("${cache.timeout.entity.point}")
    private long pointTimeout;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public boolean exists(LongIdKey key) throws ServiceException {
        try {
            return internalExists(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("判断实体是否存在时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private boolean internalExists(LongIdKey key) throws Exception {
        return categoryCache.exists(key) || categoryDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public Category get(LongIdKey key) throws ServiceException {
        try {
            if (categoryCache.exists(key)) {
                return categoryCache.get(key);
            } else {
                if (!categoryDao.exists(key)) {
                    throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
                }
                Category category = categoryDao.get(key);
                categoryCache.push(category, categoryTimeout);
                return category;
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public LongIdKey insert(Category category) throws ServiceException {
        try {
            if (Objects.nonNull(category.getKey()) && internalExists(category.getKey())) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_EXISTED);
            }
            if (Objects.isNull(category.getKey())) {
                category.setKey(keyFetcher.fetchKey());
            }
            categoryDao.insert(category);
            categoryCache.push(category, categoryTimeout);
            return category.getKey();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void update(Category category) throws ServiceException {
        try {
            if (Objects.nonNull(category.getKey()) && !internalExists(category.getKey())) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            categoryCache.push(category, categoryTimeout);
            categoryDao.update(category);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("更新实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(LongIdKey key) throws ServiceException {
        try {
            if (!internalExists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            internalDelete(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("删除实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private void internalDelete(LongIdKey key) throws com.dwarfeng.subgrade.stack.exception.DaoException, com.dwarfeng.subgrade.stack.exception.CacheException {
        List<Category> categories = categoryDao.lookup(CategoryMaintainService.CHILD_FOR_PARENT, new Object[]{key});
        categories.forEach(category -> category.setKey(null));
        categoryDao.batchUpdate(categories);
        categoryCache.batchPush(categories, categoryTimeout);

        List<Point> points = pointDao.lookup(PointMaintainService.CHILD_FOR_CATEGORY, new Object[]{key});
        points.forEach(point -> point.setCategoryKey(null));
        pointDao.batchUpdate(points);
        pointCache.batchPush(points, pointTimeout);

        categoryDao.delete(key);
        categoryCache.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<Category> lookup(String preset, Object[] objs) throws ServiceException {
        try {
            return PagingUtil.pagedData(categoryDao.lookup(preset, objs));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<Category> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        try {
            return PagingUtil.pagedData(pagingInfo, categoryDao.lookupCount(preset, objs), categoryDao.lookup(preset, objs, pagingInfo));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void lookupDelete(String preset, Object[] objs) throws ServiceException {
        try {
            List<LongIdKey> longIdKeys = categoryDao.lookupDelete(preset, objs);
            for (LongIdKey longIdKey : longIdKeys) {
                internalDelete(longIdKey);
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询并删除实体时发生异常", LogLevel.WARN, sem, e);
        }
    }
}
