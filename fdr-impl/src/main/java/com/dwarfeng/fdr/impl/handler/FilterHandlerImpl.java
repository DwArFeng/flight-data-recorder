package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilterSupport;
import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.exception.UnsupportedFilterTypeException;
import com.dwarfeng.fdr.stack.handler.Filter;
import com.dwarfeng.fdr.stack.handler.FilterHandler;
import com.dwarfeng.fdr.stack.service.FilterSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class FilterHandlerImpl implements FilterHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterHandlerImpl.class);

    @Autowired
    private List<FilterMaker> filterMakers;
    @Autowired
    private FilterSupportMaintainService service;

    @PostConstruct
    public void init() {
        for (FilterMaker filterMaker : filterMakers) {
            try {
                service.insertIfNotExists(
                        new FilterSupport(
                                new StringIdKey(filterMaker.provideType()),
                                filterMaker.provideLabel(),
                                filterMaker.provideDescription(),
                                filterMaker.provideExampleContent()
                        )
                );
            } catch (Exception e) {
                LOGGER.warn("未能向 FilterSupportMaintainService 中确认或添加过滤器信息", e);
            }
        }
    }

    @Override
    public Filter make(FilterInfo filterInfo) throws FilterException {
        try {
            // 生成过滤器。
            LOGGER.debug("通过过滤器信息构建新的的过滤器...");
            FilterMaker filterMaker = filterMakers.stream().filter(maker -> maker.supportType(filterInfo.getType()))
                    .findFirst().orElseThrow(() -> new UnsupportedFilterTypeException(filterInfo.getType()));
            Filter filter = filterMaker.makeFilter(filterInfo);
            LOGGER.debug("过滤器构建成功!");
            LOGGER.debug("过滤器: " + filter);
            return filter;
        } catch (FilterException e) {
            throw e;
        } catch (Exception e) {
            throw new FilterException(e);
        }
    }
}
