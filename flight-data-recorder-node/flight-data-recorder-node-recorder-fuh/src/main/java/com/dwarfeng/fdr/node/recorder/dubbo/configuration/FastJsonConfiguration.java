package com.dwarfeng.fdr.node.recorder.dubbo.configuration;

import com.alibaba.fastjson.parser.ParserConfig;
import com.dwarfeng.fdr.impl.handler.fnt.preset.BlankConfig;
import com.dwarfeng.fdr.impl.handler.fnt.preset.IntegerFilter;
import com.dwarfeng.fdr.impl.handler.fnt.preset.IntegerRangeTrigger;
import com.dwarfeng.fdr.impl.handler.fnt.struct.StructuredFilterInfo;
import com.dwarfeng.fdr.impl.handler.fnt.struct.StructuredTriggerInfo;
import com.dwarfeng.fdr.sdk.bean.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
@Configuration
public class FastJsonConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastJsonConfiguration.class);

    public FastJsonConfiguration() {
        LOGGER.info("正在配置 FastJson autotype 白名单");
        //实体对象。
        ParserConfig.getGlobalInstance().addAccept(FastJsonCategory.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonPoint.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonFilterInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonTriggerInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonFilteredValue.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonPersistenceValue.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonRealtimeValue.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonTriggeredValue.class.getCanonicalName());
        //过滤器与触发器结构化对象。
        ParserConfig.getGlobalInstance().addAccept(StructuredFilterInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(StructuredTriggerInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(BlankConfig.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(IntegerFilter.Config.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(IntegerRangeTrigger.Config.class.getCanonicalName());
        LOGGER.debug("FastJson autotype 白名单配置完毕");
    }
}
