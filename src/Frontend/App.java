package Frontend;

import Backend.Users.User;
import Frontend.Utils.Generics;

/**
 * The type App.
 */
public class App {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Prep.init();

        while(!Generics.isExit()) {
            program();
        }

        Generics.sc.close();
    }

    private static void program() {

        // login system

        User user = Authentication.login();
        Frontend.Utils.UserHolder.setUser(user);

        // initiate Mprodutor, Mmusico or Madmin
        while (null != Frontend.Utils.UserHolder.getUser()) {
            Frontend.Utils.Prompt.cleanPrompt();
            Frontend.Utils.UserHolder.initMenu();
            // save changes
            Frontend.Prep.saveData();
        }
    }

}
