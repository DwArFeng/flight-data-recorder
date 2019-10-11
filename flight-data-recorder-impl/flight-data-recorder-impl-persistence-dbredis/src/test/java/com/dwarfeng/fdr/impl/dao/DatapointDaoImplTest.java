package com.dwarfeng.fdr.impl.dao;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.fdr.impl.bean.key.NameKeyImpl;
import com.dwarfeng.fdr.stack.dao.PointDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/application-context*.xml")
public class DatapointDaoImplTest {

	@Autowired
	private PointDao datapointDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGet() {
	}

	@Test
	public void testInsert() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelect() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectCount() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDatavalue() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetTriggerSetting() {
		fail("Not yet implemented");
	}

	@Test
	public void testClearDatavalue() {
		fail("Not yet implemented");
	}

	@Test
	public void testClearTriggerSetting() {
		fail("Not yet implemented");
	}

}
