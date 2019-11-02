package com.dwarfeng.fdr.node.manager.ws.webservice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class HelloWebServiceTest {

    @Autowired
    private HelloWebService helloWebService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetWelcomeMessage() {
        assertEquals(helloWebService.getWelcomeMessage(), "欢迎您使用WebService访问飞行数据记录仪");
    }

}