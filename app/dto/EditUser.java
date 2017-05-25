package dto;

import play.data.validation.Constraints.*;

/**
 *
 * @author r-tominaga
 */
public class EditUser {
    @Required(message="")
    public String userid;
    @Required(message="")
    public String username;

}
