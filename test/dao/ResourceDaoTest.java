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

import dto.Resource;

/**
*
* @author リコーITソリューションズ株式会社 team.KAT-UNE
*/
public class ResourceDaoTest {	//完了！！


	@Test
	public void test2_1() throws SQLException {
		ResourceDao rd = new ResourceDao();
		assertThat(rd.delete("r000000001"), is(1));

	}

	@Test
	public void test2_2() throws SQLException {
		ResourceDao rd = new ResourceDao();
		assertThat(rd.delete("u002"), is(0));

	}

	@Test
	public void test2_3() throws SQLException {
		ResourceDao rd = new ResourceDao();
		assertThat(rd.delete(null), is(0));

	}

	@Test
	public void test2_4() throws SQLException {
		ResourceDao rd = new ResourceDao();
		assertThat(rd.delete("A\'or\'A\'=\'A\'"), is(0));

	}


	/**
	 * DataSet1(UT002)のデータ2を使用するテスト
	 * {@link dao.ResourceDao#displayAll()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test
	public void test1_2() throws SQLException {
		//fail("まだ実装されていません");
		dao.ResourceDao resourceDao = new dao.ResourceDao();
		List<dto.Resource> resourceList = new ArrayList<dto.Resource>();

		//テスト用リストを用意
		List<dto.Resource> testList= new ArrayList<dto.Resource>();

		String spl = "";
		List<String> fac = null;
		Timestamp uss = null;
		Timestamp use = null;
		Timestamp uss2 =Timestamp.valueOf("2018-09-11 10:00:00");
		Timestamp use2 =Timestamp.valueOf("2018-09-11 11:00:00");


		testList.add(new Resource("r007","新横浜13F会議室A", "新横浜","会議室",24,spl,1,fac,uss,use));
		testList.add(new Resource("r008","新横浜16F会議室C", "新横浜","会議室",12,spl,1,fac,uss,use));
		testList.add(new Resource("r009","新横浜16F会議室D", "新横浜","会議室",112,spl,0,fac,uss,use));
		testList.add(new Resource("r010","新横浜16F会議室E", "新横浜","会議室",18,spl,1,fac,uss,use));
		testList.add(new Resource("r001","晴海412S", "晴海","会議室",5,spl,0,fac,uss,use));
		testList.add(new Resource("r002","晴海415M", "晴海","会議室",8,spl,1,fac,uss,use));
		testList.add(new Resource("r003","晴海414L", "晴海","会議室",24,"新人教育のため占有",0,fac,uss2,use2));
		testList.add(new Resource("r004","晴海4203【MELBORNE】", "晴海","会議室",12,spl,0,fac,uss,use));
		testList.add(new Resource("r005","晴海4208【VANCOUVER】", "晴海","会議室",8,spl,0,fac,uss,use));
		testList.add(new Resource("u006","晴海UCS-41NI", "晴海","UCS",0,spl,1,fac,uss,use));

		//resourceとtestLsitが一致するかのテスト
		for(int i=0; i<resourceList.size(); i++){
			//System.out.println(i);
			assertThat(resourceList.get(i).getResourceId(),is(testList.get(i).getResourceId()));
			assertThat(resourceList.get(i).getResourceName(),is(testList.get(i).getResourceName()));
			assertThat(resourceList.get(i).getOfficeName(),is(testList.get(i).getOfficeName()));
			assertThat(resourceList.get(i).getCategory(),is(testList.get(i).getCategory()));
			assertThat(resourceList.get(i).getCapacity(),is(testList.get(i).getCapacity()));
			assertThat(resourceList.get(i).getSupplement(),is(testList.get(i).getSupplement()));
			assertThat(resourceList.get(i).getDeleted(),is(testList.get(i).getDeleted()));
			assertThat(resourceList.get(i).getFacility(),is(testList.get(i).getFacility()));
			assertThat(resourceList.get(i).getUsageStopStartDate(),is(testList.get(i).getUsageStopStartDate()));
			assertThat(resourceList.get(i).getUsageStopEndDate(),is(testList.get(i).getUsageStopEndDate()));
		}
	}


	/**
	 * DataSet1(UT002)のデータ2を使用するテスト
	 * {@link dao.ResourceDao#displayAll()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test
	public void test3_1() throws SQLException {
		//fail("まだ実装されていません");
		dao.ResourceDao resourceDao = new dao.ResourceDao();
		String argument = "r000000003";
		List<String> facility = new ArrayList<String>();
		facility.add("ホワイトボード有");
		facility.add("プロジェクター有");

		Resource resource = resourceDao.displayDetails(argument);

		assertThat(resource.getResourceId(),is("r000000003"));
		assertThat(resource.getResourceName(),is("晴海414L"));
		assertThat(resource.getCategory(),is("会議室"));
		assertThat(resource.getOfficeName(),is("晴海"));
		assertThat(resource.getCapacity(),is(24));
		assertThat(resource.getSupplement(),is("新人教育のため占有"));
		assertThat(resource.getUsageStopStartDate(),is(Timestamp.valueOf("2018-11-09 10:00:00")));
		assertThat(resource.getUsageStopEndDate(),is(Timestamp.valueOf("2018-11-09 11:00:00")));
		assertThat(resource.getDeleted(),is(0));
		assertThat(resource.getFacility(),is(facility));

	}

	/**
	 * DataSet1(UT002)のデータ2を使用するテスト
	 * {@link dao.ResourceDao#displayAll()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test
	public void test3_2() throws SQLException {
		//fail("まだ実装されていません");
		dao.ResourceDao resourceDao = new dao.ResourceDao();
		String argument = "null";
		Resource resource = resourceDao.displayDetails(argument);

		assertThat(resource,nullValue());
	}

	/**
	 * DataSet1(UT002)のデータ2を使用するテスト
	 * {@link dao.ResourceDao#displayAll()} のためのテスト・メソッド。
	 * @throws SQLException
	 */
	@Test
	public void test3_3() throws SQLException {
		//fail("まだ実装されていません");
		dao.ResourceDao resourceDao = new dao.ResourceDao();
		String argument = "0003";
		Resource resource = resourceDao.displayDetails(argument);

		assertThat(resource,nullValue());
	}

}

