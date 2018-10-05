package service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dto.Resource;

public class ChangeResouceServiceTest {

	@Test
	public void test1() {
		List<String> list=new ArrayList<String>();
		Resource resource=new Resource("r000000003","晴海414L","晴海", "会議室",24,null,0,list,null,null);
		ChangeResourceService service = new ChangeResourceService(resource);

		assertThat(service.validate(),is(true));
	}

	@Test
	public void test2() {
		List<String> list=new ArrayList<String>();
		Resource resource=new Resource("r000000003","晴海414L","晴海", "会議室",1000,null,0,list,null,null);
		ChangeResourceService service = new ChangeResourceService(resource);

		assertThat(service.validate(),is(false));
		assertThat(service.getValidationMessage(),is("定員は1～999または0で入力してください"));
	}

	@Test
	public void test3() throws SQLException {
		List<String> list=new ArrayList<String>();
		Resource resource=new Resource("r000000011","晴海414L","晴海", "会議室",1000,null,0,list,null,null);
		ChangeResourceService service = new ChangeResourceService(resource);

		service.execute();

		assertThat(service.getResult(),is(0));
		assertThat(service.getResultResource(),nullValue());

	}


	@Test
	public void test5() throws SQLException {
		List<String> list=new ArrayList<String>();
		Resource resource=new Resource("r000000010","新横浜16F会議室E","新横浜", "会議室",18,null,1,list,null,null);
		ChangeResourceService service = new ChangeResourceService(resource);

		service.execute();

		assertThat(service.getResult(),is(0));


		Resource testResource=service.getResultResource();
		assertThat(testResource.getResourceId(),is("r000000010"));
		assertThat(testResource.getResourceName(),is("新横浜16F会議室E"));
		assertThat(testResource.getOfficeName(),is("新横浜"));
		assertThat(testResource.getCategory(),is("会議室"));
		assertThat(testResource.getCapacity(),is(18));
		assertThat(testResource.getSupplement(),is(""));
		assertThat(testResource.getFacility().get(0),is("ホワイトボード有"));
		assertThat(testResource.getDeleted(),is(1));
		assertThat(testResource.getUsageStopStartDate(),nullValue());
		assertThat(testResource.getUsageStopEndDate(),nullValue());

	}


}
