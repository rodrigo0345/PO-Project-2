package Backend.Albums;

import Backend.Sessions.Session;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class AlbumEditado extends Album {
    private boolean isEdited;
    private final Set<Backend.Sessions.Session> sessions = new TreeSet<>();
    private Backend.Sessions.Session lastSessionAdded;

    // there are 2 produtores, one for the original album and one for the edited album
    private Backend.Users.Produtor produtor;

    public AlbumEditado(String titulo, String genero,
            Album original,
            Backend.Instruments.Repos instruments,
            Backend.Albums.Repos albums,
            Backend.Users.Repos users, Backend.Sessions.Repos sessions, Backend.Users.Produtor producer) {

        super(titulo, genero, null, // still being edited
                original.getProdutor(), instruments, albums, users, sessions);

        this.isEdited = false;
        producer.addProjeto(this);
        this.setProdutor(producer);
    }

    public AlbumEditado(String titulo){
        super(titulo);
        this.isEdited = false;
        this.setProdutor(null);
    }

    public void setProdutor(Backend.Users.Produtor produtor) {
        if (produtor == null) return;
        produtor.addProjeto(this);
        this.produtor = produtor;
    }

    @Override
    public Backend.Users.Produtor getProdutor() {
        return this.produtor;
    }

    public void setAlbumAsComplete() {
        this.isEdited = true;
        this.setDate(LocalDate.now());
    }

    public boolean isEdited() {
        return isEdited;
    }

    public Session addSession(LocalDate date) throws IllegalArgumentException {
        if (this.isEdited) {
            throw new IllegalArgumentException("The album is already finished");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("The given date is a past date");
        }

        // the constructor of session is responsible for also adding the session into the specified album
        Backend.Sessions.Session session = new Backend.Sessions.Session(date, this, getSessionsRepo(),
                                                            getUsersRepo(), getInstrumentsRepo(), getAlbumsRepo());
        this.lastSessionAdded = session;
        return session;
    }

    public boolean addSession(Session s){
        if (this.isEdited) {
            throw new IllegalArgumentException("The album is already finished");
        }
        if (s.getDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("The given date is a past date");
        }
        this.lastSessionAdded = s;
        return sessions.add(s);
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
