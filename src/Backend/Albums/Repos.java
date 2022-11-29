package Backend.Albums;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class Repos implements Serializable {
    private static Set<Album> albums = new TreeSet<>();

    public static Album getAlbum(String name) {
        return null;
    }

    public static Set<Album> getAlbums() {
        return albums;
    }
}
