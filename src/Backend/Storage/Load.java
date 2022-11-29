package Backend.Storage;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.prefs.BackingStoreException;

public class Load {
    
    private String pathUsers = null;
    private String pathInstruments = null;
    private String pathSessions = null;
    private String pathAlbums = null;
    
    public Load(String pathUsers, String pathInstruments, String pathAlbums, String pathSessions) {
        this.pathUsers = pathUsers;
        this.pathInstruments = pathInstruments;
        this.pathAlbums = pathAlbums;
        this.pathSessions = pathSessions;
    }
    
    public Backend.Users.Repos loadUsers() throws IOException, ClassNotFoundException{
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(pathUsers));
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object aux = ois.readObject();
        ois.close();
        return (Backend.Users.Repos) aux;
    }


    public Backend.Instruments.Repos loadInstruments() throws IOException, ClassNotFoundException{
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(pathInstruments));
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object aux = ois.readObject();
        ois.close();
        return (Backend.Instruments.Repos) aux;
    }

    public Backend.Albums.Repos loadAlbums() throws IOException, ClassNotFoundException{
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(pathAlbums));
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object aux = ois.readObject();
        ois.close();
        return (Backend.Albums.Repos) aux;
    }

    public Backend.Sessions.Repos loadSessions() throws IOException, ClassNotFoundException{
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(pathSessions));
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object aux = ois.readObject();
        ois.close();
        return (Backend.Sessions.Repos) aux;
    }

}
