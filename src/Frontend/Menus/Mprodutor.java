package Frontend.Menus;

import java.awt.*;
import java.rmi.StubNotFoundException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

import Backend.Albums.Album;
import Backend.Albums.AlbumEditado;
import Backend.Tracks.Track;
import Backend.Users.Musician;
import Backend.Users.Produtor;
import Frontend.Utils.Generics;

public class Mprodutor implements Menu {
    private int option;
    private Produtor user;

    public Mprodutor(Produtor user) {
        this.user = user;
    }

    @Override
    public void mostrarMenu() {
        Scanner sc = new Scanner(System.in);

        // escrever aqui os menus
        System.out.println("Producer Menu - Logged as " + user.getUsername());
        System.out.println("1. Edit profile");
        System.out.println("2. Start/Edit the editing of an album");
        System.out.println("3. End recording session");
        System.out.println("4. See the state of an album");
        System.out.println("5. Your Albums");
        System.out.println("6. Recording Sessions of a day");
        System.out.println("7. Consultar dados");   //NÃO FUNCIONA
        System.out.println("8. Log out");

        option = Generics.checkOption("Introduza a opção: ");
    }

    public Produtor getUser() {
        return user;
    }

    @Override
    public void executeOption(Backend.Instruments.Repos instruments, Backend.Albums.Repos albums,
            Backend.Users.Repos users, Backend.Sessions.Repos sessions) {
        Scanner sc = new Scanner(System.in);
        switch (option) {
            case 1:
                System.out.println("[1] - Edit name");
                System.out.println("[2] - Edit username");
                System.out.println("[3] - Edit email");
                System.out.println("[4] - Edit password");

                option = Generics.checkOption("Introduza a opção: ");

                try {
                    switch (option) {
                        case 1:
                            
                            String name = Generics.readString("Name: ");
                            user.setName(name);
                            break;
                        case 2:
                            
                            String surname = Generics.readString("New username: ");
                            user.setUsername(surname); // throws exception
                            break;
                        case 3:
                            
                            String email = Generics.readString("New email: ");
                            user.setEmail(email);
                            break;
                        case 4:
                            
                            String password = Generics.readString("New password: ");
                            user.setPassword(password);
                            break;
                        default:
                            System.out.println("Invalid option");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid option");
                }
                break;
            case 2:
                System.out.println("1. Start a new edit of an album");
                System.out.println("2. Edit an existing edit of an album");

                int option2;
                try {
                    option2 = sc.nextInt();
                } catch (Exception e) {
                    System.out.println("Invalid option");
                    sc.nextLine();
                    return;
                }

                if (option2 == 1) {

                    String albumName = Generics.readString("Original Album's name: ");
                    String newAlbumName = Generics.readString("New Album's name: ");

                    Backend.Albums.AlbumEditado album;
                    try {
                        album = user.createAlbumEdit(albumName, newAlbumName);
                    } catch( ClassNotFoundException | IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        return;
                    }

                    String choice = Generics.readString("Plan a new recording session? (y/n)");
                    while(choice.equals("y")) {
                        String newDate = Generics.readString("Date for starting the session (dd/mm/yyyy HH:mm): ");
                        LocalDateTime newDateStart = Frontend.Utils.Generics.stringToDate(newDate);
                        newDate = Generics.readString("Date for ending the session (dd/mm/yyyy HH:mm): ");
                        LocalDateTime newDateEnd = Frontend.Utils.Generics.stringToDate(newDate);
                        Backend.Sessions.Session newSession = null;

                        try {
                            album.addSession(newDateStart, newDateEnd);
                            newSession = album.getLastSessionAdded();
                        } catch (IllegalArgumentException e){
                            System.out.println(e.getMessage());
                        }

                        String artist = Generics.readString("Add new artist? (y/n)");
                        while(artist.equals("y")) {
                            
                            artist = Generics.readString("Artist's username: ");

                            Backend.Users.User aux = users.getUser(artist);
                            if (!(aux instanceof Backend.Users.Musician) || aux == null) {
                                System.out.println("The given musician does not exist");
                                return;
                            }

                            // add this session to the musician
                            Backend.Users.Musician artistInstance = (Musician) aux;
                            try {
                                artistInstance.addSession(album, newSession);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                sc.nextLine();
                                // no need to return
                            }

                            
                            artist = Generics.readString("Add new artist? (y/n)");
                        }

                        choice = Generics.readString("Plan a new recording session (y/n)");
                    }

                } else if (option2 == 2) {
                    
                    String albumName = Generics.readString("Name of the album: ");
                    Backend.Albums.Album album = albums.getAlbum(albumName);

                    if (!(album instanceof Backend.Albums.AlbumEditado)){
                        System.out.println("The album you selected is not editable!");
                        return;
                    } else if (user.equals(album.getProdutor())) {
                        System.out.println("You cannot edit this album! Permission denied!");
                        return;
                    }

                    System.out.println("1. Add a new recording session");
                    System.out.println("2. Delete a recording session");
                    System.out.println("3. Add a new artist");
                    System.out.println("4. Remove an artist");
                    System.out.println("5. Add a new track");
                    System.out.println("6. End all past recording sessions");
                    int choice = Generics.checkInt("Introduza a opção: ");

                    switch(choice) {
                        case 1:
                            LocalDateTime d = Frontend.Utils.Generics.readDate("Choose a starting date to the recording: ");
                            LocalDateTime f = Frontend.Utils.Generics.readDate("Choose an ending date to the recording: ");
                           ((AlbumEditado) album).addSession(d,f);
                            break;
                        case 2:
                          
                            String id = Generics.readString("ID of the recording session: ");
                            boolean success = sessions.deleteSession(UUID.fromString(id));
                            if (!success) { System.out.println("The session you are trying to delete does not exist"); return;}
                            System.out.println("Session deleted with success");
                            break;
                        case 3:
                            
                            String username = Generics.readString("Username of the artist: ");
                            Backend.Users.User artist = users.getUser(username);
                            if (!(artist instanceof Musician) || artist == null) {
                                System.out.println("Invalid username, the username you " +
                                        "selected is either not an artist or does not exist!");
                                return;
                            }

                            // always need to add the album to the
                            // artist and then the artist to the album
                            ((Musician) artist).addAlbum(album);
                            album.addArtist((Musician) artist);
                            break;
                        case 4:
                            String username2 = Generics.readString("Username of the artist: ");
                            boolean success2 = album.removeArtist(username2);
                            if (!success2) {
                                System.out.println("The system was not able to delete the user you" +
                                        " provided!");
                                sc.nextLine();
                            }
                            break;
                        case 5:

                            String trackName = Generics.readString("Name of the track: ");
                            String genre = Generics.readString("Genre of the track: ");
                            int duration = Generics.checkInt("Duration: ");
                            String nameAlbum = Generics.readString("Name of the associated album: ");
                            Album a = user.getProjeto(nameAlbum) == null? user.getProjeto(nameAlbum): user.getOldAlbum(nameAlbum);

                            Backend.Tracks.Track newTrack = new Track(a, trackName, genre ,duration);
                            boolean success3 = album.addTrack(newTrack);
                            if (!success3) {
                                System.out.println("The name of the track needs to be unique inside the album!");
                                sc.nextLine();
                            }
                            break;
                        case 6:
                            System.out.println("Ending recording sessions...");
                            boolean res = sessions.endRecordingSessions();
                            if (!res) {
                                System.out.println("No recording session was ended!");
                                sc.nextLine();
                            }
                            break;
                        default:
                            System.out.println("Invalid selection");
                    }

                } else {
                    System.out.println("Invalid option");
                }

                break;
            case 3:
                System.out.println("Ending recording sessions...");
                boolean res = sessions.endRecordingSessions();
                if (!res) {
                    System.out.println("No recording session was ended!");
                    sc.nextLine();
                }
                break;
            case 4:

                String albumName = Generics.readString("Select the album to examine: ");
                Backend.Albums.Album album = albums.getAlbum(albumName);

                if (!(album instanceof AlbumEditado)){
                    System.out.println(albumName + " was never edited here!");
                    return;
                }
                if (((Backend.Albums.AlbumEditado) album).isEdited()) {
                    System.out.println(albumName + " this album is now complete! Date of conclusion: "
                                        + album.getDate());
                } else {
                    System.out.println(albumName + " is still being edited!");
                }
                break;
            case 5:
                Set<Album> oldProjects = user.getOldAlbums();
                Set<AlbumEditado> myEditedProjects = user.getProjetos();


                System.out.println("--- Old albums ---");
                if (oldProjects.isEmpty()) { System.out.println("No albums...");}
                else {
                    for (Album album1: oldProjects){
                        System.out.println(album1);
                    }
                }

                System.out.println("--- New albums ---");
                if(myEditedProjects.isEmpty()) {
                    System.out.println("No albums...");
                } else {
                    for (AlbumEditado album1 : myEditedProjects) {
                        System.out.println(album1);
                    }
                }
                sc.nextLine();
                break;
            case 6: // já encontra mais do que uma sessao por dia
                String day = Generics.readString("Select the day to examine D: ");
                LocalDateTime dInicio = Frontend.Utils.Generics.stringToDate(day + " 00:00");
                LocalDateTime dFim = Frontend.Utils.Generics.stringToDate(day + " 23:59");
                Backend.Sessions.Session session = user.findSessionByDate(dInicio, dFim);
                System.out.println(session);
                break;
            case 7:
                System.out.println("Name: " + user.getName());
                System.out.println("Username: " + user.getUsername());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Password: " + user.getPassword());
                sc.nextLine();
                break;
            case 8:
                this.user = null;
                break;
            default:
                System.out.println("Invalid selection");
                break;
        }

    }
    
}
