package com.dwarfeng.fdr.node.manager.ws.webservice.impl;

import com.dwarfeng.fdr.node.manager.ws.webservice.HelloWebService;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

@WebService
@Component("helloWebService")
public class HelloWebServiceImpl implements HelloWebService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWebServiceImpl.class);

    @Override
    @TimeAnalyse
    public String getWelcomeMessage() {
        LOGGER.debug("HelloWebService: 接收到客户端获取欢迎信息，输出欢迎信息...");
        return "欢迎您使用WebService访问飞行数据记录仪";
    }
}
