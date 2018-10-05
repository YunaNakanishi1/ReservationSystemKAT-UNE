package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import handler.CommonValidator;

public class CommonValidaterTest {

	@Test
	public void testNotSetOn1() throws Exception, IllegalArgumentException, InvocationTargetException {
		CommonValidator commonValidator=new CommonValidator();
		java.lang.reflect.Method method =CommonValidator.class.getDeclaredMethod("notSetOn", String.class);

		method.setAccessible(true);
		boolean actual=(boolean)method.invoke(commonValidator,new Object[]{null});

		assertThat(actual,is(true));
	}

	@Test
	public void testNotSetOn2() throws Exception, IllegalArgumentException, InvocationTargetException {
		CommonValidator commonValidator=new CommonValidator();
		java.lang.reflect.Method method =CommonValidator.class.getDeclaredMethod("notSetOn", String.class);

		method.setAccessible(true);
		boolean actual=(boolean)method.invoke(commonValidator,"");

		assertEquals(true,actual);
	}

	@Test
	public void testNotSetOn3() throws Exception, IllegalArgumentException, InvocationTargetException {
		CommonValidator commonValidator=new CommonValidator();
		java.lang.reflect.Method method =CommonValidator.class.getDeclaredMethod("notSetOn", String.class);

		method.setAccessible(true);
		boolean actual=(boolean)method.invoke(commonValidator,"aaa");

		assertEquals(false,actual);
	}


	@Test
	public void testNotNumericOn1() throws Exception, IllegalArgumentException, InvocationTargetException  {
		CommonValidator commonValidator=new CommonValidator();
		java.lang.reflect.Method method =CommonValidator.class.getDeclaredMethod("notNumericOn", String.class);

		method.setAccessible(true);
		boolean actual=(boolean)method.invoke(commonValidator,new Object[]{null});

		assertThat(actual,is(true));

	}

	@Test
	public void testNotNumericOn2() throws Exception, IllegalArgumentException, InvocationTargetException  {
		CommonValidator commonValidator=new CommonValidator();
		java.lang.reflect.Method method =CommonValidator.class.getDeclaredMethod("notNumericOn", String.class);

		method.setAccessible(true);
		boolean actual=(boolean)method.invoke(commonValidator,"aaa");

		assertThat(actual,is(true));

	}

	@Test
	public void testNotNumericOn3() throws Exception, IllegalArgumentException, InvocationTargetException  {
		CommonValidator commonValidator=new CommonValidator();
		java.lang.reflect.Method method =CommonValidator.class.getDeclaredMethod("notNumericOn", String.class);

		method.setAccessible(true);
		boolean actual=(boolean)method.invoke(commonValidator,"1");

		assertThat(actual,is(false));

	}


	@Test
	public void testNotDateOn1() throws Exception, IllegalArgumentException, InvocationTargetException {
		CommonValidator commonValidator=new CommonValidator();
		java.lang.reflect.Method method =CommonValidator.class.getDeclaredMethod("notDateOn", String.class,String.class,String.class);

		method.setAccessible(true);
		boolean actual=(boolean)method.invoke(commonValidator,"2018/12/10","00","00");

		assertThat(actual,is(false));

	}

	@Test
	public void testNotDateOn2() throws Exception, IllegalArgumentException, InvocationTargetException {
		CommonValidator commonValidator=new CommonValidator();
		java.lang.reflect.Method method =CommonValidator.class.getDeclaredMethod("notDateOn", String.class,String.class,String.class);

		method.setAccessible(true);
		boolean actual=(boolean)method.invoke(commonValidator,"2018/06/31","00","00");

		assertThat(actual,is(true));

	}



	@Test
	public void testNotDateOn3() throws Exception, IllegalArgumentException, InvocationTargetException {
		CommonValidator commonValidator=new CommonValidator();
		java.lang.reflect.Method method =CommonValidator.class.getDeclaredMethod("notDateOn", String.class,String.class,String.class);

		method.setAccessible(true);
		boolean actual=(boolean)method.invoke(commonValidator,"06/31","00","00");

		assertThat(actual,is(true));

	}

	@Test
	public void testNotDateOn4() throws Exception, IllegalArgumentException, InvocationTargetException {
		CommonValidator commonValidator=new CommonValidator();
		java.lang.reflect.Method method =CommonValidator.class.getDeclaredMethod("notDateOn", String.class,String.class,String.class);

		method.setAccessible(true);
		boolean actual=(boolean)method.invoke(commonValidator,"2018/11/11/1","00","00");

		assertThat(actual,is(true));

	}




}
