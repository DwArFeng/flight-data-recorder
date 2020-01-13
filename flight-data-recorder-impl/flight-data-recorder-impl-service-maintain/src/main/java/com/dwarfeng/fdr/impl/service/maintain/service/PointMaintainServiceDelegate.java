package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.sdk.crud.PointCrudHelper;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.cache.PointCache;
import com.dwarfeng.fdr.stack.dao.PointDao;
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
public class PointMaintainServiceDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointMaintainServiceDelegate.class);

    @Autowired
    private PointCrudHelper helper;

    @Autowired
    private PointDao pointDao;
    @Autowired
    private PointCache pointCache;

    @Value("${cache.timeout.entity.point}")
    private long pointTimeout;

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public Point get(@NotNull GuidKey key) throws ServiceException {
        try {
            return helper.noAdviseGet(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey insert(@NotNull Point point) throws ServiceException {
        try {
            return helper.noAdviseInsert(point);
        } catch (Exception e) {
            throw new ServiceException(ServiceExceptionCodes.UNDEFINE, e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey update(@NotNull Point point) throws ServiceException {
        try {
            return helper.noAdviseUpdate(point);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(@NotNull GuidKey key) throws ServiceException {
        try {
            helper.noAdviseDelete(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }


    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<Point> getPoints(GuidKey categoryGuid, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        try {
            //定义中间变量。
            List<Point> categories;
            long count;

            LOGGER.debug("查询指定的Point对应的子项...");
            categories = pointDao.getPoints(categoryGuid, lookupPagingInfo);
            count = pointDao.getPointCount(categoryGuid);
            if (count > 0) {
                for (Point point : categories) {
                    LOGGER.debug("将查询到的的实体 " + point.toString() + " 插入缓存中...");
                    pointCache.push(point.getKey(), point, pointTimeout);
                }
                LOGGER.debug("抓取实体 " + categoryGuid.toString() + " 对应的子项并插入缓存...");
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
