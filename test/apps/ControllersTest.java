import org.junit.*;

import static org.fest.assertions.api.Assertions.*;
import static play.test.Helpers.*;

import play.mvc.*;
import models.User;
import static controllers.routes.ref.UserController.*;

public class ControllersTest extends FakeApp{
  @Before
  public void setUpTest() {
    start(fakeApplication(inMemoryDatabase()));
  }

  @Test
  public void callIndex() {
    Result result = callAction(UserController.index());
    assertThat(status(result)).isEqualTo(OK);
    assertThat(contentType(result)).isEqualTo("text/html");
    assertThat(charset(result)).isEqualTo("utf-8");
  }

  //
  @Test
  public void callPostTest() {
    Result result = route(fakeRequest(POST, "/users/post"));

    assertThat(status(result)).isEqualTo(SEE_OTHER);
    assertThat(redirectLocation(result)).isEqualTo("/users");
  }
  /**
    *
    **/
    //フォームに入力した値が正しい時に/usersページに移行しているか
  @Test
  public void loginSuccessTest() {
    Map<String, String> params = new HashMap<String,String>();
    params.put("userid", "207");
    params.put("password", "password");

    Result result = route(
            fakeRequest(POST, "/users")
            .withFormUrlEncodedBody(params)
            );

    assertThat(status(result)).isEqualTo(SEE_OTHER);
    assertThat(redirectLocation(result)).isEqualTo("/users");
    assertThat(session(result)).isNotNull();
  }
  //フォームに入力した値が誤っているときログイン画面に戻りセッションの値がクリアされているか。
  //エラー表示は出ているか
  @Test
    public void loginErrorTest() {

        Map<String,String> params = new HashMap<>();
        params.put("userid","4971");
        params.put("password","incorrect");

        Result result = route(fakeRequest(POST,"/users").withFormUrlEncodedBody(params));
        Form form = new Form(User.class).bindFromRequest();
        User getUser = form.get();
        assertThat(getUser.id).isEmpty();
        assertThat(getUser.password).isEmpty();

        assertThat(status(result)).isEqualTo(BAD_REQUEST);
        assertThat(contentAsString(result)).isEqualTo("/users");
        assertThat(contentAsString(result)).contains("IDかPassword、もしくはその両方が間違っています");
        assertThat(session(result)).isNull();
        assertThat(form).isNull();
    }

}
