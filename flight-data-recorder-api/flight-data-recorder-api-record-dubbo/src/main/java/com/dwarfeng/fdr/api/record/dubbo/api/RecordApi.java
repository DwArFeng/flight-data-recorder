package com.dwarfeng.fdr.api.record.dubbo.api;

import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.dto.RecordResult;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.Service;

public interface RecordApi extends Service {

    /**
     * 向程序中记录数据。
     *
     * @param dataInfo 指定的数据信息。
     * @return 记录结果。
     * @throws ServiceException 服务异常。
     */
    RecordResult record(DataInfo dataInfo) throws ServiceException;

}
