package Frontend.Menus;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import Backend.Instruments.Repos;
import Backend.Users.Admin;
import Frontend.Utils.Generics;

// Menu of the administrators
public class Madmin implements Menu {
    private int option;
    private Admin user;

    public Madmin(Admin user) {
        this.user = user;
    }

    public Admin getUser() {
        return user;
    }

    @Override
    public void mostrarMenu() {
       
        System.out.println("Menu de Administrador - Logged as " + user.getUsername());
        System.out.println("1. Add a new producer");
        System.out.println("2. Add a new musician");
        System.out.println("3. Remove user");
        System.out.println("4. Add music instrument");
        System.out.println("5. Show all session requests");
        System.out.println("6. Show all recording sessions");
        System.out.println("7. Show all albums being edited");
        System.out.println("8. Stats");
        System.out.println("9. Show all users");
        System.out.println("10. Show all instruments");
        System.out.println("11. Add a new album");
        System.out.println("12. Show all albums");
        System.out.println("13. Consultar dados");
        System.out.println("14. Log out");

        option = Generics.checkOption("Introduza a opção: ");

    }

    @Override
    public void executeOption(Backend.Instruments.Repos instruments, Backend.Albums.Repos albums,
            Backend.Users.Repos users, Backend.Sessions.Repos sessions) {
       
        Scanner sc = new Scanner(System.in);

        switch (option) {
            case 1:
                String name = Generics.readString("Name: ");
                String username = Generics.readString("Username: ");
                String password = Generics.readString("Password: ");
                String email = Generics.readString("Email: ");

               try {
                   user.addProdutor(name, email, username, password);
               } catch (Exception e) {
                   System.out.println(e.getMessage());
               }
                
                break;
            case 2:

                name = Generics.readString("Name: ");
                username = Generics.readString("Username: ");
                password = Generics.readString("Password: ");
                email = Generics.readString("Email: ");
               
                try {
                    user.addMusician(name, username, password, email);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 3:
            
                username = Generics.readString("Username: ");
                try {
                    user.removeUser(username);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 4:
            
                name = Generics.readString("Name of the instrument: ");
                
                try {
                    user.addInstrument(name);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 5:                                                         
                if (this.user.getAllSessionRequests() == null) {
                    System.out.println("No session requests");
                    return;
                }
                System.out.print("Select a session request: ");
                UUID id = UUID.fromString(sc.nextLine());                       //FALTA ALTERAR
                String answer = Generics.readString("Accept or reject? (y/n)");
                if (answer.equals("y")) {
                    try {
                        this.user.acceptSessionRequest(id);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if (answer.equals("n")) {
                    try {
                        this.user.acceptSessionRequest(id);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Invalid option");
                }
                break;
            case 6:
                this.user.showAllRecordingSessions();
                sc.nextLine();
                break;
            case 7:
                this.user.showAllAlbumsBeingEdited();
                sc.nextLine();
                break;
            case 8:
                this.user.getStats();
                break;
            case 9:
                Map<String, Backend.Users.User> list = users.getUsers();
                for (Map.Entry<String, Backend.Users.User> entry : list.entrySet()) {
                    System.out.println(entry.getValue().toString());
                }
                sc.nextLine();
                break;
            case 10:
                Map<String, Backend.Instruments.Instrument> list2 = instruments.getInstruments();
                for (Map.Entry<String, Backend.Instruments.Instrument> entry : list2.entrySet()) {
                    System.out.println(entry.getValue().toString());
                }
                sc.nextLine();
                break;
            case 11:
                // creating an album
                
                String titleOfTheAlbum = Generics.readString("Name of the album: ");
                String producer = Generics.readString("Producer: ");
                String genre = Generics.readString("Genre: ");
                String d = Generics.readString("Date of release (dd MM yyyy): ");
                LocalDateTime date = Generics.stringToDate(d);;
                
                // verify that the inserted date is valid
            
                // verify that the inserted producer is valid
                try {
                    Backend.Users.User aux = users.getUser(producer);
                    if (aux instanceof Backend.Users.Produtor) {
                        Backend.Users.Produtor prod = (Backend.Users.Produtor) aux;
                        user.addAlbum(titleOfTheAlbum, genre, date, prod);
                    } else {
                        System.out.println("Invalid producer");
                        sc.nextLine();
                        return;
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    sc.nextLine();
                    return;
                }

                // adicionar musicas ao album
                String answer2 = "y";
                while (answer2.equals("y")) {
                    answer2 = Generics.readString("Add a song to the album? (y/n)");
                    this.addTrackToAlbum(answer2, user, users, titleOfTheAlbum);
                }
                break;
            case 12:
                Map<String, Backend.Albums.Album> list3 = albums.getAlbums();
                for (Backend.Albums.Album album : list3.values()) {
                    System.out.println(album.toString());
                }
                sc.nextLine();
                break;
            case 13:
                System.out.println("Name: " + user.getName());
                System.out.println("Username: " + user.getUsername());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Password: " + user.getPassword());
                sc.nextLine();
                break;
            case 14:
                this.user = null;
                break;
            default:
                System.out.println("Invalid option");
                break;
        }

    }

    private void addTrackToAlbum(String ans, Backend.Users.Admin user, Backend.Users.Repos users,
            String titleOfTheAlbum) {
        Scanner sc = new Scanner(System.in);
        Backend.Albums.Album a = user.getAlbumsRepo().getAlbum(titleOfTheAlbum);
        if (ans.equals("y")) {

            String titleOfTheSong = Generics.readString("Title of the song: ");
            int duration = Generics.checkInt("Duration: ");
            sc.nextLine(); // flush
            String genre = Generics.readString("Genre: ");
            String musician = Generics.readString("Musician: ");
            try {
                Backend.Tracks.Track t = new Backend.Tracks.Track(a, titleOfTheSong,
                        genre, duration);
                t.addArtist((Backend.Users.Musician) users.getUser(musician));
                user.addTrackToAlbum(titleOfTheAlbum, t);

                // add musicians to the track
                String answer3 = "y";
                while (answer3.equals("y")) {
                    answer3 = Generics.readString("Add a new musician to the track? (y/n)");
                    this.addArtistToTrack(answer3, user, users, t);
                }

                user.addTrackToAlbum(titleOfTheAlbum, t);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (ans.equals("n")) {
            option = -1;
        } else {
            System.out.println("Invalid option");
        }
    }

    private void addArtistToTrack(String ans, Backend.Users.Admin user, Backend.Users.Repos users,
            Backend.Tracks.Track t) {
        Scanner sc = new Scanner(System.in);
        if (ans.equals("y")) {
            System.out.println("");
            String musician2 = Generics.readString("Musician: ");
            t.addArtist((Backend.Users.Musician) users.getUser(musician2));
        } else if (ans.equals("n")) {
            option = -1;
        } else {
            System.out.println("Invalid option");
        }
    }

}
