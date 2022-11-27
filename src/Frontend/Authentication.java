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
        sc.close();
        User user = Backend.UserRepo.UserRepo.login(username, password);
        return user;
    }
}
