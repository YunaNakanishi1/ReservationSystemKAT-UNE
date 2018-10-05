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
public class ShowResourceRegistServiceTest {

	/**
	 * {@link service.ShowResourceRegistService#execute()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test
	public void test1() throws SQLException {
		//fail("まだ実装されていません");
		ShowResourceRegistService srrs = new ShowResourceRegistService();
		srrs.execute();

		assertThat(srrs.getCategoryList().size(),is(0));
		assertThat(srrs.getOfficeList().size(),is(0));
		assertThat(srrs.getFacilityList().size(),is(0));

	}


}
