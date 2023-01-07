package Frontend.Menus;
import Frontend.Utils.Generics;
import Frontend.Utils.Prompt;

/**
 * The type Madmin.
 */
// Menu of the administrators
public class Madmin {
    private static int option;

    /**
     * Mostrar menu.
     */
    public static void mostrarMenu() {

        Frontend.Utils.Generics.menuAdminHeader();
        Frontend.Utils.Generics.menuAdminContent();

        option = Prompt.checkOption("[?] - Introduza a opção: ");
        
        if(16 == Madmin.option || 17 == Madmin.option) {
            Frontend.Utils.UserHolder.setUser(null);

            if (17 == Madmin.option) {
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
                Prompt.outputError("[!] - ERRO: Opção inválida!");
                break;
        }

    }

}
