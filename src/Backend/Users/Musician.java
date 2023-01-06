package Backend.Users;

import java.util.Set;
import java.util.TreeSet;

import Backend.Albums.AlbumEditado;
import Backend.Instruments.Instrument;
import Backend.Sessions.Session;

/**
 * The type Musician.
 */
public class Musician extends User {
    private final Set<Backend.Albums.Album> albums = new TreeSet<>();
    private final Set<Backend.Sessions.Session> sessions = new TreeSet<>();


    // usado apenas para testes, mas não tem qualquer
    // uso na aplicação
    private final Set<Instrument> instruments = new TreeSet<>();

    /**
     * Instantiates a new Musician.
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
    public Musician(String name, String email, String username, String password, Backend.Users.Repos users,
                    Backend.Instruments.Repos instruments, Backend.Albums.Repos albums, Backend.Sessions.Repos sessions) {
        super(name, email, username, password, users, instruments, albums, sessions);
        this.getUsersRepo().addUser(this);
    }

    /**
     * Add album boolean.
     *
     * @param album the album
     * @return the boolean
     */
    public boolean addAlbum(Backend.Albums.Album album) {
        return albums.add(album);
    }

    /**
     * Remove album.
     *
     * @param album the album
     */
    public void removeAlbum(Backend.Albums.Album album) {
        albums.remove(album);
    }

    /**
     * Gets albums.
     *
     * @return the albums
     */
    public Set<Backend.Albums.Album> getAlbums() {
        return albums;
    }

    /**
     * Request instrument for session.
     *
     * @param instrument the instrument
     * @param s          the s
     * @param quantity   the quantity
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void requestInstrumentForSession(Instrument instrument, Session s, int quantity) throws IllegalArgumentException {
        if(!s.getInvitedMusicians().containsKey(this.getUsername()))
            throw new IllegalArgumentException("O músico não foi convidado para a sessão em específico!");
        
        s.addPendingInstrument(instrument, quantity);
    }

    /**
     * Add instrument.
     *
     * @param instrument the instrument
     */
// usado apenas para testes, mas não tem qualquer
    // uso na aplicação
    public void addInstrument(Instrument instrument) {
        instruments.add(instrument);
    }

    /**
     * Remove instrument.
     *
     * @param instrument the instrument
     */
// usado apenas para testes, mas não tem qualquer
    // uso na aplicação
    public void removeInstrument(Instrument instrument) {
        instruments.remove(instrument);
    }

    /**
     * Gets instruments.
     *
     * @return the instruments
     */
// usado apenas para testes, mas não tem qualquer
    // uso na aplicação
    public Set<Instrument> getInstruments() {
        return instruments;
    }

    /**
     * Add session.
     *
     * @param album      the album
     * @param newSession the new session
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void addSession(AlbumEditado album, Session newSession)
        throws IllegalArgumentException{
        this.addAlbum(album); // O valor de retorno não nos interessa neste caso
        boolean add = this.sessions.add(newSession);
        if (!add){
            throw new IllegalArgumentException("O músico já estava na sessão");
        }
    }

    /**
     * Remove session.
     *
     * @param album   the album
     * @param session the session
     */
    public void removeSession(AlbumEditado album, Session session) {
        this.removeAlbum(album);
        this.sessions.remove(session);
    }

    /**
     * Gets sessions.
     *
     * @return the sessions
     */
    public Set<Session> getSessions() {
        return sessions;
    }

    @Override
    public boolean equals(Object a) {
        if (a instanceof Musician) {
            return this.getUsername().equals(((Musician) a).getUsername());
        }
        return false;
    }
}
