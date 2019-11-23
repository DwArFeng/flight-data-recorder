package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.dutil.basic.str.UUIDUtil;
import com.dwarfeng.fdr.api.maintain.dubbo.api.CategoryMaintainApi;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
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
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class CategoryMaintainApiImplTest {

    @Autowired
    private CategoryMaintainApi api;

    private Category parentCategory;
    private List<Category> childCategories;

    @Before
    public void setUp() throws Exception {
        parentCategory = new Category(
                new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
                null,
                "parent",
                "test-parent"
        );
        childCategories = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Category childCategory = new Category(
                    new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
                    parentCategory.getKey(),
                    "child-" + (i + 1),
                    "test-child"
            );
            childCategories.add(childCategory);
        }
    }

    @After
    public void tearDown() throws Exception {
        parentCategory = null;
        childCategories.clear();
    }

    @Test
    public void test() throws ServiceException {
        try {
            api.insert(parentCategory);
            for (Category category : childCategories) {
                api.insert(category);
            }
            assertEquals(5, api.getChilds(parentCategory.getKey(), new LookupPagingInfo(false, 0, 0)).getCount());
        } finally {
            api.delete(parentCategory.getKey());
            for (Category category : childCategories) {
                api.delete(category.getKey());
            }
        }
    }

}