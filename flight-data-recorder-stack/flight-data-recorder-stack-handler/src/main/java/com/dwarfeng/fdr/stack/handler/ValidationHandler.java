package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ValidationException;

/**
 * 验证处理器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface ValidationHandler {

    /**
     * 验证UuidKey是否合法。
     *
     * @param uuidKey 指定的UuidKey。
     * @throws ValidationException 不合法时抛出的异常。
     */
    void uuidKeyValidation(UuidKey uuidKey) throws ValidationException;

    /**
     * 验证分类是否合法。
     *
     * @param category 指定的Category。
     * @throws ValidationException 不合法时抛出的异常。
     */
    void categoryValidation(Category category) throws ValidationException;

    /**
     * 验证查询分页信息是否合法。
     *
     * @param lookupPagingInfo 指定的查询分页信息。
     * @throws ValidationException 符合法时抛出的异常。
     */
    void lookupPagingInfoValidation(LookupPagingInfo lookupPagingInfo) throws ValidationException;

    /**
     * 验证数据点是否合法。
     *
     * @param point 指定的Point。
     * @throws ValidationException 不合法时抛出的异常。
     */
    void pointValidation(Point point) throws ValidationException;


}
