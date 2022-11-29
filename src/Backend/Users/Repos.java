package Backend.Users;

import java.util.HashMap;
import java.util.Map;

public class Repos {
    private Map<String, User> users = new HashMap<String, User>();

    private boolean checkUser(String username, String password) {
        User user = users.get(username);
        if (user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public User getUser(String username, String password) {
        if (checkUser(username, password))
            return users.get(username);
        return null;
    }

    public void removeUser(String username) {
        users.remove(username);
    }

    public void devUsers() {
        addUser(new Admin("Admin", "admin", "admin", "admin"));
    }

    // only used to load the data from the files
    public Map<String, User> getUsers() {
        return users;
    }
}
