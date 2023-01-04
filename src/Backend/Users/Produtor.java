package Backend.Users;

import Backend.Albums.Album;
import Backend.Albums.AlbumEditado;
import Backend.Sessions.Session;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

public class Produtor extends User {
    private final Set<Backend.Albums.AlbumEditado> projetos = new TreeSet<>();
    private final Set<Backend.Albums.Album> oldAlbums = new TreeSet<>();

    public Produtor(String name, String email, String username, String password, Backend.Users.Repos users,
            Backend.Instruments.Repos instruments, Backend.Albums.Repos albums, Backend.Sessions.Repos sessions) {
        super(name, email, username, password, users, instruments, albums, sessions);
        super.getUsersRepo().addUser(this);
    }

    public void addProjeto(Backend.Albums.AlbumEditado projeto) throws IllegalArgumentException {
        if(projeto.getProdutor() != null && projeto.getProdutor().equals(this)){
            throw new IllegalArgumentException("O produtor já é o produtor do dado projeto!");
        }
        //if (projeto.getProdutor() != null) {
        //    throw new IllegalArgumentException("The given album is already associated with another producer");
        //}
        projetos.add(projeto);
    }

    public void removeProjeto(Backend.Albums.AlbumEditado projeto, Produtor backup) {
        projetos.remove(projeto);
        projeto.setProdutor(backup);
    }

    public Set<Backend.Albums.AlbumEditado> getProjetos() {
        return projetos;
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

    public Backend.Albums.AlbumEditado getProjeto(String titulo) {
        for (Backend.Albums.AlbumEditado projeto : projetos) {
            if (projeto.getTitulo().equals(titulo)) {
                return projeto;
            }
        }
        return null;
    }

    public Backend.Albums.AlbumEditado createAlbumEdit(String albumName, String newAlbumName)
        throws ClassNotFoundException, IllegalArgumentException {
        // checking for possible errors
        Backend.Albums.Album original = getAlbumsRepo().getAlbum(albumName);
        if (original == null) {
            throw new ClassNotFoundException(albumName + " não foi encontrado.");
        } else if (!getAlbumsRepo().isTituloValid(newAlbumName)) {
            throw new IllegalArgumentException(newAlbumName + " já existe.");
        }
        // create the new album edit
        Backend.Albums.AlbumEditado albumEdit = new Backend.Albums.AlbumEditado(
            newAlbumName, original.getGenero(), original, getInstrumentsRepo(),
            getAlbumsRepo(), getUsersRepo(), getSessionsRepo(), this);
        this.addProjeto(albumEdit);
        return albumEdit;
    }

    public Session findSessionByDate(LocalDateTime dateInicio, LocalDateTime dateFim) {
        for(AlbumEditado a: projetos) {
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
        return super.getAlbumsRepo().getAlbum(nome);
    }

    public void addMusicianToSession(Musician m, Session s) throws IllegalArgumentException {
        s.addInvitedMusician(m);
    }
}
