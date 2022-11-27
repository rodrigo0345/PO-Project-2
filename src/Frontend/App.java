package Frontend;

import Backend.UserRepo.UserRepo;
import Backend.Users.Admin;
import Backend.Users.Musician;
import Backend.Users.Produtor;
import Backend.Users.User;

public class App {
    public static void main(String[] args) throws Exception {
        UserRepo.devUsers();
        User auth = Authentication.loginPrompt();
        if (auth != null) {
            if (auth instanceof Musician) {
                Musician user = (Musician) auth;
            } else if (auth instanceof Admin) {
                Admin user = (Admin) auth;
            } else if (auth instanceof Produtor) {
                Produtor user = (Produtor) auth;
            }
        }
    }

}
