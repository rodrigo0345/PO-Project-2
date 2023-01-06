package Backend.Users;

import java.io.Serializable;

/**
 * The type User.
 */
public abstract class User implements Comparable<User>, Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String email;
    private String username;
    private String password;

    private final Backend.Instruments.Repos instrumentsRepo;
    private final Backend.Albums.Repos albumsRepo;
    private final Backend.Users.Repos usersRepo;
    private final Backend.Sessions.Repos sessionsRepo;

    /**
     * Instantiates a new User.
     *
     * @param name        the name
     * @param email       the email
     * @param username    the username
     * @param password    the password
     * @param users       the users
     * @param instruments the instruments
     * @param albums      the albums
     * @param sessions    the sessions
     */
    protected User(String name, String email, String username, String password, Backend.Users.Repos users,
                   Backend.Instruments.Repos instruments, Backend.Albums.Repos albums, Backend.Sessions.Repos sessions) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.usersRepo = users;
        this.instrumentsRepo = instruments;
        this.albumsRepo = albums;
        this.sessionsRepo = sessions;
        this.setFirstUsername(username);
    }

    /**
     * Gets sessions repo.
     *
     * @return the sessions repo
     */
    public Backend.Sessions.Repos getSessionsRepo() {
        return sessionsRepo;
    }

    /**
     * Gets users repo.
     *
     * @return the users repo
     */
    public Backend.Users.Repos getUsersRepo() {
        return usersRepo;
    }

    /**
     * Gets albums repo.
     *
     * @return the albums repo
     */
    public Backend.Albums.Repos getAlbumsRepo() {
        return albumsRepo;
    }

    /**
     * Gets instruments repo.
     *
     * @return the instruments repo
     */
    public Backend.Instruments.Repos getInstrumentsRepo() {
        return instrumentsRepo;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void setName(String name) throws IllegalArgumentException {
        Backend.Useful.StringChecker.validName(name);  // already throws IllegalArgumentException
        this.name = name;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void setEmail(String email) throws IllegalArgumentException {
        Backend.Useful.StringChecker.validEmail(email);
        this.email = email;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void setUsername(String username) throws IllegalArgumentException {
        if (!usersRepo.isUsernameAvailable(username))
            return;
        usersRepo.removeUser(this.username);
        this.username = username;
        usersRepo.addUser(this);
    }

    private void setFirstUsername(String username) {
        if (!usersRepo.isUsernameAvailable(username))
            return;
        this.username = username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "name=" + name + ", email=" + email + ", username=" + username;
    }

    @Override
    public int compareTo(User o) {
        return this.username.compareTo(o.username);
    }
}
