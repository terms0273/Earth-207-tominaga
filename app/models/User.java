package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotBlank;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;




/**
 *
 * @author r-tominaga
 */
@Entity
public class User extends Model{

    @Id
    public long id;

    @NotBlank
    public String userid;

    public String username;

    public String password;

    public boolean admin; //ユーザータイプ

    public boolean dltflg;

    public static Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);
}
