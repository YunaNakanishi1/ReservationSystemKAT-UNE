/* Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Right Reserved.
 */
package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
* DataSet1(UT002)のデータ3を使用するテストをまとめています.
* @author リコーITソリューションズ株式会社 team.KAT-UNE
*/
public class ResourceDaoTest_dataSet3_use {	//完了！！

	/**
	 * {@link dao.ResourceDao#displayAll()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test
	public void test1_3() throws SQLException {
		//fail("まだ実装されていません");
		dao.ResourceDao resourceDao = new dao.ResourceDao();
		List<dto.Resource> resourceList = new ArrayList<dto.Resource>();
		resourceList = resourceDao.displayAll();

		assertThat(resourceList.size(),is(0));

	}

	/**
	 * {@link dao.ResourceDao#getMaxId()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test
	public void test6_3() throws SQLException {
		//fail("まだ実装されていません");
		ResourceDao rd = new ResourceDao();

		assertThat(rd.getMaxId(),is(nullValue()));
	}
}
