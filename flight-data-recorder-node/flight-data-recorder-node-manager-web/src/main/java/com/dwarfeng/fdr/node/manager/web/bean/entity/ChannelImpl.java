package com.dwarfeng.fdr.node.manager.web.bean.entity;

import com.dwarfeng.fdr.node.manager.web.bean.key.ChannelKeyImpl;
import com.dwarfeng.fdr.stack.bean.entity.Channel;

public class ChannelImpl implements Channel {

    private static final long serialVersionUID = 2637696850814900093L;

    private ChannelKeyImpl key;

    private boolean defaultChannel;

    private boolean enabled;

    private String remark;

    public ChannelImpl() {
    }

    public ChannelImpl(ChannelKeyImpl key, boolean defaultChannel, boolean enabled, String remark) {
        this.key = key;
        this.defaultChannel = defaultChannel;
        this.enabled = enabled;
        this.remark = remark;
    }

    @Override
    public ChannelKeyImpl getKey() {
        return key;
    }

    public void setKey(ChannelKeyImpl key) {
        this.key = key;
    }

    @Override
    public boolean isDefaultChannel() {
        return defaultChannel;
    }

    public void setDefaultChannel(boolean defaultChannel) {
        this.defaultChannel = defaultChannel;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ChannelImpl{" +
                "key=" + key +
                ", defaultChannel=" + defaultChannel +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                '}';
    }
}
