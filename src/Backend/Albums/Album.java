package Backend.Albums;

import Backend.Users.Musician;
import Backend.Users.Produtor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    private LocalDateTime date;
    private Produtor produtorOriginal;

    private Backend.Instruments.Repos instrumentsRepo;
    private Backend.Albums.Repos albumsRepo;
    private Backend.Users.Repos usersRepo;
    private Backend.Sessions.Repos sessionsRepo;

    public Album(String titulo, String genero, LocalDateTime date, Produtor produtorOriginal, Backend.Instruments.Repos instruments,
            Backend.Albums.Repos albums,
            Backend.Users.Repos users, Backend.Sessions.Repos sessions)
            throws IllegalArgumentException {
        this.genero = genero;
        this.date = date;
        this.instrumentsRepo = instruments;
        this.albumsRepo = albums;
        this.usersRepo = users;
        this.sessionsRepo = sessions;

        this.setTitulo(titulo);
        if(produtorOriginal == null) throw new IllegalArgumentException("Produtor is invalid");
        this.setProdutorOriginal(produtorOriginal);
        this.albumsRepo.addAlbum(this); // dependency
    }

    // only used for testing
    public Album(String titulo) throws IllegalArgumentException {
        this.instrumentsRepo = new Backend.Instruments.Repos();
        this.albumsRepo = new Backend.Albums.Repos();
        this.usersRepo = new Backend.Users.Repos();
        this.sessionsRepo = new Backend.Sessions.Repos();
        this.setTitulo(titulo);
        this.albumsRepo.addAlbum(this); // dependency
    }

    // automatically adds the album to the musician's list of albums,
    // should not be used to add artists to an edited album
    public boolean addArtist(Musician artist) {
        boolean a = artists.add(artist);
        boolean b = artist.addAlbum(this);
        artist.addAlbum(this);
        return a && b;
    }

    public Musician getArtist(String username) {
        for (Musician m : artists) {
            if (m.getUsername().equals(username))
                return m;
        }
        return null;
    }

    public Set<Musician> getArtists() {
        return this.artists;
    }

    public boolean removeArtist(String username) {
        Musician aux = getArtist(username);
        if (aux == null)
            return false;
        return artists.remove(aux);
    }

    public boolean deleteArtist(Musician user) {
        return artists.remove(user);
    }

    public void removeArtist(Musician artist) {
        artists.remove(artist);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date.truncatedTo(ChronoUnit.MINUTES);
    }

    public String getTitulo() {
        return this.titulo;
    }

    public boolean setTitulo(String titulo) throws IllegalArgumentException {
        if (!this.albumsRepo.isTituloValid(titulo))
            throw new IllegalArgumentException(titulo + " already exists");
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

    // only use for albums that are not editable
    public void setProdutorOriginal(Produtor produtor) {
        if (produtor == null) {
            return; // just to avoid exceptions as there are cases where this needs to continue
        }
        this.produtorOriginal = produtor;
        this.produtorOriginal.addOldAlbum(this);
    }

    public Produtor getProdutor() {
        return this.produtorOriginal;
    }

    @Override
    public String toString() {
        String aux = "";
        if (this.produtorOriginal != null) {
            aux = "produtor=" + this.produtorOriginal.getUsername();
        }

        aux += "titulo=" + titulo + ", genero=" + genero + ", date=" + date;

        int i = 0;
        for (Musician m : this.artists) {
            i++;
            aux += "\n" + i + " " + m.getUsername();
        }
        return aux;
    }

    @Override
    public int compareTo(Album o) {
        if (o == null)
            return -1;
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

    public void setGenero(String genre) {
        this.genero = genre;
    }

    public boolean removeTrack(String trackname) {
        if (!this.doesTrackExist(trackname)) {
            return false;
        }
        tracks.remove(trackname);
        return true;
    }

    public Map<String, Backend.Tracks.Track> getTracks() {
        return tracks;
    }

    public boolean doesTrackExist(String trackname) {
        return this.getTracks().containsKey(trackname);
    }
}
