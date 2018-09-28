package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class ResourceDaoTest {

	//@Test
	public void test2_1() {
		ResourceDao rd = new ResourceDao();
		try {
			assertThat(rd.delete("r001"), is(1));
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	//@Test
	public void test2_2() {
		ResourceDao rd = new ResourceDao();
		try {
			assertThat(rd.delete("u002"), is(0));
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	//@Test
	public void test2_3() {
		ResourceDao rd = new ResourceDao();
		try {
			assertThat(rd.delete(null), is(0));
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	//@Test
	public void test2_4() {
		ResourceDao rd = new ResourceDao();
		try {
			assertThat(rd.delete("A\'or\'A\'=\'A\'"), is(0));
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	@Test(expected = SQLException.class)
	public void test2_5() {
		ResourceDao rd = new ResourceDao();
		try {
			assertThat(rd.delete("u002"), is(0));
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
