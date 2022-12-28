package Frontend.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

import javax.sound.midi.Soundbank;

public class Generics {
    // already has the scanner

   private final static Scanner sc = new Scanner(System.in); // Pusemos static porque dava erro

    public static LocalDateTime readDate(String msg){
        System.out.println(msg);
        String d = null;
        
        try {
             d = sc.nextLine();
        } catch (Exception e) {
            System.out.println("Erro! Data inválida");
            return null;
        }   
        return stringToDate(d);
    }

    public static LocalDateTime stringToDate(String d){
        LocalDateTime date = null;

        // verify that the inserted date is valid
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.ITALY);
            date = LocalDateTime.parse(d, df);
        } catch (Exception e) {
            System.out.println("Invalid date");
            sc.nextLine();
            return null;
        }
        return date;
    }

    private static void outputError(String message) {
        System.err.println(message);
    }

    private static void write(String message) {
        System.out.println(message);
    }

    public static String readString(String message){
        write(message);
        return sc.nextLine();
    }

    public static Double readDouble(String message){
        Double number = null;
        String text;

        do{
            write(message);
            text = sc.nextLine();

            try{
                number = Double.parseDouble(text);
            }catch(NumberFormatException e){
                outputError(text + " não é um número decimal válido.");
            }
        }while(number == null);
    
        return number;
    }

    public static int checkInt(String message){

        Integer number = null;
        String text;

        do{
            write(message);
            text = sc.nextLine();

            try{
                number = Integer.parseInt(text);
             }
             catch(NumberFormatException e){
                outputError(text + " não é um número inteiro válido");
             }
        }while(number == null);

        return number;
    }

    public static int checkOption(String message){

        Integer number = null;
        String text;

        do{
            write(message);
            text = sc.nextLine();

            try{
                number = Integer.parseInt(text);
            }catch(NumberFormatException e){
                outputError(text + " is an invalid option.");
                sc.nextLine();
            }
        }while(number == null);

        return number;
    }

}
