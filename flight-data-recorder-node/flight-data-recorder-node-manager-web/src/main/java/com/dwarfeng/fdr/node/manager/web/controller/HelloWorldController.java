package com.dwarfeng.fdr.node.manager.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldController.class);

    @RequestMapping("hello-world")
    public String helloWorld() {
        LOGGER.info("跳转到 hello-world 页面...");
        return "hello-world";
    }
}
