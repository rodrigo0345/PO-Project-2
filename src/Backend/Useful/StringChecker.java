package Backend.Useful;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type String checker.
 */
public class StringChecker {
    /**
     * Has numbers boolean.
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean hasNumbers(String str) {
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * Starts with caps boolean.
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean startsWithCaps(String str) {
        Pattern p = Pattern.compile("^[A-Z]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * Has spaces boolean.
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean hasSpaces(String str) {
        Pattern p = Pattern.compile("\\s");
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * Valid name boolean.
     *
     * @param str the str
     * @return the boolean
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static boolean validName(String str) throws IllegalArgumentException {
        boolean hasNumber = StringChecker.hasNumbers(str);
        boolean startsWithCaps = StringChecker.startsWithCaps(str);
        //boolean hasSpaces = StringChecker.hasSpaces(str);
        if (null == str){
            throw new IllegalArgumentException("Nome a null");
        }
        if (hasNumber) {
            throw new IllegalArgumentException("Nome não pode conter números");
        }
        if (!startsWithCaps) {
            throw new IllegalArgumentException("Nome deve começar com letra maiúscula");
        }
        return true;
    }

    /**
     * Valid address boolean.
     *
     * @param str the str
     * @return the boolean
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static boolean validAddress(String str) throws IllegalArgumentException {
        boolean hasNumber = StringChecker.hasNumbers(str);
        boolean startsWithCaps = StringChecker.startsWithCaps(str);
        boolean hasSpaces = StringChecker.hasSpaces(str);
        if (!hasNumber || !startsWithCaps || !hasSpaces) {
            throw new IllegalArgumentException("Morada inválida: <País> <Nome da Rua> <Número da porta>");
        }
        return true;
    }

    /**
     * Valid email boolean.
     *
     * @param str the str
     * @return the boolean
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static boolean validEmail(String str) throws IllegalArgumentException {
        // check for format of email with regex
        Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = p.matcher(str);
        if (!m.find()) {
            throw new IllegalArgumentException("Email inválido");
        }
        return true;
    }
}
