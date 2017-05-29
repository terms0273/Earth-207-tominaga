package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.User;
import org.junit.*;
import apps.FakeApp;
import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;
import static play.data.Form.form;
import play.data.*;
import play.data.Form;
import com.avaje.ebean.Ebean;

public class UserTest extends FakeApp {
    @Test
    public void createDbTest() throws Exception{
      String sql = "INSERT INTO USER VALUES ('1', '207', 'r-tominaga', '12345', true, false);";
      int row = Ebean.createSqlUpdate(sql).execute();
      assertThat(row).isEqualTo(1);

    }

    //保存した内容が正しいか
    @Test
    public void saveDataTest() {
        String userid = "207";
        String username = "r-tominaga";

        User user = new User();
        user.userid = userid;
        user.username = username;
        user.password = "12345";
        user.admin = true;
        user.dltflg = false;

        user.save();

        User actual = User.find.where().eq("userid", userid).findUnique();

        assertThat(actual.userid).isEqualTo(userid);
    }

    @Test
    public void IdCheckTest(){
        List<String> ids = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        ids.add("12345");
        ids.add("1234567890");
        for(String id:ids){
            map.put("Id",id);
            Form<User> form = form(User.class).bind(map);
            assertThat(form.hasErrors()).isFalse();
        }
    }
    /**
     * 異常系のid入力チェック
     */
    @Test
    public void idCheckErrTest(){
        List<String> ids = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        ids.add("1234");
        ids.add("12345678901");
        for(String id:ids){
            map.put("Id",id);
            Form<User> form = form(User.class).bind(map);
            assertThat(form.hasErrors()).isTrue();
        }
    }
    /**
     * 正常系のpassword入力チェック
     */
    @Test
    public void testPwCheck(){
        List<String> pws = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        pws.add("12345");
        pws.add("12345678901234567890");
        for(String id:pws){
            map.put("Id",id);
            Form<User> form = form(User.class).bind(map);
            assertThat(form.hasErrors()).isFalse();
        }
    }
    /**
     * 異常系のpassword入力チェック
     */
    @Test
    public void pwCheckErrTest(){
        List<String> pws = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        pws.add("1234");
        pws.add("123456789012345678901");
        for(String id:pws){
            map.put("Id",id);
            Form<User> form = form(User.class).bind(map);
            assertThat(form.hasErrors()).isTrue();
        }
    }
    /**
     * DBに値を入れ,DBから取得し同じかどうかチェックする
     */
     /*
    @Test
    public void userDbCheckTest(){
        String sql = "select userid,username,password from user where userid=:userid";
        List<String> sqlRows = new ArrayList();
        sqlRows = Ebean.createSqlQuery(sql).setParameter("userid","207").findList();
        assertThat(sqlRows.get(0).getString("password")).isEqualTo(User.user.password);
        assertThat(sqlRows.get(0).getString("username")).isEqualTo(User.user.username);
    }
    */
    /**
     * dbに入れた値がModelにも入っているかチェックする
     */
     /*
    @Test
    public void testUserCheck(){
        User getUser = User.find.byId("207").toString();
        assertThat(getUser.password).isEqualTo(User.user.password);
        assertThat(getUser.username).isEqualTo(User.user.username);
    }*/

}
