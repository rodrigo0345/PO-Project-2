package Frontend.Utils;

/**
 * The type Repos holder.
 */
public class ReposHolder {
    private static Backend.Instruments.Repos instruments;
    private static Backend.Albums.Repos albums;
    private static Backend.Users.Repos users;
    private static Backend.Sessions.Repos sessions;

    /**
     * Init.
     *
     * @param instruments the instruments
     * @param albums      the albums
     * @param users       the users
     * @param sessions    the sessions
     */
    public static void init(Backend.Instruments.Repos instruments, Backend.Albums.Repos albums,
                            Backend.Users.Repos users, Backend.Sessions.Repos sessions) {
        ReposHolder.instruments = instruments;
        ReposHolder.albums = albums;
        ReposHolder.users = users;
        ReposHolder.sessions = sessions;
    }

    /**
     * Gets instruments.
     *
     * @return the instruments
     */
    public static Backend.Instruments.Repos getInstruments() {
        return instruments;
    }

    /**
     * Gets albums.
     *
     * @return the albums
     */
    public static Backend.Albums.Repos getAlbums() {
        return albums;
    }

    /**
     * Gets users.
     *
     * @return the users
     */
    public static Backend.Users.Repos getUsers() {
        return users;
    }

    /**
     * Gets sessions.
     *
     * @return the sessions
     */
    public static Backend.Sessions.Repos getSessions() {
        return sessions;
    }
}
