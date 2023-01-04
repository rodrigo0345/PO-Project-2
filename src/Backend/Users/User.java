package Backend.Users;

import java.io.Serializable;

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

    public Backend.Sessions.Repos getSessionsRepo() {
        return sessionsRepo;
    }

    public Backend.Users.Repos getUsersRepo() {
        return usersRepo;
    }

    public Backend.Albums.Repos getAlbumsRepo() {
        return albumsRepo;
    }

    public Backend.Instruments.Repos getInstrumentsRepo() {
        return instrumentsRepo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        Backend.Useful.StringChecker.validName(name);  // already throws IllegalArgumentException
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws IllegalArgumentException {
        Backend.Useful.StringChecker.validEmail(email);
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws IllegalArgumentException {
        if (!usersRepo.isUsernameValid(username))
            return;
        usersRepo.removeUser(this.username);
        this.username = username;
        usersRepo.addUser(this);
    }

    private void setFirstUsername(String username) {
        if (!usersRepo.isUsernameValid(username))
            return;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

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
