package Frontend.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Generics {
    // already has the scanner
    public static LocalDate readDate(){
        Scanner sc = new Scanner(System.in);
        String d = sc.nextLine();
        return stringToDate(d);
    }

    public static LocalDate stringToDate(String d){
        LocalDate date = null;
        Scanner sc = new Scanner(System.in);

        // verify that the inserted date is valid
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE);
            date = LocalDate.parse(d, df);
        } catch (Exception e) {
            System.out.println("Invalid date");
            sc.nextLine();
            return null;
        }
        return date;
    }
}
