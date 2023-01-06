package Frontend.Utils;

/**
 * The type Prompt.
 */
public class Prompt {

    /**
     * Press enter to continue.
     */
    public static void pressEnterToContinue() {
        System.out.println("Pressione ENTER para continuar...");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Press enter to continue.
     *
     * @param message the message
     */
    public static void pressEnterToContinue(String message) {
        System.out.println(message);
        pressEnterToContinue();
    }

    /**
     * Clean prompt.
     */
    public static void cleanPrompt() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Output error.
     *
     * @param message the message
     */
    public static void outputError(String message) {
        System.err.println(message);
    }

    private static void write(String message) {
        System.out.print(message);
    }

    /**
     * Read string string.
     *
     * @param message the message
     * @return the string
     */
    public static String readString(String message){
        write(message);
        return Generics.sc.nextLine();
    }

    /**
     * Read double double.
     *
     * @param message the message
     * @return the double
     */
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

    /**
     * Check int int.
     *
     * @param message the message
     * @return the int
     */
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

    /**
     * Check option int.
     *
     * @param message the message
     * @return the int
     */
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

    /**
     * Go back boolean.
     *
     * @param input the input
     * @return the boolean
     */
// em caso de o utilizador querer voltar atras no programa
    public static boolean goBack(String input) {
        return input.equals("\\");
    }
}
