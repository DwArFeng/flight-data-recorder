package com.dwarfeng.fdr.impl.dao.hnh.dao;

import com.dwarfeng.dutil.basic.str.UUIDUtil;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.dao.CategoryDao;
import com.dwarfeng.fdr.stack.dao.PointDao;
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
public class PointDaoImplTest {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private PointDao pointDao;

    private Category parentCategory;
    private List<Point> points;

    @Before
    public void setUp() {
        parentCategory = new Category(
                new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
                null,
                "parent",
                "test-parent"
        );
        points = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Point point = new Point(
                    new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
                    parentCategory.getKey(),
                    "point-" + (i + 1),
                    "test-point",
                    true,
                    true
            );
            points.add(point);
        }
    }

    @After
    public void tearDown() {
        parentCategory = null;
        points.clear();
    }

    @Test
    @Transactional(transactionManager = "daoTransactionManager")
    public void test() throws DaoException {
        categoryDao.insert(parentCategory);
        for (Point point : points) {
            pointDao.insert(point);
        }
        assertTrue(categoryDao.exists(parentCategory.getKey()));
        for (Point point : points) {
            assertTrue(pointDao.exists(point.getKey()));
        }
        assertEquals(5, pointDao.getPointCount(parentCategory.getKey()));
        assertEquals(5, pointDao.getPoints(parentCategory.getKey(), LookupPagingInfo.LOOKUP_ALL).size());
        categoryDao.delete(parentCategory.getKey());
        for (Point point : points) {
            pointDao.delete(point.getKey());
        }
    }
}