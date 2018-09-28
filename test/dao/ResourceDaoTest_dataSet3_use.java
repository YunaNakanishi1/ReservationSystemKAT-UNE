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
public class ResourceDaoTest_dataSet3_use {

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

		assertThat(resourceList.get(0).getResourceId(),is(""));
		assertThat(resourceList.get(0).getResourceName(),is(""));
		assertThat(resourceList.get(0).getCategory(),is(""));
		assertThat(resourceList.get(0).getOfficeName(),is(""));
		assertThat(resourceList.get(0).getCapacity(),nullValue());
		assertThat(resourceList.get(0).getSupplement(),is(""));
		assertThat(resourceList.get(0).getUsageStopStartDate(),nullValue());
		assertThat(resourceList.get(0).getUsageStopEndDate(),nullValue());
		assertThat(resourceList.get(0).getDeleted(),nullValue());
		assertThat(resourceList.get(0).getFacility(),nullValue());
	}
}
