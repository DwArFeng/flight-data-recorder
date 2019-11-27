package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.DaoException;

import java.util.List;

/**
 * 数据点数据访问层。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface PointDao extends BaseDao<UuidKey, Point> {

    List<Point> getPoints(UuidKey categoryUuidKey, LookupPagingInfo lookupPagingInfo) throws DaoException;

    long getChildCount(UuidKey categoryUuidKey) throws DaoException;

}
