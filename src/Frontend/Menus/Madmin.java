package Frontend.Menus;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import Backend.Users.Admin;

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
        Scanner sc = new Scanner(System.in);
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

        try {
            option = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid option");
        }
    }

    @Override
    public void executeOption(Backend.Instruments.Repos instruments, Backend.Albums.Repos albums,
            Backend.Users.Repos users) {
        Scanner sc = new Scanner(System.in);
        switch (option) {
            case 1:
                System.out.println("Name: ");
                String name = sc.nextLine();
                System.out.println("Username: ");
                String username = sc.nextLine();
                System.out.println("Password: ");
                String password = sc.nextLine();
                System.out.println("Email: ");
                String email = sc.nextLine();

                try {
                    user.addProdutor(name, email, username, password);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 2:
                System.out.println("Name: ");
                name = sc.nextLine();
                System.out.println("Username: ");
                username = sc.nextLine();
                System.out.println("Password: ");
                password = sc.nextLine();
                System.out.println("Email: ");
                email = sc.nextLine();

                try {
                    user.addMusician(name, username, password, email);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 3:
                System.out.println("Username: ");
                username = sc.nextLine();
                try {
                    user.removeUser(username);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 4:
                System.out.println("Name of the instrument: ");
                name = sc.nextLine();
                try {
                    user.addInstrument(name);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 5:
                if (this.user.showAllSessionRequests() == -1) {
                    System.out.println("No session requests");
                    return;
                }
                ;
                System.out.print("Select a session request: ");
                int id = sc.nextInt();
                System.out.println("Accept or reject? (y/n)");
                String answer = sc.nextLine();
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
                break;
            case 7:
                this.user.showAllAlbumsBeingEdited();
                break;
            case 8:
                this.user.showStats();
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
                System.out.println("Name of the album: ");
                String titleOfTheAlbum = sc.nextLine();
                System.out.println("Producer: ");
                String producer = sc.nextLine();
                System.out.println("Genre: ");
                String genre = sc.nextLine();
                System.out.println("Date of release (dd MM yyyy): ");
                String d = sc.nextLine();
                LocalDate date = null;
                try {
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd MM yyyy", Locale.FRANCE);
                    date = LocalDate.parse(d, df);
                } catch (Exception e) {
                    System.out.println("Invalid date");
                    sc.nextLine();
                    return;
                }

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
                int option2 = 0;
                while (option2 != -1) {
                    System.out.println("Add a song to the album? (y/n)");
                    String answer2 = sc.nextLine();
                    if (answer2.equals("y")) {
                        System.out.println("Title of the song: ");
                        String titleOfTheSong = sc.nextLine();
                        System.out.println("Duration: ");
                        int duration = sc.nextInt();
                        System.out.println("Genre: ");
                        genre = sc.nextLine();
                        System.out.println("Musician: ");
                        String musician = sc.nextLine();
                        try {
                            Backend.Tracks.Track t = new Backend.Tracks.Track(titleOfTheSong,
                                    genre, duration);
                            t.addArtist((Backend.Users.Musician) users.getUser(musician));
                            user.addTrackToAlbum(titleOfTheAlbum, t);

                            // add musicians to the track
                            int option3 = 0;
                            while (option3 != -1) {
                                System.out.println("Add a new musician to the track? (y/n)");
                                String answer3 = sc.nextLine();
                                if (answer3.equals("y")) {
                                    System.out.println("Musician: ");
                                    String musician2 = sc.nextLine();
                                    t.addArtist((Backend.Users.Musician) users.getUser(musician2));
                                } else if (answer3.equals("n")) {
                                    option = -1;
                                } else {
                                    System.out.println("Invalid option");
                                }
                            }

                            user.addTrackToAlbum(titleOfTheAlbum, t);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (answer2.equals("n")) {
                        option = -1;
                    } else {
                        System.out.println("Invalid option");
                    }
                }
                break;
            default:
                System.out.println("Invalid option");
                break;
        }

    }

}
