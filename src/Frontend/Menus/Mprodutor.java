package Frontend.Menus;
import Frontend.Utils.Generics;
import Frontend.Utils.Prompt;

/**
 * The type Mprodutor.
 */
public class Mprodutor {
    private static int option;

    /**
     * Mostrar menu.
     */
    public static void mostrarMenu() {//Traduzido

        // escrever aqui os menus

        Frontend.Utils.Generics.menuProducerHeader();
        Frontend.Utils.Generics.menuProducerContent();

        option = Prompt.checkOption("[?] - Introduza a opção: ");

        if(8 == Mprodutor.option || 9 == Mprodutor.option) {
            Frontend.Utils.UserHolder.setUser(null);

            if(9 == Mprodutor.option) {
                Generics.setExit(true);
            }

            return; // log out
        }

        executeOption();
    }

    /**
     * Execute option.
     */
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
