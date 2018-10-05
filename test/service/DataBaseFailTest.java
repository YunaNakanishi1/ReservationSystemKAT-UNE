/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package service;

import java.sql.SQLException;

import org.junit.Test;

import dto.User;


/**
*
*@author リコーITソリューションズ株式会社 KAT-UNE
*/
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
		ShowResourceListService showResourceListService = new ShowResourceListService();
		showResourceListService.execute();
	}

	/**
	 * ※データベースを止めた状態でやってみるとうまくいきます.
	 * {@link service.ShowResourceDetailsService#execute()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test(expected =java.sql.SQLException.class)
	public void test4() throws SQLException {
		//fail("まだ実装されていません");
		ShowResourceDetailsService showResourceDetailsService = new ShowResourceDetailsService("r000000003");
		showResourceDetailsService.execute();
	}

	/**
	 * ※データベースを止めた状態でやってみるとうまくいきます.
	 * {@link service.CheckAuthorityService#execute()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test(expected =java.sql.SQLException.class)
	public void CheckAuthorityServicetest4() throws SQLException {
		//fail("まだ実装されていません");
		CheckAuthorityService checkAuthorityService = new CheckAuthorityService("r003");
		checkAuthorityService.execute();
	}

	/**
	 * ※データベースを止めた状態でやってみるとうまくいきます.
	 * {@link service.ShowResourceChangeService#execute()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test(expected =java.sql.SQLException.class)
	public void ShowResourceChangeServiceTest6() throws SQLException {
		//fail("まだ実装されていません");
		ShowResourceChangeService showResourceChangeService
		= new ShowResourceChangeService("r000000003");
		showResourceChangeService.execute();
	}

	/**
	 *
	 * {@link service.ShowResourceChangeService#execute()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test(expected =java.sql.SQLException.class)
	public void ShowResourceRegistServiceTest3() throws SQLException {
		//fail("まだ実装されていません");
		ShowResourceRegistService showResourceRegistService
		= new ShowResourceRegistService();
		showResourceRegistService.execute();
	}

}
