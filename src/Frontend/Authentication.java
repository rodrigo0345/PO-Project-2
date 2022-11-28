package Frontend;

import java.util.Scanner;

import Backend.Users.User;

public class Authentication {
    public static User loginPrompt() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Username: ");
        String username = sc.nextLine();
        System.out.println("Password: ");
        String password = sc.nextLine();
        User user = Backend.Users.Repos.getUser(username, password);
        return user;
    }
}
