package com.dwarfeng.fdr.impl.handler.consumer;

import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import com.dwarfeng.fdr.impl.handler.Consumer;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.service.FilteredValueWriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FilteredValueConsumer implements Consumer<FilteredValue> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilteredValueConsumer.class);

    @Autowired
    private FilteredValueWriteService filteredValueWriteService;

    @Override
    public void consume(List<FilteredValue> elements) {
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        try {
            try {
                filteredValueWriteService.batchWrite(elements);
                return;
            } catch (Exception e) {
                LOGGER.warn("数据插入失败, 试图使用不同的策略进行插入: 逐条插入", e);
            }

            List<FilteredValue> failedList = new ArrayList<>();

            for (FilteredValue filteredValue : elements) {
                try {
                    filteredValueWriteService.write(filteredValue);
                } catch (Exception e) {
                    LOGGER.error("数据插入失败, 放弃对数据的插入: " + filteredValue, e);
                    failedList.add(filteredValue);
                }
            }

            if (!failedList.isEmpty()) {
                LOGGER.error("记录数据时发生异常, 最多 " + failedList.size() + " 个数据信息丢失");
                failedList.forEach(realtimeValue -> LOGGER.debug(realtimeValue + ""));
            }
        } finally {
            tm.stop();
            LOGGER.info("消费者处理了 " + elements.size() + " 条数据, 共用时 " + tm.getTimeMs() + " 毫秒");
        }
    }
}
