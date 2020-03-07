package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.exception.FilterException;

/**
 * 过滤器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface Filter {

    /**
     * 测试一个数据是否能通过过滤器。
     *
     * <p> 如果指定的数据不能通过过滤器，则返回被过滤的数据值;否则返回 null。</>
     *
     * @param dataInfo 指定的数据。
     * @return 被过滤的数据值，其主键为 null 即可。
     * @throws FilterException 过滤器异常。
     */
    FilteredValue test(DataInfo dataInfo) throws FilterException;
}
