package com.dwarfeng.fdr.impl.persistence.hibernate.dao;

import com.dwarfeng.fdr.impl.persistence.hibernate.bean.entity.PointImpl;
import com.dwarfeng.fdr.impl.persistence.hibernate.bean.key.UuidKeyImpl;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyseAdvisor;
import com.dwarfeng.fdr.stack.bean.constraint.PointConstraint;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.dao.PointDao;
import com.dwarfeng.fdr.stack.exception.DaoException;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
@TimeAnalyseAdvisor.TimeAnalyse
@Validated
public class PointDaoImpl extends AbstractBaseDao<UuidKey, Point, PointConstraint> implements PointDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Autowired
    private Mapper mapper;

    @Override
    public Point get(@NotNull UuidKey key) throws DaoException {
        UuidKeyImpl keyImpl = mapper.map(key, UuidKeyImpl.class);
        try {
            return hibernateTemplate.get(PointImpl.class, keyImpl);
        } catch (Exception e) {
            throw new DaoException("无法读取指定的数据点: " + keyImpl.toString(), e);
        }
    }

    @Override
    public UuidKey insert(Point element) throws DaoException {
        PointImpl pointImpl = mapper.map(element, PointImpl.class);
        try {
            hibernateTemplate.save(pointImpl);
            hibernateTemplate.flush();
            hibernateTemplate.clear();
            return pointImpl.getKey();
        } catch (Exception e) {
            throw new DaoException("无法插入指定的数据点: " + pointImpl.toString(), e);
        }
    }

    @Override
    public UuidKey update(Point element) throws DaoException {
        PointImpl pointImpl = mapper.map(element, PointImpl.class);
        try {
            hibernateTemplate.update(pointImpl);
            hibernateTemplate.flush();
            hibernateTemplate.clear();
            return pointImpl.getKey();
        } catch (Exception e) {
            throw new DaoException("无法更新指定的数据点: " + pointImpl.toString(), e);
        }
    }

    @Override
    public void delete(UuidKey key) throws DaoException {
        UuidKeyImpl keyImpl = mapper.map(key, UuidKeyImpl.class);
        try {
            hibernateTemplate.delete(hibernateTemplate.get(PointImpl.class, keyImpl));
            hibernateTemplate.flush();
            hibernateTemplate.clear();
        } catch (Exception e) {
            throw new DaoException("无法删除指定的数据点: " + keyImpl.toString(), e);
        }
    }

    @Override
    public List<Point> select(PointConstraint constraint, LookupPagingInfo lookupPagingInfo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int selectCount(PointConstraint constraint) {
        // TODO Auto-generated method stub
        return 0;
    }

}
