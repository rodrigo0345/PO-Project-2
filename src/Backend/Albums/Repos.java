package Backend.Albums;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Repos.
 */
public class Repos implements Serializable {
    @Serial
    private static final long serialVersionUID = 10L;
    private final Map<String, Album> albums = new HashMap<>();

    /**
     * Gets album.
     *
     * @param name the name
     * @return the album
     */
    public Album getAlbum(String name) {
        return this.albums.get(name);
    }

    /**
     * Gets albums.
     *
     * @return the albums
     */
    public Map<String, Album> getAlbums() {
        return this.albums;
    }

    /**
     * Add album boolean.
     *
     * @param album the album
     * @return the boolean
     * @throws NullPointerException the null pointer exception
     */
    public boolean addAlbum(Album album) throws NullPointerException {
        if (null == album.getTitulo()) return false;
        if (!this.isTituloValid(album.getTitulo())) return false;
        this.albums.put(album.getTitulo(), album);
        return true;
    }

    /**
     * Is titulo valid boolean.
     *
     * @param titulo the titulo
     * @return the boolean
     */
    public boolean isTituloValid(String titulo) {
        return null == this.albums.get(titulo);
    }
}
