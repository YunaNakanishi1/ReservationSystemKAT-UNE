package service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class DeleteResourceServiceTest {

	@Test
	public void test1() {
		DeleteResourceService drs = new DeleteResourceService("r006");
		assertThat(drs.getResourceId(), is("r006"));
	}

	@Test
	public void test2() {
		DeleteResourceService drs = new DeleteResourceService("r001");
		try {
			drs.execute();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		assertThat(drs.getResult(), is(1));
	}

	@Test
	public void test3() {
		DeleteResourceService drs = new DeleteResourceService("u005");
		try {
			drs.execute();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		assertThat(drs.getResult(), is(0));
	}

	@Test
	public void test4() {
		DeleteResourceService drs = new DeleteResourceService(null);
		try {
			drs.execute();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		assertThat(drs.getResult(), is(0));
	}

	@Test
	public void test5() {
		DeleteResourceService drs = new DeleteResourceService("r006");
		try {
			drs.execute();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		assertThat(drs.getResult(), is(0));
	}
}
