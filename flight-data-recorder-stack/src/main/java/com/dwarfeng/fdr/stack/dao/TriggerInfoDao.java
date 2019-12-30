package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.DaoException;

import java.util.List;

/**
 * 过滤器信息数据访问层。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggerInfoDao extends BaseDao<GuidKey, TriggerInfo> {

    List<TriggerInfo> getTriggerInfos(GuidKey pointGuidKey, LookupPagingInfo lookupPagingInfo) throws DaoException;

    long getTriggerInfoCount(GuidKey pointGuidKey) throws DaoException;

}
