package Backend.Albums;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class Repos implements Serializable {
    @Serial
    private static final long serialVersionUID = 10L;
    private Set<Album> albums = new TreeSet<>();

    public Album getAlbum(String name) {
        for (Album album : albums) {
            if (album.getTitulo().equals(name)) {
                return album;
            }
        }
        return null;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void addAlbum(Album album) {
        albums.add(album);
    }

    public boolean isTituloValid(String titulo) {
        for (Album album : albums) {
            if (album.getTitulo().equals(titulo)) {
                return false;
            }
        }
        return true;
    }
}
