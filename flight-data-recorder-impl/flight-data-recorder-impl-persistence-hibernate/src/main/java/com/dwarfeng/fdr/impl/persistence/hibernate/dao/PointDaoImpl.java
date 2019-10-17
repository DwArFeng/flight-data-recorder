package com.dwarfeng.fdr.impl.persistence.hibernate.dao;

import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.dwarfeng.fdr.impl.persistence.hibernate.bean.entity.PointImpl;
import com.dwarfeng.fdr.impl.persistence.hibernate.bean.key.NameKeyImpl;
import com.dwarfeng.fdr.stack.bean.constraint.PointConstraint;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.NameKey;
import com.dwarfeng.fdr.stack.dao.PointDao;
import com.dwarfeng.fdr.stack.exception.DaoException;

@Repository
public class PointDaoImpl implements PointDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired
	private Mapper mapper;

	@Override
	public Point get(NameKey key) throws DaoException {
		NameKeyImpl keyImpl = mapper.map(key, NameKeyImpl.class);
		try {
			return hibernateTemplate.get(PointImpl.class, keyImpl);
		} catch (Exception e) {
			throw new DaoException("无法读取指定的数据点: " + keyImpl.toString(), e);
		}
	}

	@Override
	public NameKey insert(Point element) throws DaoException {
		PointImpl pointImpl = mapper.map(element, PointImpl.class);
		try {
			hibernateTemplate.save(pointImpl);
			hibernateTemplate.flush();
			hibernateTemplate.clear();
			return pointImpl.getKey();
		} catch (Exception e) {
			throw new DaoException("无法插入指定的数据点: " + element.toString(), e);
		}
	}

	@Override
	public NameKey update(Point element) throws DaoException {
		PointImpl pointImpl = mapper.map(element, PointImpl.class);
		try {
			hibernateTemplate.update(pointImpl);
			hibernateTemplate.flush();
			hibernateTemplate.clear();
			return pointImpl.getKey();
		} catch (Exception e) {
			throw new DaoException("无法更新指定的数据点: " + element.toString(), e);
		}
	}

	@Override
	public void delete(NameKey key) throws DaoException {
		NameKeyImpl keyImpl = mapper.map(key, NameKeyImpl.class);
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

	@Override
	public void addChannel(NameKey pointKey, NameKey channelKey) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeChannel(NameKey pointKey, NameKey channelKey) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearChannel(NameKey pointKey) {
		// TODO Auto-generated method stub

	}

}