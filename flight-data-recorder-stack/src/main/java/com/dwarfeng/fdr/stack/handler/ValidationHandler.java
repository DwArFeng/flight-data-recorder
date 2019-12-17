package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.*;
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

    /**
     * 验证过滤器信息是否合法。
     *
     * @param filterInfo 指定的FilterInfo。
     * @throws ValidationException 不合法时抛出的异常。
     */
    void filterInfoValidation(FilterInfo filterInfo) throws ValidationException;

    /**
     * 验证触发器信息是否合法。
     *
     * @param triggerInfo 指定的TriggerInfo。
     * @throws ValidationException 不合法时抛出的异常。
     */
    void triggerInfoValidation(TriggerInfo triggerInfo) throws ValidationException;

    /**
     * 验证被过滤数据是否合法。
     *
     * @param filteredValue 指定的被过滤数据。
     * @throws ValidationException 不合法时抛出的异常。
     */
    void filteredValueValidation(FilteredValue filteredValue) throws ValidationException;

    /**
     * 验证持久化数据是否合法。
     *
     * @param persistenceValue 指定的持久化数据。
     * @throws ValidationException 不合法时抛出的异常。
     */
    void persistenceValueValidation(PersistenceValue persistenceValue) throws ValidationException;

    /**
     * 验证实时数据是否合法。
     *
     * @param realtimeValue 指定的实时数据。
     * @throws ValidationException 不合法时抛出的异常。
     */
    void realtimeValueValidation(RealtimeValue realtimeValue) throws ValidationException;

    /**
     * 验证被触发数据是否合法。
     *
     * @param triggeredValue 指定的被触发数据。
     * @throws ValidationException 不合法时抛出的异常。
     */
    void triggeredValueValidation(TriggeredValue triggeredValue) throws ValidationException;

}
