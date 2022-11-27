package Backend.Users;

import java.util.HashMap;
import java.util.Map;

public class Repos {
    private static Map<String, User> users = new HashMap<String, User>();

    private static boolean checkUser(String username, String password) {
        User user = users.get(username);
        if (user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }

    public static void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public static User getUser(String username, String password) {
        if (checkUser(username, password))
            return users.get(username);
        return null;
    }

    public static void removeUser(String username) {
        users.remove(username);
    }
}
