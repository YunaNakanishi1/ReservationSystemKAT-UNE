package service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import dto.User;

public class LogInServiceTest {

	@Test
	public void test1() {
		User user = new User("u0123456", "password", 0);
		LogInService lis = new LogInService(user);
		assertThat(lis.getInputUser().getUserId(), is("u0123456"));
		assertThat(lis.getInputUser().getPassword(), is("password"));
		assertThat(lis.getInputUser().getAuthority(), is(0));
	}

	@Test
	public void test2() {


	}

}
