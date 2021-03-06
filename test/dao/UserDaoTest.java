package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import dto.User;

public class UserDaoTest {

    @Test
    public void test1_1() throws SQLException {
        UserDao ud = new UserDao();
        User user = new User("u0123456", "password", 0);
        assertThat(ud.getUser(user).getUserId(), is("u0123456"));
        assertThat(ud.getUser(user).getPassword(), is("password"));
        assertThat(ud.getUser(user).getAuthority(), is(1));

    }

    //	@Test
    public void test1_2() {
        UserDao ud = new UserDao();
        User user = new User("u1123456", "password", 0);
        try {
            assertThat(ud.getUser(user), nullValue());
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    //@Test
    public void test1_3() {
        UserDao ud = new UserDao();
        User user = new User("u0123456", "pass", 0);
        try {
            assertThat(ud.getUser(user), nullValue());
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    //@Test
    public void test1_4() {
        UserDao ud = new UserDao();
        User user = new User("u1123456", "pass", 0);
        try {
            assertThat(ud.getUser(user), nullValue());
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    //@Test
    public void test1_5() {
        UserDao ud = new UserDao();
        User user = new User("u1123456", null, 0);
        try {
            assertThat(ud.getUser(user), nullValue());
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    //@Test
    public void test1_6() {
        UserDao ud = new UserDao();
        User user = new User(null, "pass", 0);
        try {
            assertThat(ud.getUser(user), nullValue());
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    //@Test
    public void test1_7() {
        UserDao ud = new UserDao();
        User user = new User("u0123456", null, 0);
        try {
            assertThat(ud.getUser(user), nullValue());
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    //@Test
    public void test1_8() {
        UserDao ud = new UserDao();
        User user = new User(null, "password", 0);
        try {
            assertThat(ud.getUser(user), nullValue());
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    //@Test
    public void test1_9() {
        UserDao ud = new UserDao();
        User user = new User(null, null, 0);
        try {
            assertThat(ud.getUser(user), nullValue());
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    //@Test
    public void test1_10() {
        UserDao ud = new UserDao();
        User user = null;
        try {
            assertThat(ud.getUser(user), nullValue());
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    //@Test
    public void test1_11() {
        UserDao ud = new UserDao();
        User user = new User("A\'or\'A\'=\'A\'", "A\'or\'A\'=\'A\'", 0);
        try {
            assertThat(ud.getUser(user), nullValue());
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    @Test
    public void test1_12() {
        UserDao ud = new UserDao();
        User user = new User("u0000007", "password", 0);
        try {
            assertThat(ud.getUser(user), nullValue());
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    @Test
    public void test2_1() throws SQLException{
        UserDao ud = new UserDao();
        assertThat(ud.getAuthority("u0123456"), is(1));

    }

    @Test
    public void test2_2() throws SQLException{
        UserDao ud = new UserDao();
        assertThat(ud.getAuthority(null), is(-1));

    }

    @Test
    public void test2_3() throws SQLException{
        UserDao ud = new UserDao();
        assertThat(ud.getAuthority("nothing"), is(-1));

    }

    @Test
    public void test2_4() throws SQLException{
        UserDao ud = new UserDao();
        assertThat(ud.getAuthority("A\'or\'A\'=\'A\'"), is(-1));

    }


}
