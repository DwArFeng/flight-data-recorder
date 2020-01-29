package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.dto.RecordResult;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 数据记录服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface RecordService extends Service {

    /**
     * 向程序中记录数据。
     *
     * @param dataInfo 指定的数据信息。
     * @return 记录结果。
     * @throws ServiceException 服务异常。
     */
    RecordResult record(DataInfo dataInfo) throws ServiceException;
}
