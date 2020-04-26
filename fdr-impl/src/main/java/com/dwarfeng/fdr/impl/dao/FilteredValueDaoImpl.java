package com.dwarfeng.fdr.impl.dao;

import com.dwarfeng.fdr.impl.bean.entity.HibernateFilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.dao.FilteredValueDao;
import com.dwarfeng.fdr.stack.service.FilteredValueMaintainService;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateEntireLookupDao;
import com.dwarfeng.subgrade.impl.dao.HibernatePresetLookupDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@Repository
public class FilteredValueDaoImpl implements FilteredValueDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceValueDaoImpl.class);

    @Autowired
    private HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, FilteredValue, HibernateFilteredValue> batchBaseDao;
    @Autowired
    private HibernateEntireLookupDao<FilteredValue, HibernateFilteredValue> entireLookupDao;
    @Autowired
    private HibernatePresetLookupDao<FilteredValue, HibernateFilteredValue> presetLookupDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Autowired
    private List<NSQLGenerator> nsqlGenerators;

    @Value("${hibernate.accelerate.using_native_sql}")
    private boolean usingNativeSQL;
    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    private NSQLGenerator nsqlGenerator = null;

    @PostConstruct
    public void init() {
        nsqlGenerator = nsqlGenerators.stream()
                .filter(generator -> generator.supportType(hibernateDialect)).findAny().orElse(null);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public LongIdKey insert(FilteredValue element) throws DaoException {
        return batchBaseDao.insert(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void update(FilteredValue element) throws DaoException {
        batchBaseDao.update(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(LongIdKey key) throws DaoException {
        batchBaseDao.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public boolean exists(LongIdKey key) throws DaoException {
        return batchBaseDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public FilteredValue get(LongIdKey key) throws DaoException {
        return batchBaseDao.get(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public List<LongIdKey> batchInsert(List<FilteredValue> elements) throws DaoException {
        return batchBaseDao.batchInsert(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void batchUpdate(List<FilteredValue> elements) throws DaoException {
        batchBaseDao.batchUpdate(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void batchDelete(List<LongIdKey> keys) throws DaoException {
        batchBaseDao.batchDelete(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public boolean allExists(List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public boolean nonExists(List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public List<FilteredValue> batchGet(List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.batchGet(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public List<FilteredValue> lookup() throws DaoException {
        return entireLookupDao.lookup();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public List<FilteredValue> lookup(PagingInfo pagingInfo) throws DaoException {
        return entireLookupDao.lookup(pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public int lookupCount() throws DaoException {
        return entireLookupDao.lookupCount();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public List<FilteredValue> lookup(String preset, Object[] objs) throws DaoException {
        try {
            if (Objects.equals(FilteredValueMaintainService.CHILD_FOR_POINT_BETWEEN, preset) && usingNativeSQL) {
                if (Objects.isNull(nsqlGenerator)) {
                    LOGGER.warn("指定的 hibernateDialect: " + hibernateDialect + ", 不受支持, 将不会使用原生SQL进行查询");
                    return presetLookupDao.lookup(preset, objs);
                }
                LOGGER.debug("使用原生SQL进行查询...");
                List<FilteredValue> filteredValues = hibernateTemplate.executeWithNativeSession(
                        session -> session.doReturningWork(connection -> {
                            try {
                                return nsqlGenerator.lookupFilteredForPoint(connection, objs);
                            } catch (Exception e) {
                                LOGGER.warn("原生SQL查询返回异常", e);
                                return null;
                            }
                        })
                );
                if (Objects.nonNull(filteredValues)) {
                    return filteredValues;
                } else {
                    LOGGER.warn("原生SQL查询返回值无效, 不使用原生SQL再次进行查询...");
                    return presetLookupDao.lookup(preset, objs);
                }
            } else if (Objects.equals(FilteredValueMaintainService.CHILD_FOR_FILTER_BETWEEN, preset) && usingNativeSQL) {
                if (Objects.isNull(nsqlGenerator)) {
                    LOGGER.warn("指定的 hibernateDialect: " + hibernateDialect + ", 不受支持, 将不会使用原生SQL进行查询");
                    return presetLookupDao.lookup(preset, objs);
                }
                LOGGER.debug("使用原生SQL进行查询...");
                List<FilteredValue> filteredValues = hibernateTemplate.executeWithNativeSession(
                        session -> session.doReturningWork(connection -> {
                            try {
                                return nsqlGenerator.lookupFilteredForFilter(connection, objs);
                            } catch (Exception e) {
                                LOGGER.warn("原生SQL查询返回异常", e);
                                return null;
                            }
                        })
                );
                if (Objects.nonNull(filteredValues)) {
                    return filteredValues;
                } else {
                    LOGGER.warn("原生SQL查询返回值无效, 不使用原生SQL再次进行查询...");
                    return presetLookupDao.lookup(preset, objs);
                }
            } else {
                return presetLookupDao.lookup(preset, objs);
            }
        } catch (DaoException e) {
            throw e;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public List<FilteredValue> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws DaoException {
        try {
            if (Objects.equals(FilteredValueMaintainService.CHILD_FOR_POINT_BETWEEN, preset) && usingNativeSQL) {
                if (Objects.isNull(nsqlGenerator)) {
                    LOGGER.warn("指定的 hibernateDialect: " + hibernateDialect + ", 不受支持, 将不会使用原生SQL进行查询");
                    return presetLookupDao.lookup(preset, objs, pagingInfo);
                }
                LOGGER.debug("使用原生SQL进行查询...");
                List<FilteredValue> filteredValues = hibernateTemplate.executeWithNativeSession(
                        session -> session.doReturningWork(connection -> {
                            try {
                                return nsqlGenerator.lookupFilteredForPoint(connection, objs, pagingInfo);
                            } catch (Exception e) {
                                LOGGER.warn("原生SQL查询返回异常", e);
                                return null;
                            }
                        })
                );
                if (Objects.nonNull(filteredValues)) {
                    return filteredValues;
                } else {
                    LOGGER.warn("原生SQL查询返回值无效, 不使用原生SQL再次进行查询...");
                    return presetLookupDao.lookup(preset, objs, pagingInfo);
                }
            } else if (Objects.equals(FilteredValueMaintainService.CHILD_FOR_FILTER_BETWEEN, preset) && usingNativeSQL) {
                if (Objects.isNull(nsqlGenerator)) {
                    LOGGER.warn("指定的 hibernateDialect: " + hibernateDialect + ", 不受支持, 将不会使用原生SQL进行查询");
                    return presetLookupDao.lookup(preset, objs, pagingInfo);
                }
                LOGGER.debug("使用原生SQL进行查询...");
                List<FilteredValue> filteredValues = hibernateTemplate.executeWithNativeSession(
                        session -> session.doReturningWork(connection -> {
                            try {
                                return nsqlGenerator.lookupFilteredForFilter(connection, objs, pagingInfo);
                            } catch (Exception e) {
                                LOGGER.warn("原生SQL查询返回异常", e);
                                return null;
                            }
                        })
                );
                if (Objects.nonNull(filteredValues)) {
                    return filteredValues;
                } else {
                    LOGGER.warn("原生SQL查询返回值无效, 不使用原生SQL再次进行查询...");
                    return presetLookupDao.lookup(preset, objs, pagingInfo);
                }
            } else {
                return presetLookupDao.lookup(preset, objs, pagingInfo);
            }
        } catch (DaoException e) {
            throw e;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public int lookupCount(String preset, Object[] objs) throws DaoException {
        try {
            if (Objects.equals(FilteredValueMaintainService.CHILD_FOR_POINT_BETWEEN, preset) && usingNativeSQL) {
                if (Objects.isNull(nsqlGenerator)) {
                    LOGGER.warn("指定的 hibernateDialect: " + hibernateDialect + ", 不受支持, 将不会使用原生SQL进行查询");
                    return presetLookupDao.lookupCount(preset, objs);
                }
                LOGGER.debug("使用原生SQL进行查询...");
                Integer count = hibernateTemplate.executeWithNativeSession(
                        session -> session.doReturningWork(connection -> {
                            try {
                                return nsqlGenerator.lookupFilteredCountForPoint(connection, objs);
                            } catch (Exception e) {
                                LOGGER.warn("原生SQL查询返回异常", e);
                                return null;
                            }
                        })
                );
                if (Objects.nonNull(count)) {
                    return count;
                } else {
                    LOGGER.warn("原生SQL查询返回值无效, 不使用原生SQL再次进行查询...");
                    return presetLookupDao.lookupCount(preset, objs);
                }
            } else if (Objects.equals(FilteredValueMaintainService.CHILD_FOR_FILTER_BETWEEN, preset) && usingNativeSQL) {
                if (Objects.isNull(nsqlGenerator)) {
                    LOGGER.warn("指定的 hibernateDialect: " + hibernateDialect + ", 不受支持, 将不会使用原生SQL进行查询");
                    return presetLookupDao.lookupCount(preset, objs);
                }
                LOGGER.debug("使用原生SQL进行查询...");
                Integer count = hibernateTemplate.executeWithNativeSession(
                        session -> session.doReturningWork(connection -> {
                            try {
                                return nsqlGenerator.lookupFilteredCountForFilter(connection, objs);
                            } catch (Exception e) {
                                LOGGER.warn("原生SQL查询返回异常", e);
                                return null;
                            }
                        })
                );
                if (Objects.nonNull(count)) {
                    return count;
                } else {
                    LOGGER.warn("原生SQL查询返回值无效, 不使用原生SQL再次进行查询...");
                    return presetLookupDao.lookupCount(preset, objs);
                }
            } else {
                return presetLookupDao.lookupCount(preset, objs);
            }
        } catch (DaoException e) {
            throw e;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
