package Frontend.Utils;

/**
 * The type Prompt.
 */
public class Prompt {

    /**
     * Press enter to continue.
     */

    public static void pressEnterToContinue() {
        ConsoleColors color = new ConsoleColors();
        System.out.println(color.getGREEN());
        System.out.println("Pressione ENTER para continuar");
        System.out.println(color.getWHITE());
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
        ConsoleColors color = new ConsoleColors();

        System.out.println(color.getRED());
        System.out.println(message);
        System.out.println(color.getWHITE());
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
        ConsoleColors color = new ConsoleColors();
        System.out.print(color.getRED());
        System.err.println(message);
        System.out.print(color.getWHITE());
    }

    private static void write(String message) {
        ConsoleColors color = new ConsoleColors();
        System.out.print(color.getYELLOW());
        System.out.print(message);
    }

    /**
     * Read string string.
     *
     * @param message the message
     * @return the string
     */
    public static String readString(String message){
        ConsoleColors color = new ConsoleColors();
        System.out.print(color.getYELLOW());
        write(message);
        System.out.print(color.getWHITE());
        return Generics.sc.nextLine();
    }

    /**
     * Read double double.
     *
     * @param message the message
     * @return the double
     */
    public static Double readDouble(String message){
        ConsoleColors color = new ConsoleColors();
        Double number = null;
        String text;

        do{
            write(message);
            System.out.println(color.getYELLOW());
            text = Generics.sc.nextLine();
            System.out.println(color.getWHITE());

            try{
                number = Double.parseDouble(text);
            }catch(NumberFormatException e){
                System.out.println(color.getRED());
                outputError("[!] - ERRO: " + text +" não é um decimal válido");
                System.out.println(color.getWHITE());
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
        ConsoleColors color = new ConsoleColors();
        Integer number = null;
        String text;

        do{
            System.out.println(color.getYELLOW());
            write(message);
            System.out.println(color.getWHITE());
            text = Generics.sc.nextLine();

            try{
                number = Integer.parseInt(text);
            }
            catch(NumberFormatException e){
                System.out.println(color.getRED());
                outputError(" [!] - ERRO: " + text +" não é um inteiro válido");
                System.out.println(color.getWHITE());
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
        ConsoleColors color = new ConsoleColors();
        Integer number = null;
        String text;

        do{
            System.out.println(color.getGREEN());
            write(message);
            System.out.print(color.getWHITE());
            text = Generics.sc.nextLine();

            try{
                number = Integer.parseInt(text);
            }catch(NumberFormatException e){
                System.out.println(color.getRED());
                outputError("[!] - ERRO: " + text +" não é um algarismo válido");
                System.out.println(color.getWHITE());
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
