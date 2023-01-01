package Frontend.Utils;

public class UserHolder {//Traduzido
    private static Backend.Users.User user;

    public static void setUser(Backend.Users.User user) {
        UserHolder.user = user;
    }

    public static Backend.Users.User getUser() {
        return user;
    }

    public static boolean isProdutor() {
        return user instanceof Backend.Users.Produtor;
    }

    public static boolean isMusician() {
        return user instanceof Backend.Users.Musician;
    }

    public static boolean isAdmin() {
        return user instanceof Backend.Users.Admin;
    }

    // initiate Mprodutor, Mmusico or Madmin
    public static void initMenu() {
        if(user == null) return;
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
            throw new IllegalArgumentException("Utilizador não é Produtor nem Músico ou Admin");
        }
    }

}
