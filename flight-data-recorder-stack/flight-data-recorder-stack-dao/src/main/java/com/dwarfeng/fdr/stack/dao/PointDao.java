package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.constraint.PointConstraint;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 数据点数据访问层。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface PointDao extends BaseDao<UuidKey, Point, PointConstraint> {


}
