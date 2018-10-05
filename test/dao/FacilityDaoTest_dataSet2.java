package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FacilityDaoTest_dataSet2 {

	@Test
	public void test2() throws SQLException {

		FacilityDao fd = new FacilityDao();
		List<String> facilityList=new ArrayList<String>();
		facilityList.add("ホワイトボード有");
		facilityList.add("プロジェクター有");
		facilityList.add("来客優先");
		facilityList.add("UCS常設");
		facilityList.add("TV会議システム");

		assertThat(fd.facility().get(0),is(facilityList.get(0)));
		assertThat(fd.facility().get(1),is(facilityList.get(1)));
		assertThat(fd.facility().get(2),is(facilityList.get(2)));
		assertThat(fd.facility().get(3),is(facilityList.get(3)));
		assertThat(fd.facility().get(4),is(facilityList.get(4)));
	}


}
