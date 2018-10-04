/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 *
 *@author リコーITソリューションズ株式会社 KAT-UNE
 */
public class ShowResourceChangeService_CommonDataSet1 {

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
	public void test3() throws SQLException {
		//fail("まだ実装されていません");
		ShowResourceChangeService showResourceChangeService =
				new ShowResourceChangeService("nothing");
		showResourceChangeService.execute();
		assertThat(showResourceChangeService.getResource(),nullValue());
		assertThat(showResourceChangeService.getCategoryList().get(0),is("会議室"));
		assertThat(showResourceChangeService.getCategoryList().get(1),is("UCS"));
		assertThat(showResourceChangeService.getCategoryList().get(2),is("応接室"));
		assertThat(showResourceChangeService.getOfficeList().get(0),is("晴海"));
		assertThat(showResourceChangeService.getOfficeList().get(1),is("新横浜"));
		assertThat(showResourceChangeService.getFacilityList().get(0),is("ホワイトボード有"));
		assertThat(showResourceChangeService.getFacilityList().get(1),is("プロジェクター有"));
		assertThat(showResourceChangeService.getFacilityList().get(2),is("来客優先"));
		assertThat(showResourceChangeService.getFacilityList().get(3),is("UCS常設"));
		assertThat(showResourceChangeService.getFacilityList().get(4),is("TV会議システム"));
		assertThat(showResourceChangeService.getFacilityList().get(5),is("OAボード"));
	}


	/**
	 * {@link service.ShowResourceChangeService#execute()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test
	public void test4() throws SQLException {
		//fail("まだ実装されていません");
		ShowResourceChangeService showResourceChangeService =
				new ShowResourceChangeService(null);
		showResourceChangeService.execute();
		assertThat(showResourceChangeService.getResource(),nullValue());
		assertThat(showResourceChangeService.getCategoryList().get(0),is("会議室"));
		assertThat(showResourceChangeService.getCategoryList().get(1),is("UCS"));
		assertThat(showResourceChangeService.getCategoryList().get(2),is("応接室"));
		assertThat(showResourceChangeService.getOfficeList().get(0),is("晴海"));
		assertThat(showResourceChangeService.getOfficeList().get(1),is("新横浜"));
		assertThat(showResourceChangeService.getFacilityList().get(0),is("ホワイトボード有"));
		assertThat(showResourceChangeService.getFacilityList().get(1),is("プロジェクター有"));
		assertThat(showResourceChangeService.getFacilityList().get(2),is("来客優先"));
		assertThat(showResourceChangeService.getFacilityList().get(3),is("UCS常設"));
		assertThat(showResourceChangeService.getFacilityList().get(4),is("TV会議システム"));
		assertThat(showResourceChangeService.getFacilityList().get(5),is("OAボード"));
	}

	/**
	 * {@link service.ShowResourceChangeService#execute()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test
	public void test5() throws SQLException {
		//fail("まだ実装されていません");
		ShowResourceChangeService showResourceChangeService =
				new ShowResourceChangeService("r000000003");
		showResourceChangeService.execute();

		Timestamp uss2 =Timestamp.valueOf("2018-10-12 9:00:00");
		Timestamp use2 =Timestamp.valueOf("2018-11-12 9:00:00");

		List<String> facility = new ArrayList<String>();
		facility.add("ホワイトボード有");
		facility.add("プロジェクター有");

		assertThat(showResourceChangeService.getResource().getResourceId(),is("r000000003"));
		assertThat(showResourceChangeService.getResource().getResourceName(),is("晴海414L"));
		assertThat(showResourceChangeService.getResource().getCategory(),is("会議室"));
		assertThat(showResourceChangeService.getResource().getOfficeName(),is("晴海"));
		assertThat(showResourceChangeService.getResource().getCapacity(),is(24));
		assertThat(showResourceChangeService.getResource().getSupplement(),is("新人教育のため占有"));
		assertThat(showResourceChangeService.getResource().getUsageStopStartDate(),is(uss2));
		assertThat(showResourceChangeService.getResource().getUsageStopEndDate(),is(use2));
		assertThat(showResourceChangeService.getResource().getDeleted(),is(0));
		assertThat(showResourceChangeService.getResource().getCapacity(),is(24));
		assertThat(showResourceChangeService.getResource().getFacility().get(0),is("ホワイトボード有"));
		assertThat(showResourceChangeService.getResource().getFacility().get(1),is("プロジェクター有"));
		assertThat(showResourceChangeService.getCategoryList().get(0),is("会議室"));
		assertThat(showResourceChangeService.getCategoryList().get(1),is("UCS"));
		assertThat(showResourceChangeService.getCategoryList().get(2),is("応接室"));
		assertThat(showResourceChangeService.getOfficeList().get(0),is("晴海"));
		assertThat(showResourceChangeService.getOfficeList().get(1),is("新横浜"));
		assertThat(showResourceChangeService.getFacilityList().get(0),is("ホワイトボード有"));
		assertThat(showResourceChangeService.getFacilityList().get(1),is("プロジェクター有"));
		assertThat(showResourceChangeService.getFacilityList().get(2),is("来客優先"));
		assertThat(showResourceChangeService.getFacilityList().get(3),is("UCS常設"));
		assertThat(showResourceChangeService.getFacilityList().get(4),is("TV会議システム"));
		assertThat(showResourceChangeService.getFacilityList().get(5),is("OAボード"));

	}


}
