package Backend.UserRepo;

import java.util.Set;
import java.util.TreeSet;

import Backend.Users.User;

public class UserRepo {
    private static Set<User> users = new TreeSet<>();

    public static void addUser(User user) {
        users.add(user);
    }

    public static void removeUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                users.remove(user);
                return;
            }
        }
    }

    public static User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static void devUsers() {
        Backend.Users.Admin admin = new Backend.Users.Admin("Admin-dev", "Admin@email.com", "admin", "admin");
        UserRepo.addUser(admin);

        Backend.Users.Produtor produtor = new Backend.Users.Produtor("Produtor-dev", "Prod@email.com", "produtor",
                "produtor");
        UserRepo.addUser(produtor);

        Backend.Users.Musician musician = new Backend.Users.Musician("Musician-dev", "Mus@email.com", "musician",
                "musician");
        UserRepo.addUser(musician);
    }
}
