package Backend.Storage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Save extends Thread { //Traduzido

    private final Object itemToStore;
    private final String pathToStore;

    public Save(Object item, String path) {
        this.itemToStore = item;
        this.pathToStore = path;
    }

    public synchronized boolean save() throws IOException {
        FileOutputStream fos = new FileOutputStream(pathToStore);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        try {
            oos.writeObject(itemToStore);
            oos.close();
            return true;
        } catch (IOException e) {
            System.out.println("Save - Erro ao gravar a informação " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Save - A path providenciada não é valida " + e.getMessage());
        } finally {
            oos.close();
        }
        return false;
    }

    public void run() {
        try {
            if (!save()) {
                System.out.println("Erro ao guardar...");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Save - A path providenciada não é valida " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Save - Erro ao gravar a informação " + e.getMessage());
        }
    }
}
