package com.dwarfeng.fdr.node.validate.test.bean.key;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class UuidKeyImplTest {

    @Autowired
    private Tester tester;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testNotUsingValidate() {
        tester.notUsingValidate(new UuidKeyImpl(null));
        tester.notUsingValidate(new UuidKeyImpl(""));
    }

    @Test(expected = ConstraintViolationException.class)
    public void testNotUsingValidate_1() {
        tester.usingValidate(null);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testNotUsingValidate_2() {
        tester.usingValidate(new UuidKeyImpl(null));
    }

    @Test(expected = ConstraintViolationException.class)
    public void testNotUsingValidate_3() {
        tester.usingValidate(new UuidKeyImpl(""));
    }


    @Component
    @Validated
    public static class Tester {

        public void notUsingValidate(UuidKeyImpl uuidKey) {

        }

        public void usingValidate(@NotNull @Valid UuidKeyImpl uuidKey) {

        }

    }
}