package Frontend.Menus;

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
        System.out.println("1. Add a new producer");
        System.out.println("2. Add a new musician");
        System.out.println("3. Remove user");
        System.out.println("4. Add music instrument");
        System.out.println("5. Show all session requests");
        System.out.println("6. Show all recording sessions");
        System.out.println("7. Show all albums being edited");
        System.out.println("8. Stats");

        try {
            option = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid option");
        }
    }

    @Override
    public void executeOption() {

        switch (option) {
            case 1:
                // addProducer();
                break;
            case 2:
                // addMusician();
                break;
            case 3:
                // removeUser();
                break;
            case 4:
                // addMusicInstrument();
                break;
            case 5:
                // showAllSessionRequests();
                break;
            case 6:
                // showAllRecordingSessions();
                break;
            case 7:
                // showAllAlbumsBeingEdited();
                break;
            case 8:
                // showStats();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }

    }

}
