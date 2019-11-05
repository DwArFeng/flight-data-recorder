package com.dwarfeng.fdr.node.validate.test.bean.key;

import com.dwarfeng.fdr.stack.bean.key.ChannelKey;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class ChannelKeyImpl implements ChannelKey {

    private static final long serialVersionUID = -1375060132447867717L;

    @NotBlank
    private String pointName;

    @NotBlank
    private String channelName;

    public ChannelKeyImpl() {
    }

    public ChannelKeyImpl(String pointName, String channelName) {
        this.pointName = pointName;
        this.channelName = channelName;
    }

    public static ChannelKeyImpl of(String pointName, String channelName) {
        return new ChannelKeyImpl(pointName, channelName);
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
        return Objects.equals(this.getChannelName(), that.getChannelName());
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
