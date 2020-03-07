package com.dwarfeng.fdr.impl.configuration;

import com.dwarfeng.fdr.impl.handler.FilterHandlerImpl;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FilterConfiguration {

    @Bean
    public Map<LongIdKey, FilterHandlerImpl.FilterMetaData> longIdKeyFilterMetaDataMap() {
        return new HashMap<>();
    }
}
