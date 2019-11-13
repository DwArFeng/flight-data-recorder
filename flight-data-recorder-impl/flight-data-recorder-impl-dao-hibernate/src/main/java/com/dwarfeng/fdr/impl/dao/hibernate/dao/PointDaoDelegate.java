package com.dwarfeng.fdr.impl.dao.hibernate.dao;

import com.dwarfeng.fdr.impl.dao.hibernate.bean.entity.PointImpl;
import com.dwarfeng.fdr.impl.dao.hibernate.bean.key.UuidKeyImpl;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.constraint.PointConstraint;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.DaoException;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
@Component
public class PointDaoDelegate extends AbstractBaseDaoDelegate<UuidKey, Point, PointConstraint> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointDaoDelegate.class);

    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Autowired
    private Mapper mapper;

    @TimeAnalyse
    @Override
    public Point get(UuidKey key) throws DaoException {
        try {
            UuidKeyImpl keyImpl = mapper.map(key, UuidKeyImpl.class);
            return hibernateTemplate.get(PointImpl.class, keyImpl);
        } catch (Exception e) {
            throw new DaoException("无法读取指定的数据点: " + key.toString(), e);
        }
    }

    @TimeAnalyse
    @Override
    public UuidKey insert(Point point) throws DaoException {
        try {
            PointImpl pointImpl = mapper.map(point, PointImpl.class);
            hibernateTemplate.save(pointImpl);
            hibernateTemplate.flush();
            hibernateTemplate.clear();
            return pointImpl.getKey();
        } catch (Exception e) {
            throw new DaoException("无法插入指定的数据点: " + point.toString(), e);
        }
    }

    @TimeAnalyse
    @Override
    public UuidKey update(Point point) throws DaoException {
        try {
            UuidKeyImpl keyImpl = mapper.map(point.getKey(), UuidKeyImpl.class);
            PointImpl pointImpl = Optional.ofNullable(hibernateTemplate.get(PointImpl.class, keyImpl))
                    .orElseThrow(() -> new NullPointerException("数据点 " + point.toString() + " 不存在"));
            mapper.map(point, pointImpl);
            hibernateTemplate.update(pointImpl);
            hibernateTemplate.flush();
            hibernateTemplate.clear();
            return pointImpl.getKey();
        } catch (Exception e) {
            throw new DaoException("无法更新指定的数据点: " + point.toString(), e);
        }
    }

    @TimeAnalyse
    @Override
    public void delete(UuidKey key) throws DaoException {
        try {
            UuidKeyImpl keyImpl = mapper.map(key, UuidKeyImpl.class);
            hibernateTemplate.delete(hibernateTemplate.get(PointImpl.class, keyImpl));
            hibernateTemplate.flush();
            hibernateTemplate.clear();
        } catch (Exception e) {
            throw new DaoException("无法删除指定的数据点: " + key.toString(), e);
        }
    }

    @TimeAnalyse
    @Override
    public List<Point> select(PointConstraint constraint, LookupPagingInfo lookupPagingInfo) throws DaoException {
        // TODO Auto-generated method stub
        return null;
    }
}
