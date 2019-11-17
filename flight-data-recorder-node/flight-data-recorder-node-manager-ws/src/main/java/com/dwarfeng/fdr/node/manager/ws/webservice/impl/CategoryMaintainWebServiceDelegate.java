package com.dwarfeng.fdr.node.manager.ws.webservice.impl;

import com.dwarfeng.fdr.node.manager.ws.bean.entity.WsCategory;
import com.dwarfeng.fdr.node.manager.ws.bean.key.WsUuidKey;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.CategoryMaintainService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Component
@Validated
public class CategoryMaintainWebServiceDelegate {

    @Autowired
    private CategoryMaintainService service;
    @Autowired
    private Mapper mapper;

    @TimeAnalyse
    public WsCategory get(@NotNull @Valid WsUuidKey key) throws ServiceException {
        throw new IllegalStateException("not implemented yet.");
    }

    @TimeAnalyse
    public WsUuidKey insert(@NotNull @Valid WsCategory category) throws ServiceException {
        throw new IllegalStateException("not implemented yet.");
    }

    @TimeAnalyse
    public List<? extends WsUuidKey> batchInsert(Collection<? extends WsCategory> categories) throws ServiceException {
        throw new IllegalStateException("not implemented yet.");
    }

    @TimeAnalyse
    public WsUuidKey update(WsCategory category) throws ServiceException {
        throw new IllegalStateException("not implemented yet.");
    }

    @TimeAnalyse
    public List<? extends WsUuidKey> batchUpdate(Collection<? extends WsCategory> categories) throws ServiceException {
        throw new IllegalStateException("not implemented yet.");
    }

    @TimeAnalyse
    public void delete(WsUuidKey key) throws ServiceException {
        throw new IllegalStateException("not implemented yet.");
    }

    @TimeAnalyse
    public void batchDelete(Collection<? extends WsUuidKey> keys) throws ServiceException {
        throw new IllegalStateException("not implemented yet.");
    }

}
