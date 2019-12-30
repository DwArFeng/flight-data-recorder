package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;

/**
 * 数据点维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface PointMaintainService extends EntityCrudService<GuidKey, Point> {

    PagedData<Point> getPoints(GuidKey categoryGuid, LookupPagingInfo lookupPagingInfo) throws ServiceException;

}
