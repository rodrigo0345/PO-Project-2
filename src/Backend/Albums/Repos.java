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
}
