package Backend.Users;

import java.util.Set;
import java.util.TreeSet;

import Backend.Instruments.Instrument;

public class Musician extends User {
    private Set<Backend.Albums.Album> albums = new TreeSet<>();
    private Set<Instrument> instruments = new TreeSet<>();

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

}
