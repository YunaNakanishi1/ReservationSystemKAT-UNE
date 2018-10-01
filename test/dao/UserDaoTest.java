package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import dto.User;

public class UserDaoTest {

	@Test
	public void test1_1() throws SQLException {
		UserDao ud = new UserDao();
		User user = new User("u0123456", "password", 0);
		assertThat(ud.getUser(user).getUserId(), is("u0123456"));
		assertThat(ud.getUser(user).getPassword(), is("password"));
		assertThat(ud.getUser(user).getAuthority(), is(1));

	}

	@Test
	public void test1_2() throws SQLException {
		UserDao ud = new UserDao();
		User user = new User("u1123456", "password", 0);
		assertThat(ud.getUser(user), nullValue());

	}

	@Test
	public void test1_3() throws SQLException {
		UserDao ud = new UserDao();
		User user = new User("u0123456", "pass", 0);
		assertThat(ud.getUser(user), nullValue());

	}

	@Test
	public void test1_4() throws SQLException {
		UserDao ud = new UserDao();
		User user = new User("u1123456", "pass", 0);
		assertThat(ud.getUser(user), nullValue());

	}

	@Test
	public void test1_5() throws SQLException {
		UserDao ud = new UserDao();
		User user = new User("u1123456", null, 0);
		assertThat(ud.getUser(user), nullValue());

	}

	@Test
	public void test1_6() throws SQLException {
		UserDao ud = new UserDao();
		User user = new User(null, "pass", 0);
		assertThat(ud.getUser(user), nullValue());

	}

	@Test
	public void test1_7() throws SQLException {
		UserDao ud = new UserDao();
		User user = new User("u0123456", null, 0);
		assertThat(ud.getUser(user), nullValue());

	}

	@Test
	public void test1_8() throws SQLException {
		UserDao ud = new UserDao();
		User user = new User(null, "password", 0);
		assertThat(ud.getUser(user), nullValue());
	}

	@Test
	public void test1_9() throws SQLException {
		UserDao ud = new UserDao();
		User user = new User(null, null, 0);
		assertThat(ud.getUser(user), nullValue());
	}

	@Test
	public void test1_10() throws SQLException {
		UserDao ud = new UserDao();
		User user = null;
			assertThat(ud.getUser(user), nullValue());

	}

	//@Test
	public void test1_11() throws SQLException {
		UserDao ud = new UserDao();
		User user = new User("A\'or\'A\'=\'A\'", "A\'or\'A\'=\'A\'", 0);
		assertThat(ud.getUser(user), nullValue());

	}

	@Test
	public void test1_12() throws SQLException {
		UserDao ud = new UserDao();
		User user = new User("u0000007", "password", 0);
		assertThat(ud.getUser(user), nullValue());

	}

	@Test
	public void test2_1() throws SQLException {
		UserDao ud = new UserDao();
		assertThat(ud.getAuthority("u0123456"), is(1));

	}

	@Test
	public void test2_2() throws SQLException {
		UserDao ud = new UserDao();
		assertThat(ud.getAuthority(null), is(-1));

	}

	@Test
	public void test2_3() throws SQLException {
		UserDao ud = new UserDao();
		assertThat(ud.getAuthority("nothing"), is(-1));

	}

	@Test
	public void test2_4() throws SQLException {
		UserDao ud = new UserDao();
		assertThat(ud.getAuthority("A\'or\'A\'=\'A\'"), is(-1));
	}


}
