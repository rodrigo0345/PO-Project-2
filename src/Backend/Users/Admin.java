package Backend.Users;

import java.io.Serializable;

import Backend.Instruments.*;

public class Admin extends User implements Serializable {

    public Admin(String name, String email, String username, String password) {
        super(name, email, username, password);
    }

    public void addInstrument(String name) {
        Instrument instrument = new Instrument(name);
        Backend.Instruments.Repos.addInstrument(instrument);
    }

    public void removeInstrument(String name) {
        Backend.Instruments.Repos.removeInstrument(name);
    }

    public void addMusician(String name, String email, String username, String password) {
        Musician musician = new Musician(name, email, username, password);
        Backend.Users.Repos.addUser(musician);
    }

    public void addProdutor(String name, String email, String username, String password) {
        Produtor produtor = new Produtor(name, email, username, password);
        Backend.Users.Repos.addUser(produtor);
    }

    public void removeUser(String username) {
        Backend.Users.Repos.removeUser(username);
    }

    public int showAllSessionRequests() {
        if (Backend.Sessions.Repos.getSessions() == null) {
            return -1;
        } else {
            for (Backend.Sessions.Session session : Backend.Sessions.Repos.getSessions()) {
                if (session.getAccepted() == null) {
                    System.out.println(session);
                }
            }
        }
        return 0;
    }

    public void showAllRecordingSessions() {
        for (Backend.Sessions.Session session : Backend.Sessions.Repos.getSessions()) {
            if (((Boolean)session.getAccepted()).equals(true)) {
                System.out.println(session);
            }
        }

    }

    public void showAllAlbumsBeingEdited() {
        for (Backend.Albums.Album album : Backend.Albums.Repos.getAlbums()) {
            if (album instanceof Backend.Albums.AlbumEditado && !((Backend.Albums.AlbumEditado)album).isEdited()) {
                System.out.println(album);
            }
        }
    }

    public void showAllAlbumsEdited() {
        for (Backend.Albums.Album album : Backend.Albums.Repos.getAlbums()) {
            if (album instanceof Backend.Albums.AlbumEditado && ((Backend.Albums.AlbumEditado)album).isEdited()) {
                System.out.println(album);
            }
        }
    }

    public void showStats() {
    }

    public void acceptSessionRequest(int id) {
        (Backend.Sessions.Repos.getSession(id)).setAccepted(true);
    }

    public void rejectSessionRequest(int id) {
        (Backend.Sessions.Repos.getSession(id)).setAccepted(false);
    }
}
