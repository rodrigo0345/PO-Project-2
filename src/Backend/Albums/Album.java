package Backend.Albums;

import Backend.Users.Musician;
import Backend.Users.Produtor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Album.
 */
public class Album implements Serializable, Comparable<Album> {//Traduzido
    @Serial
    private static final long serialVersionUID = 8L;
    private final Map<String, Backend.Tracks.Track> tracks = new HashMap<>();
    private final Set<Musician> artists = new TreeSet<>();
    private String titulo;
    private String genero;
    private LocalDateTime date;
    private Produtor produtorOriginal;

    private final Backend.Instruments.Repos instrumentsRepo;
    private final Backend.Albums.Repos albumsRepo;
    private final Backend.Users.Repos usersRepo;
    private final Backend.Sessions.Repos sessionsRepo;

    /**
     * Instantiates a new Album.
     *
     * @param titulo           the titulo
     * @param genero           the genero
     * @param date             the date
     * @param produtorOriginal the produtor original
     * @param instruments      the instruments
     * @param albums           the albums
     * @param users            the users
     * @param sessions         the sessions
     * @throws IllegalArgumentException the illegal argument exception
     */
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
        if(null == produtorOriginal) throw new IllegalArgumentException("Produtor inválido");
        this.setProdutorOriginal(produtorOriginal);
        this.albumsRepo.addAlbum(this); // dependency
    }

    /**
     * Instantiates a new Album.
     *
     * @param titulo the titulo
     * @throws IllegalArgumentException the illegal argument exception
     */
// only used for testing
    public Album(String titulo) throws IllegalArgumentException {
        this.instrumentsRepo = new Backend.Instruments.Repos();
        this.albumsRepo = new Backend.Albums.Repos();
        this.usersRepo = new Backend.Users.Repos();
        this.sessionsRepo = new Backend.Sessions.Repos();
        this.setTitulo(titulo);
        this.albumsRepo.addAlbum(this); // dependency
    }

    /**
     * Add artist boolean.
     *
     * @param artist the artist
     * @return the boolean
     */
// automatically adds the album to the musician's list of albums,
    // should not be used to add artists to an edited album
    public boolean addArtist(Musician artist) {
        boolean a = artists.add(artist);
        boolean b = artist.addAlbum(this);
        artist.addAlbum(this);
        return a && b;
    }

    /**
     * Gets artist.
     *
     * @param username the username
     * @return the artist
     */
    public Musician getArtist(String username) {
        for (Musician m : artists) {
            if (m.getUsername().equals(username))
                return m;
        }
        return null;
    }

    /**
     * Gets artists.
     *
     * @return the artists
     */
    public Set<Musician> getArtists() {
        return this.artists;
    }

    /**
     * Remove artist boolean.
     *
     * @param username the username
     * @return the boolean
     */
    public boolean removeArtist(String username) {
        Musician aux = getArtist(username);
        if (null == aux)
            return false;

        return artists.remove(aux);
    }

    /**
     * Remove artist boolean.
     *
     * @param user the user
     * @return the boolean
     */
    public boolean removeArtist(Musician user) {
        // remove the album from the musician's list of albums
        for (Musician m : artists) {
            if (m.equals(user))
                return artists.remove(m);
        }
        return artists.remove(user);
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(LocalDateTime date) {
        this.date = date.truncatedTo(ChronoUnit.MINUTES);
    }

    /**
     * Gets titulo.
     *
     * @return the titulo
     */
    public String getTitulo() {
        return this.titulo;
    }

    /**
     * Sets titulo.
     *
     * @param titulo the titulo
     * @return the titulo
     * @throws IllegalArgumentException the illegal argument exception
     */
    public boolean setTitulo(String titulo) throws IllegalArgumentException {
        if (!this.albumsRepo.isTituloValid(titulo))
            throw new IllegalArgumentException(titulo + " já existe");
        this.titulo = titulo;
        return true;
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
     * Add track boolean.
     *
     * @param track the track
     * @return the boolean
     */
    public boolean addTrack(Backend.Tracks.Track track) {
        if (!isTracknameValid(track.getTitulo()))
            return false;
        tracks.put(track.getTitulo(), track);
        return true;
    }

    private boolean isTracknameValid(String trackname) {
        return !tracks.containsKey(trackname);
    }

    /**
     * Remove track.
     *
     * @param track the track
     */
    public void removeTrack(Backend.Tracks.Track track) {
        tracks.remove(track.getTitulo());
    }

    /**
     * Sets produtor original.
     *
     * @param produtor the produtor
     */
// only use for albums that are not editable
    public void setProdutorOriginal(Produtor produtor) {
        if (null == produtor) {
            return; // just to avoid exceptions as there are cases where this needs to continue
        }
        this.produtorOriginal = produtor;
        this.produtorOriginal.addOldAlbum(this);
    }

    /**
     * Gets produtor.
     *
     * @return the produtor
     */
    public Produtor getProdutor() {
        return this.produtorOriginal;
    }

    @Override
    public String toString() {
        String aux = "";
        if (null != produtorOriginal) {
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
        if (null == o)
            return -1;
        return this.titulo.compareTo(o.titulo);
    }

    /**
     * Gets instruments repo.
     *
     * @return the instruments repo
     */
    public Backend.Instruments.Repos getInstrumentsRepo() {
        return instrumentsRepo;
    }

    /**
     * Gets users repo.
     *
     * @return the users repo
     */
    public Backend.Users.Repos getUsersRepo() {
        return usersRepo;
    }

    /**
     * Gets sessions repo.
     *
     * @return the sessions repo
     */
    public Backend.Sessions.Repos getSessionsRepo() {
        return sessionsRepo;
    }

    /**
     * Gets albums repo.
     *
     * @return the albums repo
     */
    public Backend.Albums.Repos getAlbumsRepo() {
        return albumsRepo;
    }

    /**
     * Sets genero.
     *
     * @param genre the genre
     */
    public void setGenero(String genre) {
        this.genero = genre;
    }

    /**
     * Remove track boolean.
     *
     * @param trackname the trackname
     * @return the boolean
     */
    public boolean removeTrack(String trackname) {
        if (!this.doesTrackExist(trackname)) {
            return false;
        }
        tracks.remove(trackname);
        return true;
    }

    /**
     * Gets tracks.
     *
     * @return the tracks
     */
    public Map<String, Backend.Tracks.Track> getTracks() {
        return tracks;
    }

    /**
     * Does track exist boolean.
     *
     * @param trackname the trackname
     * @return the boolean
     */
    public boolean doesTrackExist(String trackname) {
        return this.tracks.containsKey(trackname);
    }
}
