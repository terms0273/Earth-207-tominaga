import org.junit.*;
import controllers.Application.*;

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

import org.mindrot.jbcrypt.BCrypt;


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
    Result result = route(fakeRequest(GET, "/index"));
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
    params.put("userpwd", "12345");
    params.put("userpwd2", "12345");
    params.put("admin", "true");

    Result result = route(
            fakeRequest(POST, "/doSignup")
            .withFormUrlEncodedBody(params)
            );
    assertThat(status(result)).isNotNull();

  }


  //まずDBにユーザー作成をしてやる
  @Test
  public void loginSuccessTest() {
    User user = new User();
    user.id = 1;
    user.userid = "207";
    user.username = "r-tominaga";
    user.password = BCrypt.hashpw("12345", BCrypt.gensalt(13));
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
    assertThat(redirectLocation(result)).isEqualTo("/goUsers/1");
    assertThat(session(result)).isNotNull();
  }
  /**
  *フォームに入力した値が誤っているときログイン画面に戻りセッションの値がクリアされているか。
  *エラー表示は出ているか
  **/
  @Test
    public void loginErrorTest() {
      User user = new User();
      user.id = 1;
      user.userid = "207";
      user.username = "r-tominaga";
      user.password = BCrypt.hashpw("12345", BCrypt.gensalt(13));
      user.admin = true;
      user.dltflg = false;
      user.save();

      Map<String, String> params = new HashMap<String,String>();
      params.put("userid", "4971");
      params.put("password", "incorrect");

      Result result = route(
              fakeRequest(POST, "/doLogin")
              .withFormUrlEncodedBody(params)
              );

      assertThat(status(result)).isEqualTo(BAD_REQUEST);
      assertThat(contentAsString(result)).contains("IDかPassword、もしくはその両方が間違っています");
      assertThat(session(result)).isEmpty();
    }

  @Test
    public void logoutTest() {
      User user = new User();
      user.id = 1;
      user.userid = "207";
      user.username = "r-tominaga";
      user.password = BCrypt.hashpw("12345", BCrypt.gensalt(13));
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
      Result result2 = route(
              fakeRequest(GET, "/logout")
      );
      assertThat(session(result2)).isNull();
      assertThat(status(result2)).isEqualTo(SEE_OTHER);
      assertThat(redirectLocation(result2)).isEqualTo("/");

    }



}
