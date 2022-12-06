package Backend.Users;

import java.util.Set;
import java.util.TreeSet;

import Backend.Albums.AlbumEditado;
import Backend.Instruments.Instrument;
import Backend.Sessions.Session;

public class Musician extends User {
    private Set<Backend.Albums.Album> albums = new TreeSet<>();
    private Set<Instrument> instruments = new TreeSet<>();
    private Set<Backend.Sessions.Session> sessions = new TreeSet<>();

    public Musician(String name, String email, String username, String password, Backend.Users.Repos users,
            Backend.Instruments.Repos instruments, Backend.Albums.Repos albums, Backend.Sessions.Repos sessions) {
        super(name, email, username, password, users, instruments, albums, sessions);
    }

    public void addAlbum(Backend.Albums.Album album) {
        albums.add(album);
    }

    public void removeAlbum(Backend.Albums.Album album) {
        albums.remove(album);
    }

    public Set<Backend.Albums.Album> getAlbums() {
        return albums;
    }

    public void addInstrument(Instrument instrument) {
        instruments.add(instrument);
    }

    public void removeInstrument(Instrument instrument) {
        instruments.remove(instrument);
    }

    public Set<Instrument> getInstruments() {
        return instruments;
    }

    public void addSession(AlbumEditado album, Session newSession)
        throws Exception{
        this.addAlbum(album);
        boolean add = this.sessions.add(newSession);
        if (add == false){
            throw new Exception("Musician was already in this session");
        }
    }

    @Override
    public boolean equals(Object a) {
        if (a instanceof Musician) {
            if (this.getUsername() == ((Musician) a).getUsername()) {
                return true;
            }
        }
        return false;
    }
}
