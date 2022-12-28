package Backend.Albums;

import Backend.Sessions.Session;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class AlbumEditado extends Album {
    /*
     Quando se trata de referenciar sessões é muito mais seguro utilizar o ID do que a data, a opção de data é apenas para
        facilitar a visualização do utilizador
     */

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
        this.setDate(LocalDateTime.now());
    }

    public boolean isEdited() {
        return isEdited;
    }

    public Session addSession(LocalDateTime dateInicio, LocalDateTime dateFim) throws IllegalArgumentException {
        if(dateInicio.isAfter(dateFim)) throw new IllegalArgumentException("Data de inicio tem de ser antes da data de fim");
        if (this.isEdited) {
            throw new IllegalArgumentException("The album is already finished");
        }
        if (dateInicio.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("The given start date is a past date");
        }

        // the constructor of session is responsible for also adding the session into the specified album
        Backend.Sessions.Session session = new Backend.Sessions.Session(dateInicio, dateFim, this, getSessionsRepo(),
                                                            getUsersRepo(), getInstrumentsRepo(), getAlbumsRepo());
        this.lastSessionAdded = session;
        return session;
    }

    public boolean addSession(Session s){
        if (this.isEdited) {
            throw new IllegalArgumentException("The album is already finished");
        }
        if (s.getDataInicio().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("The given date is a past date");
        }
        // check if any musician in this session has another session at the same time
        for (Backend.Users.Musician musician : s.getInvitedMusicians().values()) {
            for (Backend.Sessions.Session session : musician.getSessions()) {
                if (session.getDataInicio().isBefore(s.getDataFim()) && session.getDataFim().isAfter(s.getDataInicio())) {
                    throw new IllegalArgumentException("The musician " + musician.getUsername() + " is already in another session at the same time");
                }
            }
        }
        this.lastSessionAdded = s;
        return sessions.add(s);
    }

    public Backend.Sessions.Session getLastSessionAdded(){
        return this.lastSessionAdded;
    }

    public boolean removeSession(LocalDateTime dateInicio) throws IllegalArgumentException {
        if (this.isEdited) {
            throw new IllegalArgumentException("The album you are trying to edit is already finished.");
        }
        if (dateInicio.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("You cannot delete a session that has already finished.");
        }

        Backend.Sessions.Session found = null;
        for(Backend.Sessions.Session s: sessions){
            if(s.getDataInicio().equals(dateInicio)) {
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
