package Backend.Users;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import Backend.Instruments.*;

public class Admin extends User {

    public Admin(String name, String email, String username, String password,
            Backend.Instruments.Repos instruments, Backend.Albums.Repos albums,
            Backend.Users.Repos users, Backend.Sessions.Repos sessions) {
        super(name, email, username, password, users, instruments, albums, sessions);
    }

    public void addInstrument(String name) {
        Instrument instrument = new Instrument(name);
        getInstrumentsRepo().addInstrument(instrument);
    }

    public void removeInstrument(String name) {
        getInstrumentsRepo().removeInstrument(name);
    }

    public void addMusician(String name, String email, String username, String password)
            throws IllegalArgumentException {
        Musician musician = new Musician(name, email, username, password, getUsersRepo(), getInstrumentsRepo(),
                getAlbumsRepo(), getSessionsRepo());
        if (getUsersRepo().isUserValid(musician)) {
            getUsersRepo().addUser(musician);
        } else {
            throw new IllegalArgumentException("Username already exists");
        }
    }

    public void addProdutor(String name, String email, String username, String password)
            throws IllegalArgumentException {
        Produtor produtor = new Produtor(name, email, username, password, getUsersRepo(), getInstrumentsRepo(),
                getAlbumsRepo(), getSessionsRepo());
        if (getUsersRepo().isUserValid(produtor)) {
            getUsersRepo().addUser(produtor);
        } else {
            throw new IllegalArgumentException("Username already exists");
        }
    }

    public void removeUser(String username) {
        // implement a way of removing all the associated albums and musics
        getUsersRepo().removeUser(username);
    }

    // returns -1 if the Sessions repo is empty
    public int showAllSessionRequests() {
        if (getSessionsRepo().getSessions().size() == 0) {
            return -1;
        } else {
            for (Backend.Sessions.Session session : getSessionsRepo().getSessions()) {
                if (session.getAccepted() == null) {
                    System.out.println(session);
                }
            }
        }
        return 0;
    }

    public void showAllRecordingSessions() {
        for (Backend.Sessions.Session session : getSessionsRepo().getSessions()) {
            if (((Boolean) session.getAccepted()).equals(true)) {
                System.out.println(session);
            }
        }
    }

    public void showAllAlbumsBeingEdited() {
        for (Backend.Albums.Album album : getAlbumsRepo().getAlbums().values()) {
            if (album instanceof Backend.Albums.AlbumEditado && !((Backend.Albums.AlbumEditado) album).isEdited()) {
                System.out.println(album);
            }
        }
    }

    public void showAllAlbumsEdited() {
        for (Backend.Albums.Album album : getAlbumsRepo().getAlbums().values()) {
            if (album instanceof Backend.Albums.AlbumEditado && ((Backend.Albums.AlbumEditado) album).isEdited()) {
                System.out.println(album);
            }
        }
    }

    public void showStats() {
    }

    public void acceptSessionRequest(int id) {
        (getSessionsRepo().getSession(id)).setAccepted(true);
    }

    public void rejectSessionRequest(int id) {
        (getSessionsRepo().getSession(id)).setAccepted(false);
    }

    public void addAlbum(String name, String genre, LocalDate date, Backend.Users.Produtor produtor) {
        Backend.Albums.Album album = new Backend.Albums.Album(name, genre, date, produtor, instrumentsRepo, albumsRepo,
                usersRepo,
                sessionsRepo);
        getAlbumsRepo().addAlbum(album);
    }

    public void addMusicianToAlbum(String username, String titleOfTheAlbum) {
        Backend.Albums.Album album = getAlbumsRepo().getAlbum(titleOfTheAlbum);
        Backend.Users.Musician musician = (Backend.Users.Musician) getUsersRepo().getUser(username);
        album.addArtist(musician);
        musician.addAlbum(album);
    }

    // aqui tratamos de adicionar albums que não foram editados na editora
    public void setProdutorToAlbum(String username, String titleOfTheAlbum) {
        Backend.Albums.Album album = getAlbumsRepo().getAlbum(titleOfTheAlbum);
        Backend.Users.Produtor produtor = (Backend.Users.Produtor) getUsersRepo().getUser(username);
        album.setProdutor(produtor);
        produtor.addOldAlbum(album);
    }

    // automaticamente associamos o album aos artistas que participaram na track
    public void addTrackToAlbum(String titleOfTheAlbum, Backend.Tracks.Track track) {
        Backend.Albums.Album album = getAlbumsRepo().getAlbum(titleOfTheAlbum);
        album.addTrack(track);
        for (Backend.Users.Musician musician : track.getArtists()) {
            musician.addAlbum(album);
        }
    }

}
