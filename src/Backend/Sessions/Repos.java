package Backend.Sessions;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class Repos implements Serializable {
    private static Set<Session> sessions = new TreeSet<>();

    public static Set<Session> getSessions() {
        return sessions;
    }

    public static Session getSession(int id) {
        return null;
    }


}
