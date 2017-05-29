package views;
import controllers.*;

import org.junit.Test;
import org.junit.Before;

import models.User;
import views.html.*;

import static org.fest.assertions.Assertions.*;
import static play.test.Helpers.*;
import static play.data.Form.form;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class ViewTest {

    @Test
    public void testRenderLoginpage() {
        Content html = views.html.login.render("Login", new Form(Application.SampleForm.class));
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Login");
        assertThat(contentAsString(html)).contains("Signup");
    }

    public void testRenderSignup(){
      Content html = views.html.signup.render("新規作成画面", new Form(Application.SignupForm.class));
      assertThat(contentType(html)).isEqualTo("text/html");
      assertThat(contentAsString(html)).contains("USER ID");
      assertThat(contentAsString(html)).contains("USER NAME");
      assertThat(contentAsString(html)).contains("PASSWORD");

    }

}
