package com.dwarfeng.fdr.sdk.validation;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class DenseUUIDTest {

    @Autowired
    private Tester tester;

    private Person person1;
    private Person person2;
    private Person person3;

    @Before
    public void setUp() throws Exception {
        person1 = new Person("IXMRPoceQii9s9LyiMk/vg", "张三");
        person2 = new Person("IXMRPoceQii9s9LyiMk/vgA", "李四");
        person3 = new Person("IXMRPoceQi!9s9LyiMk/vg", "王五");
    }

    @After
    public void tearDown() throws Exception {
        person1 = null;
        person2 = null;
        person3 = null;
    }

    @Test
    public void test1() {
        tester.test(person1);
    }

    @Test(expected = ConstraintViolationException.class)
    public void test2() {
        tester.test(person2);
    }

    @Test(expected = ConstraintViolationException.class)
    public void test3() {
        tester.test(person3);
    }

    @Component
    @Validated
    public static class Tester {
        public void test(@Valid Person person) {
            //Do nothing but validate.
        }
    }

    public static class Person {

        @DenseUUID
        private String id;
        private String name;

        public Person() {
        }

        public Person(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

}