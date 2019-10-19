package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.Channel;
import com.dwarfeng.fdr.stack.bean.key.ChannelKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;

/**
 * 维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface ChannelMaintainService extends Service {

    /**
     * 获取指定的键是否存在。
     *
     * @param key 指定的键。
     * @return 指定的键是否存在。
     * @throws ServiceException 服务异常。
     */
    public boolean exists(ChannelKey key) throws ServiceException;

    /**
     * 获得指定的键对应的通道。
     *
     * @param key 指定的键。
     * @return 指定的键对应的通道。
     * @throws ServiceException 服务异常。
     */
    public Channel get(ChannelKey key) throws ServiceException;

    /**
     * 添加通道。
     *
     * @param point 指定的通道。
     * @throws ServiceException 服务异常。
     */
    public void add(Channel channel) throws ServiceException;

    /**
     * 移除通道。
     *
     * @param key 指定的通道对应的键。
     * @throws ServiceException 服务异常。
     */
    public void remove(ChannelKey key) throws ServiceException;

}
