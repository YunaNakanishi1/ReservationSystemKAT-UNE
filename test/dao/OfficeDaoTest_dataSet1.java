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
public class OfficeDaoTest_dataSet1 {

	/**
	 * {@link dao.OfficeDao#officeName()} のためのテスト・メソッド。
	 */
	@Test
	public void test1() throws SQLException  {
//		fail("まだ実装されていません");
		OfficeDao od = new OfficeDao();
		List<String> officeList = new ArrayList<String>();
		officeList.add("晴海");

		for(int i=0;i<officeList.size();i++){
			assertThat(od.officeName().get(i),is(officeList.get(i)));
		}
	}
}