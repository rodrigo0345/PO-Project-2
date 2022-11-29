package Frontend.Menus;

import java.util.Scanner;
import java.util.Set;

import Backend.Users.Musician;

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
        System.out.println("1. Edit profile");
        System.out.println("2. Associated albums");
        System.out.println("3. Future recording sessions");
        System.out.println("4. Request an instrument for a specific recording session");
        System.out.println("5. See the state of all recording sessions");

        try {
            this.option = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid option");
        }
    }

    public void executeOption() {
        Scanner sc = new Scanner(System.in);
        
        switch (option) {
            case 1:
                System.out.println("[1] - Edit name");
                System.out.println("[2] - Edit username");
                System.out.println("[3] - Edit email");
                System.out.println("[4] - Edit password");
                System.out.println("[5] - Edit email");

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
                        case 5:
                            System.out.println("New email: ");
                            String email2 = sc.next();
                            user.setEmail(email2);
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
                Set<Backend.Albums.Album> albums = this.user.getAlbums();

                for (Backend.Albums.Album album : albums) {
                    if (album instanceof Backend.Albums.Album) {
                        System.out.println(album.getTitulo());
                    }
                }
                break;
            case 3:
                Set<Backend.Albums.Album> albums2 = this.user.getAlbums();

                for (Backend.Albums.Album album : albums2) {
                    if (album instanceof Backend.Albums.Album && !((Backend.Albums.AlbumEditado)album).isEdited()) {
                        System.out.println(album);
                    }
                }
                break;
            case 4:
                Set<Backend.Instruments.Instrument> availableInstruments = this.user.getInstruments();
                
                System.out.println("Instrument's name: ");
                break;
            case 5:
                System.out.println("See the state of all recording sessions");
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }
}