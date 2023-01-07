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

        ConsoleColors color = new ConsoleColors();

        System.out.println(color.getCYAN());
        System.out.println(
                        " ________________________________________________________________________________________________________________________");
        System.out.println(color.getYELLOW());
        System.out.println(
                        "     _______  ______   __   __  ___   __    _  ___   _______  _______  ______    _______  ______   _______  ______   ");
        System.out.println(
                        "    |   _   ||      | |  |_|  ||   | |  |  | ||   | |       ||       ||    _ |  |   _   ||      | |       ||    _ | ");
        System.out.println(
                        "    |  |_|  ||  _    ||       ||   | |   |_| ||   | |  _____||_     _||   | ||  |  |_|  ||  _    ||   _   ||   | || ");
        System.out.println(
                        "    |       || | |   ||       ||   | |       ||   | | |_____   |   |  |   |_||_ |       || | |   ||  | |  ||   |_||_");
        System.out.println(
                        "    |       || |_|   ||       ||   | |  _    ||   | |_____  |  |   |  |    __  ||       || |_|   ||  |_|  ||    __  |");
        System.out.println(
                        "    |   _   ||       || ||_|| ||   | | | |   ||   |  _____| |  |   |  |   |  | ||   _   ||       ||       ||   |  | |");
        System.out.println(
                        "    |__| |__||______| |_|   |_||___| |_|  |__||___| |_______|  |___|  |___|  |_||__| |__||______| |_______||___|  |_|");
        System.out.println(color.getCYAN());
        System.out.println(
                        " ________________________________________________________________________________________________________________________");
                        
        System.out.println(color.getWHITE());
        System.out.println("                                   Menu de Administrador - Logado como " +
                                                        Frontend.Utils.UserHolder.getUser().getUsername());
        System.out.println(color.getCYAN());
        System.out.println(
                        "                        **************************************************************");
        System.out.println(color.getYELLOW());
        System.out.print("                                [1]");
        System.out.print(color.getWHITE());
        System.out.print(" - Adicionar um novo produtor");
        System.out.println(color.getYELLOW());
        System.out.print("                                [2]");
        System.out.print(color.getWHITE());
        System.out.print(" - Adicionar um novo músico");
        System.out.println(color.getYELLOW());
        System.out.print("                                [3]");
        System.out.print(color.getWHITE());
        System.out.print(" - Remover um utilizador");
        System.out.println(color.getYELLOW());
        System.out.print("                                [4]");
        System.out.print(color.getWHITE());
        System.out.print(" - Adicionar um instrumento musical");
        System.out.println(color.getYELLOW());
        System.out.print("                                [5]");
        System.out.print(color.getWHITE());
        System.out.print(" - Exibir todos os pedidos de sessão");
        System.out.println(color.getYELLOW());
        System.out.print("                                [6]");
        System.out.print(color.getWHITE());
        System.out.print(" - Exibir todos as sessões de gravação");
        System.out.println(color.getYELLOW());
        System.out.print("                                [7]");
        System.out.print(color.getWHITE());
        System.out.print(" - Exibir todos os álbuns em edição");
        System.out.println(color.getYELLOW());
        System.out.print("                                [8]");
        System.out.print(color.getWHITE());
        System.out.print(" - Estatisticas");
        System.out.println(color.getYELLOW());
        System.out.print("                                [9]");
        System.out.print(color.getWHITE());
        System.out.print(" - Exibir todos os users");
        System.out.println(color.getYELLOW());
        System.out.print("                               [10]");
        System.out.print(color.getWHITE());
        System.out.print(" - Exibir todos os instrumentos");
        System.out.println(color.getYELLOW());
        System.out.print("                               [11]");
        System.out.print(color.getWHITE());
        System.out.print(" - Adicionar um novo álbum");
        System.out.println(color.getYELLOW());
        System.out.print("                               [12]");
        System.out.print(color.getWHITE());
        System.out.print(" - Exibir todos os álbuns");
        System.out.println(color.getYELLOW());
        System.out.print("                               [13]");
        System.out.print(color.getWHITE());
        System.out.print(" - Consultar dados");
        System.out.println(color.getYELLOW());
        System.out.print("                               [14]");
        System.out.print(color.getWHITE());
        System.out.print(" - Validar Requisição de Instrumentos");
        System.out.println(color.getYELLOW());
        System.out.print("                               [15]");
        System.out.print(color.getWHITE());
        System.out.print(" - Remover um album");
        System.out.println(color.getYELLOW());
        System.out.print("                               [16]");
        System.out.print(color.getWHITE());
        System.out.print(" - Log out");
        System.out.println(color.getYELLOW());
        System.out.print("                               [17]");
        System.out.print(color.getWHITE());
        System.out.println(" - Exit");
        System.out.println(color.getCYAN());
        System.out.println(
                        "                       **************************************************************");

       
        
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
                Prompt.outputError("Opção inválida");
                break;
        }

    }

}
