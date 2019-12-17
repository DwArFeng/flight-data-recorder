package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;

/**
 * 分类维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface CategoryMaintainService extends EntityCrudService<UuidKey, Category> {

    PagedData<Category> getChilds(UuidKey uuidKey, LookupPagingInfo lookupPagingInfo) throws ServiceException;

}
