package Backend.Users;

import Backend.Albums.Album;
import Backend.Albums.AlbumEditado;
import Backend.Sessions.Session;

import java.text.DateFormat;
import java.time.LocalDate;
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

    public void addProjeto(Backend.Albums.AlbumEditado projeto) {
        if (projeto.getProducer() != null && !projeto.getProducer().equals(this)) return;
        projetos.add(projeto);
    }

    public void removeProjeto(Backend.Albums.AlbumEditado projeto) {
        projetos.remove(projeto);
    }

    public Set<Backend.Albums.AlbumEditado> getProjetos() {
        return projetos;
    }

    // when the album is added by the admin
    public void addOldAlbum(Backend.Albums.Album album) {
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
            throw new ClassNotFoundException(albumName + " not found.");
        } else if (getAlbumsRepo().isTituloValid(newAlbumName)) {
            throw new IllegalArgumentException(newAlbumName + " already exists.");
        }
        // create the new album edit
        Backend.Albums.AlbumEditado albumEdit = new Backend.Albums.AlbumEditado(
            newAlbumName, original.getGenero(), getInstrumentsRepo(),
            getAlbumsRepo(), getUsersRepo(), getSessionsRepo(), this);
        this.addProjeto(albumEdit);
        return albumEdit;
    }

    public Session findSessionByDate(LocalDate d) {
        for(AlbumEditado a: projetos) {
            Set<Backend.Sessions.Session> associatedSessions = a.getAllSessions();
            for (Backend.Sessions.Session s : associatedSessions) {
                if (s.getDate().equals(d)) return s;
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
