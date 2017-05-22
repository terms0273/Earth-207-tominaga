package controllers;



import javax.swing.JFrame;
import javax.swing.JOptionPane;
import models.User;
import play.mvc.*;
import play.data.*;
import views.html.*;
import org.mindrot.jbcrypt.BCrypt;


public class Application extends Controller {

  //Form用内部クラス
  public static class SampleForm{
    public String userid;
    public String password;
  }
  //ログイン画面に飛ばす
  public static Result index(){
    return ok(login.render("Login", new Form(SampleForm.class)));
  }

/*public static Result doLogin() {
  Form<Login> loginForm = new Form(SampleForm.class).bindFromRequest();
  if (loginForm.hasErrors()) {
      return badRequest(login.render("ログイン失敗", new Form(SampleForm.class)));
  } else {
      session().clear();
      session("userid", loginForm.get().userid);
      return redirect(routes.Application.goUsers(""));
  }
}*/

  public static Result doLogin(){
    //findメソッドでUSER ID(フォーム)からDBを検索。パスワードが一致していればメイン画面表示
    Form<SampleForm> f = new Form(SampleForm.class).bindFromRequest();
    boolean errFlg = false;
    User data = null;
    if(!f.hasErrors()){
      //フォームにエラーなし
      String input = f.get().userid;
      String input2 = f.get().password;
      System.out.println(input);
      System.out.println(input2);
      //input2 = BCrypt.hashpw(input2, BCrypt.gensalt());

      data = User.find.where().eq("userid", input).findUnique(); //このdataにユーザーの登録情報が入っている。

      if(data != null && BCrypt.checkpw(input2, data.password)){
        setSession(data);
        System.out.print(session("userid"));
        return redirect(routes.Application.goUsers());
      }else{
        errFlg = true;
      }

    }else{
      //フォームにエラーがあった場合
      errFlg = true;
    }
    //フォームにエラーがあろうとなかろうと共通
    if(errFlg == true){
      return badRequest(login.render("ログイン失敗", new Form(SampleForm.class)));
    }else{
      ;
    }
    return ok(login.render("", new Form(SampleForm.class)));
  }

  //ログイン画面で新規作成ボタンを押したときのページ移行
  public static Result goSignup(){
    return ok(signup.render("新規作成画面", new Form(SampleForm.class)));
  }

  //新規作成画面で新規作成ボタンを押したときのアクション
  public static Result doSignup(){
    Form<User> form = new Form(User.class).bindFromRequest();
        if(!form.hasErrors()){
            User requestuser = form.get();
            requestuser.password = BCrypt.hashpw(requestuser.password, BCrypt.gensalt(13));
            requestuser.save();
            return redirect(routes.Application.goUsers());
        }else{
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "入力エラー");
            return redirect(routes.Application.goSignup());
        }
  }

  public static Result goUsers(){
    return ok(users.render("基本情報落ちた、日本死ね"));
  }

  public static Result logout(){
        clearSession();
        System.out.println(session("userid"));
        return redirect(routes.Application.index());
    }

    private static void setSession(User user) {
        session("username",user.username);
        session("userid",user.userid);
    }

    private static void clearSession() {
        session().clear();
    }

}
