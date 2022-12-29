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
import Frontend.Utils.Prompt;

// Menu of the administrators
public class Madmin {
    private static int option;

    public static void mostrarMenu() {
       
        System.out.println("Menu de Administrador - Logged as " +
                                        Frontend.Utils.UserHolder.getUser().getUsername());
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
        System.out.println("15. Exit");

        option = Prompt.checkOption("Introduza a opção: ");

        if(option == 14 || option == 15) {
            Frontend.Utils.UserHolder.setUser(null);

            if (option == 15) {
                Generics.setExit(true);
            }

            return; // log out
        }

        executeOption();
    }

    private static void executeOption() {

        Frontend.Actions.AdminAction.setUser(Frontend.Utils.UserHolder.getUser());

        switch (option) {
            case 1:
                Frontend.Actions.AdminAction.addProducer();
                break;
            case 2:
                Frontend.Actions.AdminAction.addMusician();
                break;
            case 3:
                Frontend.Actions.AdminAction.removeUser();
                break;
            case 4:
                Frontend.Actions.AdminAction.addInstrument();
                break;
            case 5:                                                         
                Frontend.Actions.AdminAction.showAllSessionRequests();
                break;
            case 6:
                Frontend.Actions.AdminAction.showAllSessions();
                break;
            case 7:
                Frontend.Actions.AdminAction.showAllAlbumsBeingEdited();
                break;
            case 8:
                Frontend.Actions.AdminAction.stats();
                break;
            case 9:
                Frontend.Actions.AdminAction.showAllUsers();
                break;
            case 10:
                Frontend.Actions.AdminAction.showAllInstruments();
                break;
            case 11:
                Frontend.Actions.AdminAction.addAlbum();
                break;
            case 12:
                Frontend.Actions.AdminAction.showAllAlbums();
                break;
            case 13:
                Frontend.Actions.AdminAction.checkData();
                break;
            default:
                Prompt.outputError("Opção inválida");
                break;
        }

    }

}
