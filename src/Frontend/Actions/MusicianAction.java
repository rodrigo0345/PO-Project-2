package Frontend.Actions;

import Backend.Albums.Album;
import Backend.Instruments.Instrument;
import Backend.Instruments.Repos;
import Backend.Sessions.Session;
import Frontend.Utils.Generics;
import Frontend.Utils.Prompt;
import Frontend.Utils.ReposHolder;

import java.time.LocalDateTime;
import java.util.Set;

public class MusicianAction {

        private static Backend.Users.Musician user;

        public static void setUser(Backend.Users.User user) {
            if (user instanceof Backend.Users.Musician) {
                MusicianAction.user = (Backend.Users.Musician) user;
            } else {
                throw new IllegalArgumentException("User is not a Produtor");
            }
        }

    public static void editProfile() {
        System.out.println("[1] - Edit name");
        System.out.println("[2] - Edit username");
        System.out.println("[3] - Edit email");
        System.out.println("[4] - Edit password");
        System.out.println("[5] - See the state of all recording sessions");

        try {
            int option = Prompt.checkOption("Introduza a opção: ");

            switch (option) {
                case 1:
                    String name = Prompt.readString("New name: ");
                    user.setName(name);
                    break;
                case 2:
                    String surname = Prompt.readString("New username: ");
                    user.setUsername(surname);
                    break;
                case 3:
                    String email = Prompt.readString("New email: ");
                    user.setEmail(email);
                    break;
                case 4:
                    String password = Prompt.readString("New password: ");
                    user.setPassword(password);
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Invalid option");
        }
    }

    public static void showAssociatedAlbums() {
        Set<Album> aux = user.getAlbums();

        for (Backend.Albums.Album album : aux) {
            if (album instanceof Backend.Albums.Album) {
                System.out.println(album);
            }
        }
        Generics.sc.nextLine();
    }

    public static void showFutureRecordingSessions() {
        // get all the sessions with the musician in it
        Set<Session> relatedSessions = ReposHolder.getSessions().getMusicianSessions(user);
        for (Session s: relatedSessions){
            System.out.println(s);
        }
        Generics.sc.nextLine();
    }

    // does not treat errors
    public static void requestInstrumentForSession() {
        // mudem isto que devem ir buscar a sessao por id e não por data pff
        System.out.println("Access option 3 to see all the available sessions");
        LocalDateTime date = Frontend.Utils.Generics.readDate("Date of the session: ");
        Session selectedSession = ReposHolder.getSessions().getSession(date);

        Set<Backend.Instruments.Instrument> availableInstruments = user.getInstruments();
        for(Backend.Instruments.Instrument i: availableInstruments){
            System.out.println(i);
        }
        String instrumentName = Prompt.readString("Instrument's name: ");
        Instrument instrument = ReposHolder.getInstruments().getInstrument(instrumentName);

        // the admin will then be able to accept or deny the request
        selectedSession.addPendingInstrument(instrument);
    }

    public static void showStatOfAllRecordingSessions() {
        System.out.println("See the state of all recording sessions");
        for(Session s: ReposHolder.getSessions().getPendingSessions()){
            System.out.println("Pending: " + s);
        }

        for(Session s: ReposHolder.getSessions().getSessions()){
            if (s.isCompleted() == false){
                System.out.println("Accepted: " + s);
            }
            else {
                System.out.println("Done: " + s);
            }
        }
        Generics.sc.nextLine();
    }

    public static void checkData() {
        System.out.println("Name: " + user.getName());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: " + user.getPassword());
        //falta a lista de instrumentos que cada músico toca
        Generics.sc.nextLine();
    }
}
