package com.dwarfeng.fdr.impl.cache.redis.bean.entity;

import com.dwarfeng.fdr.stack.bean.entity.Channel;
import com.dwarfeng.fdr.stack.bean.key.ChannelKey;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class ChannelImpl implements Channel {

    @Override
    public boolean isDefaultChannel() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getRemark() {
        return null;
    }

    @Override
    public ChannelKey getKey() {
        return null;
    }
}
