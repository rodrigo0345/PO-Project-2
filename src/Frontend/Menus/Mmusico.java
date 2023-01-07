package Frontend.Menus;
import Frontend.Utils.Generics;
import Frontend.Utils.Prompt;

/**
 * The type Mmusico.
 */
public class Mmusico{
    private static int option;

    /**
     * Mostrar menu.
     */
    public static void mostrarMenu() {

        ConsoleColors color = new ConsoleColors();

        System.out.println(color.getCYAN());
        System.out.println(
                "________________________________________________________________________________________________________________________");
        System.out.println(color.getYELLOW());
        System.out.println("                                __   __  __   __  _______  ___   _______  _______    ");
        System.out.println("                               |  |_|  ||  | |  ||       ||   | |       ||       |");
        System.out.println("                               |       ||  | |  ||  _____||   | |       ||   _   | ");
        System.out.println("                               |       ||  |_|  || |_____ |   | |       ||  | |  |");
        System.out.println("                               |       ||       ||_____  ||   | |      _||  |_|  |");
        System.out.println("                               | ||_|| ||       | _____| ||   | |     |_ |       |");
        System.out.println("                               |_|   |_||_______||_______||___| |_______||_______|");
        System.out.println(color.getCYAN());
        System.out.println(
                "________________________________________________________________________________________________________________________");
        
        System.out.println(color.getWHITE());
        System.out.println("                                          Menu de Musico - Logado como " + Frontend.Utils.UserHolder.getUser().getUsername());

        System.out.println(color.getCYAN());
        System.out.println("                        **************************************************************");
        System.out.println(color.getYELLOW());
        System.out.print("                                [1]");
        System.out.print(color.getWHITE());
        System.out.print(" - Editar perfil");
        System.out.println(color.getYELLOW());
        System.out.print("                                [2]");
        System.out.print(color.getWHITE());
        System.out.print(" - Álbuns associados");
        System.out.println(color.getYELLOW());
        System.out.print("                                [3]");
        System.out.print(color.getWHITE());
        System.out.print(" - Futuras gravações de sessão");
        System.out.println(color.getYELLOW());
        System.out.print("                                [4]");
        System.out.print(color.getWHITE());
        System.out.print(" - Requisitar instrumento para uma determinada sessão de gravação");
        System.out.println(color.getYELLOW());
        System.out.print("                                [5]");
        System.out.print(color.getWHITE());
        System.out.print(" - Verificar estado de todas as sessões de gravação");
        System.out.println(color.getYELLOW());
        System.out.print("                                [6]");
        System.out.print(color.getWHITE());
        System.out.println(" - Consultar dados");
        System.out.print(color.getYELLOW());
        System.out.print("                                [7]");
        System.out.print(color.getWHITE());
        System.out.print(" - Log out");
        System.out.println(color.getYELLOW());
        System.out.print("                                [8]");
        System.out.print(color.getWHITE());
        System.out.println(" - Exit");
 
 

        option = Prompt.checkOption("[?] - Introduza a opção: ");

        if(7 == Mmusico.option || 8 == Mmusico.option) {
            Frontend.Utils.UserHolder.setUser(null);

            if(8 == Mmusico.option) {
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

        Frontend.Actions.MusicianAction.setUser(Frontend.Utils.UserHolder.getUser());

        switch (option) {
            case 1:
                Frontend.Actions.MusicianAction.editProfile();
                break;
            case 2:
                Frontend.Actions.MusicianAction.showAssociatedAlbums();
                break;
            case 3:
                Frontend.Actions.MusicianAction.showFutureRecordingSessions();
                break;
            case 4:
                Frontend.Actions.MusicianAction.requestInstrumentForSession();
                break;
            case 5:
                Frontend.Actions.MusicianAction.showStatOfAllRecordingSessions();
                break;
            case 6:
                Frontend.Actions.MusicianAction.checkData();
                break;
            default:
                Frontend.Utils.Prompt.outputError("Opção inválida!");
                break;
        }
    }
}