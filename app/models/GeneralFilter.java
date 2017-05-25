package models;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;
/**
 *
 * @author r-tominaga
 */

public class GeneralFilter extends Security.Authenticator{

    @Override
    public String getUsername(Context ctx){
      String sessionChk = ctx.session().get("userid");
      if(sessionChk == null){
        return null;
      }
      sessionChk = ctx.session().get("admin");
      if(sessionChk.equals("false")){
        return null;
      }
      return sessionChk;
    }
    @Override
    public Result onUnauthorized(Context ctx){
      String returnUrl = ctx.request().uri();
      if(returnUrl == null){
        returnUrl = "/";
      }
      ctx.session().put("returnUrl", returnUrl);
      return redirect(controllers.routes.Application.goUsers(Long.parseLong(ctx.session().get("id"))));
    }
}
