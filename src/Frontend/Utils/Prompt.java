package Frontend.Utils;

import java.util.Scanner;
import Frontend.Utils.Generics.*;

public class Prompt {//Traduzido

    public static void pressEnterToContinue() {
        System.out.println("Pressione ENTER para continuar...");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pressEnterToContinue(String message) {
        System.out.println(message);
        pressEnterToContinue();
    }

    public static void cleanPrompt() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void outputError(String message) {
        System.err.println(message);
    }

    private static void write(String message) {
        System.out.print(message);
    }

    public static String readString(String message){
        write(message);
        return Generics.sc.nextLine();
    }

    public static Double readDouble(String message){
        Double number = null;
        String text;

        do{
            write(message);
            text = Generics.sc.nextLine();

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
            text = Generics.sc.nextLine();

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
            text = Generics.sc.nextLine();

            try{
                number = Integer.parseInt(text);
            }catch(NumberFormatException e){
                outputError(text + " não é uma opção válida.");
                Generics.sc.nextLine();
            }
        }while(number == null);

        return number;
    }
}
