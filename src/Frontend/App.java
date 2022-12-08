package Frontend;

import Backend.Users.Admin;
import Backend.Users.Musician;
import Backend.Users.Produtor;
import Backend.Users.User;
import Frontend.Menus.Madmin;
import Frontend.Menus.Mmusico;
import Frontend.Menus.Mprodutor;

import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Backend.Instruments.Repos instruments;
        Backend.Albums.Repos albums;
        Backend.Users.Repos users;
        Backend.Sessions.Repos sessions;

        // prepare threads to load data
        Backend.Storage.Load load = new Backend.Storage.Load("users.txt", "instruments.txt", "albums.txt",
                "sessions.txt");

        try {
            instruments = load.loadInstruments();
            albums = load.loadAlbums();
            users = load.loadUsers();
            sessions = load.loadSessions();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            // omit error message because this is triggered the first time the program is run
            instruments = new Backend.Instruments.Repos();
            albums = new Backend.Albums.Repos();
            users = new Backend.Users.Repos();
            sessions = new Backend.Sessions.Repos();
        }

        // create test users
        users.devUsers(instruments, albums, users, sessions);

        User auth = null;
        while(true) {
            program(auth, instruments, albums, users, sessions);
        }
    }

    private static void program(User auth, Backend.Instruments.Repos instruments, Backend.Albums.Repos albums,
            Backend.Users.Repos users, Backend.Sessions.Repos sessions) {
        
        Scanner sc = new Scanner(System.in);
        // login system
        while (auth == null) {
            try {
                auth = Authentication.loginPrompt(users);
                cleanPrompt();
            } catch (Exception e) {
                System.out.println("Failed to login: " + e.getMessage());
                sc.nextLine();
            }
        }   

        // access user's functionalities
        if (auth != null) {
            if (auth instanceof Musician) {
                Musician user = (Musician) auth;
                Mmusico menu = new Mmusico(user);
                while (menu.getUser() != null) {
                    menu.mostrarMenu();
                    cleanPrompt();
                    menu.executeOption(instruments, albums, users, sessions);
                    cleanPrompt();
                    save(albums, instruments, users, sessions);
                }
            } else if (auth instanceof Admin) {
                Admin user = (Admin) auth;
                Madmin menu = new Madmin(user);
                while (menu.getUser() != null) {
                    menu.mostrarMenu();
                    cleanPrompt();
                    menu.executeOption(instruments, albums, users, sessions);
                    cleanPrompt();
                    save(albums, instruments, users, sessions);
                }
            } else if (auth instanceof Produtor) {
                Produtor user = (Produtor) auth;
                Mprodutor menu = new Mprodutor(user);
                while (menu.getUser() != null) {
                    menu.mostrarMenu();
                    cleanPrompt();
                    menu.executeOption(instruments, albums, users, sessions);
                    cleanPrompt();
                    save(albums, instruments, users, sessions);
                }
            } else {
                System.out.println("User type not recognized");
                sc.nextLine();
            }
        }
    }

    private static void save(Backend.Albums.Repos albums, Backend.Instruments.Repos instruments,
            Backend.Users.Repos users, Backend.Sessions.Repos sessions) {
        // não é a maneira mais eficiente de fazer isso, mas não é possivel iniciar a
        // mesma thread duas vezes
        Backend.Storage.Save saveUsers = new Backend.Storage.Save(users, "users.txt");
        Backend.Storage.Save saveAlbums = new Backend.Storage.Save(albums, "albums.txt");
        Backend.Storage.Save saveInstruments = new Backend.Storage.Save(instruments, "instruments.txt");
        Backend.Storage.Save saveSessions = new Backend.Storage.Save(sessions, "sessions.txt");
        saveUsers.start();
        saveAlbums.start();
        saveInstruments.start();
        saveSessions.start();
    }

    private static void cleanPrompt() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
