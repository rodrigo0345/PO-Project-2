package Backend.Sessions;

import Backend.Instruments.Instrument;
import Backend.Users.Musician;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

/**
 * The type Repos.
 */
public class Repos implements Serializable {//Traduzido

    // for sessions waiting for the admin approval
    private final Set<Session> pendingSessions = new TreeSet<>();
    private final Set<Session> sessions = new TreeSet<>();
    @Serial
    private static final long serialVersionUID = 3L;

    /**
     * Gets sessions.
     *
     * @return the sessions
     */
    public Set<Session> getSessions() {
        return this.sessions;
    }

    /**
     * Gets session.
     *
     * @param id the id
     * @return the session
     */
    public Session getSession(UUID id) {
        for(Session s: this.sessions){
            if(s.getId().equals(id)){
                return s;
            }
        }
        return null;
    }

    /**
     * Delete session boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public boolean deleteSession(UUID id){
        return this.pendingSessions.remove(this.getSession(id)) || this.sessions.remove(this.getSession(id));
    }

    /**
     * Get session session.
     *
     * @param date the date
     * @return the session
     */
    public Session getSession(LocalDateTime date){
        for(Session s: this.sessions){
            if(s.getDataInicio().equals(date)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Gets musician sessions.
     *
     * @param musician the musician
     * @return the musician sessions
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Set<Session> getMusicianSessions(Musician musician) throws IllegalArgumentException {
        if(null == musician) {throw new IllegalArgumentException("Músico não pode ser null");}

        Set<Session> aux = new TreeSet<>();
        for(Session s: this.sessions) {
            if (s.isCompleted()){
                /*não faz nada porque quero apenas apanhar as sessões que ainda não foram completas*/
            }
            else if (null != s.getInvitedMusicians().get(musician.getUsername())) {
                aux.add(s);
            }
        }
        return aux;
    }

    /**
     * Get pending sessions set.
     *
     * @return the set
     */
    public Set<Session> getPendingSessions(){
        return this.pendingSessions;
    }

    /**
     * Add pending session boolean.
     *
     * @param session the session
     * @return the boolean
     */
    public boolean addPendingSession(Session session){
        return this.pendingSessions.add(session);
    }

    /**
     * Get pending session session.
     *
     * @param date the date
     * @return the session
     */
    public Session getPendingSession(LocalDateTime date){
        for(Session s: this.pendingSessions){
            if(s.getDataInicio().equals(date)){
                return s;
            }
        }
        return null;
    }

    /**
     * Get instruments requests set.
     *
     * @return the set
     */
//já aprovadas
    public Set<Instrument> getInstrumentsRequests(){
        for(Session s: this.sessions){
            return s.getApprovedInstruments(); }
        return null;
    }

    /**
     * Approve session boolean.
     *
     * @param s the s
     * @return the boolean
     */
    public boolean approveSession(Session s){
        return this.pendingSessions.remove(s) && this.sessions.add(s);
    }

    /**
     * Deny session boolean.
     *
     * @param s the s
     * @return the boolean
     */
    public boolean denySession(Session s){
        return this.pendingSessions.remove(s);
    }

    /**
     * End recording sessions boolean.
     *
     * @return the boolean
     * @throws Exception the exception
     */
    public boolean endRecordingSessions() throws Exception {
        int count = 0;

        // pending sessions do not count here
        for (Session s: this.sessions) {
            if(s.getDataFim().isBefore(LocalDateTime.now()) && !s.isCompleted()){
                s.setCompleted(true);
                count++;
            }
        }
        return 0 != count;
    }
}
