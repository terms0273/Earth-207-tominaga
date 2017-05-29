import org.junit.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.fest.assertions.Assertions.*;
import static play.test.Helpers.*;
import com.avaje.ebean.Ebean;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import play.data.Form;
import play.data.*;
import static play.data.Form.form;
import controllers.*;

import play.mvc.*;
import models.User;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.*;
import apps.FakeApp;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class ControllersTest extends FakeApp{
  @Before
  public void setUpTest() {
    start(fakeApplication(inMemoryDatabase()));
  }
  /*
  @Test
  public void callIndex() {
    Result result = route(fakeRequest(GET, "/index/get"));
    assertThat(status(result)).isEqualTo(OK);
    assertThat(contentType(result)).isEqualTo("text/html");
    assertThat(charset(result)).isEqualTo("utf-8");
  }


  @Test
  public void callPostTest() {
    Result result = route(fakeRequest(POST, "/goUsers/post"));

    assertThat(status(result)).isEqualTo(SEE_OTHER);
    assertThat(redirectLocation(result)).isEqualTo("/goUsers");
  }
  */
  /**
    *フォームに入力した値が正しい時にusersに移行しているか
    **/

  @Test
  public void signupTest(){
    Map<String, String> params = new HashMap<String,String>();
    params.put("userid", "207");
    params.put("username", "r-tominaga");
    params.put("password", "12345");
    params.put("password2", "12345");

    Result result = route(
            fakeRequest(POST, "/doSignup")
            .withFormUrlEncodedBody(params)
            );
  }


  //まずDBにユーザー作成をしてやる
  @Test
  public void loginSuccessTest() {
    User user = new User();
    user.userid = "207";
    user.username = "r-tominaga";
    user.password = "12345";
    user.admin = true;
    user.dltflg = false;
    user.save();

    Map<String, String> params = new HashMap<String,String>();
    params.put("userid", "207");
    params.put("password", "12345");

    Result result = route(
            fakeRequest(POST, "/doLogin")
            .withFormUrlEncodedBody(params)
            );

    assertThat(status(result)).isEqualTo(SEE_OTHER);
    assertThat(redirectLocation(result)).isEqualTo("/goUsers");
    assertThat(session(result)).isNotNull();
  }
  /**
  *フォームに入力した値が誤っているときログイン画面に戻りセッションの値がクリアされているか。
  *エラー表示は出ているか
  **/
  @Test
    public void loginErrorTest() {
        Map<String,String> params = new HashMap<>();
        params.put("userid","4971");
        params.put("password","incorrect");

        Result result = route(fakeRequest(POST,"/doLogin").withFormUrlEncodedBody(params));
        Form<User> form = new Form(User.class).bindFromRequest();
        User getUser = form.get();
        assertThat(getUser.userid).isEmpty();
        assertThat(getUser.password).isEmpty();

        assertThat(status(result)).isEqualTo(BAD_REQUEST);
        assertThat(contentAsString(result)).isEqualTo("/index");
        assertThat(contentAsString(result)).contains("IDかPassword、もしくはその両方が間違っています");
        assertThat(session(result)).isNull();
        assertThat(form).isNull();
    }

}
