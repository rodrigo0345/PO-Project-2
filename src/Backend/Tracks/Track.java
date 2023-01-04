package Backend.Tracks;

import Backend.Albums.Album;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class Track implements Serializable {
    private static final long serialVersionUID = 9L;

    private final Set<Backend.Users.Musician> artists = new TreeSet<>();
    private String titulo;
    private String genero;
    private int duration;

    private final Album album;

    public Track(Album album, String titulo, String genero, int duration) {
        this.titulo = titulo;
        this.genero = genero;
        this.duration = duration;
        this.album = album;
        album.addTrack(this);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void addArtist(Backend.Users.Musician artist) {
        artists.add(artist);
    }

    public void removeArtist(Backend.Users.Musician artist) {
        artists.remove(artist);
    }

    public Set<Backend.Users.Musician> getArtists() {
        return artists;
    }

    @Override
    public String toString() {
        return "Track [duration=" + duration + ", genero=" + genero + ", titulo=" + titulo + "]";
    }
}
