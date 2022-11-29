package Backend.Storage;

public class Storage extends Thread {
    
    private static Object itemToStore = null;
    
    private Storage(Object itemToStore) {
        this.itemToStore = itemToStore;
    }
    
    public synchronized static boolean save() {
        
    }

    public synchronized static Object load() {

    }
    
    public void run() {
        
    }
}
