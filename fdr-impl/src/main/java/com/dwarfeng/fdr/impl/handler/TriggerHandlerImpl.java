package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.entity.TriggerSupport;
import com.dwarfeng.fdr.stack.exception.TriggerException;
import com.dwarfeng.fdr.stack.exception.UnsupportedTriggerTypeException;
import com.dwarfeng.fdr.stack.handler.Trigger;
import com.dwarfeng.fdr.stack.handler.TriggerHandler;
import com.dwarfeng.fdr.stack.service.TriggerSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class TriggerHandlerImpl implements TriggerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggerHandlerImpl.class);

    @Autowired
    private List<TriggerMaker> triggerMakers;
    @Autowired
    private TriggerSupportMaintainService service;

    @PostConstruct
    public void init() {
        for (TriggerMaker triggerMaker : triggerMakers) {
            try {
                service.insertIfNotExists(
                        new TriggerSupport(
                                new StringIdKey(triggerMaker.provideType()),
                                triggerMaker.provideLabel(),
                                triggerMaker.provideDescription(),
                                triggerMaker.provideExampleContent()
                        )
                );
            } catch (Exception e) {
                LOGGER.warn("未能向 TriggerSupportMaintainService 中确认或添加过滤器信息", e);
            }
        }
    }

    @Override
    public Trigger make(TriggerInfo triggerInfo) throws TriggerException {
        try {
            // 生成触发器。
            LOGGER.debug("通过触发器信息构建新的的触发器...");
            TriggerMaker triggerMaker = triggerMakers.stream().filter(maker -> maker.supportType(triggerInfo.getType()))
                    .findFirst().orElseThrow(() -> new UnsupportedTriggerTypeException(triggerInfo.getType()));
            Trigger trigger = triggerMaker.makeTrigger(triggerInfo);
            LOGGER.debug("触发器构建成功!");
            LOGGER.debug("触发器: " + trigger);
            return trigger;
        } catch (TriggerException e) {
            throw e;
        } catch (Exception e) {
            throw new TriggerException(e);
        }
    }
}
