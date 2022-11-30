package Backend.Users;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Repos implements Serializable {
    private Map<String, User> users = new HashMap<String, User>();
    private static long serialVersionUID = 2L;

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

    public void devUsers(Backend.Instruments.Repos instruments, Backend.Albums.Repos albums,
            Backend.Users.Repos users, Backend.Sessions.Repos sessions) {
        addUser(new Admin("Admin", "admin", "admin", "admin", instruments, albums, users, sessions));

        // Musician
        addUser(new Musician("Musician", "musician", "musician", "musician", users, instruments, albums, sessions));
    }

    // only used to load the data from the files
    public Map<String, User> getUsers() {
        return users;
    }

    public boolean isUserValid(User u) {
        return !users.containsKey(u.getUsername());
    }

    public boolean isUsernameValid(String u) {
        return !users.containsKey(u);
    }
}
