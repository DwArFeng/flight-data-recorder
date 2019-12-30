package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.DaoException;

import java.util.List;

/**
 * 分类数据访问层。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface CategoryDao extends BaseDao<GuidKey, Category> {

    List<Category> getChilds(GuidKey guidKey, LookupPagingInfo lookupPagingInfo) throws DaoException;

    long getChildCount(GuidKey guidKey) throws DaoException;
}
