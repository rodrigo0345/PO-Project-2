package Backend.Sessions;

import Backend.Albums.AlbumEditado;
import Backend.Instruments.Instrument;
import Backend.Users.Musician;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Session implements Serializable, Comparable<Session> {//Traduzido
    @Serial
    private static final long serialVersionUID = 4L;
    private final Map<String, Musician> invitedArtists = new HashMap<>();
    private final Set<Instrument> pendentInstruments = new TreeSet<>();
    private final Set<Instrument> instruments = new TreeSet<>();
    private final Set<Instrument> refusedInstruments = new TreeSet<>();
    private LocalDateTime startDate, endDate;
    private UUID id = UUID.randomUUID();
    private boolean completed, accepted, rejected;
    private final AlbumEditado album;
    private final Backend.Sessions.Repos sessionRepos;
    private final Backend.Users.Repos userRepos;
    private final Backend.Instruments.Repos instrumentRepos;
    private final Backend.Albums.Repos albumRepos;

    public Session(LocalDateTime datainicio, LocalDateTime datafim, AlbumEditado album, Backend.Sessions.Repos sessions, Backend.Users.Repos users,
                   Backend.Instruments.Repos instruments, Backend.Albums.Repos albums) throws IllegalArgumentException {
        this.startDate = datainicio;
        this.endDate = datafim;
        this.sessionRepos = sessions;
        this.userRepos = users;
        this.instrumentRepos = instruments;
        this.albumRepos = albums;
        this.album = album;
        album.addSession(this);
        sessions.addPendingSession(this);
    }

    public LocalDateTime getDataInicio() {
        return this.startDate;
    }

    public void setDataInicio(LocalDateTime date) {
        this.startDate = date.truncatedTo(ChronoUnit.MINUTES);
    }

    public LocalDateTime getDataFim() {
        return this.endDate;
    }

    public void setDataFim(LocalDateTime date) {
        this.endDate = date.truncatedTo(ChronoUnit.MINUTES);
    }

    public UUID getId() {
        return this.id;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) throws Exception {
        if (this.sessionRepos.getPendingSessions().contains(this)) {
            throw new Exception("Sessão ainda não foi aceite pelo administrador");
        }
        this.completed = completed;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((null == this.id) ? 0 : this.id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (null == obj)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        Session other = (Session) obj;
        if (null == this.id) {
            return false;
        }
        return other.id.equals(this.id)
                || (other.startDate.equals(this.startDate) && other.endDate.equals(this.endDate));
    }

    // used for exceptions
    public void setId(UUID id2) {
        this.id = id2;
    }

    public boolean isAccepted() {
        return this.accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;

        if(accepted) {
            this.sessionRepos.getSessions().add(this);
        } else {
            this.album.getAllSessions().remove(this);
        }
        this.sessionRepos.getPendingSessions().remove(this);
    }

    public void addInvitedMusician(Backend.Users.Musician m) throws IllegalArgumentException {
        if(!this.accepted) throw new IllegalArgumentException("A sessão que está a tentar modificar ainda não foi aprovada!");
        if(this.completed) throw new IllegalArgumentException("A sessão a que está a tentar aceder já foi terminada!");
        // check if the musician does not have other session at the same time
        for(Session s : m.getSessions()) {
            if(s.startDate.isBefore(this.endDate) && s.endDate.isAfter(this.startDate)) {
                throw new IllegalArgumentException("O músico que está a tentar adicionar já se encontra em outra sessão ao mesmo tempo!");
            }
        }
        this.invitedArtists.put(m.getUsername(), m);
        m.addSession(this.album, this);
    }

    public void removeInvitedMusician(Backend.Users.Musician m) throws IllegalArgumentException {
        if(!this.accepted) throw new IllegalArgumentException("A sessão que está a tentar modificar ainda não foi aprovada!");
        if(this.completed) throw new IllegalArgumentException("A sessão a que está a tentar aceder já foi terminada!");
        this.invitedArtists.remove(m.getUsername());
        m.removeSession(this.album, this);
    }

    public void removeAllInvitedMusicians() throws IllegalArgumentException {
        this.invitedArtists.forEach((s, artist) -> this.removeInvitedMusician(artist));
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
    // only accessed by the musician.
    // Creates a copy of an Instrument and adds it to the session
    public Instrument addPendingInstrument(Instrument instrument, int quantity) throws IllegalArgumentException {
        if(!this.accepted) throw new IllegalArgumentException("A sessão que está a tentar modificar ainda não foi aprovada!");

        if(!this.instrumentRepos.getInstruments().containsKey(instrument.getName().toLowerCase())) {
            throw new IllegalArgumentException("O intrumento que pretende requisitar ainda não existe em estúdio.");
        }

        if(0 > quantity) {
            throw new IllegalArgumentException("Não existem instrumentos suficientes para " +
                    "satisfazer a sua requisição nesta hora.");
        }
        else if(quantity > instrument.getQuantidade()) {
            throw new IllegalArgumentException("Não existem instrumentos suficientes no estúdio " +
                    "para satisfazer a sua requisição.");
        }

        // precisamos de fazer uma cópia do instrumento para conseguir retirar a quantidade que vamos usar
        // isto porque temos um mecanismo em que o instrumento é partilhado entre as sessões em diferentes horarios
        Instrument copy;
        try {
            copy = (Instrument) instrument.clone();
            copy.setId(instrument.getId()); // this is needed
            copy.setQuantidade(quantity);
        } catch (CloneNotSupportedException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        this.pendentInstruments.add(copy);
        return instrument;
    }

    // waits for the permission of the administrator the session itself needs to be approved by the admin too
    // only accessed by the musician
    public Instrument addPendingInstrument(String name) throws IllegalArgumentException {
        if(!this.accepted) throw new IllegalArgumentException("A sessão que está a tentar modificar ainda não foi aprovada!");

        if(!this.instrumentRepos.getInstruments().containsKey(name.toLowerCase())){
            throw new IllegalArgumentException("O intrumento que pretende requisitar ainda não existe em estúdio.");
        }
        Instrument i = this.instrumentRepos.getInstrument(name.toLowerCase());
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
        this.refusedInstruments.add(instrument);
        return this.pendentInstruments.remove(instrument);
    }

    public Set<Instrument> getApprovedInstruments(){
        return this.instruments;
    }

    public boolean doesSessionOverlap(Session other){
        return !this.startDate.isAfter(other.endDate) && !this.endDate.isBefore(other.startDate) && (!this.startDate.isEqual(other.startDate) || !this.endDate.isEqual(other.endDate));
    }

    public boolean doesSessionOverlap(LocalDateTime start, LocalDateTime end){
return !this.startDate.isAfter(end) && !this.endDate.isBefore(start) && (!this.startDate.isEqual(start) || !this.endDate.isEqual(end));
    }

    @Override
    public int compareTo(Session o) {
        if(o.startDate.isBefore(this.startDate)){
            return -1;
        } else if(o.startDate.equals(this.startDate)) {
            return 0;
        }
        return 1;
    }

    public boolean equals(Session o){
        if(o.startDate.equals(this.startDate) && o.endDate.equals(this.endDate)) { return true; }
        else return o.id.equals(this.id);
    }

    @Override
    public String toString(){
        return "id da sessão = " + this.id + "data inicio = " + this.startDate + "data fim = " + this.endDate + "edicao de album = " + this.album;
    }

    public void setRejected(boolean b) {
        this.rejected = b;
    }

    public boolean wasRejected() {
        return this.rejected;
    }
}
