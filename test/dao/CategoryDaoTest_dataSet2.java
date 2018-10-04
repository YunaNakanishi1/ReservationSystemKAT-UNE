package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CategoryDaoTest_dataSet2 {

	@Test
	public void testCategory() throws SQLException {
		CategoryDao fd = new CategoryDao();
		List<String> categoryList=new ArrayList<String>();
		categoryList.add("会議室");
		categoryList.add("UCS");
		categoryList.add("UCS2");
		categoryList.add("UCS3");
		categoryList.add("UCS4");

		assertThat(fd.category().get(0),is(categoryList.get(0)));
		assertThat(fd.category().get(1),is(categoryList.get(1)));
		assertThat(fd.category().get(2),is(categoryList.get(2)));
		assertThat(fd.category().get(3),is(categoryList.get(3)));
		assertThat(fd.category().get(4),is(categoryList.get(4)));
	}


}
