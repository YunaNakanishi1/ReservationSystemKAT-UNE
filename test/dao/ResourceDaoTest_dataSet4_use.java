package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dto.Resource;

public class ResourceDaoTest_dataSet4_use {

	@Test
	public void test4_1() throws SQLException {
		ResourceDao resourceDao = new ResourceDao();
		List<String> list = new ArrayList<String>();
		list.add("ホワイトボード有");
		String spl = "";
		Timestamp uss = null;
		Timestamp use = null;
		Resource resource = new Resource("r000000001", "晴海412S", "晴海", "会議室", 10, spl, 0, list, uss, use);
		int check = resourceDao.change(resource);

		assertThat(check, is(1));
	}

	@Test
	public void test4_2() throws SQLException {
		ResourceDao resourceDao = new ResourceDao();
		List<String> list = new ArrayList<String>();
		list.add("ホワイトボード有");
		list.add("プロジェクター有");
		String spl = "";
		Timestamp uss = null;
		Timestamp use = null;
		Resource resource = new Resource("r000000002", "晴海412S", "晴海", "会議室", 8, spl, 0, list, uss, use);
		int check = resourceDao.change(resource);

		assertThat(check, is(0));
	}

	@Test
	public void test4_3() throws SQLException {
		ResourceDao resourceDao = new ResourceDao();
		List<String> list = new ArrayList<String>();
		list.add("ホワイトボード有");
		list.add("プロジェクター有");
		String spl = "";
		Timestamp uss = null;
		Timestamp use = null;
		Resource resource = new Resource("r000000011", "新横浜16F会議室B", "新横浜", "会議室", 50, spl, 1, list, uss, use);
		int check = resourceDao.change(resource);

		assertThat(check, is(1));
	}

	//@Test //エラー発生
	public void test4_4() throws SQLException {
		ResourceDao resourceDao = new ResourceDao();
		Resource resource =  null;
		int check = resourceDao.change(resource);

		assertThat(check, is(0));
	}

	@Test
	public void test4_5() throws SQLException {
		ResourceDao resourceDao = new ResourceDao();
		List<String> list = new ArrayList<String>();
		list.add("ホワイトボード有");
		list.add("プロジェクター有");
		String spl = "";
		Timestamp uss = null;
		Timestamp use = null;
		Resource resource = new Resource("r000000006", "新横浜16F会議室C", "新横浜", "会議室", 50, spl, 0, list, uss, use);
		int check = resourceDao.change(resource);

		assertThat(check, is(0));
	}

	@Test
	public void test4_6() throws SQLException {
		ResourceDao resourceDao = new ResourceDao();
		List<String> list = new ArrayList<String>();
		list.add("ホワイトボード有");
		list.add("プロジェクター有");
		String spl = "";
		Timestamp uss = null;
		Timestamp use = null;
		Resource resource = new Resource("r000000111", "新横浜16F会議室C", "新横浜", "会議室", 112, spl, 0, list, uss, use);
		int check = resourceDao.change(resource);

		assertThat(check, is(0));
	}

	@Test
	public void test4_7() throws SQLException {
		ResourceDao resourceDao = new ResourceDao();
		List<String> list = new ArrayList<String>();
		list.add("ホワイトボード有");
		list.add("プロジェクター有");
		String spl = "";
		Timestamp uss = null;
		Timestamp use = null;
		Resource resource = new Resource("r000000004", "A \' or \' A \' = \' A \' ", "晴海", "会議室", 12, spl, 0, list, uss, use);
		int check = resourceDao.change(resource);

		assertThat(check, is(1));
	}

	@Test
	public void test4_8() throws SQLException {
		ResourceDao resourceDao = new ResourceDao();
		List<String> list = new ArrayList<String>();
		list.add("ホワイトボード有");
		list.add("プロジェクター有");
		String spl = "";
		Timestamp uss = null;
		Timestamp use = null;
		Resource resource = new Resource("r000000001", "晴海412S", "晴海", "会議室", 12, "A \' or \' A \' = \' A \'", 0, list, uss, use);
		int check = resourceDao.change(resource);

		assertThat(check, is(1));
	}


	//@Test //ぬるぽ
	public void test5_1() throws SQLException {
		ResourceDao resourceDao = new ResourceDao();
		Resource resource = null;
		int check = resourceDao.regist(resource);

		assertThat(check, is(0));
	}

	@Test(expected = SQLException.class)
	public void test5_2() throws SQLException {
		ResourceDao resourceDao = new ResourceDao();
		List<String> list = new ArrayList<String>();
		list.add("ホワイトボード有");
		list.add("プロジェクター有");
		String spl = "";
		Timestamp uss = null;
		Timestamp use = null;
		Resource resource = new Resource("r000000001", "晴海412S", "晴海", "会議室", 12, spl, 0, list, uss, use);
		resourceDao.regist(resource);
	}

	@Test
	public void test5_3() throws SQLException {
		ResourceDao resourceDao = new ResourceDao();
		List<String> list = new ArrayList<String>();
		list.add("ホワイトボード有");
		list.add("プロジェクター有");
		String spl = "";
		Timestamp uss = null;
		Timestamp use = null;
		Resource resource = new Resource("r000000120", "新横浜16F会議室F", "晴海", "会議室", 112, spl, 0, list, uss, use);
		resourceDao.regist(resource);
	}

	@Test
	public void test5_4() throws SQLException {
		ResourceDao resourceDao = new ResourceDao();
		List<String> list = new ArrayList<String>();
		list.add("ホワイトボード有");
		list.add("プロジェクター有");
		String spl = "";
		Timestamp uss = null;
		Timestamp use = null;
		Resource resource = new Resource("r000000112", "A \' or \' A \' = \' A \'", "晴海", "会議室", 112, spl, 0, list, uss, use);
		resourceDao.regist(resource);
	}

	@Test
	public void test5_5() throws SQLException {
		ResourceDao resourceDao = new ResourceDao();
		List<String> list = new ArrayList<String>();
		list.add("ホワイトボード有");
		list.add("プロジェクター有");
		String spl = "";
		Timestamp uss = null;
		Timestamp use = null;
		Resource resource = new Resource("r000000113", "新横浜16F会議室C ", "晴海", "会議室", 112, "A \' or \' A \' = \' A \'", 0, list, uss, use);
		resourceDao.regist(resource);
	}
}
