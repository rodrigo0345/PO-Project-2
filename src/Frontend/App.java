package Frontend;

import java.io.IOException;

import Backend.Users.Admin;
import Backend.Users.Musician;
import Backend.Users.Produtor;
import Backend.Users.User;
import Frontend.Menus.Madmin;
import Frontend.Menus.Mmusico;
import Frontend.Menus.Mprodutor;

public class App {
    public static void main(String[] args) {
        // repos are not static otherwise we would have problems with the serialization
        Backend.Instruments.Repos instruments; Backend.Albums.Repos albums; Backend.Users.Repos users;
        initialize(instruments, albums, users);

        // prepare threads to load data
        Backend.Storage.Load loadUsers = new Backend.Storage.Load(users, "users.txt");
        Backend.Storage.Load loadAlbums = new Backend.Storage.Load(albums, "albums.txt");
        Backend.Storage.Load loadInstruments = new Backend.Storage.Load(instruments, "instruments.txt");

        // prepare threads to save data
        Backend.Storage.Save saveUsers = new Backend.Storage.Save(users, "users.txt");
        Backend.Storage.Save saveAlbums = new Backend.Storage.Save(users, "albums.txt");
        Backend.Storage.Save saveInstruments = new Backend.Storage.Save(users, "instruments.txt");

        load(loadUsers, loadAlbums, loadInstruments);

        // test
        Backend.Users.Repos.getUsers();

        users.devUsers();

        User auth = null;
        try{
            auth = Authentication.loginPrompt();
        }   
        catch(Exception e){
            System.out.println("Failed to login: " + e.getMessage());
        }
        

        if (auth != null) {
            if (auth instanceof Musician) {
                Musician user = (Musician) auth;
                Mmusico menu = new Mmusico(user);
                while (menu.getUser() != null) {
                    menu.mostrarMenu();
                    menu.executeOption();
                    save(saveUsers, saveAlbums, saveInstruments);
                }
            } else if (auth instanceof Admin) {
                Admin user = (Admin) auth;
                Madmin menu = new Madmin(user);
                while (menu.getUser() != null) {
                    menu.mostrarMenu();
                    menu.executeOption();
                    save(saveUsers, saveAlbums, saveInstruments);
                }
            } else if (auth instanceof Produtor) {
                Produtor user = (Produtor) auth;
                Mprodutor menu = new Mprodutor(user);
                while (menu.getUser() != null) {
                    menu.mostrarMenu();
                    menu.executeOption();
                    save(saveUsers, saveAlbums, saveInstruments);
                }
            }
        }
    }

    public static void initialize(Backend.Instruments.Repos instruments, Backend.Albums.Repos albums, Backend.Users.Repos users) {
        instruments = new Backend.Instruments.Repos();
        albums = new Backend.Albums.Repos();
        users = new Backend.Users.Repos();
    }

    public static void save(Backend.Storage.Save saveUsers, Backend.Storage.Save saveAlbums, Backend.Storage.Save saveInstruments) {
        saveUsers.start();
        saveAlbums.start();
        saveInstruments.start();
    }

    public static void load(Backend.Storage.Load loadUsers, Backend.Storage.Load loadAlbums, Backend.Storage.Load loadInstruments) {
        try {
            loadUsers.load();
            loadAlbums.load();
            loadInstruments.load();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
