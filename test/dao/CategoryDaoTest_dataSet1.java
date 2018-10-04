package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CategoryDaoTest_dataSet1 {

	@Test
	public void testCategory() throws SQLException {
		CategoryDao fd = new CategoryDao();
		List<String> categoryList=new ArrayList<String>();
		categoryList.add("会議室");

		assertThat(fd.category().get(0),is(categoryList.get(0)));
	}


}
