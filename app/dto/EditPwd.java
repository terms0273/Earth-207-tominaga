package dto;

import play.data.validation.Constraints.*;

/**
 *
 * @author r-tominaga
 */
public class EditPwd {

    @Required(message="PASSWORDが無効です")
    public String oldPassword;

    @Required(message="PASSWORDが無効です")
    public String newPassword;

    @Required(message="CONFIRM PASSWORDが無効です")
    public String confirmPassword;
}
