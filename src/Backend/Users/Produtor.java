package Backend.Users;

import Backend.Albums.Album;
import Backend.Albums.AlbumEditado;
import Backend.Sessions.Session;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

public class Produtor extends User {

    // new albums edits referencia todos os albums editados "aqui" em estúdio
    private final Set<Backend.Albums.AlbumEditado> newAlbumsEdits = new TreeSet<>();

    // old albums apenas referencia os albums que não foram editados "aqui"
    private final Set<Backend.Albums.Album> oldAlbums = new TreeSet<>();

    public Produtor(String name, String email, String username, String password, Backend.Users.Repos users,
                    Backend.Instruments.Repos instruments, Backend.Albums.Repos albums, Backend.Sessions.Repos sessions) {
        super(name, email, username, password, users, instruments, albums, sessions);
        this.getUsersRepo().addUser(this);
    }

    public void addNewAlbumEdit(Backend.Albums.AlbumEditado projeto) throws IllegalArgumentException {
        if(null != projeto.getProdutor() && projeto.getProdutor().equals(this)){
            throw new IllegalArgumentException("Atenção que o produtor já é o produtor do dado projeto!");
        }
        newAlbumsEdits.add(projeto);
    }

    public void removeNewAlbumEdit(AlbumEditado projeto, Produtor replacementProducer) {
        newAlbumsEdits.remove(projeto);
        projeto.setProdutor(replacementProducer);
    }

    public Set<Backend.Albums.AlbumEditado> getNewAlbumsEdits() {
        return newAlbumsEdits;
    }

    public Backend.Albums.AlbumEditado getNewAlbumEdit(String titulo) {
        for (Backend.Albums.AlbumEditado projeto : newAlbumsEdits) {
            if (projeto.getTitulo().equals(titulo)) {
                return projeto;
            }
        }
        return null;
    }

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

    // when the album is added by the admin
    public void addOldAlbum(Backend.Albums.Album album) {
        if(album instanceof Backend.Albums.AlbumEditado && !((Backend.Albums.AlbumEditado) album).isEdited()){
            return; // simply continue the program.
        }
        oldAlbums.add(album);
    }

    public void removeOldAlbum(Backend.Albums.Album album) {
        oldAlbums.remove(album);
    }

    public Set<Backend.Albums.Album> getOldAlbums() {
        return oldAlbums;
    }


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

    public Album getOldAlbum(String nome) {
        return this.getAlbumsRepo().getAlbum(nome);
    }

    public void addMusicianToSession(Musician m, Session s) throws IllegalArgumentException {
        s.addInvitedMusician(m);
    }
}
