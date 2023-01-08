package Frontend.Utils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * The type Generics.
 */
public class Generics {

    /**
     * The constant sc.
     */
// used accross the frontend
    public static final Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);

    // exit the program
    private static boolean exit;

    /**
     * Read date local date time.
     *
     * @param msg the msg
     * @return the local date time
     */
    public static LocalDateTime readDate(String msg){
        String d = Frontend.Utils.Prompt.readString(msg);
        return stringToDate(d);
    }

    /**
     * String to date local date time.
     *
     * @param d the d
     * @return the local date time
     */
    public static LocalDateTime stringToDate(String d){
        LocalDateTime date = null;

        // verify that the inserted date is valid
        try {
            String[] dateSplitedByDateAndTime = d.split(" ");
            String[] dateSplited = dateSplitedByDateAndTime[0].split("/");
            String[] timeSplited = dateSplitedByDateAndTime[1].split(":");

            date = LocalDateTime.of(
                Integer.parseInt(dateSplited[2]),
                Integer.parseInt(dateSplited[1]),
                Integer.parseInt(dateSplited[0]),
                Integer.parseInt(timeSplited[0]),
                Integer.parseInt(timeSplited[1])
            );
        } catch (Exception e) {

            ConsoleColors color = new ConsoleColors();
            System.out.println(color.getRED());
            Frontend.Utils.Prompt.outputError("[!] - ERRO: Data inválida!");
            Frontend.Utils.Prompt.pressEnterToContinue();
            return null;
        }
        return date;
    }

    /**
     * Is exit boolean.
     *
     * @return the boolean
     */
    public static boolean isExit() {
        return exit;
    }

    /**
     * Sets exit.
     *
     * @param exit the exit
     */
    public static void setExit(boolean exit) {
        Generics.exit = exit;
    }


    public static void menuAdminHeader(){

        ConsoleColors color = new ConsoleColors();
        System.out.print("\033[H\033[2J");
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

    }

    public static void menuAdminContent(){

        ConsoleColors color = new ConsoleColors();

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
    }


public static void menuMusicianHeader(){

    System.out.print("\033[H\033[2J");
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
}

public static void menuMusicianContent(){

    ConsoleColors color = new ConsoleColors();
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
    System.out.print(" - Futuras sessões de gravação");
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
    System.out.println(" - Exit");}

public static void menuProducerHeader(){

    System.out.print("\033[H\033[2J");
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

}

public static void menuProducerContent(){

    ConsoleColors color = new ConsoleColors();

    System.out.println(color.getYELLOW());
    System.out.print("                                [1]");
    System.out.print(color.getWHITE());
    System.out.print(" - Editar perfil");
    System.out.println(color.getYELLOW());
    System.out.print("                                [2]");
    System.out.print(color.getWHITE());
    System.out.print(" - Iniciar ou editar um album");
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
}
}