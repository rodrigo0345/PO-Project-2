package Backend.Sessions;

import Backend.Albums.AlbumEditado;
import Backend.Instruments.Instrument;
import Backend.Users.Musician;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Session implements Serializable, Comparable<Session> {
    private static final long serialVersionUID = 4L;
    private final Map<String, Musician> invitedArtists = new HashMap<>();
    private final Set<Instrument> pendentInstruments = new TreeSet<>();
    private final Set<Instrument> instruments = new TreeSet<>();

    private LocalDateTime dateInicio;
    private LocalDateTime dateFim;
    private UUID id = UUID.randomUUID();
    private boolean completed = false;
    private boolean accepted = false;
    private AlbumEditado album;
    private Backend.Sessions.Repos sessionRepos;
    private Backend.Users.Repos userRepos;
    private Backend.Instruments.Repos instrumentRepos;
    private Backend.Albums.Repos albumRepos;

    public Session(LocalDateTime datainicio, LocalDateTime datafim, AlbumEditado album, Backend.Sessions.Repos sessions, Backend.Users.Repos users,
                   Backend.Instruments.Repos instruments, Backend.Albums.Repos albums) throws IllegalArgumentException {
        this.dateInicio = datainicio;
        this.dateFim = datafim;
        this.sessionRepos = sessions;
        this.userRepos = users;
        this.instrumentRepos = instruments;
        this.albumRepos = albums;
        this.album = album;
        album.addSession(this);
        sessions.addPendingSession(this);
    }

    public LocalDateTime getDataInicio() {
        return dateInicio;
    }

    public void setDataInicio(LocalDateTime date) {
        this.dateInicio = date.truncatedTo(ChronoUnit.MINUTES);
    }

    public LocalDateTime getDataFim() {
        return dateFim;
    }

    public void setDataFim(LocalDateTime date) {
        this.dateFim = date.truncatedTo(ChronoUnit.MINUTES);
    }

    public UUID getId() {
        return id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Session other = (Session) obj;
        if (id == null) {
            return false;
        }
        return other.getId().equals(this.id)
                || (other.getDataInicio().equals(this.getDataInicio()) && other.getDataFim().equals(this.getDataFim()));
    }

    // used for exceptions
    public void setId(UUID id2) {
        this.id = id2;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;

        if(accepted == true) {
            this.sessionRepos.getSessions().add(this);
        } else {
            this.album.getAllSessions().remove(this);
        }
        this.sessionRepos.getPendingSessions().remove(this);
    }

    public void addInvitedMusician(Backend.Users.Musician m) throws IllegalArgumentException {
        if(!this.isAccepted()) throw new IllegalArgumentException("The session you are trying to modify wasn't yet approved!");
        if(this.isCompleted()) throw new IllegalArgumentException("The session you are trying to access is already completed!");
        // check if the musician does not have other session at the same time
        for(Session s : m.getSessions()) {
            if(s.getDataInicio().isBefore(this.getDataFim()) && s.getDataFim().isAfter(this.getDataInicio())) {
                throw new IllegalArgumentException("The musician you are trying to add is already in another session at the same time!");
            }
        }
        this.invitedArtists.put(m.getUsername(), m);
        m.addSession(album, this);
    }


    public Backend.Users.Musician getInvitedMusician(Musician musician){
        return this.invitedArtists.get(musician.getUsername());
    }

    public Backend.Users.Musician getInvitedMusician(String username){
        return this.invitedArtists.get(username);
    }

    public Map<String, Backend.Users.Musician> getInvitedMusicians(){
        return this.invitedArtists;
    }

    // waits for the permission of the administrator the session itself needs to be approved by the admin too
    // only accessed by the musician
    public Instrument addPendingInstrument(Instrument instrument) throws IllegalArgumentException {
        if(!this.isAccepted()) throw new IllegalArgumentException("The session you are trying to modify wasn't yet approved!");

        if(!instrumentRepos.getInstruments().containsKey(instrument.getName().toLowerCase())) {
            throw new IllegalArgumentException("The instrument you requested does not exist in the studio yet.");
        }
        this.pendentInstruments.add(instrument);
        return instrument;
    }

    // waits for the permission of the administrator the session itself needs to be approved by the admin too
    // only accessed by the musician
    public Instrument addPendingInstrument(String name) throws IllegalArgumentException {
        if(!this.isAccepted()) throw new IllegalArgumentException("The session you are trying to modify wasn't yet approved!");

        if(!instrumentRepos.getInstruments().containsKey(name.toLowerCase())){
            throw new IllegalArgumentException("The instrument you requested does not exist in the studio yet.");
        }
        Instrument i = instrumentRepos.getInstrument(name.toLowerCase());
        this.pendentInstruments.add(i);
        return i;
    }

    public Set<Instrument> getPendentInstruments(){
        return this.pendentInstruments;
    }

    // only Administrators can have access to this method
    public boolean approveInstrument(Instrument instrument){
        return this.pendentInstruments.remove(instrument) && this.instruments.add(instrument);
    }

    // only Administrators can have access to this method
    public boolean denyInstrument(Instrument instrument){
        return this.pendentInstruments.remove(instrument);
    }

    public Set<Instrument> getApprovedInstruments(){
        return this.instruments;
    }

    public boolean doesSessionOverlap(Session other){
        if (this.getDataInicio().isAfter(other.getDataFim()) || this.getDataFim().isBefore(other.getDataInicio())) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Session o) {
        if(o.getDataInicio().isBefore(this.getDataInicio())){
            return -1;
        } else if(o.getDataInicio().equals(this.getDataInicio())) {
            return 0;
        }
        return 1;
    }

    public boolean equals(Session o){
        if(o.getDataInicio().equals(this.getDataInicio()) && o.getDataFim().equals(this.getDataFim())) { return true; }
        else if(o.id.equals(o.getId())) { return true; }

        return false;
    }
}
