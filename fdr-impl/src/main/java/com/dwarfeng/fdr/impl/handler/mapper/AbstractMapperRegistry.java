package com.dwarfeng.fdr.impl.handler.mapper;

import com.dwarfeng.fdr.impl.handler.MapperMaker;
import com.dwarfeng.fdr.impl.handler.MapperSupporter;

import java.util.Objects;

/**
 * 抽象映射器注册。
 *
 * @author DwArFeng
 * @since 1.7.2
 */
public abstract class AbstractMapperRegistry implements MapperMaker, MapperSupporter {

    protected String mapperType;

    public AbstractMapperRegistry() {
    }

    public AbstractMapperRegistry(String mapperType) {
        this.mapperType = mapperType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(mapperType, type);
    }

    @Override
    public String provideType() {
        return mapperType;
    }

    public String getMapperType() {
        return mapperType;
    }

    public void setMapperType(String mapperType) {
        this.mapperType = mapperType;
    }

    @Override
    public String toString() {
        return "AbstractMapperRegistry{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }
}
