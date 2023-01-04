package Frontend.Menus;

import java.awt.*;
import java.rmi.StubNotFoundException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

import Backend.Albums.Album;
import Backend.Albums.AlbumEditado;
import Backend.Tracks.Track;
import Backend.Users.Musician;
import Backend.Users.Produtor;
import Frontend.Utils.Generics;
import Frontend.Utils.Prompt;

public class Mprodutor {
    private static int option;

    public static void mostrarMenu() {//Traduzido

        // escrever aqui os menus
        System.out.println("Producer Menu - Logged as " + Frontend.Utils.UserHolder.getUser().getUsername());
        System.out.println("1. Editar perfil");
        System.out.println("2. Start/Edit the editing of an album");
        System.out.println("3. Terminar sessão de gravação");
        System.out.println("4. Verificar o estado de um álbum");
        System.out.println("5. Os seus álbums");
        System.out.println("6. Sessões de gravação de um dia");
        System.out.println("7. Consultar dados");   
        System.out.println("8. Log out");
        System.out.println("9. Exit");

        option = Prompt.checkOption("Introduza a opção: ");

        if(option == 8 || option == 9) {
            Frontend.Utils.UserHolder.setUser(null);

            if(option == 9) {
                Generics.setExit(true);
            }

            return; // log out
        }

        executeOption();
    }

    public static void executeOption() {

        Frontend.Actions.ProdutorAction.setUser(Frontend.Utils.UserHolder.getUser());

        switch (option) {
            case 1:
                Frontend.Actions.ProdutorAction.editProfile();
                break;
            case 2:
                Frontend.Actions.ProdutorAction.startOrCreateEditingAlbum();
                break;
            case 3:
                Frontend.Actions.ProdutorAction.endRecordingSession();
                break;
            case 4:
                Frontend.Actions.ProdutorAction.showStateOfAlbum();
                break;
            case 5:
                Frontend.Actions.ProdutorAction.showYourAlbums();
                break;
            case 6: // já encontra mais do que uma sessao por dia
                Frontend.Actions.ProdutorAction.showRecordingSessionsOfDay();
                break;
            case 7:
                Frontend.Actions.ProdutorAction.checkData();
                break;
            default:
                Prompt.outputError("Opção inválida");
                break;
        }

    }
    
}
