package com.dwarfeng.fdr.stack.bean.relation;

import com.dwarfeng.fdr.stack.bean.key.Key;

/**
 * 表示所属的关系。
 *
 * @param <U> 主对象类型。
 * @param <V> 所属关系对象类型。
 */
public interface Belongs<U extends Key, V extends Key> extends Relation<U> {

    /**
     * 获得主对象的所属对象。
     *
     * @return 主对象的所属对象。
     */
    V getBelongs();
}
