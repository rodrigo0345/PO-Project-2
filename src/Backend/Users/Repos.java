package Backend.Users;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Repos.
 */
public class Repos implements Serializable {
    private final Map<String, User> users = new HashMap<String, User>();
    private static final long serialVersionUID = 2L;

    private boolean checkUser(String username, String password) {
        User user = users.get(username);
        if (null != user) {
            return user.getPassword().equals(password);
        }
        return false;
    }

    /**
     * Add user.
     *
     * @param user the user
     */
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @param password the password
     * @return the user
     */
    public User getUser(String username, String password) {
        if (checkUser(username, password))
            return users.get(username);
        return null;
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     */
    public User getUser(String username) {
        return users.get(username);
    }

    /**
     * Remove user.
     *
     * @param username the username
     */
    public void removeUser(String username) {
        users.remove(username);
    }

    /**
     * Init admin users.
     *
     * @param instruments the instruments
     * @param albums      the albums
     * @param users       the users
     * @param sessions    the sessions
     */
    public void initAdminUsers(Backend.Instruments.Repos instruments, Backend.Albums.Repos albums,
                         Backend.Users.Repos users, Backend.Sessions.Repos sessions) {

        // check if admin was already created
        if (null == users.getUser("admin")) {
            addUser(new Admin(
                    "Admin",
                    "admin",
                    "admin",
                    "admin",
                    instruments,
                    albums,
                    users,
                    sessions)
            );
        }

    }

    /**
     * Gets users.
     *
     * @return the users
     */
// only used to load the data from the files
    public Map<String, User> getUsers() {
        return users;
    }

    /**
     * Is username available boolean.
     *
     * @param u the u
     * @return the boolean
     */
    public boolean isUsernameAvailable(String u) {
        return !users.containsKey(u);
    }
}
