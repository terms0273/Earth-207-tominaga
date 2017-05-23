package controllers;

import static controllers.Application.edit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import models.User;
import play.mvc.*;
import play.data.*;
import views.html.*;
import org.mindrot.jbcrypt.BCrypt;
import static scala.sys.process.BasicIO.input;
import java.util.*;

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
      //input2 = BCrypt.hashpw(input2, BCrypt.gensalt());

      data = User.find.where().eq("userid", input).findUnique(); //このdataにユーザーの登録情報が入っている。

      if(data != null && BCrypt.checkpw(input2, data.password)){
        setSession(data);
        System.out.print(session("userid"));
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
            return redirect(routes.Application.goUsers(requestuser.id));
        }else{
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "入力エラー");
            return redirect(routes.Application.goSignup());
        }
  }

  public static Result goUsers(Long id){
    return ok(users.render("Earth", id));
  }

  public static Result logout(){
        clearSession();
        System.out.println(session("userid"));
        return redirect(routes.Application.index());
    }

    private static void setSession(User user) {
        session("username",user.username);
        session("userid",user.userid);
        session("id", String.valueOf(user.id));
    }

    private static void clearSession() {
        session().clear();
    }

    public static Result edit(Long id){
      User requestuser = User.find.byId(id);
      Form<User> eForm = new Form(User.class).fill(requestuser);
      return ok(edit.render("ユーザー編集画面", eForm, id));
      }

    public static Result update(Long id){
      Form<User> eForm = new Form(User.class).bindFromRequest();
      int userRows = 0;
      if(!eForm.hasErrors()){
        String input = eForm.get().userid;
        User inputForm = User.find.byId(id);
        userRows = User.find.where().eq("userid", input).findRowCount();
        System.out.println(userRows);
        if(userRows > 0){
          JFrame frame = new JFrame();
          JOptionPane.showMessageDialog(frame, "既にそのUSER IDは使われています。");
        }else{
          inputForm.userid = eForm.get().userid;
          inputForm.username = eForm.get().username;
          inputForm.update();
          System.out.println(inputForm.userid);

        }
      }else{
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "正しく入力してください。");
      }
      return redirect(routes.Application.edit(id));
  }

  public static Result userlist(Long id){
     List<models.User> userindex = new ArrayList<models.User>();
     userindex = User.find.findList();
    return ok(userlist.render("登録ユーザー", userindex, id));
  }

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

  public static Result editpwd(Long id){
    return ok(editpwd.render("パスワード変更", new Form(SampleForm.class), id));
  }

  public static Result changePwd(Long id){
    User user = User.find.byId(id);
    Form<User> form = new Form(User.class).bindFromRequest();
    if(!form.hasErrors()){
      　//パスワード変更用のクラス作る
        String input1 = form.get().oldpwd;
        String input2 = form.get().newpwd;
        String input3 = form.get().newpwd2;
        if(BCrypt.checkpw(input1, user.password)){
          if(input2 == input3){
            user.password = BCrypt.hashpw(input2, BCrypt.gensalt(13));
            user.update();
          }else{
              //新しく入力したパスが同一でないことを警告
          }
        }else{
        //フォームの入力エラー
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "入力エラー");
        }
        return redirect(routes.Application.goUsers(id));
    }
  }
}
