package Backend.Tracks;

import Backend.Albums.Album;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Track.
 */
public class Track implements Serializable {
    private static final long serialVersionUID = 9L;

    private final Set<Backend.Users.Musician> artists = new TreeSet<>();
    private String titulo;
    private String genero;
    private int duration;

    private final Album album;

    /**
     * Instantiates a new Track.
     *
     * @param album    the album
     * @param titulo   the titulo
     * @param genero   the genero
     * @param duration the duration
     */
    public Track(Album album, String titulo, String genero, int duration) {
        this.titulo = titulo;
        this.genero = genero;
        this.duration = duration;
        this.album = album;
        album.addTrack(this);
    }

    /**
     * Gets titulo.
     *
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Sets titulo.
     *
     * @param titulo the titulo
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Gets genero.
     *
     * @return the genero
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Sets genero.
     *
     * @param genero the genero
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets duration.
     *
     * @param duration the duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Add artist.
     *
     * @param artist the artist
     */
    public void addArtist(Backend.Users.Musician artist) {
        artists.add(artist);
    }

    /**
     * Remove artist.
     *
     * @param artist the artist
     */
    public void removeArtist(Backend.Users.Musician artist) {
        artists.remove(artist);
    }

    /**
     * Gets artists.
     *
     * @return the artists
     */
    public Set<Backend.Users.Musician> getArtists() {
        return artists;
    }

    @Override
    public String toString() {
        return "Track [duration=" + duration + ", genero=" + genero + ", titulo=" + titulo + "]";
    }
}
