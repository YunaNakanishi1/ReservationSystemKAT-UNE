/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;

import dto.Resource;

/**
*
*@author リコーITソリューションズ株式会社 KAT-UNE
*/
public class ServiceValidatorTest {

	/**
	 * {@link service.ServiceValidator#setResourseDetailValidate(dto.Resource)} のためのテスト・メソッド。
	 */
	@Test
	public void test1() {
		//fail("まだ実装されていません");
		ServiceValidator sv = new ServiceValidator();
		String resourceId = "r000000001";
		String resourceName = "晴海412Sああああああああああああああああああああああああ";
		String category = "会議室";
		String officeName = "晴海";
		int capacity = 10;
		String supplement = "プレゼンテーション研修";
		int deleted = 0;
		List<String> facility = null;
		Timestamp uss = Timestamp.valueOf("2018-11-09 9:00:00");
		Timestamp use = Timestamp.valueOf("2018-11-09 11:00:00");
		Boolean result = sv.setResourseDetailValidate(new Resource
				(resourceId, resourceName, category, officeName, capacity,
						supplement, deleted, facility, uss, use));
		assertThat(result,is(true));
	}


	/**
	 * {@link service.ServiceValidator#setResourseDetailValidate(dto.Resource)} のためのテスト・メソッド。
	 */
	@Test
	public void test2() {
		//fail("まだ実装されていません");
		ServiceValidator sv = new ServiceValidator();
		String resourceId = "r000000001";
		String resourceName = "パブロディエゴホセフランシスコデパウロファンネポムセーノクリス";
		String category = "会議室";
		String officeName = "晴海";
		int capacity = 10;
		String supplement = "プレゼンテーション研修";
		int deleted = 0;
		List<String> facility = null;
		Timestamp uss = Timestamp.valueOf("2018-11-09 9:00:00");
		Timestamp use = Timestamp.valueOf("2018-11-09 11:00:00");
		Boolean result = sv.setResourseDetailValidate(new Resource
				(resourceId, resourceName, category, officeName, capacity,
						supplement, deleted, facility, uss, use));
		assertThat(result,is(false));
	}



	/**
	 * {@link service.ServiceValidator#setResourseDetailValidate(dto.Resource)} のためのテスト・メソッド。
	 */
	@Test
	public void test3() {
		//fail("まだ実装されていません");
		ServiceValidator sv = new ServiceValidator();
		String resourceId = "r000000001";
		String resourceName = "晴海412S";
		String category = "会議室";
		String officeName = "晴海";
		int capacity = -1;
		String supplement = "プレゼンテーション研修";
		int deleted = 0;
		List<String> facility = null;
		Timestamp uss = Timestamp.valueOf("2018-11-09 9:00:00");
		Timestamp use = Timestamp.valueOf("2018-11-09 11:00:00");
		Boolean result = sv.setResourseDetailValidate(new Resource
				(resourceId, resourceName, category, officeName, capacity,
						supplement, deleted, facility, uss, use));
		assertThat(result,is(false));
	}


	/**
	 * {@link service.ServiceValidator#setResourseDetailValidate(dto.Resource)} のためのテスト・メソッド。
	 */
	@Test
	public void test4() {
		//fail("まだ実装されていません");
		ServiceValidator sv = new ServiceValidator();
		String resourceId = "r000000001";
		String resourceName = "晴海412S";
		String category = "会議室";
		String officeName = "晴海";
		int capacity = 1000;
		String supplement = "プレゼンテーション研修";
		int deleted = 0;
		List<String> facility = null;
		Timestamp uss = Timestamp.valueOf("2018-11-09 9:00:00");
		Timestamp use = Timestamp.valueOf("2018-11-09 11:00:00");
		Boolean result = sv.setResourseDetailValidate(new Resource
				(resourceId, resourceName, category, officeName, capacity,
						supplement, deleted, facility, uss, use));
		assertThat(result,is(false));
	}


	/**
	 * {@link service.ServiceValidator#setResourseDetailValidate(dto.Resource)} のためのテスト・メソッド。
	 */
	@Test
	public void test5() {
		//fail("まだ実装されていません");
		ServiceValidator sv = new ServiceValidator();
		String resourceId = "r000000001";
		String resourceName = "新横浜16F会議室F";
		String category = "会議室";
		String officeName = "晴海";
		int capacity = 10;
		String supplement = "プレゼンテーション研修";
		int deleted = 0;
		List<String> facility = null;
		Timestamp uss = null;	//Timestamp.valueOf("2018-11-09 9:00:00");
		Timestamp use = Timestamp.valueOf("2018-11-09 11:00:00");
		Boolean result = sv.setResourseDetailValidate(new Resource
				(resourceId, resourceName, category, officeName, capacity,
						supplement, deleted, facility, uss, use));
		assertThat(result,is(false));
	}

	/**
	 * {@link service.ServiceValidator#setResourseDetailValidate(dto.Resource)} のためのテスト・メソッド。
	 */
	@Test
	public void test6() {
		//fail("まだ実装されていません");
		ServiceValidator sv = new ServiceValidator();
		String resourceId = "r000000001";
		String resourceName = "新横浜16F会議室F";
		String category = "会議室";
		String officeName = "晴海";
		int capacity = 10;
		String supplement = "プレゼンテーション研修";
		int deleted = 0;
		List<String> facility = null;
		Timestamp uss = Timestamp.valueOf("2018-11-09 9:00:00");
		Timestamp use = null; //Timestamp.valueOf("2018-11-09 11:00:00");
		Boolean result = sv.setResourseDetailValidate(new Resource
				(resourceId, resourceName, category, officeName, capacity,
						supplement, deleted, facility, uss, use));
		assertThat(result,is(false));
	}

	/**
	 * {@link service.ServiceValidator#setResourseDetailValidate(dto.Resource)} のためのテスト・メソッド。
	 */
	@Test
	public void test7() {
		//fail("まだ実装されていません");
		ServiceValidator sv = new ServiceValidator();
		String resourceId = "r000000001";
		String resourceName = "新横浜16F会議室F";
		String category = "会議室";
		String officeName = "晴海";
		int capacity = 10;
		String supplement = "プレゼンテーション研修";
		int deleted = 0;
		List<String> facility = null;
		Timestamp uss = null;	//Timestamp.valueOf("2018-11-09 9:00:00");
		Timestamp use = null; //Timestamp.valueOf("2018-11-09 11:00:00");
		Boolean result = sv.setResourseDetailValidate(new Resource
				(resourceId, resourceName, category, officeName, capacity,
						supplement, deleted, facility, uss, use));
		assertThat(result,is(true));
	}


	/**
	 * {@link service.ServiceValidator#setResourseDetailValidate(dto.Resource)} のためのテスト・メソッド。
	 */
	@Test
	public void test8() {
		//fail("まだ実装されていません");
		ServiceValidator sv = new ServiceValidator();
		String resourceId = "r000000001";
		String resourceName = "新横浜16F会議室F";
		String category = "会議室";
		String officeName = "晴海";
		int capacity = 10;
		String supplement = "プレゼンテーション研修";
		int deleted = 0;
		List<String> facility = null;
		Timestamp uss = Timestamp.valueOf("2018-11-09 11:00:00");
		Timestamp use = Timestamp.valueOf("2018-11-09 9:00:00");
		Boolean result = sv.setResourseDetailValidate(new Resource
				(resourceId, resourceName, category, officeName, capacity,
						supplement, deleted, facility, uss, use));
		assertThat(result,is(false));
	}


	/**
	 * {@link service.ServiceValidator#setResourseDetailValidate(dto.Resource)} のためのテスト・メソッド。
	 */
	@Test
	public void test9() {
		//fail("まだ実装されていません");
		ServiceValidator sv = new ServiceValidator();
		String resourceId = "r000000001";
		String resourceName = "新横浜16F会議室F";
		String category = "会議室";
		String officeName = "晴海";
		int capacity = 10;
		String supplement = "プレゼンテーション研修うううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううううう";
		int deleted = 0;
		List<String> facility = null;
		Timestamp uss = Timestamp.valueOf("2018-11-09 9:00:00");
		Timestamp use = Timestamp.valueOf("2018-11-09 11:00:00");
		Boolean result = sv.setResourseDetailValidate(new Resource
				(resourceId, resourceName, category, officeName, capacity,
						supplement, deleted, facility, uss, use));
		assertThat(result,is(false));
	}
}
