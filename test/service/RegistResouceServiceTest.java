package service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dto.Resource;

public class RegistResouceServiceTest {

	@Test
	public void test1() {
		List<String> list=new ArrayList<String>();
		Resource resource=new Resource(null,"晴海414L","晴海", "会議室",24,null,0,list,null,null);
		RegistResourceService service = new RegistResourceService(resource);

		assertThat(service.validate(),is(true));
	}


	@Test
	public void test2() {
		List<String> list=new ArrayList<String>();
		Resource resource=new Resource(null,"晴海414L","晴海", "会議室",1000,null,0,list,null,null);
		RegistResourceService service = new RegistResourceService(resource);

		assertThat(service.validate(),is(false));
		assertThat(service.getValidationMessage(),is("定員は1～999または0で入力してください"));
	}





}
