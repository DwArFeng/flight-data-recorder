package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.Channel;
import com.dwarfeng.fdr.stack.bean.entity.TriggerSetting;
import com.dwarfeng.fdr.stack.bean.key.ChannelKey;
import com.dwarfeng.fdr.stack.bean.key.NameKey;

/**
 * 触发器设置缓存。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggerSettingEntityCache extends BaseCache<NameKey, TriggerSetting> {
}
