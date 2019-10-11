package com.dwarfeng.fdr.stack.bean.constraint;

import com.dwarfeng.fdr.stack.bean.Bean;
import com.dwarfeng.fdr.stack.bean.entity.Entity;
import com.dwarfeng.fdr.stack.bean.key.Key;

/**
 * 约束标记接口。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface Constraint<E extends Entity<? extends Key>> extends Bean {

}
