import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.*;

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

public class ApplicationTest {

    /*フォームの情報。これをDBに保存する。
    public static class User{
      public String userid;
      public String username;
      public String password;
    }*/

    @Test
    public void testRenderLoginpage() {
        Content html = views.html.loginpage.render();
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Login Page");
        assertThat(contentAsString(html)).contains("Login");
        assertThat(contentAsString(html)).contains("Signup");
    }

    public void testRenderSignup(){
      Content html = views.html.signup.render();
      assertThat(contentType(html)).isEqualTo("text/html");
      assertThat(contentAsString(html)).contains("USER ID");
      assertThat(contentAsString(html)).contains("USER NAME");
      assertThat(contentAsString(html)).contains("PASSWORD");

    }

}
