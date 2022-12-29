package Frontend;

import java.util.Scanner;

import Backend.Users.Repos;
import Backend.Users.User;

public class Authentication {

    public static User login() {
        User auth = null;
        while (auth == null) {
            try {
                auth = Authentication.loginPrompt(Frontend.Utils.ReposHolder.getUsers());
                Frontend.Utils.Prompt.cleanPrompt();
            } catch (Exception e) {
                Frontend.Utils.Prompt.outputError(e.getMessage());
            }
        }
        return auth;
    }

    public static User loginPrompt(Repos users) throws Exception {

        String username = Frontend.Utils.Prompt.readString("Username: ");
        String password = Frontend.Utils.Prompt.readString("Password: ");

        User user = users.getUser(username, password);
        if (user == null) {
            throw new Exception("Invalid username or password");
        }
        return user;
    }
}
