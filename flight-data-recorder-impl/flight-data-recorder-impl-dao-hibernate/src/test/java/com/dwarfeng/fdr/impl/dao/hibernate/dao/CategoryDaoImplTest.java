package com.dwarfeng.fdr.impl.dao.hibernate.dao;

import com.dwarfeng.dutil.basic.str.UUIDUtil;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.dao.CategoryDao;
import com.dwarfeng.fdr.stack.exception.DaoException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class CategoryDaoImplTest {

    @Autowired
    private CategoryDao dao;

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
    @Transactional
    public void test() throws DaoException {
        dao.insert(parentCategory);
        for (Category category : childCategories) {
            dao.insert(category);
        }
        assertTrue(dao.exists(parentCategory.getKey()));
        for (Category category : childCategories) {
            assertTrue(dao.exists(category.getKey()));
        }
        assertEquals(5, dao.getChildCount(parentCategory.getKey()));
        assertEquals(5, dao.getChilds(parentCategory.getKey(), new LookupPagingInfo(false, 0, 0)).size());
    }
}