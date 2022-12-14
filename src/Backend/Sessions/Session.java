package Backend.Sessions;

import Backend.Albums.AlbumEditado;
import Backend.Instruments.Instrument;
import Backend.Users.Musician;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Session implements Serializable, Comparable<Session> {
    private static final long serialVersionUID = 4L;
    private final Map<String, Musician> invitedArtists = new HashMap<>();
    private final Set<Instrument> pendentInstruments = new TreeSet<>();
    private final Set<Instrument> instruments = new TreeSet<>();

    private LocalDate date;
    private UUID id = UUID.randomUUID();
    private boolean completed = false;
    private boolean accepted = false;
    private AlbumEditado album = null;
    private Backend.Sessions.Repos sessionRepos;
    private Backend.Users.Repos userRepos;
    private Backend.Instruments.Repos instrumentRepos;
    private Backend.Albums.Repos albumRepos;

    public Session(LocalDate date, AlbumEditado album, Backend.Sessions.Repos sessions, Backend.Users.Repos users,
                   Backend.Instruments.Repos instruments, Backend.Albums.Repos albums) throws IllegalArgumentException {
        this.date = date;
        this.sessionRepos = sessions;
        this.userRepos = users;
        this.instrumentRepos = instruments;
        this.albumRepos = albums;
        this.album = album;
        album.addSession(this);
        sessions.addPendingSession(this);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
            return other.id == null;
        }
        return other.getId().equals(this.id)
                || other.getDate().equals(this.id);
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
    }

    public void addInvitedMusician(Backend.Users.Musician m){
        this.invitedArtists.put(m.getUsername(), m);
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

    // waits for the permission of the administrator
    public void addPendingInstrument(Instrument instrument) throws IllegalArgumentException {
        if(!instrumentRepos.getInstruments().containsKey(instrument.getName().toLowerCase())) {
            throw new IllegalArgumentException("The instrument you requested does not exist in the studio yet.");
        }
        this.pendentInstruments.add(instrument);
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

    @Override
    public int compareTo(Session o) {
        if(o.date.isBefore(this.date)){
            return -1;
        } else if(o.date.equals(this.date)) {
            return 0;
        }
        return 1;
    }

    public boolean equals(Session o){
        if(o.date.equals(this.date)) { return true; }
        else if(o.id.equals(o.getId())) { return true; }

        return false;
    }
}
