package Backend.Albums;

import java.sql.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class AlbumEditado extends Album {
    private boolean isEdited;
    private Set<Backend.Sessions.Session> sessions = new TreeSet<>();
    private Backend.Users.Produtor producer;

    public AlbumEditado(String titulo, String genero, Backend.Users.Produtor producer) {
        super(titulo, genero, null);
        this.isEdited = false;
        this.producer = producer;
    }

    public void setProducer(Backend.Users.Produtor producer) {
        this.producer = producer;
    }

    public Backend.Users.Produtor getProducer() {
        return producer;
    }

    private void setEdited() {
        this.isEdited = true;
        this.setDate(new Date(System.currentTimeMillis()));
    }

    public void addSession(Date date) {
        if (this.isEdited) {
            return;
        }
        if (date.before(new Date(System.currentTimeMillis()))) {
            return;
        }
        Backend.Sessions.Session session = new Backend.Sessions.Session(date);
        sessions.add(session);
    }

    public void removeSession(Date date) {
        if (this.isEdited) {
            return;
        }
        if (date.before(new Date(System.currentTimeMillis()))) {
            return;
        }
        Backend.Sessions.Session session = new Backend.Sessions.Session(date);
        sessions.remove(session);
    }

    public void markSessionAsCompleted(UUID id) {
        if (this.isEdited) {
            return;
        }
        Backend.Sessions.Session session = new Backend.Sessions.Session(null);
        session.setId(id);
        if (sessions.contains(session)) {
            session.setCompleted(true);
        }
    }
}