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

    public AlbumEditado(String titulo, String genero,
            Backend.Instruments.Repos instruments,
            Backend.Albums.Repos albums,
            Backend.Users.Repos users, Backend.Sessions.Repos sessions, Backend.Users.Produtor producer) {
        super(titulo, genero, null, producer, instruments, albums, users, sessions);
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
        this.setDate(LocalDate.now());
    }

    public boolean isEdited() {
        return isEdited;
    }

    public Backend.Sessions.Session addSession(LocalDate date) throws IllegalArgumentException {
        if (this.isEdited) {
            throw new IllegalArgumentException("The given album is already finished");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("The given date is a past date");
        }
        Backend.Sessions.Session session = new Backend.Sessions.Session(date);
        sessions.add(session);
        return session;
    }

    public void removeSession(LocalDate date) throws IllegalArgumentException {
        if (this.isEdited) {
            throw new IllegalArgumentException("The album you are trying to edit is already finished.");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("You cannot delete a session that has already finished.");
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

    public Set<Backend.Sessions.Session> getAllSessions() {
        return sessions;
    }
}
