package Backend.Albums;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;
import java.util.TreeSet;

import Backend.Users.Musician;

public class Album implements Serializable {
    private static long serialVersionUID = 8L;
    private Set<Musician> artists = new TreeSet<>();
    private String titulo;
    private String genero;
    private Date date;

    Backend.Instruments.Repos instrumentsRepo;
    Backend.Albums.Repos albumsRepo;
    Backend.Users.Repos usersRepo;
    Backend.Sessions.Repos sessionsRepo;

    public Album(String titulo, String genero, Date date, Backend.Instruments.Repos instruments,
            Backend.Albums.Repos albums,
            Backend.Users.Repos users, Backend.Sessions.Repos sessions) {
        this.titulo = titulo;
        this.genero = genero;
        this.date = date;
        this.instrumentsRepo = instruments;
        this.albumsRepo = albums;
        this.usersRepo = users;
        this.sessionsRepo = sessions;
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

    public void setTitulo(String titulo) {
        if (albumsRepo.isTituloValid(titulo)) {
            this.titulo = titulo;
        }
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    @Override
    public String toString() {
        return "titulo=" + titulo + ", genero=" + genero + ", date=" + date;
    }
}
