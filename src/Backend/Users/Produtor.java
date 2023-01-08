package Backend.Users;

import Backend.Albums.Album;
import Backend.Albums.AlbumEditado;
import Backend.Sessions.Session;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Produtor.
 */
public class Produtor extends User {

    // new albums edits referencia todos os albums editados "aqui" em estúdio
    private final Set<Backend.Albums.AlbumEditado> newAlbumsEdits = new TreeSet<>();

    // old albums apenas referencia os albums que não foram editados "aqui"
    private final Set<Backend.Albums.Album> oldAlbums = new TreeSet<>();

    /**
     * Instantiates a new Produtor.
     *
     * @param name        the name
     * @param email       the email
     * @param username    the username
     * @param password    the password
     * @param users       the users
     * @param instruments the instruments
     * @param albums      the albums
     * @param sessions    the sessions
     */
    public Produtor(String name, String email, String username, String password, Backend.Users.Repos users,
                    Backend.Instruments.Repos instruments, Backend.Albums.Repos albums, Backend.Sessions.Repos sessions) {
        super(name, email, username, password, users, instruments, albums, sessions);
        this.getUsersRepo().addUser(this);
    }

    /**
     * Add new album edit.
     *
     * @param projeto the projeto
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void addNewAlbumEdit(Backend.Albums.AlbumEditado projeto) throws IllegalArgumentException {
        if(null != projeto.getProdutor() && projeto.getProdutor().equals(this)){
            throw new IllegalArgumentException("Atenção que o produtor já é o produtor do dado projeto!");
        }
        newAlbumsEdits.add(projeto);
    }

    /**
     * Remove new album edit.
     *
     * @param projeto             the projeto
     * @param replacementProducer the replacement producer
     */
    public void assignNewProducer(AlbumEditado projeto, Produtor replacementProducer) {
        newAlbumsEdits.remove(projeto);
        projeto.setProdutor(replacementProducer);
    }

    public void removeNewAlbumEdit(Backend.Albums.AlbumEditado projeto) {
        newAlbumsEdits.remove(projeto);
    }

    /**
     * Gets new albums edits.
     *
     * @return the new albums edits
     */
    public Set<Backend.Albums.AlbumEditado> getNewAlbumsEdits() {
        return newAlbumsEdits;
    }

    /**
     * Gets new album edit.
     *
     * @param titulo the titulo
     * @return the new album edit
     */
    public Backend.Albums.AlbumEditado getNewAlbumEdit(String titulo) {
        for (Backend.Albums.AlbumEditado projeto : newAlbumsEdits) {
            if (projeto.getTitulo().equals(titulo)) {
                return projeto;
            }
        }
        return null;
    }

    /**
     * Create new album edit backend . albums . album editado.
     *
     * @param albumName    the album name
     * @param newAlbumName the new album name
     * @return the backend . albums . album editado
     * @throws ClassNotFoundException   the class not found exception
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Backend.Albums.AlbumEditado createNewAlbumEdit(String albumName, String newAlbumName)
            throws ClassNotFoundException, IllegalArgumentException {
        // checking for possible errors
        Backend.Albums.Album original = getAlbumsRepo().getAlbum(albumName);
        if (null == original) {
            throw new ClassNotFoundException(albumName + " não foi encontrado.");
        } else if (!getAlbumsRepo().isTituloValid(newAlbumName)) {
            throw new IllegalArgumentException(newAlbumName + " já existe.");
        }
        // create the new album edit
        Backend.Albums.AlbumEditado albumEdit = new Backend.Albums.AlbumEditado(
                newAlbumName, original.getGenero(), original, getInstrumentsRepo(),
                getAlbumsRepo(), getUsersRepo(), getSessionsRepo(), this);
        return albumEdit;
    }

    /**
     * Add old album.
     *
     * @param album the album
     */
// when the album is added by the admin
    public void addOldAlbum(Backend.Albums.Album album) {
        if(album instanceof Backend.Albums.AlbumEditado && !((Backend.Albums.AlbumEditado) album).isEdited()){
            return; // simply continue the program.
        }
        oldAlbums.add(album);
    }

    /**
     * Remove old album.
     *
     * @param album the album
     */
    public void removeOldAlbum(Backend.Albums.Album album) {
        oldAlbums.remove(album);
    }

    /**
     * Gets old albums.
     *
     * @return the old albums
     */
    public Set<Backend.Albums.Album> getOldAlbums() {
        return oldAlbums;
    }


    /**
     * Find session by date session.
     *
     * @param dateInicio the date inicio
     * @param dateFim    the date fim
     * @return the session
     */
// muito usado nas actions do Produtor
    public Session findSessionByDate(LocalDateTime dateInicio, LocalDateTime dateFim) {
        for(AlbumEditado a: newAlbumsEdits) {
            Set<Backend.Sessions.Session> associatedSessions = a.getAllSessions();
            for (Backend.Sessions.Session s : associatedSessions) {
                // if data inicio is between the session dates
                if (s.getDataInicio().isAfter(dateInicio) && s.getDataInicio().isBefore(dateFim)) {
                    return s;
                }
            }
        }
        return null;
    }

    /**
     * Gets old album.
     *
     * @param nome the nome
     * @return the old album
     */
    public Album getOldAlbum(String nome) {
        return this.getAlbumsRepo().getAlbum(nome);
    }

    /**
     * Add musician to session.
     *
     * @param m the m
     * @param s the s
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void addMusicianToSession(Musician m, Session s) throws IllegalArgumentException {
        s.addInvitedMusician(m);
    }
}
