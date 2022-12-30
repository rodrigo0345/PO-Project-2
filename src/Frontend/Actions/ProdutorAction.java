package Frontend.Actions;

import Backend.Albums.Album;
import Backend.Albums.AlbumEditado;
import Backend.Albums.Repos;
import Backend.Sessions.Session;
import Backend.Tracks.Track;
import Backend.Users.Musician;
import Frontend.Utils.Generics;
import Frontend.Utils.Prompt;
import Frontend.Utils.ReposHolder;

import java.rmi.StubNotFoundException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class ProdutorAction {
    private static Backend.Users.Produtor user;

    public static void setUser(Backend.Users.User user) {
        if (user instanceof Backend.Users.Produtor) {
            ProdutorAction.user = (Backend.Users.Produtor) user;
        } else {
            throw new IllegalArgumentException("User is not a Produtor");
        }
    }

    public static void editProfile() {
        System.out.println("[1] - Edit name");
        System.out.println("[2] - Edit username");
        System.out.println("[3] - Edit email");
        System.out.println("[4] - Edit password");

        int option = Prompt.checkOption("Introduza a opção: ");

        try {
            switch (option) {
                case 1:
                    String name = Prompt.readString("Name: ");
                    user.setName(name);
                    break;
                case 2:
                    String surname = Prompt.readString("New username: ");
                    user.setUsername(surname); // throws exception
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
                    Prompt.outputError("Invalid option");
                    break;
            }
        } catch (Exception e) {
            Prompt.outputError(e.getMessage());
        }
    }

    // demasiado grande, tem de ser dividido em vários métodos
    public static void startOrCreateEditingAlbum() {
        System.out.println("1. Start a new edit of an album");
        System.out.println("2. Edit an existing edit of an album");

        int option = Prompt.checkOption("Introduza a opção: ");

        if (option == 1) {
            String albumName = Prompt.readString("Album's name: ");
            String EditionAlbumName = Prompt.readString("Name for the Album's edition: ");
            
            Backend.Albums.AlbumEditado album;

            try {
                album = user.createAlbumEdit(albumName, EditionAlbumName);
            } catch( ClassNotFoundException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
                Generics.sc.nextLine();
                return;
            }

            String choice = Prompt.readString("Plan a new recording session? (y/n)");
            while(choice.equals("y")) {
                String newDate = Prompt.readString("Date for starting the session (dd/mm/yyyy HH:mm): ");
                LocalDateTime newDateStart = Frontend.Utils.Generics.stringToDate(newDate);
                newDate = Prompt.readString("Date for ending the session (dd/mm/yyyy HH:mm): ");
                LocalDateTime newDateEnd = Frontend.Utils.Generics.stringToDate(newDate);
                Backend.Sessions.Session newSession = null;

                try {
                    album.addSession(newDateStart, newDateEnd);
                    newSession = album.getLastSessionAdded();
                } catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }

                String artist = Prompt.readString("Add new artist? (y/n)");
                while(artist.equals("y")) {

                    artist = Prompt.readString("Artist's username: ");

                    Backend.Users.User aux = ReposHolder.getUsers().getUser(artist);
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
                        Generics.sc.nextLine();
                        // no need to return
                    }

                    artist = Prompt.readString("Add new artist? (y/n)");
                }

                choice = Prompt.readString("Plan a new recording session (y/n)");
            }

        } else if (option == 2) {
            String albumName = Prompt.readString("Name of the album: ");
            Backend.Albums.Album album = ReposHolder.getAlbums().getAlbum(albumName);

            if (!(album instanceof Backend.Albums.AlbumEditado)){
                System.out.println("The album you selected is not editable!");
                Generics.sc.nextLine();
                return;
            } else if (!(user.equals(album.getProdutor()))) {
                System.out.println("You cannot edit this album! Permission denied!");
                Generics.sc.nextLine();
                return;
            }

            System.out.println("1. Add a new recording session");
            System.out.println("2. Delete a recording session");
            System.out.println("3. Add a new artist");
            System.out.println("4. Remove an artist");
            System.out.println("5. Add a new track");
            System.out.println("6. End all past recording sessions");
            int choice = Prompt.checkInt("Introduza a opção: ");

            switch(choice) {
                case 1:
                    String dataInicio = Prompt.readString("Hora de inicio da sessão(dd/MM/yyyy HH:mm): ");
                    LocalDateTime novaDataInicio = Generics.stringToDate(dataInicio);
                    String dataFim = Prompt.readString("Hora de fim da sessão(dd/MM/yyyy HH:mm): ");
                    LocalDateTime novaDataFim = Generics.stringToDate(dataFim);

                    ((AlbumEditado) album).addSession(novaDataInicio, novaDataFim);

                    //System.out.println("Choose a date to the recording: ");
                    //LocalDate d = Frontend.Utils.Generics.readDate();
                    //((AlbumEditado) album).addSession(d);

                    break;
                case 2:
                    System.out.println();
                    String id = Prompt.readString("ID of the recording session: ");
                    boolean success = ReposHolder.getSessions().deleteSession(UUID.fromString(id));
                    if (!success) { System.out.println("The session you are trying to delete does not exist"); return;}
                    System.out.println("Session deleted with success");
                    break;
                case 3:

                    String username = Prompt.readString("Username of the artist: ");
                    Backend.Users.User artist = ReposHolder.getUsers().getUser(username);
                    if (!(artist instanceof Musician) || artist == null) {
                        System.out.println("Invalid username, the username you " +
                                "selected is either not an artist or does not exist!");
                        Generics.sc.nextLine();
                        return;
                    }

                    // always need to add the album to the
                    // artist and then the artist to the album
                    ((Musician) artist).addAlbum(album);
                    album.addArtist((Musician) artist);
                    break;
                case 4:
                    String username2 = Prompt.readString("Username of the artist: ");
                    boolean success2 = album.removeArtist(username2);
                    if (!success2) {
                        System.out.println("The system was not able to delete the user you" +
                                " provided!");
                        Generics.sc.nextLine();
                    }
                    break;
                case 5:

                    String trackName = Prompt.readString("Name of the track: ");
                    String genre = Prompt.readString("Genre of the track: ");
                    int duration = Prompt.checkInt("Duration: ");
                    String nameAlbum = Prompt.readString("Name of the associated album: ");
                    Album a = user.getProjeto(nameAlbum) == null? user.getProjeto(nameAlbum): user.getOldAlbum(nameAlbum);

                    Backend.Tracks.Track newTrack = new Track(a, trackName, genre ,duration);
                    boolean success3 = album.addTrack(newTrack);
                    if (!success3) {
                        System.out.println("The name of the track needs to be unique inside the album!");
                        Generics.sc.nextLine();
                    }
                    break;
                case 6:
                    System.out.println("Ending recording sessions...");
                    boolean res = ReposHolder.getSessions().endRecordingSessions();
                    if (!res) {
                        System.out.println("No recording session was ended!");
                        Generics.sc.nextLine();
                    }
                    break;
                default:
                    System.out.println("Invalid selection");
            }

        } else {
            System.out.println("Invalid option");
        }
    }

    public static void endRecordingSession() {
        System.out.println("Ending recording sessions...");
        boolean res = ReposHolder.getSessions().endRecordingSessions();
        if (!res) {
            System.out.println("No recording session was ended!");
            Generics.sc.nextLine();
        }
    }

    public static void showStateOfAlbum() {
        String albumName = Prompt.readString("Select the album to examine: ");
        Backend.Albums.Album album = ReposHolder.getAlbums().getAlbum(albumName);

        if (!(album instanceof AlbumEditado)){
            System.out.println(albumName + " was never edited here!");
            Generics.sc.nextLine();
            return;
        }
        if (((Backend.Albums.AlbumEditado) album).isEdited()) {
            System.out.println(albumName + " this album is now complete! Date of conclusion: "
                    + album.getDate());
        } else {
            System.out.println(albumName + " is still being edited!");
            Generics.sc.nextLine();
        }
    }

    public static void showYourAlbums() {
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
        Generics.sc.nextLine();
    }

    public static void showRecordingSessionsOfDay() {
        String day = Prompt.readString("Select the day to examine D: ");
        LocalDateTime dInicio = Frontend.Utils.Generics.stringToDate(day + " 00:00");
        LocalDateTime dFim = Frontend.Utils.Generics.stringToDate(day + " 23:59");
        Backend.Sessions.Session session = user.findSessionByDate(dInicio, dFim);
        System.out.println(session);
    }

    public static void checkData() {
        System.out.println("Name: " + user.getName());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: " + user.getPassword());
        Generics.sc.nextLine();
    }
}
