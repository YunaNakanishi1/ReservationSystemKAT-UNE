/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

/**
 *
 *@author リコーITソリューションズ株式会社 KAT-UNE
 */
public class ShowResourceChangeServiceTest {	//完了！

	/**
	 * {@link service.ShowResourceChangeService#validate()} のためのテスト・メソッド。
	 */
	@Test
	public void testValidate() {
		//fail("まだ実装されていません");
	}

	/**
	 * {@link service.ShowResourceChangeService#execute()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test
	public void test1() throws SQLException {
		//fail("まだ実装されていません");
		ShowResourceChangeService showResourceChangeService =
				new ShowResourceChangeService("nothing");
		showResourceChangeService.execute();
		assertThat(showResourceChangeService.getResource(),nullValue());
		assertThat(showResourceChangeService.getCategoryList().size(),is(0));
		assertThat(showResourceChangeService.getOfficeList().size(),is(0));
		assertThat(showResourceChangeService.getFacilityList().size(),is(0));
	}


	/**
	 * {@link service.ShowResourceChangeService#execute()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test
	public void test2() throws SQLException {
		//fail("まだ実装されていません");
		ShowResourceChangeService showResourceChangeService =
				new ShowResourceChangeService(null);
		showResourceChangeService.execute();
		assertThat(showResourceChangeService.getResource(),nullValue());
		assertThat(showResourceChangeService.getCategoryList().size(),is(0));
		assertThat(showResourceChangeService.getOfficeList().size(),is(0));
		assertThat(showResourceChangeService.getFacilityList().size(),is(0));
	}
}
