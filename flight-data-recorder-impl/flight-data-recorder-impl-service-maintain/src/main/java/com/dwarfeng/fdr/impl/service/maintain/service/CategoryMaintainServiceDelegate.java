package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.sdk.crud.CategoryCrudHelper;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.cache.CategoryCache;
import com.dwarfeng.fdr.stack.cache.CategoryHasChildCache;
import com.dwarfeng.fdr.stack.dao.CategoryDao;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
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
    private AsyncBean asyncBean;

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CategoryCache categoryCache;
    @Autowired
    private CategoryHasChildCache categoryHasChildCache;

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

            if (categoryHasChildCache.exists(guidKey)) {
                LOGGER.debug("在缓存中发现了 " + guidKey.toString() + " 对应的子项列表，直接返回缓存中的值...");
                categories = categoryHasChildCache.get(guidKey, lookupPagingInfo);
                count = categoryHasChildCache.size(guidKey);
            } else {
                LOGGER.debug("查询指定的Category对应的子项...");
                categories = categoryDao.getChilds(guidKey, lookupPagingInfo);
                count = categoryDao.getChildCount(guidKey);
                if (count > 0) {
                    for (Category category : categories) {
                        LOGGER.debug("将查询到的的实体 " + category.toString() + " 插入缓存中...");
                        categoryCache.push(category.getKey(), category, categoryTimeout);
                    }
                    LOGGER.debug("抓取实体 " + guidKey.toString() + " 对应的子项并插入缓存...");
                    asyncBean.fetchChild2Cache(guidKey);
                }
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

    @Component
    public static class AsyncBean {

        @Autowired
        private CategoryDao categoryDao;
        @Autowired
        private CategoryHasChildCache categoryHasChildCache;

        @Value("${cache.timeout.one_to_many.category_has_child}")
        private long categoryHasChildTimeout;
        @Value("${cache.batch_fetch_size.category}")
        private int categoryFetchSize;

        @Transactional(transactionManager = "hibernateTransactionManager")
        @TimeAnalyse
        @Async
        public void fetchChild2Cache(GuidKey guidKey) {
            try {
                int totlePage;
                int currPage;
                long count = categoryDao.getChildCount(guidKey);
                totlePage = Math.max((int) Math.ceil((double) count / categoryFetchSize), 1);
                currPage = 0;
                categoryHasChildCache.delete(guidKey);
                do {
                    LookupPagingInfo lookupPagingInfo = new LookupPagingInfo(true, currPage++, categoryFetchSize);
                    List<Category> childs = categoryDao.getChilds(guidKey, lookupPagingInfo);
                    if (childs.size() > 0) {
                        categoryHasChildCache.push(guidKey, childs, categoryHasChildTimeout);
                    }
                } while (currPage < totlePage);
            } catch (Exception e) {
                LOGGER.warn("将分类 " + guidKey.toString() + " 的子项添加进入缓存时发生异常，异常信息如下", e);
            }
        }
    }
}
