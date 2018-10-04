package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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

	@Test(expected = SQLException.class)
	public void test3_5() throws SQLException {
		//fail("まだ実装されていません");
		FacilityDao fd = new FacilityDao();
		List<String> facilityList=new ArrayList<String>();
		facilityList.add("ホワイトボード有");
		assertThat(fd.facility().get(0),is(facilityList.get(0)));
	}

	@Test(expected = SQLException.class)
	public void test3_6() throws SQLException {
		//fail("まだ実装されていません");
		CategoryDao fd = new CategoryDao();
		List<String> categoryList=new ArrayList<String>();
		categoryList.add("会議室");
		assertThat(fd.category().get(0),is(categoryList.get(0)));
	}
}
