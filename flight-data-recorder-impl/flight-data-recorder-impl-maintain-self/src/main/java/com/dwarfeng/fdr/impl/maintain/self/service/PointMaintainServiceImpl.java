package com.dwarfeng.fdr.impl.maintain.self.service;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyseAdvisor;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.NameKey;
import com.dwarfeng.fdr.stack.cache.PointEntityCache;
import com.dwarfeng.fdr.stack.dao.PointDao;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service("pointMaintainService")
public class PointMaintainServiceImpl implements PointMaintainService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointMaintainServiceImpl.class);

    @Autowired
    private PointDao pointDao;
    @Autowired
    private PointEntityCache pointEntityCache;
    @Value("${timeout.entity.point}")
    private long pointEntityTimeout;


    @Override
    @Transactional(readOnly = true)
    @TimeAnalyseAdvisor.TimeAnalyse
    public boolean exists(NameKey key) throws ServiceException {
        try {
            //如果缓存中包含指定的数据点，则返回true
            if (pointEntityCache.exists(key)) return true;
            //如果缓存中不存在指定的数据点，则查询数据访问层中是否存在。
            return pointDao.exists(key);
        } catch (Exception e) {
            throw new ServiceException("无法查询数据点是否存在: " + key.toString(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @TimeAnalyseAdvisor.TimeAnalyse
    public Point get(NameKey key) throws ServiceException {
        try {
            //如果缓存中包含指定的数据点，则返回指定的数据点。
            if (pointEntityCache.exists(key)) return pointEntityCache.get(key);
            //如果缓存中没有数据点。
            //从数据持久层返回数据点。
            Point point = pointDao.get(key);
            //将获取的数据点推送到缓存中，并设置超时时间。
            pointEntityCache.push(key, point, pointEntityTimeout, TimeUnit.SECONDS);
            //返回数据点。
            return point;
        } catch (Exception e) {
            throw new ServiceException("无法获取数据点: " + key.toString(), e);
        }
    }

    @Override
    @Transactional
    @TimeAnalyseAdvisor.TimeAnalyse
    public void add(Point point) throws ServiceException {
        try {
            //数据点插入到数据访问层中。
            pointDao.insert(point);
            //将数据点信息推送到缓存中。
            pointEntityCache.push(point.getKey(), point, pointEntityTimeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new ServiceException("无法添加数据点: " + point.toString(), e);
        }
    }

    @Override
    @Transactional
    @TimeAnalyseAdvisor.TimeAnalyse
    public void remove(NameKey key) throws ServiceException {
        try {
            //将数据点从缓存中移除。
            pointEntityCache.delete(key);
            //将数据点从数据访问层移除。
            pointDao.delete(key);
        } catch (Exception e) {
            throw new ServiceException("无法移除数据点: " + key.toString(), e);
        }
    }

}
