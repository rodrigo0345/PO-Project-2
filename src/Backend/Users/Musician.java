package Backend.Users;

import java.util.Set;
import java.util.TreeSet;

import Backend.Albums.AlbumEditado;
import Backend.Instruments.Instrument;
import Backend.Sessions.Session;

public class Musician extends User {
    private final Set<Backend.Albums.Album> albums = new TreeSet<>();
    private final Set<Backend.Sessions.Session> sessions = new TreeSet<>();


    // usado apenas para testes, mas não tem qualquer
    // uso na aplicação
    private final Set<Instrument> instruments = new TreeSet<>();

    public Musician(String name, String email, String username, String password, Backend.Users.Repos users,
                    Backend.Instruments.Repos instruments, Backend.Albums.Repos albums, Backend.Sessions.Repos sessions) {
        super(name, email, username, password, users, instruments, albums, sessions);
        this.getUsersRepo().addUser(this);
    }

    public boolean addAlbum(Backend.Albums.Album album) {
        return albums.add(album);
    }
    public void removeAlbum(Backend.Albums.Album album) {
        albums.remove(album);
    }

    public Set<Backend.Albums.Album> getAlbums() {
        return albums;
    }

    public void requestInstrumentForSession(Instrument instrument, Session s, int quantity) throws IllegalArgumentException {
        if(!s.getInvitedMusicians().containsKey(this.getUsername()))
            throw new IllegalArgumentException("O músico não foi convidado para a sessão em específico!");
        
        s.addPendingInstrument(instrument, quantity);
    }

    // usado apenas para testes, mas não tem qualquer
    // uso na aplicação
    public void addInstrument(Instrument instrument) {
        instruments.add(instrument);
    }

    // usado apenas para testes, mas não tem qualquer
    // uso na aplicação
    public void removeInstrument(Instrument instrument) {
        instruments.remove(instrument);
    }

    // usado apenas para testes, mas não tem qualquer
    // uso na aplicação
    public Set<Instrument> getInstruments() {
        return instruments;
    }

    public void addSession(AlbumEditado album, Session newSession)
        throws IllegalArgumentException{
        this.addAlbum(album); // O valor de retorno não nos interessa neste caso
        boolean add = this.sessions.add(newSession);
        if (!add){
            throw new IllegalArgumentException("O músico já estava na sessão");
        }
    }

    public void removeSession(AlbumEditado album, Session session) {
        this.removeAlbum(album);
        this.sessions.remove(session);
    }

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
