import org.junit.*;

import static org.fest.assertions.api.Assertions.*;
import static play.test.Helpers.*;

import play.mvc.*;
import models.User;
import static controllers.routes.ref.UserController.*;

public class ControllersTest{
  @Before public void setUp() {
    start(fakeApplication(inMemoryDatabase()));
  }

  @Test public void callIndex() {
    Result result = callAction(UserController.index());
    assertThat(status(result)).isEqualTo(OK);
    assertThat(contentType(result)).isEqualTo("text/html");
    assertThat(charset(result)).isEqualTo("utf-8");
  }

  @Test public void callPost() {
    Result result = route(fakeRequest(POST, "/users/post"));

    assertThat(status(result)).isEqualTo(SEE_OTHER);
    assertThat(redirectLocation(result)).isEqualTo("/users");
  }

  @Test public void callCreate() {
    Map<String, String> params = new HashMap<String,String>();
    params.put("userid", "207");
    params.put("username", "r-tominaga");
    params.put("password", "password");

    Result result = route(
            fakeRequest(POST, "/users")
            .withFormUrlEncodedBody(params)
            );

    assertThat(status(result)).isEqualTo(SEE_OTHER);
    assertThat(redirectLocation(result)).isEqualTo("/users");
    assertThat(status(result)).isEqualTo(BAD_REQUEST);
  }



}
