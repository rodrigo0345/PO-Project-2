package Backend.Albums;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class Repos implements Serializable {
    private static final long serialVersionUID = 10L;
    private Set<Album> albums = new TreeSet<>();

    public Album getAlbum(String name) {
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
