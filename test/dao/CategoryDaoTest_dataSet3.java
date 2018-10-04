package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CategoryDaoTest_dataSet3 {

	@Test
	public void test() throws SQLException {
		CategoryDao fd = new CategoryDao();
		List<String> categoryList=new ArrayList<String>();
		categoryList=fd.category();

		assertThat(categoryList.size(),is(0));
	}

}
