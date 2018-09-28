package service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;

import dto.User;

public class LogInServiceTest {

//	@Test
	public void test1() {
		User user = new User("u0123456", "password", 0);
		LogInService lis = new LogInService(user);
		assertThat(lis.getInputUser().getUserId(), is("u0123456"));
		assertThat(lis.getInputUser().getPassword(), is("password"));
		assertThat(lis.getInputUser().getAuthority(), is(0));
	}

//	@Test
	public void test2() {
		User user = new User("u0123456", "password", 0);
		LogInService lis = new LogInService(user);
		assertThat(lis.checkHalfWidthChar("u0123456"), is(true));
	}

//	@Test
	public void test3() {
		User user = new User("u0123456", "password", 0);
		LogInService lis = new LogInService(user);
		assertThat(lis.checkHalfWidthChar("う0123456"), is(false));
	}

//	@Test
	public void test4() {
		User user = new User("u0123456", "password", 0);
		LogInService lis = new LogInService(user);
		assertThat(lis.checkHalfWidthChar(null), is(false));
	}

//	@Test
	public void test5() {
		User user = new User("u0123456", "password", 0);
		LogInService lis = new LogInService(user);
		assertThat(lis.validate(), is(true));
	}

//	@Test
	public void test6() {
		User user = new User("あ0123456", "password", 0);
		LogInService lis = new LogInService(user);
		assertThat(lis.validate(), is(false));
	}

//	@Test
	public void test7() {
		User user = new User("u012345", "password", 0);
		LogInService lis = new LogInService(user);
		assertThat(lis.validate(), is(false));
	}

//	@Test
	public void test8() {
		User user = new User("u01234567", "password", 0);
		LogInService lis = new LogInService(user);
		assertThat(lis.validate(), is(false));
	}

//	@Test
	public void test9() {
		User user = new User("u0123456", "ぱassword", 0);
		LogInService lis = new LogInService(user);
		assertThat(lis.validate(), is(false));
	}

//	@Test
	public void test10() {
		User user = new User("u0123456", "password", 0);
		LogInService lis = new LogInService(user);
		try {
			lis.execute();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		assertThat(lis.getResultUser().getUserId(), is("u0123456"));
		assertThat(lis.getResultUser().getPassword(), is("password"));
		assertThat(lis.getResultUser().getAuthority(), is(1));
	}

	//@Test
	public void test11() {
		User user = null;
		LogInService lis = new LogInService(user);
		try {
			lis.execute();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		assertThat(lis.getResultUser(), nullValue());
		assertThat(lis.getValidationMessage(), is("認証に失敗しました"));

	}

}
