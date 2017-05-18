package models.finder;

public class CheckFinder{
  public static Option<Query<Check>> getCheckById(String userId){
    //sql
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT 'id', 'name', 'result', 'created', 'modified' ");
    sql.append("FROM 'checks' WHERE 'name' = ? ORDER BY 'created' LIMIT ?, 10 ");
    RawSql rawSql = RawSqlBuilder
    .unparsed(sql.toString())
    .columnMapping("userId", "userId")
    .columnMapping("user_name", "user_name")
    .columnMapping("password", "password")
    .columnMapping("created", "created")
    .columnMapping("modified", "modified")
    .create();
  Query<Check> query = Ebean.find(Check.class);
  query.setRawSql(rawSql)
    .setParameter(1, userId);
    return OptionUtil.apply(query);
  }
}
