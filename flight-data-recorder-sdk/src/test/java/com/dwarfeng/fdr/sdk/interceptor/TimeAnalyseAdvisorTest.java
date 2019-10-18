package com.dwarfeng.fdr.sdk.interceptor;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyseAdvisor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class TimeAnalyseAdvisorTest {

    @Autowired
    private Tester tester;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        tester.testMethod();
    }

    @Component
    public static class Tester {

        private static final Logger LOGGER = LoggerFactory.getLogger(Tester.class);

        @TimeAnalyseAdvisor.TimeAnalyse
        public void testMethod() {
            try {
                Thread.sleep(500l);
            } catch (InterruptedException e) {
                LOGGER.error("方法被中断", e);
            }
        }
    }
}