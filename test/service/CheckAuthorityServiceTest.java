package service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class CheckAuthorityServiceTest {

    @Test
    public void test1() throws SQLException{
        CheckAuthorityService service = new CheckAuthorityService("u0123456");
        service.execute();
        assertThat(service.getAuthority(), is(1));
    }

    @Test
    public void test2() throws SQLException{
        CheckAuthorityService service = new CheckAuthorityService("nothing");
        service.execute();
        assertThat(service.getAuthority(), is(-1));

    }

    @Test
    public void test3() throws SQLException{
        CheckAuthorityService service = new CheckAuthorityService(null);
        service.execute();
        assertThat(service.getAuthority(), is(-1));

    }
}
