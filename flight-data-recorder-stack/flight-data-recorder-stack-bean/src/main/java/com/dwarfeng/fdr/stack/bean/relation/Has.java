package com.dwarfeng.fdr.stack.bean.relation;

import com.dwarfeng.fdr.stack.bean.key.Key;

import java.util.Collection;

/**
 * 表示拥有的关系。
 *
 * @param <U> 主对象类型。
 * @param <V> 拥有关系对象类型。
 */
public interface Has<U extends Key, V extends Key> extends Relation<U> {

    /**
     * 获取主对象的拥有对象。
     *
     * @return 主对象的拥有对象组成的集合。
     */
    Collection<V> getHas();
}
