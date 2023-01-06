package Frontend.Utils;

public class Prompt {

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
        }while(null == number);

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
        }while(null == number);

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
        }while(null == number);

        return number;
    }

    // em caso de o utilizador querer voltar atras no programa
    public static boolean goBack(String input) {
        return input.equals("\\");
    }
}
