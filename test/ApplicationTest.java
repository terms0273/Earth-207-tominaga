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

    @Test
    public void testLoginpage(){
      Map<String, String> user  = new HashMap<String, String>();
      user.put("r-tominaga", "207");
      user.put("ttc", "000");
      

    }

    @Test
    public void testRenderLoginpage() {
        Content html = views.html.loginpage.render();
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("ログイン画面");
        assertThat(contentAsString(html)).contains("ログイン");
        assertThat(contentAsString(html)).contains("新規登録");
    }

    public void testRenderSignup(){
      Content html = views.html.signup.render();
      assertThat(contentType(html)).isEqualTo("text/html");
      assertThat(contentAsString(html)).contains("USER ID");
      assertThat(contentAsString(html)).contains("USER NAME");
      assertThat(contentAsString(html)).contains("PASSWORD");

    }

}
