package Frontend;

import java.util.Scanner;

import Backend.Users.Repos;
import Backend.Users.User;

/**
 * The type Authentication.
 */
public class Authentication {

    /**
     * Login user.
     *
     * @return the user
     */
    public static User login() {
        User auth = null;
        while (null == auth) {
            try {
                auth = Authentication.loginPrompt(Frontend.Utils.ReposHolder.getUsers());
                Frontend.Utils.Prompt.cleanPrompt();
            } catch (Exception e) {
                Frontend.Utils.Prompt.outputError(e.getMessage());
            }
        }
        return auth;
    }

    /**
     * Login prompt user.
     *
     * @param users the users
     * @return the user
     * @throws Exception the exception
     */
    public static User loginPrompt(Repos users) throws Exception {

        String username = Frontend.Utils.Prompt.readString("Username: ");
        String password = Frontend.Utils.Prompt.readString("Password: ");

        User user = users.getUser(username, password);
        if (null == user) {
            throw new Exception("Username ou password inválidos");
        }
        return user;
    }
}
