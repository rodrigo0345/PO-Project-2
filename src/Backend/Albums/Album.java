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
    private String genero;
    private LocalDate date;
    private Produtor produtor;

    private Backend.Instruments.Repos instrumentsRepo;
    private Backend.Albums.Repos albumsRepo;
    private Backend.Users.Repos usersRepo;
    private Backend.Sessions.Repos sessionsRepo;

    public Album(String titulo, String genero, LocalDate date, Produtor produtor, Backend.Instruments.Repos instruments,
            Backend.Albums.Repos albums,
            Backend.Users.Repos users, Backend.Sessions.Repos sessions) {
        this.genero = genero;
        this.date = date;
        this.produtor = produtor;
        this.instrumentsRepo = instruments;
        this.albumsRepo = albums;
        this.usersRepo = users;
        this.sessionsRepo = sessions;
        this.setTitulo(titulo);
        this.albumsRepo.addAlbum(this); // dependency
    }

    // only used for testing
    public Album(String titulo){
        this.instrumentsRepo = new Backend.Instruments.Repos();
        this.albumsRepo = new Backend.Albums.Repos();
        this.usersRepo = new Backend.Users.Repos();
        this.sessionsRepo = new Backend.Sessions.Repos();
        this.setTitulo(titulo);
        this.albumsRepo.addAlbum(this); // dependency
    }

    // automatically adds the album to the musician's list of albums
    public boolean addArtist(Musician artist) {
        boolean a = artists.add(artist);
        boolean b = artist.addAlbum(this);
        return a && b;
    }

    public Musician getArtist(String username) {
        for(Musician m: artists){
            if (m.getUsername().equals(username)) return m;
        }
        return null;
    }

    public Set<Musician> getArtists(){
        return this.artists;
    }

    public boolean removeArtist(String username) {
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
        return this.titulo;
    }

    public boolean setTitulo(String titulo) {
        if (!this.albumsRepo.isTituloValid(titulo)) return false;
        this.titulo = titulo;
        return true;
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
        return !tracks.containsKey(trackname);
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
        return this.getTitulo().compareTo(o.getTitulo());
    }


    public Backend.Instruments.Repos getInstrumentsRepo() {
        return instrumentsRepo;
    }

    public Backend.Users.Repos getUsersRepo() {
        return usersRepo;
    }

    public Backend.Sessions.Repos getSessionsRepo() {
        return sessionsRepo;
    }

    public Backend.Albums.Repos getAlbumsRepo() {
        return albumsRepo;
    }
}
