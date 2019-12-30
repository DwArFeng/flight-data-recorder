package com.dwarfeng.fdr.api.maintain.dubbo.api;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;

public interface FilterInfoMaintainApi extends EntityCrudApi<GuidKey, FilterInfo> {

    PagedData<FilterInfo> getFilterInfos(GuidKey pointGuid, LookupPagingInfo lookupPagingInfo) throws ServiceException;

}
