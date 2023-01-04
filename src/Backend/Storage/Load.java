package Backend.Storage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Load {

    private final String pathUsers;
    private final String pathInstruments;
    private final String pathSessions;
    private final String pathAlbums;

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

    public Backend.Users.Repos loadUsers() throws IOException, ClassNotFoundException {
        return (Backend.Users.Repos) loading(pathUsers);
    }

    public Backend.Instruments.Repos loadInstruments() throws IOException, ClassNotFoundException {
        return (Backend.Instruments.Repos) loading(pathInstruments);
    }

    public Backend.Albums.Repos loadAlbums() throws IOException, ClassNotFoundException {
        return (Backend.Albums.Repos) loading(pathAlbums);
    }

    public Backend.Sessions.Repos loadSessions() throws IOException, ClassNotFoundException {
        return (Backend.Sessions.Repos) loading(pathSessions);
    }

}
