/* Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Right Reserved.
 */
package service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dto.Resource;


/**
*
* @author リコーITソリューションズ株式会社 team.KAT-UNE
*/
public class ShowResourceDetailsServiceTest {	//完了！！

	/**
	 * {@link service.ShowResourceDetailsService#validate()} のためのテスト・メソッド。
	 */
	@Test
	public void testValidate() {
		//fail("まだ実装されていません");
		ShowResourceDetailsService showResourceDetailsservice = new ShowResourceDetailsService("r000000003");
		assertEquals(true, showResourceDetailsservice.validate());
	}

	/**
	 * {@link service.ShowResourceDetailsService#execute()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test
	public void test1() throws SQLException {
		//fail("まだ実装されていません");
		ShowResourceDetailsService showResourceDetailsservice = new ShowResourceDetailsService("r000000003");
		showResourceDetailsservice.execute();

		Resource resource = showResourceDetailsservice.getResult();

		List<String> fac = new ArrayList<String>();
		fac.add("ホワイトボード有");
		fac.add("プロジェクター有");

		Timestamp uss =Timestamp.valueOf("2018-11-09 10:00:00");
		Timestamp use =Timestamp.valueOf("2018-11-09 11:00:00");

		assertThat(resource.getResourceId(),is("r000000003"));
		assertThat(resource.getResourceName(),is("晴海414L"));
		assertThat(resource.getOfficeName(),is("晴海"));
		assertThat(resource.getCategory(),is("会議室"));
		assertThat(resource.getCapacity(),is(24));
		assertThat(resource.getSupplement(),is("新人教育のため占有"));
		assertThat(resource.getDeleted(),is(0));
		assertThat(resource.getFacility(),is(fac));
		assertThat(resource.getUsageStopStartDate(),is(uss));
		assertThat(resource.getUsageStopEndDate(),is(use));
	}

	/**
	 * {@link service.ShowResourceDetailsService#execute()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test
	public void test2() throws SQLException {
		//fail("まだ実装されていません");
		ShowResourceDetailsService showResourceDetailsservice = new ShowResourceDetailsService("nothing");
		showResourceDetailsservice.execute();

		Resource resource = showResourceDetailsservice.getResult();

		assertThat(resource,nullValue());
	}


	/**
	 * {@link service.ShowResourceDetailsService#execute()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test
	public void test3() throws SQLException {
		//fail("まだ実装されていません");
		ShowResourceDetailsService showResourceDetailsservice = new ShowResourceDetailsService(null);
		showResourceDetailsservice.execute();

		Resource resource = showResourceDetailsservice.getResult();

		assertThat(resource,nullValue());
	}
}
