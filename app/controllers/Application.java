package controllers;


import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import models.User;
import play.mvc.*;
import play.data.*;
import views.html.*;


public class Application extends Controller {

  //Form用内部クラス
  public static class SampleForm{
    public String loginForm;
  }
  //ログイン画面に飛ばす
  public static Result index(){
    return ok(login.render("Login", new Form(SampleForm.class)));
  }

  public static Result doLogin(){
    //findメソッドでUSER ID(フォーム)からDBを検索。パスワードが一致していればメイン画面表示
    Form<SampleForm> f = new Form(SampleForm.class).bindFromRequest();

    List<User> data = null;
    if(!f.hasErrors()){
      String input = f.get().input;
      data = User.find.where().eq("userid", input).findList();
    }

    return ok(users.render("ログイン後の画面"));
  }

  //ログイン画面で新規作成ボタンを押したときのページ移行
  public static Result goSignup(){
    return ok(signup.render("新規作成画面"));
  }

  //新規作成画面で新規作成ボタンを押したときのアクション
  public static Result doSignup(){
    Form<User> form = new Form(User.class).bindFromRequest();
        if(!form.hasErrors()){
            User requestuser = form.get();
            requestuser.save();
            return redirect(routes.Application.users());
        }else{
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "入力エラー");
            return redirect(routes.Application.goSignup());
        }
  }

}
