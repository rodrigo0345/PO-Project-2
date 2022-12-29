package Frontend;

import java.io.IOException;

public class Prep {

    /* Responsible for loading in program's data in the beginning of the application */
    public static void init() {
        Backend.Instruments.Repos instruments;
        Backend.Albums.Repos albums;
        Backend.Users.Repos users;
        Backend.Sessions.Repos sessions;

        // prepare to load data
        Backend.Storage.Load load = new Backend.Storage.Load("users.txt", "instruments.txt", "albums.txt",
                "sessions.txt");

        try {
            instruments = load.loadInstruments();
            albums = load.loadAlbums();
            users = load.loadUsers();
            sessions = load.loadSessions();
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

        // create test users
        users.devUsers(instruments, albums, users, sessions);

        // load all actions
        Frontend.Utils.ReposHolder.init(instruments, albums, users, sessions);
    }

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
