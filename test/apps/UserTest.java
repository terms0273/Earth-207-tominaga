package models;

import models.User;
import org.junit.*;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class UserTest extends FakeApp {
    @Test
    public void testSaveData() {
        String userId = "207";
        User user = new User(userId , "r-tominaga", "password");
        user.save();

        User actual = User.findData(userId);

        assertThat(actual.userId).isEqualTo(userId);
    }
}
