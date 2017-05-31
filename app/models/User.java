package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotBlank;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import org.hibernate.validator.constraints.NotBlank;
/**
 *
 * @author r-tominaga
 */
@Entity
public class User extends Model{

    @Id
    public long id;

    @NotBlank(message = "文字列を入力してください")
    public String userid;

    @NotBlank(message = "文字列を入力してください")
    public String username;

    @NotBlank(message = "文字列を入力してください")
    public String password;

    public boolean admin; //ユーザータイプ

    public boolean dltflg;

    public static Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);
}
