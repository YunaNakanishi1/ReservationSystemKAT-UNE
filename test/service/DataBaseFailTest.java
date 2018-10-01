package service;

import java.sql.SQLException;

import org.junit.Test;

import dto.User;

public class DataBaseFailTest {


	/**
	 * deleteResource
	 * @throws SQLException
	 */
	@Test(expected = SQLException.class)
	public void test5() throws SQLException {
		DeleteResourceService drs = new DeleteResourceService("r006");
		drs.execute();
	}


	/**
	 * loginService
	 * @throws SQLException
	 */
	@Test(expected = SQLException.class)
	public void test12()  throws SQLException {
		User user = new User("u0123456", "password", 0);
		LogInService lis = new LogInService(user);
		lis.execute();
	}


	/**
	 * ※データベースを止めた状態でやってみるとうまくいきます.
	 * {@link service.ShowResourceListService#execute()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test(expected =java.sql.SQLException.class)
	public void test2() throws SQLException {
		//fail("まだ実装されていません");
		ShowResourceListService showResourceListservice = new ShowResourceListService();
		showResourceListservice.execute();
	}

}
