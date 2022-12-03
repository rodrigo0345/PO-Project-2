package Backend.Albums;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import Backend.Users.Musician;
import Backend.Users.Produtor;

public class Album implements Serializable, Comparable<Album> {
    private static long serialVersionUID = 8L;
    private Map<String, Backend.Tracks.Track> tracks = new HashMap<>();
    private Set<Musician> artists = new TreeSet<>();
    private String titulo;
    private String genero;
    private LocalDate date;
    private Produtor produtor;

    Backend.Instruments.Repos instrumentsRepo;
    Backend.Albums.Repos albumsRepo;
    Backend.Users.Repos usersRepo;
    Backend.Sessions.Repos sessionsRepo;

    public Album(String titulo, String genero, LocalDate date, Produtor produtor, Backend.Instruments.Repos instruments,
            Backend.Albums.Repos albums,
            Backend.Users.Repos users, Backend.Sessions.Repos sessions) {
        this.titulo = titulo;
        this.genero = genero;
        this.date = date;
        this.produtor = produtor;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public void addTrack(Backend.Tracks.Track track) {
        if (!isTracknameValid(track.getTitulo()))
            return;
        tracks.put(track.getTitulo(), track);
    }

    private boolean isTracknameValid(String trackname) {
        if (tracks.containsKey(trackname)) {
            return false;
        }
        return true;
    }

    public void removeTrack(Backend.Tracks.Track track) {
        tracks.remove(track.getTitulo());
    }

    public void setProdutor(Produtor produtor) {
        this.produtor = produtor;
    }

    public Produtor getProdutor() {
        return produtor;
    }

    @Override
    public String toString() {
        return "titulo=" + titulo + ", genero=" + genero + ", date=" + date;
    }

    @Override
    public int compareTo(Album o) {
        return this.titulo.compareTo(o.titulo);
    }
}
