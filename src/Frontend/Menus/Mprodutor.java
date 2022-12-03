package Frontend.Menus;

import java.util.Scanner;

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
            Backend.Users.Repos users) {
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
                    // String albumName = sc.nextLine();
                    System.out.println("New Album's name: ");
                    // String albumName = sc.nextLine();
                    System.out.println("New Album genre: ");
                    String albumGenre = sc.nextLine();

                    // user.createProject();
                } else if (option2 == 2) {

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
