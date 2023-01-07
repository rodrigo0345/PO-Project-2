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

        ConsoleColors color = new ConsoleColors();

        System.out.println(color.getCYAN());
        System.out.println(
                " ________________________________________________________________________________________________________________________");
        System.out.println(color.getYELLOW());
        System.out.println(
                "                        _______  ______    _______  ______   __   __  _______  _______  ______   ");
        System.out.println(
                "                       |       ||    _ |  |       ||      | |  | |  ||       ||       ||    _ | ");
        System.out.println(
                "                       |    _  ||   | ||  |   _   ||  _    ||  | |  ||_     _||   _   ||   | || ");
        System.out.println(
                "                       |   |_| ||   |_||_ |  | |  || | |   ||  |_|  |  |   |  |  | |  ||   |_||_");
        System.out.println(
                "                       |    ___||    __  ||  |_|  || |_|   ||       |  |   |  |  |_|  ||    __  |");
        System.out.println(
                "                       |   |    |   |  | ||       ||       ||       |  |   |  |       ||   |  | |");
        System.out.println(
                "                       |___|    |___|  |_||_______||______| |_______|  |___|  |_______||___|  |_|");
        System.out.println(color.getCYAN());
        System.out.println(
                " ________________________________________________________________________________________________________________________");
        System.out.println(color.getRESET());
        System.out.println(color.getWHITE());
        System.out.println("                                            Producer Menu - Logged as " + Frontend.Utils.UserHolder.getUser().getUsername());

        System.out.println(color.getCYAN());
        System.out.println("                        **************************************************************");
        System.out.println(color.getYELLOW());
        System.out.print("                                [1]");
        System.out.print(color.getWHITE());
        System.out.print(" - Editar perfil");
        System.out.println(color.getYELLOW());
        System.out.print("                                [2]");
        System.out.print(color.getWHITE());
        System.out.print(" - Start/Edit the editing of an album");
        System.out.println(color.getYELLOW());
        System.out.print("                                [3]");
        System.out.print(color.getWHITE());
        System.out.print(" - Terminar sessão de gravação");
        System.out.println(color.getYELLOW());
        System.out.print("                                [4]");
        System.out.print(color.getWHITE());
        System.out.print(" - Verificar o estado de um álbum");
        System.out.println(color.getYELLOW());
        System.out.print("                                [5]");
        System.out.print(color.getWHITE());
        System.out.print(" - Os seus álbums");
        System.out.println(color.getYELLOW());
        System.out.print("                                [6]");
        System.out.print(color.getWHITE());
        System.out.print(" - Sessões de gravação de um dia");
        System.out.println(color.getYELLOW());
        System.out.print("                                [7]");
        System.out.print(color.getWHITE());
        System.out.print(" - Consultar dados");
        System.out.println(color.getYELLOW());
        System.out.print("                                [8]");
        System.out.print(color.getWHITE());
        System.out.println(" - Log out");
        System.out.print(color.getYELLOW());
        System.out.print("                                [9]");
        System.out.print(color.getWHITE());
        System.out.println(" - Exit");
        System.out.println(color.getCYAN());
        System.out.println("                       **************************************************************");
        

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
