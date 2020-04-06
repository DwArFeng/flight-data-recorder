package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.handler.Filter;

/**
 * 过滤器制造器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface FilterMaker {

    /**
     * 返回制造器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 制造器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 根据指定的过滤器信息生成一个过滤器对象。
     * <p>可以保证传入的过滤器信息中的类型是支持的。</p>
     *
     * @param filterInfo 指定的过滤器信息。
     * @return 制造出的过滤器。
     * @throws FilterException 过滤器异常。
     */
    Filter makeFilter(FilterInfo filterInfo) throws FilterException;
}
