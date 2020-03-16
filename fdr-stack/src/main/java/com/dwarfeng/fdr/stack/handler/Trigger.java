package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.exception.TriggerException;

/**
 * 触发器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface Trigger {

    /**
     * 测试一个数据是否能通过触发器。
     *
     * <p> 如果指定的数据满足触发条件，则返回被触发的数据值;否则返回 null。</>
     *
     * @param dataInfo 指定的数据。
     * @return 被触发的数据值，其主键为 null 即可。
     * @throws TriggerException 触发器异常。
     */
    TriggeredValue test(DataInfo dataInfo) throws TriggerException;
}
