package com.dwarfeng.fdr.stack.bean.relation;

import com.dwarfeng.fdr.stack.bean.Bean;
import com.dwarfeng.fdr.stack.bean.key.Key;

/**
 * 关系接口。
 *
 * @param <U> 关系的主对象。
 */
public interface Relation<U extends Key> extends Bean {

    /**
     * 获得关系的主对象。
     *
     * @return 关系的主对象。
     */
    U getIdentity();
}
