package Frontend;

import Frontend.Utils.ReposHolder;

import java.io.IOException;

/**
 * The type Prep.
 */
public class Prep {

    /**
     * Init.
     */
    /* Responsible for loading in program's data in the beginning of the application */
    public static void init() {
        // prepare to load data
        Backend.Storage.Load load = new Backend.Storage.Load("users.txt", "instruments.txt", "albums.txt",
                "sessions.txt");

        Backend.Instruments.Repos instruments;
        Backend.Albums.Repos albums;
        Backend.Users.Repos users;
        Backend.Sessions.Repos sessions;

        try {
            users = load.loadUsers();

            // if I don't load from the admin, all the repos will be different
            instruments = users.getUser("admin").getInstrumentsRepo();
            albums = users.getUser("admin").getAlbumsRepo();
            sessions = users.getUser("admin").getSessionsRepo();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            // omit error message because this is triggered the first time the program is running
            instruments = new Backend.Instruments.Repos();
            albums = new Backend.Albums.Repos();
            users = new Backend.Users.Repos();
            sessions = new Backend.Sessions.Repos();
        }

        // load all actions
        Frontend.Utils.ReposHolder.init(instruments, albums, users, sessions);

        // create test users
        ReposHolder.getUsers().initAdminUsers(ReposHolder.getInstruments(), ReposHolder.getAlbums(),
                                ReposHolder.getUsers(), ReposHolder.getSessions());
    }

    /**
     * Save data.
     */
    public static void saveData() {
        // não é a maneira mais eficiente de fazer isso, mas não é possivel iniciar a
        // mesma thread duas vezes
        Backend.Storage.Save saveUsers = new Backend.Storage.Save(
                Frontend.Utils.ReposHolder.getUsers(),
                "users.txt"
        );
        Backend.Storage.Save saveAlbums = new Backend.Storage.Save(
                Frontend.Utils.ReposHolder.getAlbums(),
                "albums.txt"
        );
        Backend.Storage.Save saveInstruments = new Backend.Storage.Save(
                Frontend.Utils.ReposHolder.getInstruments(),
                "instruments.txt"
        );
        Backend.Storage.Save saveSessions = new Backend.Storage.Save(
                Frontend.Utils.ReposHolder.getSessions(),
                "sessions.txt"
        );
        saveUsers.start();
        saveAlbums.start();
        saveInstruments.start();
        saveSessions.start();
    }
}
