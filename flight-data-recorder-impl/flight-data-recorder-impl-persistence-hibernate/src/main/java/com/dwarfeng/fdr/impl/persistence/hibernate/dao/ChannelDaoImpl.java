package com.dwarfeng.fdr.impl.persistence.hibernate.dao;

import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.dwarfeng.fdr.impl.persistence.hibernate.bean.entity.ChannelImpl;
import com.dwarfeng.fdr.impl.persistence.hibernate.bean.key.ChannelKeyImpl;
import com.dwarfeng.fdr.stack.bean.constraint.ChannelConstraint;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Channel;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.ChannelKey;
import com.dwarfeng.fdr.stack.bean.key.NameKey;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.dao.ChannelDao;
import com.dwarfeng.fdr.stack.exception.DaoException;

@Repository
public class ChannelDaoImpl implements ChannelDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired
	private Mapper mapper;

	@Override
	public Channel get(ChannelKey key) throws DaoException {
		ChannelKeyImpl keyImpl = mapper.map(key, ChannelKeyImpl.class);
		try {
			return hibernateTemplate.get(ChannelImpl.class, keyImpl);
		} catch (Exception e) {
			throw new DaoException("无法读取指定的通道: " + keyImpl.toString(), e);
		}
	}

	@Override
	public ChannelKey insert(Channel element) throws DaoException {
		ChannelImpl channelImpl = mapper.map(element, ChannelImpl.class);
		try {
			hibernateTemplate.save(channelImpl);
			hibernateTemplate.flush();
			hibernateTemplate.clear();
			return channelImpl.getKey();
		} catch (Exception e) {
			throw new DaoException("无法插入指定的通道: " + element.toString(), e);
		}
	}

	@Override
	public ChannelKey update(Channel element) throws DaoException {
		ChannelImpl channelImpl = mapper.map(element, ChannelImpl.class);
		try {
			hibernateTemplate.update(channelImpl);
			hibernateTemplate.flush();
			hibernateTemplate.clear();
			return channelImpl.getKey();
		} catch (Exception e) {
			throw new DaoException("无法更新指定的通道: " + element.toString(), e);
		}
	}

	@Override
	public void delete(ChannelKey key) throws DaoException {
		ChannelKeyImpl keyImpl = mapper.map(key, ChannelKeyImpl.class);
		try {
			hibernateTemplate.delete(hibernateTemplate.get(ChannelImpl.class, keyImpl));
			hibernateTemplate.flush();
			hibernateTemplate.clear();
		} catch (Exception e) {
			throw new DaoException("无法删除指定的通道: " + keyImpl.toString(), e);
		}
	}

	@Override
	public List<Channel> select(ChannelConstraint constraint, LookupPagingInfo lookupPagingInfo) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectCount(ChannelConstraint constraint) throws DaoException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addValue(NameKey channelKey, UuidKey valueKey) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeValue(NameKey channelKey, UuidKey valueKey) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearValue(NameKey channelKey) {
		// TODO Auto-generated method stub

	}

	@Override
	public Point getPoint(NameKey channelKey) {
		// TODO Auto-generated method stub
		return null;
	}

}
