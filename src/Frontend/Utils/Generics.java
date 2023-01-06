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
            Frontend.Utils.Prompt.outputError("Data inválida");
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
}
