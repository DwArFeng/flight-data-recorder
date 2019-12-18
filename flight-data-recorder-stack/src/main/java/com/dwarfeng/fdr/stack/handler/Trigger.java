package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;

import java.util.function.Consumer;

/**
 * 触发器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface Trigger {

    /**
     * 测试一个数据是否能被触发。
     *
     * <p> 如果指定的数据能被触发，则执行指定的消费者进行回调。</>
     *
     * @param dataInfo 指定的数据。
     * @param consumer 数据不通过时进行的回调。
     */
    void test(DataInfo dataInfo, Consumer<? super TriggeredValue> consumer);
}
