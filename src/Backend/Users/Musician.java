package Backend.Users;

import java.util.Set;
import java.util.TreeSet;

public class Musician extends User {
    private Set<Backend.Albums.Album> albums = new TreeSet<>();

    public Musician(String name, String email, String username, String password) {
        super(name, email, username, password);
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
}
