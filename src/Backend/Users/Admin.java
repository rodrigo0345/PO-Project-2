package Backend.Users;

import Backend.Albums.Album;
import Backend.Albums.AlbumEditado;
import Backend.Instruments.Instrument;
import Backend.Sessions.Session;
import Backend.Tracks.Track;
import Backend.Useful.StringChecker;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class Admin extends User {

    public Admin(String name, String email, String username, String password,
            Backend.Instruments.Repos instruments, Backend.Albums.Repos albums,
            Backend.Users.Repos users, Backend.Sessions.Repos sessions) {
        super(name, email, username, password, users, instruments, albums, sessions);
        super.getUsersRepo().addUser(this);
    }

    public void addInstrument(String name) {
        Instrument instrument = new Instrument(name);
        getInstrumentsRepo().addInstrument(instrument);
    }

    public void acceptInstrumentRequest(String name, Session session){
        if (super.getInstrumentsRepo().getInstrument(name) == null) return; // does not exist
        session.approveInstrument(super.getInstrumentsRepo().getInstrument(name));
    }

    public void acceptInstrumentRequest(Instrument instrument, Session session){
        if (instrument == null) return; // does not exist
        session.approveInstrument(instrument);
    }

    public void denyInstrumentRequest(String name, Session session){
        if (super.getInstrumentsRepo().getInstrument(name) == null) return; // does not exist
        session.denyInstrument(super.getInstrumentsRepo().getInstrument(name));
    }

    public void removeInstrument(String name) {
        Instrument ref = getInstrumentsRepo().getInstrument(name);

        // preciso eliminar todas as referencias do objeto que vai ser eliminado
        for(Backend.Sessions.Session s: super.getSessionsRepo().getSessions()){
            s.getPendentInstruments().remove(ref);
            s.getApprovedInstruments().remove(ref);
        }
        for(Backend.Sessions.Session s: super.getSessionsRepo().getPendingSessions()){
            s.getApprovedInstruments().remove(ref);
            s.getPendentInstruments().remove(ref);
        }
        getInstrumentsRepo().removeInstrument(name);
    }

    public Backend.Users.Musician addMusician(String name, String email, String username, String password)
            throws IllegalArgumentException {
        if (!getUsersRepo().isUserValid(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        try {
            StringChecker.validName(name);
            StringChecker.validEmail(email);
        } catch (IllegalArgumentException e) {
            throw e;
        }
        // automatically adds it to the user repos
        return new Musician(name, email, username, password, getUsersRepo(), getInstrumentsRepo(),
                getAlbumsRepo(), getSessionsRepo());
    }

    public Produtor addProdutor(String name, String email, String username, String password)
            throws IllegalArgumentException {
        if (!getUsersRepo().isUserValid(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        try {
            StringChecker.validName(name);
            StringChecker.validEmail(email);
        } catch (IllegalArgumentException e) {
            throw e;
        }
        // automatically adds it to the user repos
        return new Produtor(name, email, username, password, getUsersRepo(), getInstrumentsRepo(),
                getAlbumsRepo(), getSessionsRepo());
    }

    public void removeUser(String username) throws Exception {
        // implement a way of removing all the associated albums and musics
        User user = getUsersRepo().getUser(username);

        if(user instanceof Musician) {
            for(Album a: super.getAlbumsRepo().getAlbums().values()){
                a.removeArtist(username);

                for(Track t: a.getTracks().values()){
                    t.removeArtist((Musician) user);
                }
            }
        } else if(user instanceof Produtor){
            for(Album a : super.getAlbumsRepo().getAlbums().values()){
                if(a.getProdutor().getUsername().equals(username)) throw new Exception("The user you are trying to remove " +
                        "has produced albums associated to him, in order to fix this issue.");
            }
        }

        getUsersRepo().removeUser(username);
    }

    // returns -1 if the Sessions repo is empty
    public Set<Session> getAllSessionRequests() {
        Set<Session> s = new TreeSet<Session>();
        if (getSessionsRepo().getPendingSessions().size() == 0) {
            return null;
        } else {
            for (Backend.Sessions.Session session : getSessionsRepo().getPendingSessions()) {
                if (session.isAccepted() == false) {
                    s.add(session);
                }
            }
        }
        return s;
    }

    public void showAllRecordingSessions() {
        for (Backend.Sessions.Session session : getSessionsRepo().getSessions()) {
            System.out.println(session);
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

    public String getStats() {
        // total de albums em edição
        int countNotFinishedAlbums = 0, countFinishedAlbums = 0;
        for(Album a: super.getAlbumsRepo().getAlbums().values()){
              if(a instanceof AlbumEditado && !((AlbumEditado)a).isEdited()){
                  countNotFinishedAlbums++;
              }
              if(a instanceof AlbumEditado && ((AlbumEditado)a).isEdited()){
                  countFinishedAlbums++;
              }
        }

        int countSessionsCompleted = 0;
        for(Session s: super.getSessionsRepo().getSessions()){
            if(s.isCompleted()){
                countSessionsCompleted++;
            }
        }
        double percentage = (double)countSessionsCompleted /
                                (double)(super.getSessionsRepo().getSessions().size() + super.getSessionsRepo().getPendingSessions().size())
                            * 100;
        return "Not finished albums: " + countNotFinishedAlbums + "\n" +
                "Finished albums: " + countFinishedAlbums + "\n" +
                "Percentage of sessions completed: " + percentage + "%";
    }

    public void acceptSessionRequest(UUID id) {
        for(Session s:getSessionsRepo().getPendingSessions()){
            if (s.getId().equals(id)) {s.setAccepted(true); return; };
        }
    }

    public void rejectSessionRequest(UUID id) {
        (getSessionsRepo().getSession(id)).setAccepted(false);
    }

    public void addAlbum(String name, String genre, LocalDate date, Backend.Users.Produtor produtor) {
        Backend.Albums.Album album = new Backend.Albums.Album(name, genre, date, produtor, super.getInstrumentsRepo(),
                super.getAlbumsRepo(), super.getUsersRepo(), super.getSessionsRepo());
        getAlbumsRepo().addAlbum(album);
        produtor.addOldAlbum(album);
    }

    public void addMusicianToAlbum(String username, String titleOfTheAlbum) {
        Backend.Albums.Album album = getAlbumsRepo().getAlbum(titleOfTheAlbum);
        Backend.Users.Musician musician = (Backend.Users.Musician) getUsersRepo().getUser(username);
        album.addArtist(musician);
        musician.addAlbum(album);
    }

    // aqui tratamos de adicionar albums que não foram editados na editora
    public void addProdutorToAlbum(String username, String titleOfTheAlbum) {
        Backend.Albums.Album album = getAlbumsRepo().getAlbum(titleOfTheAlbum);
        Backend.Users.Produtor produtor = (Backend.Users.Produtor) getUsersRepo().getUser(username);
        album.setProdutorOriginal(produtor);
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

    public Set<Instrument> getPendentInstruments(){
        Set<Instrument> i = new TreeSet<>();
        for(Session s: getSessionsRepo().getSessions()){
            i.addAll(s.getPendentInstruments());
        }
        return i;
    }
}
