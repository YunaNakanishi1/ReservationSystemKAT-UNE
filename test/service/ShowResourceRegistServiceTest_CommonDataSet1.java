/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
*
*@author リコーITソリューションズ株式会社 KAT-UNE
*/
public class ShowResourceRegistServiceTest_CommonDataSet1 {

	/**
	 * {@link service.ShowResourceRegistService#validate()} のためのテスト・メソッド。
	 */
	@Test
	public void testValidate() {
		//fail("まだ実装されていません");
		ShowResourceRegistService srrs = new ShowResourceRegistService();

		assertThat(srrs.validate(),is(true));
	}

	/**
	 * {@link service.ShowResourceRegistService#execute()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test
	public void test2() throws SQLException {
		//fail("まだ実装されていません");
		ShowResourceRegistService srrs = new ShowResourceRegistService();
		srrs.execute();

		List<String> facility = new ArrayList<String>();
		facility.add("ホワイトボード有");
		facility.add("プロジェクター有");

		assertThat(srrs.getCategoryList().get(0),is("会議室"));
		assertThat(srrs.getCategoryList().get(1),is("UCS"));
		assertThat(srrs.getCategoryList().get(2),is("応接室"));
		assertThat(srrs.getOfficeList().get(0),is("晴海"));
		assertThat(srrs.getOfficeList().get(1),is("新横浜"));
		assertThat(srrs.getFacilityList().get(0),is("ホワイトボード有"));
		assertThat(srrs.getFacilityList().get(1),is("プロジェクター有"));
		assertThat(srrs.getFacilityList().get(2),is("来客優先"));
		assertThat(srrs.getFacilityList().get(3),is("UCS常設"));
		assertThat(srrs.getFacilityList().get(4),is("TV会議システム"));
		assertThat(srrs.getFacilityList().get(5),is("OAボード"));

	}

	/**
	 * {@link service.ShowResourceRegistService#getCategoryList()} のためのテスト・メソッド。
	 */
	@Test
	public void testGetCategoryList() {
		//fail("まだ実装されていません");
	}

	/**
	 * {@link service.ShowResourceRegistService#getOfficeList()} のためのテスト・メソッド。
	 */
	@Test
	public void testGetOfficeList() {
		//fail("まだ実装されていません");
	}

	/**
	 * {@link service.ShowResourceRegistService#getFacilityList()} のためのテスト・メソッド。
	 */
	@Test
	public void testGetFacilityList() {
		//fail("まだ実装されていません");
	}

}
