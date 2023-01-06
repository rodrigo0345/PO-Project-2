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
