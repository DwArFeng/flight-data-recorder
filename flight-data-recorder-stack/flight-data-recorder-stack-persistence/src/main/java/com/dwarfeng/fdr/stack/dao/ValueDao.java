package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.constraint.ValueConstraint;
import com.dwarfeng.fdr.stack.bean.entity.Value;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 数据值数据访问层。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface ValueDao extends BaseDao<UuidKey, Value, ValueConstraint> {

    /**
     * 获取指定数据值的前一个数据值。
     *
     * @param valueKey 指定数据值键。
     * @return 指定数据值的前一个数据值。
     */
    Value getPreviousValue(UuidKey valueKey);

    /**
     * 获取指定数据值的下一个数据值。
     *
     * @param valueKey 指定数据值键。
     * @return 指定数据值的下一个数据值。
     */
    Value getNextValue(UuidKey valueKey);

}
