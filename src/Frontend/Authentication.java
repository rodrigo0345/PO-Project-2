package Frontend;

import java.util.Scanner;

import Backend.Users.Repos;
import Backend.Users.User;
import Frontend.Menus.ConsoleColors;

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

        ConsoleColors color = new ConsoleColors();

        System.out.println(color.getCYAN());
        System.out.println(
                "____________________________________________________________________________________________________________");
        System.out.println(color.getYELLOW());
        System.out.println("                                     __   __  _______  __    _  __   __                  ");
        System.out.println("                                    |  |_|  ||       ||  |  | ||  | |  |");
        System.out.println("                                    |       ||    ___||   |_| ||  | |  |");
        System.out.println("                                    |       ||   |___ |       ||  |_|  |");
        System.out.println("                                    |       ||    ___||  _    ||       |");
        System.out.println("                                    | ||_|| ||   |___ | | |   ||       |");
        System.out.println("                                    |_|   |_||_______||_|  |__||_______|");
        System.out.println(color.getCYAN());
        System.out.println(
                "____________________________________________________________________________________________________________");
        System.out.println(color.getWHITE());       
        String username = Frontend.Utils.Prompt.readString("            Username: ");
        String password = Frontend.Utils.Prompt.readString("            Password: ");

        User user = users.getUser(username, password);
        if (null == user) {
            System.out.println(color.getRED());
            throw new Exception("[!] - ERRO: Username ou password inválidos");
        }
        return user;
    }
}
