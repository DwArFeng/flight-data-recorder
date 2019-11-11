package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.Bean;
import com.dwarfeng.fdr.stack.bean.key.Key;

/**
 * Entity 接口。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface Entity<K extends Key> extends Bean {

    /**
     * 获取实体的主键。
     *
     * @return 实体的主键。
     */
    K getKey();

}
