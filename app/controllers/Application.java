package controllers;

import static controllers.Application.edit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import models.User;
import models.Login;
import play.mvc.*;
import play.data.*;
import play.data.Form;
import static play.data.Form.form;
import play.filters.csrf.AddCSRFToken;
import play.filters.csrf.RequireCSRFCheck;
import views.html.*;
import org.mindrot.jbcrypt.BCrypt;
import static scala.sys.process.BasicIO.input;
import java.util.*;


public class Application extends Controller {

  @RequireCSRFCheck
  public static Result authenticate(){
    Form<SampleForm> loginForm = form(SampleForm.class).bindFromRequest();
    if(loginForm.hasErrors()){
      return badRequest(views.html.login.render("ログイン", loginForm));
    }else{
      session("userid", loginForm.get().userid);
      String returnUrl = ctx().session().get("returnUrl");
      if(returnUrl == null || returnUrl.equals("") || returnUrl.equals(routes.Application.index().absoluteURL(request()))){
        returnUrl = routes.Application.doLogin().url();
      }
      return redirect(returnUrl);
    }
  }

  //ログイン画面に飛ばす。ここ名前変えたいな～。
  public static Result index(){
    return ok(login.render("Login", new Form(SampleForm.class)));
  }
  //Form用内部クラス
  public static class SampleForm{
    public String userid;
    public String password;
  }

  public static class PwdchForm{
    public String oldpwd;
    public String newpwd;
    public String newpwd2;
  }

  public static class SignupForm{
    public String userid;
    public String username;
    public String userpwd;
    public String userpwd2;
    public boolean admin;
  }

  public static class EditUser{
    public String userid;
    public String username;
  }


  @AddCSRFToken
  public static Result doLogin(){
    //findメソッドでUSER ID(フォーム)からDBを検索。パスワードが一致していればメイン画面表示
    Form<SampleForm> f = new Form(SampleForm.class).bindFromRequest();
    boolean errFlg = false;
    User data = null;
    if(!f.hasErrors()){
      //フォームにエラーなし
      String input = f.get().userid;
      String input2 = f.get().password;
      //input2 = BCrypt.hashpw(input2, BCrypt.gensalt());

      data = User.find.where().eq("userid", input).findUnique();
      //このdataにユーザーの登録情報が入っている。

      if(data != null && BCrypt.checkpw(input2, data.password) && data.dltflg == false){
        setSession(data);
        return redirect(routes.Application.goUsers(data.id));
      }else{
        errFlg = true;
      }
    }else{
      //フォームにエラーがあった場合
      errFlg = true;
    }
    //フォームにエラーがあろうとなかろうと共通
    if(errFlg == true){
      return badRequest(login.render("IDかPassword、もしくはその両方が間違っています", new Form(SampleForm.class)));
    }else{
      ;
    }
    return ok(login.render("", new Form(SampleForm.class)));
  }

  //ログイン画面で新規作成ボタンを押したときのページ移行
  public static Result goSignup(){
    return ok(signup.render("新規作成画面", new Form(SignupForm.class)));
  }

  //新規作成画面で新規作成ボタンを押したときのアクション
  @AddCSRFToken
  public static Result doSignup(){
    Form<SignupForm> form = new Form(SignupForm.class).bindFromRequest();
        if(!form.hasErrors() ){
          String input1 = form.get().userid;
          String input2 = form.get().username;
          String input3 = form.get().userpwd;
          String input4 = form.get().userpwd;
          boolean input5 = form.get().admin;
            if(input3.equals(input4)){
              User requestuser = new User();
              requestuser.username = input2;
              requestuser.userid = input1;
              requestuser.admin = input5;
              requestuser.password = BCrypt.hashpw(input1, BCrypt.gensalt(13));
              requestuser.save();
              setSession(requestuser);
              return redirect(routes.Application.goUsers(requestuser.id));
            }else{
                //新しく入力したパスが同一でないことを警告
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "パスワードが同一でありません。");
            }
        }else{
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "入力エラー");
            return redirect(routes.Application.goSignup());
        }
        return redirect(routes.Application.goSignup());
  }
  @play.mvc.Security.Authenticated(models.Secured.class)
  public static Result goUsers(Long id){
    return ok(users.render("Earth", id));
  }
  @play.mvc.Security.Authenticated(models.Secured.class)
  public static Result logout(){
        session().clear();
        return redirect(routes.Application.index());
  }

    private static void setSession(User user) {
        session("username",user.username);
        session("userid",user.userid);
        session("admin", String.valueOf(user.admin));
        session("id", String.valueOf(user.id));
    }

    @play.mvc.Security.Authenticated(models.Secured.class)
    public static Result edit(Long id){
      User requestuser = User.find.byId(id);
      Form<User> eForm = new Form(User.class).fill(requestuser);
      return ok(edit.render("ユーザー編集画面", eForm, id));
    }

    @play.mvc.Security.Authenticated(models.Secured.class)
    public static Result update(Long id){
      Form<EditUser> eForm = new Form(EditUser.class).bindFromRequest();
      int userRows = 0;
      if(!eForm.hasErrors()){
        String input = eForm.get().userid;
        User inputForm = User.find.byId(id);
        userRows = User.find.where().eq("userid", input).findRowCount();
        if(userRows > 0 && !inputForm.userid.equals(input)){
          JFrame frame = new JFrame();
          JOptionPane.showMessageDialog(frame, "既にそのUSER IDは使われています。");
        }else{
          inputForm.userid = eForm.get().userid;
          inputForm.username = eForm.get().username;
          inputForm.update();
        }
      }else{
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "正しく入力してください。");
      }
      return redirect(routes.Application.edit(id));
    }
  @play.mvc.Security.Authenticated(models.GeneralFilter.class)
  public static Result userlist(Long id){
     List<models.User> userindex = new ArrayList<models.User>();
     userindex = User.find.where().eq("dltflg", false).findList();
    return ok(userlist.render("登録ユーザー", userindex, id));
  }
  @play.mvc.Security.Authenticated(models.GeneralFilter.class)
  public static Result delete(Long id){
    JFrame frame = new JFrame();
    int option = JOptionPane.showConfirmDialog(frame,
      "本当に削除しますか？","削除の確認", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
    if(option == 0){

        User dlt = User.find.byId(id);
        dlt.dltflg = true;
        dlt.update();

    }
    return redirect(routes.Application.userlist(id));
  }
  @play.mvc.Security.Authenticated(models.Secured.class)
  public static Result editpwd(Long id){
    return ok(editpwd.render("パスワード変更", new Form(SampleForm.class), id));
  }

  @play.mvc.Security.Authenticated(models.Secured.class)
  public static Result changePwd(Long id){
    User user = User.find.byId(id);
    Form<PwdchForm> form = new Form(PwdchForm.class).bindFromRequest();
    if(!form.hasErrors()){
        //パスワード変更用のクラス作る
        String input1 = form.get().oldpwd;
        String input2 = form.get().newpwd;
        String input3 = form.get().newpwd2;
        if(BCrypt.checkpw(input1, user.password)){
          if(input2.equals(input3)){
            user.password = BCrypt.hashpw(input2, BCrypt.gensalt(13));
            user.update();
          }else{
              //新しく入力したパスが同一でないことを警告
              JFrame frame = new JFrame();
              JOptionPane.showMessageDialog(frame, "新パスワードが同一でありません。");
          }
        }else{
        //フォームの入力エラー
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "入力エラー");
        }
        return redirect(routes.Application.goUsers(id));
    }
    return null;
  }
}
