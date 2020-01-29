package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.CrudService;

/**
 * 实时数据维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface RealtimeValueMaintainService extends CrudService<LongIdKey, RealtimeValue> {

}
