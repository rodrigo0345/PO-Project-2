package Backend.Users;

import java.io.Serializable;

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
        getUsersRepo().removeUser(username);
    }

    public int showAllSessionRequests() {
        if (getSessionsRepo().getSessions() == null) {
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
        for (Backend.Albums.Album album : getAlbumsRepo().getAlbums()) {
            if (album instanceof Backend.Albums.AlbumEditado && !((Backend.Albums.AlbumEditado) album).isEdited()) {
                System.out.println(album);
            }
        }
    }

    public void showAllAlbumsEdited() {
        for (Backend.Albums.Album album : getAlbumsRepo().getAlbums()) {
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
}
