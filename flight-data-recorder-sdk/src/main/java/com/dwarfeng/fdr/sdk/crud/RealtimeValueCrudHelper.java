package com.dwarfeng.fdr.sdk.crud;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.cache.RealtimeValueCache;
import com.dwarfeng.fdr.stack.dao.RealtimeValueDao;
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
import java.util.Objects;

@Component
@Validated
public class RealtimeValueCrudHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(RealtimeValueCrudHelper.class);

    @Autowired
    private RealtimeValueDao realtimeValueDao;
    @Autowired
    private RealtimeValueCache realtimeValueCache;

    @Autowired
    private GuidApi guidApi;

    @Value("${cache.timeout.entity.realtime_value}")
    private long realtimeValueTimeout;

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public RealtimeValue get(@NotNull GuidKey key) throws ServiceException {
        try {
            return noAdviseGet(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public RealtimeValue noAdviseGet(GuidKey key) throws CacheException, DaoException {
        if (realtimeValueCache.exists(key)) {
            LOGGER.debug("在缓存中发现了 " + key.toString() + " 对应的值，直接返回该值...");
            return realtimeValueCache.get(key);
        } else {
            LOGGER.debug("未能在缓存中发现 " + key.toString() + " 对应的值，读取数据访问层...");
            RealtimeValue realtimeValue = realtimeValueDao.get(key);
            LOGGER.debug("将读取到的值 " + realtimeValue.toString() + " 回写到缓存中...");
            realtimeValueCache.push(key, realtimeValue, realtimeValueTimeout);
            return realtimeValue;
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey insert(@NotNull RealtimeValue realtimeValue) throws ServiceException {
        try {
            return noAdviseInsert(realtimeValue);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public GuidKey noAdviseInsert(RealtimeValue realtimeValue) throws com.dwarfeng.sfds.stack.exception.ServiceException, CacheException, DaoException {
        //如果实体中没有主键，则向主键服务请求一个主键。
        maySetGuid(realtimeValue);

        if (realtimeValueCache.exists(realtimeValue.getKey()) || realtimeValueDao.exists(realtimeValue.getKey())) {
            LOGGER.debug("指定的实体 " + realtimeValue.toString() + " 已经存在，无法插入...");
            throw new IllegalStateException("指定的实体 " + realtimeValue.toString() + " 已经存在，无法插入...");
        } else {
            LOGGER.debug("将指定的实体 " + realtimeValue.toString() + " 插入数据访问层中...");
            realtimeValueDao.insert(realtimeValue);
            LOGGER.debug("将指定的实体 " + realtimeValue.toString() + " 插入缓存中...");
            realtimeValueCache.push(realtimeValue.getKey(), realtimeValue, realtimeValueTimeout);
            return realtimeValue.getKey();
        }
    }

    private void maySetGuid(RealtimeValue realtimeValue) throws com.dwarfeng.sfds.stack.exception.ServiceException {
        if (Objects.isNull(realtimeValue.getKey())) {
            LOGGER.debug("实体 " + realtimeValue.toString() + "没有主键，将从服务中获取GUID...");
            long guid = guidApi.nextGuid();
            LOGGER.debug("从服务中获取了guid: " + guid);
            realtimeValue.setKey(new GuidKey(guid));
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public GuidKey update(@NotNull RealtimeValue realtimeValue) throws ServiceException {
        try {
            return noAdviseUpdate(realtimeValue);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public GuidKey noAdviseUpdate(RealtimeValue realtimeValue) throws CacheException, DaoException {
        if (!realtimeValueCache.exists(realtimeValue.getKey()) && !realtimeValueDao.exists(realtimeValue.getKey())) {
            LOGGER.debug("指定的实体 " + realtimeValue.toString() + " 已经存在，无法更新...");
            throw new IllegalStateException("指定的实体 " + realtimeValue.toString() + " 已经存在，无法更新...");
        } else {
            RealtimeValue oldRealtimeValue = noAdviseGet(realtimeValue.getKey());
            LOGGER.debug("将指定的实体 " + realtimeValue.toString() + " 插入数据访问层中...");
            realtimeValueDao.update(realtimeValue);
            LOGGER.debug("将指定的实体 " + realtimeValue.toString() + " 插入缓存中...");
            realtimeValueCache.push(realtimeValue.getKey(), realtimeValue, realtimeValueTimeout);
            return realtimeValue.getKey();
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(@NotNull GuidKey key) throws ServiceException {
        try {
            if (!realtimeValueCache.exists(key) && !realtimeValueDao.exists(key)) {
                LOGGER.debug("指定的键 " + key.toString() + " 不存在，无法删除...");
                throw new IllegalStateException("指定的键 " + key.toString() + " 不存在，无法删除...");
            } else {
                RealtimeValue oldRealtimeValue = noAdviseGet(key);
                LOGGER.debug("清除实体 " + key.toString() + " 对应的子项缓存...");
                LOGGER.debug("将指定的RealtimeValue从缓存中删除...");
                realtimeValueCache.delete(key);
                LOGGER.debug("将指定的RealtimeValue从数据访问层中删除...");
                realtimeValueDao.delete(key);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public void noAdviseDelete(GuidKey key) throws CacheException, DaoException {
        if (!realtimeValueCache.exists(key) && !realtimeValueDao.exists(key)) {
            LOGGER.debug("指定的键 " + key.toString() + " 不存在，无法删除...");
            throw new IllegalStateException("指定的键 " + key.toString() + " 不存在，无法删除...");
        } else {
            RealtimeValue oldRealtimeValue = noAdviseGet(key);
            LOGGER.debug("清除实体 " + key.toString() + " 对应的子项缓存...");
            LOGGER.debug("将指定的RealtimeValue从缓存中删除...");
            realtimeValueCache.delete(key);
            LOGGER.debug("将指定的RealtimeValue从数据访问层中删除...");
            realtimeValueDao.delete(key);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void insertOrUpdateRealtimeValue(@NotNull RealtimeValue realtimeValue) throws ServiceException {
        try {
            noAdviseInsertOrUpdateRealtimeValue(realtimeValue);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public void noAdviseInsertOrUpdateRealtimeValue(RealtimeValue realtimeValue) throws CacheException, DaoException {
        if (realtimeValueCache.exists(realtimeValue.getKey()) || realtimeValueDao.exists(realtimeValue.getKey())) {
            LOGGER.debug("指定的实体 " + realtimeValue.toString() + " 已经存在，执行更新操作...");
            realtimeValueDao.update(realtimeValue);
        } else {
            LOGGER.debug("指定的实体 " + realtimeValue.toString() + " 不存在，执行插入操作...");
            LOGGER.debug("将指定的实体 " + realtimeValue.toString() + " 插入数据访问层中...");
            realtimeValueDao.insert(realtimeValue);
        }
        LOGGER.debug("将指定的实体 " + realtimeValue.toString() + " 插入缓存中...");
        realtimeValueCache.push(realtimeValue.getKey(), realtimeValue, realtimeValueTimeout);
    }
}
