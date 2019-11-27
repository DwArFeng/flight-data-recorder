package com.dwarfeng.fdr.api.maintain.dubbo.api;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;

public interface PointMaintainApi extends EntityCrudApi<UuidKey, Point> {

    PagedData<Point> getPoints(UuidKey categoryUuid, LookupPagingInfo lookupPagingInfo) throws ServiceException;

}
