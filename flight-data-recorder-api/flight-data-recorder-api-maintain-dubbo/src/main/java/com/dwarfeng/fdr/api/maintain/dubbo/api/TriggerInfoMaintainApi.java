package com.dwarfeng.fdr.api.maintain.dubbo.api;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;

public interface TriggerInfoMaintainApi extends EntityCrudApi<GuidKey, TriggerInfo> {

    PagedData<TriggerInfo> getTriggerInfos(GuidKey pointGuid, LookupPagingInfo lookupPagingInfo) throws ServiceException;

}
