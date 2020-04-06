package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.exception.UnsupportedFilterTypeException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.fdr.stack.handler.MapperHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapperHandlerImpl implements MapperHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapperHandlerImpl.class);

    @Autowired(required = false)
    private List<MapperMaker> mapperMakers = new ArrayList<>();

    @Override
    public Mapper make(String type, Object[] args) throws HandlerException {
        try {
            // 生成过滤器。
            LOGGER.debug("通过过滤器信息构建新的的映射器...");
            MapperMaker mapperMaker = mapperMakers.stream().filter(maker -> maker.supportType(type))
                    .findFirst().orElseThrow(() -> new UnsupportedFilterTypeException(type));
            Mapper mapper = mapperMaker.makeMapper(args);
            LOGGER.debug("映射器构建成功!");
            LOGGER.debug("映射器: " + mapper);
            return mapper;
        } catch (FilterException e) {
            throw e;
        } catch (Exception e) {
            throw new FilterException(e);
        }
    }
}
