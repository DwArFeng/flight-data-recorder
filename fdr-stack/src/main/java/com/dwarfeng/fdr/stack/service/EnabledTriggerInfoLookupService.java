package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 有效的过滤器信息查询服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface EnabledTriggerInfoLookupService extends Service {

    /**
     * 获取指定的数据点所属的有效的触发器信息。
     *
     * @param pointKey 指定的数据点。
     * @return 指定的数据点所属的有效的触发器信息。
     * @throws ServiceException 服务异常。
     */
    List<TriggerInfo> getEnabledTriggerInfos(LongIdKey pointKey) throws ServiceException;
}
