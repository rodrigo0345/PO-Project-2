package Backend.Albums;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Repos implements Serializable {
    @Serial
    private static final long serialVersionUID = 10L;
    private final Map<String, Album> albums = new HashMap<>();

    public Album getAlbum(String name) {
        return this.albums.get(name);
    }

    public Map<String, Album> getAlbums() {
        return this.albums;
    }

    public boolean addAlbum(Album album) throws NullPointerException {
        if (null == album.getTitulo()) return false;
        if (!this.isTituloValid(album.getTitulo())) return false;
        this.albums.put(album.getTitulo(), album);
        return true;
    }

    public boolean isTituloValid(String titulo) {
        return null == this.albums.get(titulo);
    }
}
