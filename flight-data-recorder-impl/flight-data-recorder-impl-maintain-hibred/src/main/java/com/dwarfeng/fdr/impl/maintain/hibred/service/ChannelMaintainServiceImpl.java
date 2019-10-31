package com.dwarfeng.fdr.impl.maintain.hibred.service;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyseAdvisor;
import com.dwarfeng.fdr.stack.bean.entity.Channel;
import com.dwarfeng.fdr.stack.bean.key.ChannelKey;
import com.dwarfeng.fdr.stack.cache.ChannelEntityCache;
import com.dwarfeng.fdr.stack.dao.ChannelDao;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.ChannelMaintainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service("channelMaintainService")
public class ChannelMaintainServiceImpl implements ChannelMaintainService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelMaintainServiceImpl.class);

    @Autowired
    private ChannelDao channelDao;
    @Autowired
    private ChannelEntityCache channelEntityCache;
    @Value("${timeout.entity.channel}")
    private long channelEntityTimeout;

    @Override
    @Transactional(readOnly = true)
    @TimeAnalyseAdvisor.TimeAnalyse
    public boolean exists(ChannelKey key) throws ServiceException {
        try {
            if (channelEntityCache.exists(key)) return true;
            return channelDao.exists(key);
        } catch (Exception e) {
            throw new ServiceException("无法查询通道是否存在: " + key.toString(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @TimeAnalyseAdvisor.TimeAnalyse
    public Channel get(ChannelKey key) throws ServiceException {
        try {
            if (channelEntityCache.exists(key)) return channelEntityCache.get(key);
            Channel channel = channelDao.get(key);
            channelEntityCache.push(key, channel, channelEntityTimeout, TimeUnit.SECONDS);
            return channel;
        } catch (Exception e) {
            throw new ServiceException("无法获取通道是否存在: " + key.toString(), e);
        }
    }

    @Override
    @Transactional
    @TimeAnalyseAdvisor.TimeAnalyse
    public void add(Channel channel) throws ServiceException {
        try {
            channelDao.insert(channel);
            channelEntityCache.push(channel.getKey(), channel, channelEntityTimeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new ServiceException("无法添加通道: " + channel.toString(), e);
        }
    }

    @Override
    @Transactional
    @TimeAnalyseAdvisor.TimeAnalyse
    public void remove(ChannelKey key) throws ServiceException {
        try {
            channelEntityCache.delete(key);
            channelDao.delete(key);
        } catch (Exception e) {
            throw new ServiceException("无法移除通道: " + key.toString(), e);
        }
    }
}
