/* Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Right Reserved.
 */
package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
* DataSet1(UT002)のデータ1を使用するテストをまとめています.
* @author リコーITソリューションズ株式会社 team.KAT-UNE
*/
public class ResourceDaoTest_dataSet1_use {	//完了！！

	/**
	 * {@link dao.ResourceDao#displayAll()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test
	public void test1_1() throws SQLException {
		//fail("まだ実装されていません");
		dao.ResourceDao resourceDao = new dao.ResourceDao();
		List<dto.Resource> resourceList = new ArrayList<dto.Resource>();
		resourceList = resourceDao.displayAll();

		assertThat(resourceList.get(0).getResourceId(),is("r003"));
		assertThat(resourceList.get(0).getResourceName(),is("晴海414L"));
		assertThat(resourceList.get(0).getCategory(),is("会議室"));
		assertThat(resourceList.get(0).getOfficeName(),is("晴海"));
		assertThat(resourceList.get(0).getCapacity(),is(24));
		assertThat(resourceList.get(0).getSupplement(),is("新人教育のため占有"));
		assertThat(resourceList.get(0).getUsageStopStartDate(),is(Timestamp.valueOf("2018-09-11 10:00:00")));
		assertThat(resourceList.get(0).getUsageStopEndDate(),is(Timestamp.valueOf("2018-09-11 11:00:00")));
		assertThat(resourceList.get(0).getDeleted(),is(0));
		assertThat(resourceList.get(0).getFacility(),nullValue());

	}

	@Test
	public void testDisplayDetails() throws SQLException {
		//fail("まだ実装されていません");
		ResourceDao rd = new ResourceDao();
		List<String> list = new ArrayList<String>();
		list.add("r000000001");

		assertThat(rd.getMaxId(),is("r000000001"));
	}






}
