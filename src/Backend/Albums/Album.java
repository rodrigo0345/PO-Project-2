package Backend.Albums;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;
import java.util.TreeSet;

import Backend.Users.Musician;

public class Album implements Serializable {
    private Set<Musician> artists = new TreeSet<>();
    private String titulo;
    private String genero;
    private Date date;

    public Album(String titulo, String genero, Date date) {
        this.titulo = titulo;
        this.genero = genero;
        this.date = date;
    }

    // automatically adds the album to the musician's list of albums
    public void addArtist(Musician artist) {
        artists.add(artist);
        artist.addAlbum(this);
    }

    public void removeArtist(Musician artist) {
        artists.remove(artist);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    @Override
    public String toString() {
        return "titulo=" + titulo + ", genero=" + genero + ", date=" + date;
    }
}
