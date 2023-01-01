package Frontend;

import Backend.Users.Admin;
import Backend.Users.Musician;
import Backend.Users.Produtor;
import Backend.Users.User;
import Frontend.Menus.Madmin;
import Frontend.Menus.Mmusico;
import Frontend.Menus.Mprodutor;
import Frontend.Utils.Generics;

import java.io.IOException;
import java.util.Scanner;

public class App {//Traduzido
    public static void main(String[] args) {
        Prep.init();

        while(!Generics.isExit()) {
            program();
        }

        // Generics.sc.close();
    }

    private static void program() {

        // login system

        User user = Authentication.login();
        Frontend.Utils.UserHolder.setUser(user);

        // initiate Mprodutor, Mmusico or Madmin
        while (Frontend.Utils.UserHolder.getUser() != null) {
            Frontend.Utils.Prompt.cleanPrompt();
            Frontend.Utils.UserHolder.initMenu();
            // save changes
            Frontend.Prep.saveData();
        }
    }

}
