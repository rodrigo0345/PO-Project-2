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
public class Madmin { //TRADUZIDO
    private static int option;

    public static void mostrarMenu() {
       
        System.out.println("Menu de Administrador - Logado como " +
                                        Frontend.Utils.UserHolder.getUser().getUsername());
        System.out.println("1. Adicionar um novo produtor");
        System.out.println("2. Adicionar um novo músico");
        System.out.println("3. Remover um utilizador");
        System.out.println("4. Adicionar um instrumento musical");
        System.out.println("5. Exibir todos os pedidos de sessão");
        System.out.println("6. Exibir todos as sessões de gravação");
        System.out.println("7. Exibir todos os álbuns em edição");
        System.out.println("8. Estatisticas");
        System.out.println("9. Exibir todos os users");
        System.out.println("10. Exibir todos os instrumentos");
        System.out.println("11. Adicionar um novo álbum");
        System.out.println("12. Exibir todos os álbuns");
        System.out.println("13. Consultar dados");
        System.out.println("14. Validar Requisição de Instrumentos");
        System.out.println("15. Remover um album");
        System.out.println("16. Log out");
        System.out.println("17. Exit");

        option = Prompt.checkOption("Introduza a opção: ");

        if(option == 16|| option == 17) {
            Frontend.Utils.UserHolder.setUser(null);

            if (option == 17) {
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
            case 14:
                Frontend.Actions.AdminAction.showAllInstrumentsRequests();
                break;
            case 15:
                Frontend.Actions.AdminAction.removeAlbum();
                break;
            default:
                Prompt.outputError("Opção inválida");
                break;
        }

    }

}
