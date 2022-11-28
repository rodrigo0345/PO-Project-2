package Frontend.Menus;

import java.util.Scanner;

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
        switch (option) {
            case 1:
                System.out.println("Edit profile");
                break;
            case 2:
                System.out.println("Associated albums");
                break;
            case 3:
                System.out.println("Future recording sessions");
                break;
            case 4:
                System.out.println("Request an instrument for a specific recording session");
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