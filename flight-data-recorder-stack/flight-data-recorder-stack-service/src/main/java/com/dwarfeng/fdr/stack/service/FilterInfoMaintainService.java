package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;

/**
 * 过滤器信息维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface FilterInfoMaintainService extends EntityCrudService<UuidKey, FilterInfo> {

    PagedData<FilterInfo> getFilterInfos(UuidKey pointUuid, LookupPagingInfo lookupPagingInfo) throws ServiceException;

}
