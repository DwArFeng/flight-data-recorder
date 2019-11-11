package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 分类。
 */
public interface Category extends Entity<UuidKey> {

    /**
     * 获取文件夹的名称。
     *
     * @return 文件夹的名称。
     */
    String getName();

    /**
     * 获取文件夹的描述。
     *
     * @return 文件夹的描述。
     */
    String getRemark();
}
