package Backend.Storage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Load extends Thread {
    
    private static Object loadInto = null;
    private static String pathToLoad = null;
    
    public Load(Object loadIntoArg, String path) {
        pathToLoad = path;
        loadInto = loadIntoArg;
    }
    
    public synchronized static boolean load() {
        ObjectInputStream ois = null;

        try {
            FileInputStream fis = new FileInputStream(pathToLoad);
            ois = new ObjectInputStream(fis);
            loadInto=ois.readObject();
            return true;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public void run() {
        if (!load()) {
            System.out.println("Error loading all information");
        }
    }
}
