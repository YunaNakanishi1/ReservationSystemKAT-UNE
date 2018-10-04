package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dto.Resource;
import dto.User;

public class DataBaseFailTest {	//完了！！


	/**
	 * UserDao delete(String resourceId)データベース切断
	 * @throws SQLException
	 */
	@Test(expected = SQLException.class)
	public void deletetest2_5() throws SQLException  {
		ResourceDao rd = new ResourceDao();
		assertThat(rd.delete("u002"), is(0));


	}

	/**
	 * UserDao getUser(user)データベース切断
	 * @throws SQLException
	 */
	@Test(expected = SQLException.class)
	public void getUsertest1_13() throws SQLException {
		UserDao ud = new UserDao();
		User user = new User("u0123456", "password", 0);
		assertThat(ud.getUser(user), nullValue());
	}

	//getUser(user)データベース切断
	@Test(expected = SQLException.class)
	public void getAuthoritytest2_5()  throws SQLException  {
		UserDao ud = new UserDao();
		ud.getAuthority("u0123456");
	}

	/**
	 * DataSet1(UT002)のデータ2を使用するテスト
	 * {@link dao.ResourceDao#displayAll()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test(expected = SQLException.class)
	public void test1_3() throws SQLException {
		//fail("まだ実装されていません");
		dao.ResourceDao resourceDao = new dao.ResourceDao();
		resourceDao.displayAll();
	}

	/**
	 * DataSet1(UT002)のデータ2を使用するテスト
	 * {@link dao.ResourceDao#displayAll()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test(expected = SQLException.class)
	public void test3_4() throws SQLException {
		//fail("まだ実装されていません");
		dao.ResourceDao resourceDao = new dao.ResourceDao();
		String argument = "r000000003";
		resourceDao.displayDetails(argument);
	}

	/**
	 * FacilityDaoのデータベース停止テスト
	 * {@link dao.FacilityDao#facility()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test(expected = SQLException.class)
	public void facilityDaoTest4() throws SQLException {
		//fail("まだ実装されていません");
		FacilityDao fd = new FacilityDao();
		fd.facility();
	}

	/**
	 * CategoryDaoのデータベース停止テスト
	 * {@link dao.CategoryDao#category()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test(expected = SQLException.class)
	public void categoryDaoTest4() throws SQLException {
		//fail("まだ実装されていません");
		CategoryDao fd = new CategoryDao();
		fd.category();
	}

	/**
	 * DataSet1(UT005)のデータ1を使用するテスト
	 * {@link dao.OfficeDao#officeName()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test(expected = SQLException.class)
	public void officeDaoTest4() throws SQLException {
		//fail("まだ実装されていません");
		OfficeDao od = new OfficeDao();
		od.officeName();
	}


	@Test (expected = SQLException.class)
	public void ResourceDaoChangetest4_9() throws SQLException {
		ResourceDao resourceDao = new ResourceDao();
		List<String> list = new ArrayList<String>();
		list.add("ホワイトボード有");
		String spl = "";
		Timestamp uss = null;
		Timestamp use = null;
		Resource resource = new Resource("r000000001", "晴海412S", "晴海", "会議室", 10, spl, 0, list, uss, use);
		resourceDao.change(resource);

	}


	@Test (expected = SQLException.class)
	public void ResourceDaoRegisttest5_5() throws SQLException {
		ResourceDao resourceDao = new ResourceDao();
		List<String> list = new ArrayList<String>();
		list.add("ホワイトボード有");
		list.add("プロジェクター有");
		String spl = "";
		Timestamp uss = null;
		Timestamp use = null;
		Resource resource = new Resource("r000000113", "新横浜16F会議室C ", "晴海", "会議室", 112, "A \' or \' A \' = \' A \'", 0, list, uss, use);
		resourceDao.regist(resource);
	}
}
