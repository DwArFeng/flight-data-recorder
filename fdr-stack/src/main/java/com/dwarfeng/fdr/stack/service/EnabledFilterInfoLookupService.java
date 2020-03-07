package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.dto.EnabledFilterMeta;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 有效的过滤器信息查询服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface EnabledFilterInfoLookupService extends Service {

    /**
     * 确认序列版本是否匹配。
     *
     * @param pointKey      指定的数据点。
     * @param serialVersion 待确认的数据点对应的版本。
     * @return 数据点现有的序列版本是否与对应的版本匹配。
     * @throws ServiceException 服务异常。
     */
    boolean checkSerialVersion(LongIdKey pointKey, long serialVersion) throws ServiceException;

    /**
     * 获取指定的数据点所属的有效的过滤器信息。
     *
     * @param pointKey 指定的数据点。
     * @return 指定的数据点所属的有效的过滤器信息。
     * @throws ServiceException 服务异常。
     */
    EnabledFilterMeta getEnabledFilterInfos(LongIdKey pointKey) throws ServiceException;
}
