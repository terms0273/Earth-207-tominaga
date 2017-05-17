package views;

import org.junit.Test;
import org.junit.Before;

import models.User;
import views.html.user.*;
import helper.UserHelper;

import static org.fest.assertions.Assertions.*;
import static play.test.Helpers.*;
import static play.data.Form.form;

public class UserViewTest {
    @Before public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }

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
