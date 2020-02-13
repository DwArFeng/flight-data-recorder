package com.dwarfeng.fdr.impl.service.maintain.service.operation;

import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.cache.CategoryCache;
import com.dwarfeng.fdr.stack.cache.PointCache;
import com.dwarfeng.fdr.stack.dao.CategoryDao;
import com.dwarfeng.fdr.stack.dao.PointDao;
import com.dwarfeng.fdr.stack.service.CategoryMaintainService;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.CrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryCrudOperation implements CrudOperation<LongIdKey, Category> {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private PointDao pointDao;
    @Autowired
    private CategoryCache categoryCache;
    @Autowired
    private PointCache pointCache;
    @Value("${cache.timeout.entity.category}")
    private long categoryTimeout;
    @Value("${cache.timeout.entity.point}")
    private long pointTimeout;

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return categoryCache.exists(key) || categoryDao.exists(key);
    }

    @Override
    public Category get(LongIdKey key) throws Exception {
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
    }

    @Override
    public LongIdKey insert(Category category) throws Exception {
        categoryDao.insert(category);
        categoryCache.push(category, categoryTimeout);
        return category.getKey();
    }

    @Override
    public void update(Category category) throws Exception {
        categoryCache.push(category, categoryTimeout);
        categoryDao.update(category);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
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
}
