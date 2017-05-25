package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import play.data.validation.Constraints;
import play.db.ebean.Model;
/**
 *
 * @author r-tominaga
 */
@Entity
@Table(name = "admins")
public class Admin extends Model{

    @Id
    public Long id;
    @Constraints.Required
    public String username;
    @Constraints.Required
    public String password;

    //以下、setterとgetter

}
