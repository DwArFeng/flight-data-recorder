package com.dwarfeng.fdr.stack.service;

import java.util.Collection;

import com.dwarfeng.fdr.stack.bean.entity.Channel;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.entity.TriggerSetting;
import com.dwarfeng.fdr.stack.bean.key.ChannelKey;
import com.dwarfeng.fdr.stack.bean.key.NameKey;

/**
 * 维护服务。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface MaintainService extends Service {

	public NameKey addPoint(Point point);

	public void deletePoint(NameKey nameKey);

	public ChannelKey addChannel(Point point, Channel channel);

	public void deleteChannel(ChannelKey channelKey);

	public NameKey addTriggeSetting(TriggerSetting triggerSetting);

	public void deleteTriggerSetting(NameKey triggerSettingKey);

	public void applyTriggerSetting(ChannelKey channelKey, Collection<NameKey> triggerSettingKeys);

	public void unapplyTriggerSetting(ChannelKey channelKey, Collection<NameKey> triggerSettingKeys);

}
