package Backend.Sessions;

import java.io.Serializable;
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
}
