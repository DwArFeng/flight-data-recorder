package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.service.CategoryMaintainService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class CategoryMaintainServiceImplTest {

    @Autowired
    private CategoryMaintainService service;

    private Category parentCategory;
    private List<Category> childCategories;

    @Before
    public void setUp() {
        parentCategory = new Category(
                null,
                null,
                "parent",
                "test-parent"
        );
        childCategories = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Category childCategory = new Category(
                    null,
                    parentCategory.getKey(),
                    "child-" + (i + 1),
                    "test-child"
            );
            childCategories.add(childCategory);
        }
    }

    @After
    public void tearDown() {
        parentCategory = null;
        childCategories.clear();
    }

    @Test
    public void test() throws ServiceException {
        try {
            parentCategory.setKey(service.insert(parentCategory));
            for (Category category : childCategories) {
                category.setKey(service.insert(category));
                category.setParentKey(parentCategory.getKey());
                service.update(category);
            }
            assertEquals(5, service.lookup(CategoryMaintainService.CHILD_FOR_PARENT, new Object[]{parentCategory.getKey()}).getCount());
        } finally {
            for (Category category : childCategories) {
                service.delete(category.getKey());
            }
            service.delete(parentCategory.getKey());
        }
    }
}