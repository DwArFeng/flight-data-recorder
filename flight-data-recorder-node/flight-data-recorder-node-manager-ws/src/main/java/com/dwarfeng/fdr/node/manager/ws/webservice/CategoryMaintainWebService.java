package com.dwarfeng.fdr.node.manager.ws.webservice;

import com.dwarfeng.fdr.node.manager.ws.bean.entity.WsCategory;
import com.dwarfeng.fdr.node.manager.ws.bean.key.WsUuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Collection;
import java.util.List;

/**
 * Category维护WebService
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
@WebService
public interface CategoryMaintainWebService {

    @WebMethod
    WsCategory get(WsUuidKey key) throws ServiceException;

    @WebMethod
    WsUuidKey insert(WsCategory category) throws ServiceException;

    @WebMethod
    List<? extends WsUuidKey> batchInsert(Collection<? extends WsCategory> categories) throws ServiceException;

    @WebMethod
    WsUuidKey update(WsCategory category) throws ServiceException;

    @WebMethod
    List<? extends WsUuidKey> batchUpdate(Collection<? extends WsCategory> categories) throws ServiceException;

    @WebMethod
    void delete(WsUuidKey key) throws ServiceException;

    @WebMethod
    void batchDelete(Collection<? extends WsUuidKey> keys) throws ServiceException;

}
