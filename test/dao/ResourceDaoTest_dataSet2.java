/* Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Right Reserved.
 */
package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

/**
* DataSet1(UT002)のデータ2を使用するテストをまとめています.
* @author リコーITソリューションズ株式会社 team.KAT-UNE
*/
public class ResourceDaoTest_dataSet2 {

	/**
	 * {@link dao.ResourceDao#displayAll()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test
	public void test6_2() throws SQLException {
		//fail("まだ実装されていません");
		ResourceDao rd = new ResourceDao();

		assertThat(rd.getMaxId(),is("r000000010"));
	}

}
