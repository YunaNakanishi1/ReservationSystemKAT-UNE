/**
 *
 */
package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author z00s600114
 *
 */
public class FacilityDaoTest_dataSet1 {

	/**
	 * {@link dao.FacilityDao#facility()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test
	public void testFacility() throws SQLException {
		FacilityDao fd = new FacilityDao();
		List<String> facilityList=new ArrayList<String>();
		facilityList.add("ホワイトボード有");

		assertThat(fd.facility().get(0),is(facilityList.get(0)));
	}



}
