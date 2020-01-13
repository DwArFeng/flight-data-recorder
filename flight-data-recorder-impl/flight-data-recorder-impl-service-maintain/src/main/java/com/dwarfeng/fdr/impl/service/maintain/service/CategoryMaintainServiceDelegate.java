package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.sdk.crud.CategoryCrudHelper;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.cache.CategoryCache;
import com.dwarfeng.fdr.stack.dao.CategoryDao;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Component
@Validated
public class CategoryMaintainServiceDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryMaintainServiceDelegate.class);

    @Autowired
    private CategoryCrudHelper helper;

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CategoryCache categoryCache;

    @Value("${cache.timeout.entity.category}")
    private long categoryTimeout;

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public Category get(@NotNull GuidKey key) throws ServiceException {
        try {
            return helper.noAdviseGet(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey insert(@NotNull Category category) throws ServiceException {
        try {
            return helper.noAdviseInsert(category);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey update(@NotNull Category category) throws ServiceException {
        try {
            return helper.noAdviseUpdate(category);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(@NotNull GuidKey key) throws ServiceException {
        try {
            helper.delete(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<Category> getChilds(GuidKey guidKey, @NotNull LookupPagingInfo lookupPagingInfo) throws ServiceException {
        try {
            //定义中间变量。
            List<Category> categories;
            long count;

            LOGGER.debug("查询指定的Category对应的子项...");
            categories = categoryDao.getChilds(guidKey, lookupPagingInfo);
            count = categoryDao.getChildCount(guidKey);
            if (count > 0) {
                for (Category category : categories) {
                    LOGGER.debug("将查询到的的实体 " + category.toString() + " 插入缓存中...");
                    categoryCache.push(category.getKey(), category, categoryTimeout);
                }
                LOGGER.debug("抓取实体 " + guidKey.toString() + " 对应的子项并插入缓存...");
            }

            return new PagedData<>(
                    lookupPagingInfo.getPage(),
                    Math.max((int) Math.ceil((double) count / lookupPagingInfo.getRows()), 1),
                    lookupPagingInfo.getRows(),
                    count,
                    categories
            );
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
