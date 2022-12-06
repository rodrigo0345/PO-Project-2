package Backend.Sessions;

import Backend.Users.Musician;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

public class Repos implements Serializable {
    private Set<Session> sessions = new TreeSet<>();
    private static long serialVersionUID = 3L;

    public Set<Session> getSessions() {
        return sessions;
    }

    public Session getSession(int id) {
        return null;
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
}
