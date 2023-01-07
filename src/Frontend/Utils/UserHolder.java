package Frontend.Utils;

/**
 * The type User holder.
 */
public class UserHolder {
    private static Backend.Users.User user;

    /**
     * Sets user.
     *
     * @param user the user
     */
    public static void setUser(Backend.Users.User user) {
        UserHolder.user = user;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public static Backend.Users.User getUser() {
        return user;
    }

    /**
     * Is produtor boolean.
     *
     * @return the boolean
     */
    public static boolean isProdutor() {
        return user instanceof Backend.Users.Produtor;
    }

    /**
     * Is musician boolean.
     *
     * @return the boolean
     */
    public static boolean isMusician() {
        return user instanceof Backend.Users.Musician;
    }

    /**
     * Is admin boolean.
     *
     * @return the boolean
     */
    public static boolean isAdmin() {
        return user instanceof Backend.Users.Admin;
    }

    /**
     * Init menu.
     */
// initiate Mprodutor, Mmusico or Madmin
    public static void initMenu() {
        if(null == UserHolder.user) return;
        if (isProdutor()) {
            Frontend.Menus.Mprodutor.mostrarMenu();
            Frontend.Utils.Prompt.cleanPrompt();
        } else if (isMusician()) {
            Frontend.Menus.Mmusico.mostrarMenu();
            Frontend.Utils.Prompt.cleanPrompt();
        } else if (isAdmin()) {
            Frontend.Menus.Madmin.mostrarMenu();
            Frontend.Utils.Prompt.cleanPrompt();
        }
        else {
            ConsoleColors color = new ConsoleColors();
            System.out.println(color.getRED());
            throw new IllegalArgumentException("[!] - ERRO: O utilizador não é produtor, músico ou admin");
        }
    }

}
