package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.cache.CategoryCache;
import com.dwarfeng.fdr.stack.dao.CategoryDao;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.handler.ValidationHandler;
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
import java.util.concurrent.TimeUnit;

@Component
@Validated
public class CategoryMaintainServiceDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryMaintainServiceDelegate.class);

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CategoryCache categoryCache;
    @Autowired
    private ValidationHandler validationHandler;

    @Value("${cache.timeout.entity.category}")
    private long timeout;

    @TimeAnalyse
    @Transactional(readOnly = true)
    public Category get(@NotNull UuidKey key) throws ServiceException {
        try {
            validationHandler.uuidKeyValidation(key);
            if (categoryCache.exists(key)) {
                LOGGER.debug("在缓存中发现了 " + key.toString() + " 对应的值，直接返回该值...");
                return categoryCache.get(key);
            } else {
                LOGGER.debug("未能在缓存中发现 " + key.toString() + " 对应的值，读取数据访问层...");
                Category category = categoryDao.get(key);
                LOGGER.debug("将读取到的值 " + category.toString() + " 回写到缓存中...");
                categoryCache.push(key, category, timeout, TimeUnit.MILLISECONDS);
                return category;
            }
        } catch (Exception e) {
            throw new ServiceException("服务异常，原因如下:", e);
        }
    }

    @TimeAnalyse
    @Transactional
    public UuidKey insert(@NotNull Category category) throws ServiceException {
        try {
            validationHandler.categoryValidation(category);
            if (categoryCache.exists(category.getKey()) || categoryDao.exists(category.getKey())) {
                LOGGER.debug("指定的实体 " + category.toString() + " 已经存在，无法插入...");
                throw new IllegalStateException("指定的实体 " + category.toString() + " 已经存在，无法插入...");
            } else {
                LOGGER.debug("将指定的实体 " + category.toString() + " 插入数据访问层中...");
                categoryDao.insert(category);
                LOGGER.debug("将指定的实体 " + category.toString() + " 插入缓存中...");
                categoryCache.push(category.getKey(), category, timeout, TimeUnit.MILLISECONDS);
                return category.getKey();
            }
        } catch (Exception e) {
            throw new ServiceException("服务异常，原因如下:", e);
        }
    }

    @TimeAnalyse
    @Transactional
    public UuidKey update(@NotNull Category category) throws ServiceException {
        try {
            validationHandler.categoryValidation(category);
            if (!categoryCache.exists(category.getKey()) && !categoryDao.exists(category.getKey())) {
                LOGGER.debug("指定的实体 " + category.toString() + " 已经存在，无法更新...");
                throw new IllegalStateException("指定的实体 " + category.toString() + " 已经存在，无法更新...");
            } else {
                LOGGER.debug("将指定的实体 " + category.toString() + " 插入数据访问层中...");
                categoryDao.update(category);
                LOGGER.debug("将指定的实体 " + category.toString() + " 插入缓存中...");
                categoryCache.push(category.getKey(), category, timeout, TimeUnit.MILLISECONDS);
                return category.getKey();
            }
        } catch (Exception e) {
            throw new ServiceException("服务异常，原因如下:");
        }
    }

    @TimeAnalyse
    @Transactional
    public void delete(@NotNull UuidKey key) throws ServiceException {
        try {
            validationHandler.uuidKeyValidation(key);
            if (!categoryCache.exists(key) && !categoryDao.exists(key)) {
                LOGGER.debug("指定的键 " + key.toString() + " 不存在，无法删除...");
                throw new IllegalStateException("指定的键 " + key.toString() + " 不存在，无法删除...");
            } else {
                LOGGER.debug("将指定的Category从缓存中删除...");
                categoryCache.delete(key);
                LOGGER.debug("将指定的Category从数据访问层中删除...");
                categoryDao.delete(key);
            }
        } catch (Exception e) {
            throw new ServiceException("服务异常，原因如下:");
        }
    }

    @TimeAnalyse
    @Transactional(readOnly = true)
    public PagedData<Category> getChilds(UuidKey uuidKey, @NotNull LookupPagingInfo lookupPagingInfo) throws ServiceException {
        try {
            if (Objects.nonNull(uuidKey)) {
                validationHandler.uuidKeyValidation(uuidKey);
            }
            validationHandler.lookupPagingInfoValidation(lookupPagingInfo);

            LOGGER.debug("查询指定的Category对应的子项...");
            List<Category> categories = categoryDao.getChilds(uuidKey, lookupPagingInfo);
            long count = categoryDao.getChildCount(uuidKey);
            for (Category category : categories) {
                LOGGER.debug("将查询到的的实体 " + category.toString() + " 插入缓存中...");
                categoryCache.push(category.getKey(), category, timeout, TimeUnit.MILLISECONDS);
            }
            return new PagedData<Category>(
                    lookupPagingInfo.getPage(),
                    (int) Math.ceil((double) count / lookupPagingInfo.getRows()),
                    lookupPagingInfo.getRows(),
                    count,
                    categories
            );
        } catch (Exception e) {
            throw new ServiceException("服务异常，原因如下:");
        }
    }
}
