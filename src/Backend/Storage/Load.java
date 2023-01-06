package Backend.Storage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * The type Load.
 */
public class Load {

    private final String pathUsers;
    private final String pathInstruments;
    private final String pathSessions;
    private final String pathAlbums;

    /**
     * Instantiates a new Load.
     *
     * @param pathUsers       the path users
     * @param pathInstruments the path instruments
     * @param pathAlbums      the path albums
     * @param pathSessions    the path sessions
     */
    public Load(String pathUsers, String pathInstruments, String pathAlbums, String pathSessions) {
        this.pathUsers = pathUsers;
        this.pathInstruments = pathInstruments;
        this.pathAlbums = pathAlbums;
        this.pathSessions = pathSessions;
    }

    private Object loading(String path) throws IOException, ClassNotFoundException {
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(path));
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object aux = null;
        try {
            aux = ois.readObject();
        } finally {
            ois.close();
        }
        return aux;
    }

    /**
     * Load users backend . users . repos.
     *
     * @return the backend . users . repos
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public Backend.Users.Repos loadUsers() throws IOException, ClassNotFoundException {
        return (Backend.Users.Repos) loading(pathUsers);
    }

    /**
     * Load instruments backend . instruments . repos.
     *
     * @return the backend . instruments . repos
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public Backend.Instruments.Repos loadInstruments() throws IOException, ClassNotFoundException {
        return (Backend.Instruments.Repos) loading(pathInstruments);
    }

    /**
     * Load albums backend . albums . repos.
     *
     * @return the backend . albums . repos
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public Backend.Albums.Repos loadAlbums() throws IOException, ClassNotFoundException {
        return (Backend.Albums.Repos) loading(pathAlbums);
    }

    /**
     * Load sessions backend . sessions . repos.
     *
     * @return the backend . sessions . repos
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public Backend.Sessions.Repos loadSessions() throws IOException, ClassNotFoundException {
        return (Backend.Sessions.Repos) loading(pathSessions);
    }

}
