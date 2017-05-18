package models;

import models.User;
import org.junit.*;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class UserTest extends FakeApp {
    //保存した内容が正しいか
    @Test
    public void saveDataTest() {
        String userId = "207";
        User user = new User(userId , "r-tominaga", "password");
        user.save();

        User actual = User.findData(userId);

        assertThat(actual.userId).isEqualTo(userId);
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
    @Test
    public void userDbCheckTest(){

        String sql = "select id,username,password from User where id=:id";
        List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).setParameter("id","205").findList();
        assertThat(sqlRows.get(0).getString("password")).isEqualTo(user.password);
        assertThat(sqlRows.get(0).getString("username")).isEqualTo(user.nickname);
    }
    /**
     * dbに入れた値がModelにも入っているかチェックする
     */
    @Test
    public void testUserCheck(){
        User getUser = User.find.byId("207");
        assertThat(getUser.password).isEqualTo(user.password);
        assertThat(getUser.nickname).isEqualTo(user.nickname);
    }

}
