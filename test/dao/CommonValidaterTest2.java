package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import handler.CommonValidator;

public class CommonValidaterTest2 {


	@Test
	public void testNotDateOn3_3_1() throws Exception, IllegalArgumentException, InvocationTargetException {
		CommonValidator commonValidator=new CommonValidator();
		java.lang.reflect.Method method =CommonValidator.class.getDeclaredMethod("notDateOn", String.class,String.class,String.class);

		method.setAccessible(true);
		boolean actual=(boolean)method.invoke(commonValidator,"02/28","00","00");

		assertThat(actual,is(false));
	}



}
