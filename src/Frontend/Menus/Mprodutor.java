package Frontend.Menus;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

import Backend.Albums.AlbumEditado;
import Backend.Users.Musician;
import Backend.Users.Produtor;

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
        System.out.println("7. Log out");

        try {
            option = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid option");
        }
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

                try {
                    int option = sc.nextInt();
                } catch (Exception e) {
                    System.out.println("Invalid option");
                    sc.nextLine();
                    return;
                }

                try {
                    switch (option) {
                        case 1:
                            System.out.println("New name: ");
                            String name = sc.next();
                            user.setName(name);
                            break;
                        case 2:
                            System.out.println("New username: ");
                            String surname = sc.next();
                            user.setUsername(surname); // throws exception
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
                    System.out.println("Original Album's name: ");
                    String albumName = sc.nextLine();
                    System.out.println("New Album's name: ");
                    String newAlbumName = sc.nextLine();

                    Backend.Albums.AlbumEditado album;
                    try {
                        album = user.createAlbumEdit(albumName, newAlbumName);
                    } catch( ClassNotFoundException | IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        return;
                    }

                    System.out.println("Plan a new recording session? (y/n)");
                    String choice = sc.nextLine();
                    while(choice.equals("y")) {
                        System.out.println("Date of the session (dd mm yyyy): ");
                        String newDate = sc.nextLine();
                        LocalDate newDateFormatted = null;
                        Backend.Sessions.Session newSession = null;

                        try {
                            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd MM yyyy", Locale.ITALY);
                            newDateFormatted = LocalDate.parse(newDate, df);
                            newSession = album.addSession(newDateFormatted);
                        } catch (IllegalArgumentException e){
                            System.out.println(e.getMessage());
                        } catch (Exception e) {
                            System.out.println("Invalid date");
                            sc.nextLine();
                            return;
                        }

                        System.out.println("Add new artist? (y/n)");
                        String artist = sc.nextLine();
                        while(artist.equals("y")) {
                            System.out.println("Artist's username: ");
                            artist = sc.nextLine();

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

                            System.out.println("Add new artist? (y/n)");
                            artist = sc.nextLine();
                        }

                        System.out.println("Plan a new recording session (y/n)");
                        choice = sc.nextLine();
                    }

                } else if (option2 == 2) {
                    System.out.println("Name of the album: ");
                    String albumName = sc.nextLine();
                    Backend.Albums.Album album = albums.getAlbum(albumName);

                    if (!(album instanceof Backend.Albums.AlbumEditado)){
                        System.out.println("The album you selected is not editable!");
                        return;
                    } else if (((Backend.Users.Produtor) user).equals(album.getProdutor())) {
                        System.out.println("You cannot edit this album! Permission denied!");
                        return;
                    }

                    System.out.println("1. Add a new recording session");
                    System.out.println("2. Delete a recording session");
                    System.out.println("3. Add a new artist");
                    System.out.println("4. Remove an artist");
                    System.out.println("5. Add a new track");
                    int choice = sc.nextInt();

                    switch(choice) {
                        case 1:
                            System.out.println("Choose a date to the recording: ");
                            LocalDate d = Frontend.Utils.Generics.readDate();
                            ((AlbumEditado) album).addSession(d);
                            break;
                        case 2:
                            System.out.println("ID of the recording session: ");
                            String id = sc.nextLine();
                            boolean success = sessions.deleteSession(UUID.fromString(id));
                            if (!success) { System.out.println("The session you are trying to delete does not exist"); return;}
                            System.out.println("Session deleted with success");
                            break;
                        case 3:
                            System.out.println("Ending recording session....");

                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        default:
                            System.out.println("Invalid selection");
                    }

                } else {
                    System.out.println("Invalid option");
                }

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;

            case 6:
                
                break;
            case 7:
                this.user = null;
                break;
            default:

                break;
        }

    }

}
