package Backend.Sessions;

import Backend.Instruments.Instrument;
import Backend.Users.Musician;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class Repos implements Serializable {//Traduzido

    // for sessions waiting for the admin approval
    private final Set<Session> pendingSessions = new TreeSet<>();
    private final Set<Session> sessions = new TreeSet<>();
    private static final long serialVersionUID = 3L;

    public Set<Session> getSessions() {
        return sessions;
    }

    public Session getSession(UUID id) {
        for(Session s:sessions){
            if(s.getId().equals(id)){
                return s;
            }
        }
        return null;
    }

    public boolean deleteSession(UUID id){
        return pendingSessions.remove(getSession(id)) || sessions.remove(getSession(id));
    }

    public Session getSession(LocalDateTime date){
        for(Session s: sessions){
            if(s.getDataInicio().equals(date)) {
                return s;
            }
        }
        return null;
    }

    public Set<Session> getMusicianSessions(Musician musician) throws IllegalArgumentException {
        if(musician == null) {throw new IllegalArgumentException("Músico não pode ser null");}

        Set<Session> aux = new TreeSet<Session>();
        for(Session s: sessions) {
            if (s.isCompleted()){ /*do nothing because sessions from the past dont matter in this context*/}
            else if (s.getInvitedMusicians().get(musician.getUsername()) != null) {
                aux.add(s);
            }
        }
        return aux;
    }

    public Set<Session> getPendingSessions(){
        return this.pendingSessions;
    }

    public boolean addPendingSession(Session session){
        return this.pendingSessions.add(session);
    }

    public Session getPendingSession(LocalDateTime date){
        for(Session s: pendingSessions){
            if(s.getDataInicio().equals(date)){
                return s;
            }
        }
        return null;
    }

    //já aprovadas
    public Set<Instrument> getInstrumentsRequests(){
        for(Session s: sessions){
            return s.getApprovedInstruments(); }
        return null;
    }

    public boolean doesSessionOverlap(Session s){
        for(Session session: sessions){
        if (session.getDataInicio().isAfter(s.getDataFim()) || session.getDataFim().isBefore(s.getDataInicio())) {
            return false;
        }
        }
        return true;
    }    

    public boolean approveSession(Session s){
        return pendingSessions.remove(s) && sessions.add(s);
    }

    public boolean denySession(Session s){
        return pendingSessions.remove(s);
    }

    public boolean endRecordingSessions() {
        int count = 0;

        // pending sessions do not count here
        for (Session s:sessions) {
            if(s.getDataFim().isBefore(LocalDateTime.now()) && s.isCompleted() == false){
                s.setCompleted(true);
                count++;
            }
        }
        return count != 0;
    }
}
