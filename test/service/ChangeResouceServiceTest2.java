package service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dto.Resource;

public class ChangeResouceServiceTest2 {

	@Test
	public void test4() throws SQLException {
		List<String> list=new ArrayList<String>();
		Resource resource=new Resource("r000000003","晴海414L","晴海", "会議室",24,null,0,list,null,null);
		ChangeResourceService service = new ChangeResourceService(resource);

		service.execute();

		assertThat(service.getResult(),is(1));


		Resource testResource=service.getResultResource();
		assertThat(testResource.getResourceId(),is("r000000003"));
		assertThat(testResource.getResourceName(),is("晴海414L"));
		assertThat(testResource.getOfficeName(),is("晴海"));
		assertThat(testResource.getCategory(),is("会議室"));
		assertThat(testResource.getCapacity(),is(24));
		assertThat(testResource.getSupplement(),nullValue());
		assertThat(testResource.getFacility(),is(new ArrayList<String>()));
		assertThat(testResource.getDeleted(),is(0));
		assertThat(testResource.getUsageStopStartDate(),nullValue());
		assertThat(testResource.getUsageStopEndDate(),nullValue());
	}

}
