package Backend.Albums;

import Backend.Users.Musician;
import Backend.Users.Produtor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Album implements Serializable, Comparable<Album> {
    @Serial
    private static final long serialVersionUID = 8L;
    private final Map<String, Backend.Tracks.Track> tracks = new HashMap<>();
    private final Set<Musician> artists = new TreeSet<>();
    private String titulo;
    private final String genero;
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

    public Musician getArtist(String username) {
        for(Musician m: artists){
            if (m.getUsername().equals(username)) return m;
        }
        return null;
    }

    public boolean deleteArtist(String username) {
        Musician aux = getArtist(username);
        if (aux == null) return false;
        return artists.remove(aux);
    }

    public boolean deleteArtist(Musician user){
        return artists.remove(user);
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

    public boolean addTrack(Backend.Tracks.Track track) {
        if (!isTracknameValid(track.getTitulo()))
            return false;
        tracks.put(track.getTitulo(), track);
        return true;
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
