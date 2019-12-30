package com.dwarfeng.fdr.api.maintain.dubbo.api;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;

public interface CategoryMaintainApi extends EntityCrudApi<GuidKey, Category> {

    PagedData<Category> getChilds(GuidKey guidKey, LookupPagingInfo lookupPagingInfo) throws ServiceException;
}
