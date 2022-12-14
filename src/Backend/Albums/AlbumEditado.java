package Backend.Albums;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class AlbumEditado extends Album {
    private boolean isEdited;
    private final Set<Backend.Sessions.Session> sessions = new TreeSet<>();
    private Backend.Users.Produtor producer;

    private Backend.Sessions.Session lastSessionAdded;

    public AlbumEditado(String titulo, String genero,
            Backend.Instruments.Repos instruments,
            Backend.Albums.Repos albums,
            Backend.Users.Repos users, Backend.Sessions.Repos sessions, Backend.Users.Produtor producer) {
        super(titulo, genero, null, producer, instruments, albums, users, sessions);
        this.isEdited = false;
        this.producer = producer;
    }

    public AlbumEditado(String titulo){
        super(titulo);
        this.isEdited = false;
        this.producer = null;
    }

    public void setProducer(Backend.Users.Produtor producer) {
        if (producer == null) return;
        this.producer = producer;
        this.producer.addProjeto(this);
    }

    public Backend.Users.Produtor getProducer() {
        return producer;
    }

    public void setAlbumAsComplete() {
        this.isEdited = true;
        this.setDate(LocalDate.now());
    }

    public boolean isEdited() {
        return isEdited;
    }

    public boolean addSession(LocalDate date) throws IllegalArgumentException {
        if (this.isEdited) {
            throw new IllegalArgumentException("The album is already finished");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("The given date is a past date");
        }
        Backend.Sessions.Session session = new Backend.Sessions.Session(date, getSessionsRepo(),
                                                            getUsersRepo(), getInstrumentsRepo(), getAlbumsRepo());
        this.lastSessionAdded = session;
        return sessions.add(session);
    }

    public Backend.Sessions.Session getLastSessionAdded(){
        return this.lastSessionAdded;
    }

    public boolean removeSession(LocalDate date) throws IllegalArgumentException {
        if (this.isEdited) {
            throw new IllegalArgumentException("The album you are trying to edit is already finished.");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("You cannot delete a session that has already finished.");
        }

        Backend.Sessions.Session found = null;
        for(Backend.Sessions.Session s: sessions){
            if(s.getDate().equals(date)) {
                found = s;
            }
        }
        if (found == null) return false;
        return sessions.remove(found);
    }

    public boolean removeSession(UUID id) throws IllegalArgumentException {
        if (this.isEdited) {
            throw new IllegalArgumentException("The album you are trying to edit is already finished.");
        }
        Backend.Sessions.Session found = null;
        for (Backend.Sessions.Session s: sessions){
            if(s.getId().equals(id)) {
                found = s;
            }
        }
        if (found == null) return false;
        return sessions.remove(found);
    }

    public void markSessionAsCompleted(UUID id) {
        if (this.isEdited) {
            return;
        }
        for(Backend.Sessions.Session s: sessions){
            if(s.getId().equals(id)){
                s.setCompleted(true);
            }
        }
    }

    public Set<Backend.Sessions.Session> getAllSessions() {
        return sessions;
    }
}
