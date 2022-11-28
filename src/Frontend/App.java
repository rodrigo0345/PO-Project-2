package Frontend;

import Backend.Users.Admin;
import Backend.Users.Musician;
import Backend.Users.Produtor;
import Backend.Users.User;
import Frontend.Menus.Madmin;
import Frontend.Menus.Mmusico;
import Frontend.Menus.Mprodutor;

public class App {
    public static void main(String[] args) throws Exception {
        Backend.Users.Repos.devUsers();
        User auth = Authentication.loginPrompt();
        if (auth != null) {
            if (auth instanceof Musician) {
                Musician user = (Musician) auth;
                Mmusico menu = new Mmusico(user);
                while (menu.getUser() != null) {
                    menu.mostrarMenu();
                    menu.executeOption();
                }
            } else if (auth instanceof Admin) {
                Admin user = (Admin) auth;
                Madmin menu = new Madmin(user);
                while (menu.getUser() != null) {
                    menu.mostrarMenu();
                    menu.executeOption();
                }
            } else if (auth instanceof Produtor) {
                Produtor user = (Produtor) auth;
                Mprodutor menu = new Mprodutor(user);
                while (menu.getUser() != null) {
                    menu.mostrarMenu();
                    menu.executeOption();
                }
            }
        }
    }

}
