package com.dwarfeng.fdr.impl.cache.redis.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.key.ChannelKey;

import java.util.Objects;

public class ChannelKeyImpl implements ChannelKey {

    private static final long serialVersionUID = -1375060132447867717L;

    public static ChannelKeyImpl of(String pointName, String channelName) {
        return new ChannelKeyImpl(pointName, channelName);
    }

    @JSONField(name = "point_name", ordinal = 1)
    private String pointName;

    @JSONField(name = "channel_name", ordinal = 2)
    private String channelName;

    public ChannelKeyImpl() {
    }

    public ChannelKeyImpl(String pointName, String channelName) {
        this.pointName = pointName;
        this.channelName = channelName;
    }

    @Override
    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    @Override
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (Objects.isNull(o)) return false;
        if (!(o instanceof ChannelKey)) return false;

        ChannelKey that = (ChannelKey) o;

        if (!Objects.equals(this.getPointName(), that.getPointName())) return false;
        if (!Objects.equals(this.getChannelName(), that.getChannelName())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getPointName() != null ? getPointName().hashCode() : 0;
        result = 31 * result + (getChannelName() != null ? getChannelName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChannelKeyImpl [pointName=" + pointName + ", channelName=" + channelName + "]";
    }

}
