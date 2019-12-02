package com.dwarfeng.fdr.api.maintain.dubbo.api;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;

public interface FilterInfoMaintainApi extends EntityCrudApi<UuidKey, FilterInfo> {

    PagedData<FilterInfo> getFilterInfos(UuidKey pointUuid, LookupPagingInfo lookupPagingInfo) throws ServiceException;

}
