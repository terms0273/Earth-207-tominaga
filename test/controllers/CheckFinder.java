// package models.finder;
//
// import javax.persistence.*;
// import play.libs.F.*;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import com.fasterxml.jackson.databind.JsonNode;
// import org.junit.*;
// import play.db.ebean.Model;
// import play.db.ebean.Model.Finder;
// import play.mvc.*;
// import play.test.*;
// import play.data.DynamicForm;
// import play.data.validation.ValidationError;
// import play.data.validation.Constraints.RequiredValidator;
// import play.i18n.Lang;
// import play.libs.F;
// import play.libs.F.*;
//
// import static play.test.Helpers.*;
// import static org.fest.assertions.Assertions.*;
//
//
// public class CheckFinder{
//   public static Option<Query<Check>> getCheckById(String userid){
//
//     StringBuilder sql = new StringBuilder();
//     sql.append("SELECT 'id', 'name', 'result', 'created', 'modified' ");
//     sql.append("FROM 'checks' WHERE 'name' = ? ORDER BY 'created' LIMIT ?, 10 ");
//     RawSql rawSql = RawSqlBuilder
//     .unparsed(sql.toString())
//     .columnMapping("userid", "userid")
//     .columnMapping("username", "username")
//     .columnMapping("password", "password")
//     .columnMapping("created", "created")
//     .columnMapping("modified", "modified")
//     .create();
//   Query<Check> query = Ebean.find(Check.class);
//   query.setRawSql(rawSql)
//     .setParameter(1, userid);
//     return OptionUtil.apply(query);
//   }
// }
