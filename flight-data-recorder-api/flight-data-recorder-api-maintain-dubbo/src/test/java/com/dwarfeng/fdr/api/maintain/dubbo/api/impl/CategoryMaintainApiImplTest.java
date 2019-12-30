package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.CategoryMaintainApi;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.exception.ServiceException;
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
public class CategoryMaintainApiImplTest {

    @Autowired
    private CategoryMaintainApi api;

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
            parentCategory.setKey(api.insert(parentCategory));
            for (Category category : childCategories) {
                category.setKey(api.insert(category));
                category.setParentKey(parentCategory.getKey());
                api.update(category);
            }
            assertEquals(5, api.getChilds(parentCategory.getKey(), LookupPagingInfo.LOOKUP_ALL).getCount());
        } finally {
            for (Category category : childCategories) {
                api.delete(category.getKey());
            }
            api.delete(parentCategory.getKey());
        }
    }

}