package com.dwarfeng.fdr.impl.handler.consumer;

import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import com.dwarfeng.fdr.impl.handler.Consumer;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.service.TriggeredValueWriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TriggeredValueConsumer implements Consumer<TriggeredValue> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggeredValueConsumer.class);

    @Autowired
    private TriggeredValueWriteService triggeredValueWriteService;

    @Override
    public void consume(List<TriggeredValue> elements) {
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        try {
            try {
                triggeredValueWriteService.batchWrite(elements);
                return;
            } catch (Exception e) {
                LOGGER.warn("数据插入失败, 试图使用不同的策略进行插入: 逐条插入", e);
            }

            List<TriggeredValue> failedList = new ArrayList<>();

            for (TriggeredValue triggeredValue : elements) {
                try {
                    triggeredValueWriteService.write(triggeredValue);
                } catch (Exception e) {
                    LOGGER.error("数据插入失败, 放弃对数据的插入: " + triggeredValue, e);
                    failedList.add(triggeredValue);
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
