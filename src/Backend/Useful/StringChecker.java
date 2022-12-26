package Backend.Useful;

// regex para filtrar strings
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringChecker {
    public static boolean hasNumbers(String str) {
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(str);
        return m.find();
    }
    public static boolean startsWithCaps(String str) {
        Pattern p = Pattern.compile("^[A-Z]");
        Matcher m = p.matcher(str);
        return m.find();
    }
    public static boolean hasSpaces(String str) {
        Pattern p = Pattern.compile("\\s");
        Matcher m = p.matcher(str);
        return m.find();
    }
    public static boolean validName(String str) throws IllegalArgumentException {
        boolean hasNumber = StringChecker.hasNumbers(str);
        boolean startsWithCaps = StringChecker.startsWithCaps(str);
        //boolean hasSpaces = StringChecker.hasSpaces(str);
        if (str == null){
            throw new IllegalArgumentException("Nome a null");
        }
        if (hasNumber) {
            throw new IllegalArgumentException("Nome não pode conter números");
        }
        if (!startsWithCaps) {
            throw new IllegalArgumentException("Nome deve começar com letra maiúscula");
        }
        //if (hasSpaces) {
        //    throw new IllegalArgumentException("Nome não pode conter espaços");
        //}
        return true;
    }
    public static boolean validAddress(String str) throws IllegalArgumentException {
        boolean hasNumber = StringChecker.hasNumbers(str);
        boolean startsWithCaps = StringChecker.startsWithCaps(str);
        boolean hasSpaces = StringChecker.hasSpaces(str);
        if (!hasNumber || !startsWithCaps || !hasSpaces) {
            throw new IllegalArgumentException("Morada inválida: <País> <Nome da Rua> <Número da porta>");
        }
        return true;
    }

    public static boolean validEmail(String str) throws IllegalArgumentException {
        // check for format of email with regex
        Pattern p = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher m = p.matcher(str);
        if (!m.find()) {
            throw new IllegalArgumentException("Email inválido");
        }
        return true;
    }
}
