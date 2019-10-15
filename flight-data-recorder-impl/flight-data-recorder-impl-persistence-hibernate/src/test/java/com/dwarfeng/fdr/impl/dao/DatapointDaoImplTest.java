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
import org.springframework.transaction.annotation.Transactional;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.NameKey;
import com.dwarfeng.fdr.stack.dao.PointDao;
import com.dwarfeng.fdr.stack.exception.DaoException;

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
		fail("Not yet implemented");
	}

	@Test
	@Transactional
	public void testInsert() throws DaoException {
		PointImpl pointImpl = new PointImpl();
		pointImpl.setKey(new NameKeyImpl("this-is-a-test"));
		pointImpl.setPersistence(false);
		pointImpl.setRemark("this is a test");
		pointImpl.setType("string");
		datapointDao.insert(pointImpl);
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

	public static class NameKeyImpl implements NameKey {

		private static final long serialVersionUID = -6372678944223074527L;

		private String name;

		public NameKeyImpl() {
		}

		public NameKeyImpl(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			NameKeyImpl other = (NameKeyImpl) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "NameKeyImpl [name=" + name + "]";
		}

	}

	public static class PointImpl implements Point {

		private static final long serialVersionUID = 5413283787016073082L;

		private NameKey key;
		private String type;
		private boolean isPersistence;
		private String remark;

		public PointImpl() {
		}

		public PointImpl(NameKey key, String type, boolean isPersistence, String remark) {
			this.key = key;
			this.type = type;
			this.isPersistence = isPersistence;
			this.remark = remark;
		}

		@Override
		public NameKey getKey() {
			return key;
		}

		public void setKey(NameKey key) {
			this.key = key;
		}

		@Override
		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		@Override
		public boolean isPersistence() {
			return isPersistence;
		}

		public void setPersistence(boolean isPersistence) {
			this.isPersistence = isPersistence;
		}

		@Override
		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		@Override
		public String toString() {
			return "PointImpl [key=" + key + ", type=" + type + ", isPersistence=" + isPersistence + ", remark="
					+ remark + "]";
		}

	}

}
