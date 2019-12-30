package com.dwarfeng.fdr.api.record.dubbo.api.impl;

import com.dwarfeng.fdr.api.record.dubbo.api.RecordApi;
import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.dto.RecordResult;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecordApiImpl implements RecordApi {

    @Autowired
    private RecordApiDelegate delegate;

    @Override
    public RecordResult record(DataInfo dataInfo) throws ServiceException {
        return delegate.record(dataInfo);
    }
}
