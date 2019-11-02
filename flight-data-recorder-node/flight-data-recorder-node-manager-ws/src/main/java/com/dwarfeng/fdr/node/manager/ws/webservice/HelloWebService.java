package com.dwarfeng.fdr.node.manager.ws.webservice;

import javax.jws.WebService;

/**
 * 欢迎WebService。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
@WebService
public interface HelloWebService {

    /**
     * 获取欢迎信息。
     *
     * @return 欢迎信息。
     */
    String getWelcomeMessage();
}
