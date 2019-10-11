package com.dwarfeng.fdr.impl.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.dwarfeng.fdr.stack.bean.constraint.PointConstraint;
import com.dwarfeng.fdr.stack.bean.dto.PagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.NameKey;
import com.dwarfeng.fdr.stack.dao.PointDao;

@Repository
public class PointDaoImpl implements PointDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired(required = false)
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public Point get(NameKey key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NameKey insert(Point element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NameKey update(Point element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(NameKey key) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Point> select(PointConstraint constraint, PagingInfo pagingInfo) {
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
