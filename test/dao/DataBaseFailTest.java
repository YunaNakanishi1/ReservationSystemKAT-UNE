package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import dto.User;

public class DataBaseFailTest {


	//delete(String resourceId)データベース切断
	@Test(expected = SQLException.class)
	public void deletetest2_5() {
		ResourceDao rd = new ResourceDao();
		try {
			assertThat(rd.delete("u002"), is(0));
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

	//getUser(user)データベース切断
	@Test(expected = SQLException.class)
	public void getUsertest1_13() {
		UserDao ud = new UserDao();
		User user = new User("u0123456", "password", 0);
		try {
			assertThat(ud.getUser(user), nullValue());
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	//getUser(user)データベース切断
	@Test(expected = SQLException.class)
	public void getAuthoritytest2_5() {
		UserDao ud = new UserDao();
		try {
			ud.getAuthority("u0123456");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
