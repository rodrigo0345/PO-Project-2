package Backend.Users;

import java.io.Serializable;

import Backend.Instruments.*;

public class Admin extends User {
    Backend.Instruments.Repos instruments;
    Backend.Albums.Repos albums;
    Backend.Users.Repos users;
    Backend.Sessions.Repos sessions;

    public Admin(String name, String email, String username, String password, 
                    Backend.Instruments.Repos instruments, Backend.Albums.Repos albums, 
                        Backend.Users.Repos users, Backend.Sessions.Repos sessions) {
        super(name, email, username, password);
        this.instruments = instruments;
        this.albums = albums;
        this.users = users;
        this.sessions = sessions;
    }

    public void addInstrument(String name) {
        Instrument instrument = new Instrument(name);
        instruments.addInstrument(instrument);
    }

    public void removeInstrument(String name) {
        instruments.removeInstrument(name);
    }

    public void addMusician(String name, String email, String username, String password) {
        Musician musician = new Musician(name, email, username, password);
        users.addUser(musician);
    }

    public void addProdutor(String name, String email, String username, String password) {
        Produtor produtor = new Produtor(name, email, username, password);
        users.addUser(produtor);
    }

    public void removeUser(String username) {
        users.removeUser(username);
    }

    public int showAllSessionRequests() {
        if (sessions.getSessions() == null) {
            return -1;
        } else {
            for (Backend.Sessions.Session session : sessions.getSessions()) {
                if (session.getAccepted() == null) {
                    System.out.println(session);
                }
            }
        }
        return 0;
    }

    public void showAllRecordingSessions() {
        for (Backend.Sessions.Session session : sessions.getSessions()) {
            if (((Boolean)session.getAccepted()).equals(true)) {
                System.out.println(session);
            }
        }

    }

    public void showAllAlbumsBeingEdited() {
        for (Backend.Albums.Album album : albums.getAlbums()) {
            if (album instanceof Backend.Albums.AlbumEditado && !((Backend.Albums.AlbumEditado)album).isEdited()) {
                System.out.println(album);
            }
        }
    }

    public void showAllAlbumsEdited() {
        for (Backend.Albums.Album album : albums.getAlbums()) {
            if (album instanceof Backend.Albums.AlbumEditado && ((Backend.Albums.AlbumEditado)album).isEdited()) {
                System.out.println(album);
            }
        }
    }

    public void showStats() {
    }

    public void acceptSessionRequest(int id) {
        (sessions.getSession(id)).setAccepted(true);
    }

    public void rejectSessionRequest(int id) {
        (sessions.getSession(id)).setAccepted(false);
    }
}
