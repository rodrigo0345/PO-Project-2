package Backend.Albums;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Repos implements Serializable {
    @Serial
    private static final long serialVersionUID = 10L;
    private final Map<String, Album> albums = new HashMap<>();

    public Album getAlbum(String name) {
        return albums.get(name);
    }

    public Map<String, Album> getAlbums() {
        return albums;
    }

    public boolean addAlbum(Album album) throws NullPointerException {
        if (album.getTitulo() == null) return false;
        if (!isTituloValid(album.getTitulo())) return false;
        albums.put(album.getTitulo(), album);
        return true;
    }

    public boolean isTituloValid(String titulo) {
        return albums.get(titulo) == null;
    }
}
