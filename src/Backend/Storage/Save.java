package Backend.Storage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Save extends Thread {
    
    private Object itemToStore = null;
    private String pathToStore = null;
    
    public Save(Object item, String path) {
        this.itemToStore = item;
        this.pathToStore = path;
    }
    
    public synchronized boolean save() {
        try {
            FileOutputStream fos = new FileOutputStream(pathToStore);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(itemToStore);
            oos.close();
            return true;
        } catch (IOException e) {
            System.out.println("Save - Error saving all information");
        } catch (NullPointerException e) {
            System.out.println("Save - The provided path is not valid");
        }
        return false;
    }
    
    public void run() {
        if (!save()) {
            System.out.println("Error saving...");
        }
    }
}
