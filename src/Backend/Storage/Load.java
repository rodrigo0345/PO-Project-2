package Backend.Storage;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

public class Load {
    
    private Object loadInto = null;
    private String pathToLoad = null;
    
    public Load(Object loadIntoArg, String path) {
        this.pathToLoad = path;
        this.loadInto = loadIntoArg;
    }
    
    public void load() throws IOException, ClassNotFoundException{
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(pathToLoad));
        ObjectInputStream ois = new ObjectInputStream(fis);
        this.loadInto=ois.readObject();
        ois.close();
    }
}
