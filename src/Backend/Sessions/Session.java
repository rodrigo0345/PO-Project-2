package Backend.Sessions;

import Backend.Albums.AlbumEditado;
import Backend.Instruments.Instrument;
import Backend.Users.Musician;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Session implements Serializable, Comparable<Session> {//Traduzido
    private static final long serialVersionUID = 4L;
    private final Map<String, Musician> invitedArtists = new HashMap<>();
    private final Set<Instrument> pendentInstruments = new TreeSet<>();
    private final Set<Instrument> instruments = new TreeSet<>();

    private final Set<Instrument> refusedInstruments = new TreeSet<>();

    private LocalDateTime dateInicio;
    private LocalDateTime dateFim;
    private UUID id = UUID.randomUUID();
    private boolean completed;
    private boolean accepted;

    private boolean rejected;
    private final AlbumEditado album;
    private final Backend.Sessions.Repos sessionRepos;
    private final Backend.Users.Repos userRepos;
    private final Backend.Instruments.Repos instrumentRepos;
    private final Backend.Albums.Repos albumRepos;

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

    public void setCompleted(boolean completed) throws Exception {
        if (sessionRepos.getPendingSessions().contains(this)) {
            throw new Exception("Sessão ainda não foi aceite pelo administrador");
        }
        this.completed = completed;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((null == this.id) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (null == obj)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Session other = (Session) obj;
        if (null == this.id) {
            return false;
        }
        return other.id.equals(this.id)
                || (other.dateInicio.equals(this.dateInicio) && other.dateFim.equals(this.dateFim));
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
            if(s.dateInicio.isBefore(this.dateFim) && s.dateFim.isAfter(this.dateInicio)) {
                throw new IllegalArgumentException("O músico que está a tentar adicionar já se encontra em outra sessão ao mesmo tempo!");
            }
        }
        this.invitedArtists.put(m.getUsername(), m);
        m.addSession(album, this);
    }

    public void removeInvitedMusician(Backend.Users.Musician m) throws IllegalArgumentException {
        if(!this.accepted) throw new IllegalArgumentException("A sessão que está a tentar modificar ainda não foi aprovada!");
        if(this.completed) throw new IllegalArgumentException("A sessão a que está a tentar aceder já foi terminada!");
        this.invitedArtists.remove(m.getUsername());
        m.removeSession(album, this);
    }

    public void removeAllInvitedMusicians() throws IllegalArgumentException {
        this.invitedArtists.forEach((s, artist) -> {
            removeInvitedMusician(artist);
        });
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

        if(!instrumentRepos.getInstruments().containsKey(instrument.getName().toLowerCase())) {
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

        // precisamos de fazer uma copia do instrumento de forma a conseguir retirar a quantidade que vamos usar
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

        if(!instrumentRepos.getInstruments().containsKey(name.toLowerCase())){
            throw new IllegalArgumentException("O intrumento que pretende requisitar ainda não existe em estúdio.");
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
        refusedInstruments.add(instrument);
        return this.pendentInstruments.remove(instrument);
    }

    public Set<Instrument> getApprovedInstruments(){
        return this.instruments;
    }

    public boolean doesSessionOverlap(Session other){
        return !this.dateInicio.isAfter(other.dateFim) && !this.dateFim.isBefore(other.dateInicio) && (!this.dateInicio.isEqual(other.dateInicio) || !this.dateFim.isEqual(other.dateFim));
    }

    public boolean doesSessionOverlap(LocalDateTime start, LocalDateTime end){
return !this.dateInicio.isAfter(end) && !this.dateFim.isBefore(start) && (!this.dateInicio.isEqual(start) || !this.dateFim.isEqual(end));
    }

    @Override
    public int compareTo(Session o) {
        if(o.dateInicio.isBefore(this.dateInicio)){
            return -1;
        } else if(o.dateInicio.equals(this.dateInicio)) {
            return 0;
        }
        return 1;
    }

    public boolean equals(Session o){
        if(o.dateInicio.equals(this.dateInicio) && o.dateFim.equals(this.dateFim)) { return true; }
        else return o.id.equals(o.id);
    }

    @Override
    public String toString(){
        return "id da sessão = " + id + "data inicio = " + dateInicio + "data fim = " + dateFim + "edicao de album = " + album;
    }

    public void setRejected(boolean b) {
        this.rejected = b;
    }

    public boolean wasRejected() {
        return this.rejected;
    }
}
