package service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dto.Resource;

public class RegistResouceServiceTest_data1 {

	@Test
	public void test3() throws SQLException {

			List<String> list=new ArrayList<String>();
			Resource resource=new Resource(null,"晴海414L","晴海", "会議室",24,null,0,list,null,null);
			RegistResourceService service = new RegistResourceService(resource);

			service.execute();
			assertThat(service.getResult(),is(1));
			assertThat(service.getResourceId(),is("r000000001"));
			Resource Resource=service.getResultResource().get(0)
			assertThat(service.getResultResource().get(0),is("r000000001"));


	}

}
