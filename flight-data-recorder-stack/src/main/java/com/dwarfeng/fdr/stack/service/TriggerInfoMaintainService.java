package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;

/**
 * 过滤器信息维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggerInfoMaintainService extends EntityCrudService<GuidKey, TriggerInfo> {

    PagedData<TriggerInfo> getTriggerInfos(GuidKey pointGuid, LookupPagingInfo lookupPagingInfo) throws ServiceException;

}
