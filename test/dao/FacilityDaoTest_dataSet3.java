package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FacilityDaoTest_dataSet3 {

	@Test
	public void testFacility() throws SQLException {
		FacilityDao fd = new FacilityDao();
		List<String> facilityList=new ArrayList<String>();
		facilityList = fd.facility();

		assertThat(facilityList.size(),is(0));
	}



}
