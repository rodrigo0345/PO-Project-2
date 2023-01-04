package Frontend.Utils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Generics {

    // used accross the frontend
    public static final Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);

    // exit the program
    private static boolean exit;

    public static LocalDateTime readDate(String msg){
        String d = Frontend.Utils.Prompt.readString(msg);
        return stringToDate(d);
    }

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

    public static boolean isExit() {
        return exit;
    }

    public static void setExit(boolean exit) {
        Generics.exit = exit;
    }
}
