package com.dwarfeng.fdr.api.record.dubbo.api.impl;

import com.dwarfeng.fdr.api.record.dubbo.api.RecordApi;
import com.dwarfeng.fdr.api.record.dubbo.interceptor.DubboInvokeLog;
import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.dto.RecordResult;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@DubboInvokeLog
public class RecordApiImpl implements RecordApi {

    @Autowired
    @Qualifier("recordService")
    private RecordService delegate;

    @Override
    public RecordResult record(DataInfo dataInfo) throws ServiceException {
        return delegate.record(dataInfo);
    }
}
