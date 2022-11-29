package Frontend;

import java.util.Scanner;

import Backend.Users.Repos;
import Backend.Users.User;

public class Authentication {
    public static User loginPrompt(Repos users) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Username: ");
        String username = sc.nextLine();
        System.out.println("Password: ");
        String password = sc.nextLine();
        User user = users.getUser(username, password);
        if (user == null) {
            throw new Exception("Invalid username or password");
        }
        return user;
    }
}
