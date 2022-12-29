package Frontend.Utils;

public class ReposHolder {
    private static Backend.Instruments.Repos instruments;
    private static Backend.Albums.Repos albums;
    private static Backend.Users.Repos users;
    private static Backend.Sessions.Repos sessions;

    public static void init(Backend.Instruments.Repos instruments, Backend.Albums.Repos albums,
                            Backend.Users.Repos users, Backend.Sessions.Repos sessions) {
        ReposHolder.instruments = instruments;
        ReposHolder.albums = albums;
        ReposHolder.users = users;
        ReposHolder.sessions = sessions;
    }

    public static Backend.Instruments.Repos getInstruments() {
        return instruments;
    }

    public static Backend.Albums.Repos getAlbums() {
        return albums;
    }

    public static Backend.Users.Repos getUsers() {
        return users;
    }

    public static Backend.Sessions.Repos getSessions() {
        return sessions;
    }
}
