package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.*;

public class Application extends Controller {

  //Form用内部クラス
  public static class LoginForm{
    public String loginForm;
  }

  public static Result index(){
    return ok(loginpage.render("Login", new Form(LoginForm.class)));
  }

  public static Result send(){
    Form<LoginForm> f = form(LoginForm.class).bindFromRequest();

    LoginForm data = f.get();
    return ok(loginpage.render("1234", f));
  }

}
