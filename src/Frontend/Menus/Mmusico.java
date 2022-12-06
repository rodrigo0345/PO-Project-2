package Frontend.Menus;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.Set;

import Backend.Instruments.Instrument;
import Backend.Sessions.Session;
import Backend.Users.Musician;
import Frontend.Utils.Generics;

public class Mmusico implements Menu {
    private int option;
    private Musician user;

    public Mmusico(Musician user) {
        this.user = user;
    }

    public Musician getUser() {
        return user;
    }

    @Override
    public void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Menu de Musico - Logged as " + user.getUsername());
        System.out.println("1. Edit profile");
        System.out.println("2. Associated albums");
        System.out.println("3. Future recording sessions");
        System.out.println("4. Request an instrument for a specific recording session");
        System.out.println("5. See the state of all recording sessions");
        System.out.println("6. Log out");

        try {
            this.option = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid option");
        }
    }

    public void executeOption(Backend.Instruments.Repos instruments, Backend.Albums.Repos albums,
            Backend.Users.Repos users, Backend.Sessions.Repos sessions) {
        Scanner sc = new Scanner(System.in);

        switch (option) {
            case 1:
                System.out.println("[1] - Edit name");
                System.out.println("[2] - Edit username");
                System.out.println("[3] - Edit email");
                System.out.println("[4] - Edit password");
                System.out.println("[5] - See the state of all recording sessions");

                try {
                    int option = sc.nextInt();
                    switch (option) {
                        case 1:
                            System.out.println("New name: ");
                            String name = sc.next();
                            user.setName(name);
                            break;
                        case 2:
                            System.out.println("New username: ");
                            String surname = sc.next();
                            user.setUsername(surname);
                            break;
                        case 3:
                            System.out.println("New email: ");
                            String email = sc.next();
                            user.setEmail(email);
                            break;
                        case 4:
                            System.out.println("New password: ");
                            String password = sc.next();
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
                Set<Backend.Albums.Album> aux = this.user.getAlbums();

                for (Backend.Albums.Album album : aux) {
                    if (album instanceof Backend.Albums.Album) {
                        System.out.println(album.toString());
                    }
                }
                sc.nextLine();
                break;
            case 3:
                // get all the sessions with the musician in it
                Set<Session> relatedSessions = sessions.getMusicianSessions(user);
                for (Session s: relatedSessions){
                    System.out.println(s);
                }
                sc.nextLine();
                break;
            case 4:  // does not treat errors
                System.out.println("Access option 3 to see all the available sessions");
                System.out.println("Date of the session: ");
                LocalDate date = Frontend.Utils.Generics.readDate();
                Session selectedSession = sessions.getSession(date);

                Set<Backend.Instruments.Instrument> availableInstruments = this.user.getInstruments();
                for(Backend.Instruments.Instrument i: availableInstruments){
                    System.out.println(i);
                }
                System.out.println("Instrument's name: ");
                String instrumentName = sc.nextLine();
                Instrument instrument = instruments.getInstrument(instrumentName);

                // the admin will then be able to accept or deny the request
                selectedSession.addPendendingInstrument(instrument);
                break;
            case 5:
                System.out.println("See the state of all recording sessions");
                for(Session s: sessions.getPendingSessions()){
                    System.out.println("Pending: " + s);
                }

                for(Session s: sessions.getSessions()){
                    if (s.isCompleted() == false){
                        System.out.println("Accepted: " + s);
                    }
                    else {
                        System.out.println("Done: " + s);
                    }
                }
                sc.nextLine();
                break;
            case 6:
                this.user = null;
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }
}