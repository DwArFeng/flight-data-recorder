package com.dwarfeng.fdr.impl.service.record.service;

import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.dto.RecordResult;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordServiceDelegate delegate;

    @Override
    public RecordResult record(DataInfo dataInfo) throws ServiceException {
        return delegate.record(dataInfo);
    }
}
