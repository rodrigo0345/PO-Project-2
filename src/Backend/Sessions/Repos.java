package Backend.Sessions;

import Backend.Users.Musician;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class Repos implements Serializable {

    // for sessions waiting for the admin approval
    private Set<Session> pendingSessions = new TreeSet<>();
    private Set<Session> sessions = new TreeSet<>();
    private static long serialVersionUID = 3L;

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

    public Session getSession(LocalDate date){
        for(Session s: sessions){
            if(s.getDate().equals(date)) {
                return s;
            }
        }
        return null;
    }

    public Set<Session> getMusicianSessions(Musician musician) throws IllegalArgumentException {
        if(musician == null) {throw new IllegalArgumentException("Musician cannot be null");}

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

    public Session getPendingSession(LocalDate date){
        for(Session s: pendingSessions){
            if(s.getDate().equals(date)){
                return s;
            }
        }
        return null;
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
            if(s.getDate().isBefore(LocalDate.now()) && s.isCompleted() == false){
                s.setCompleted(true);
                count++;
            }
        }
        return (count == 0)? false: true;
    }
}
