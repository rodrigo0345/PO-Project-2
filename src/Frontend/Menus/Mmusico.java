package Frontend.Menus;
import Frontend.Utils.Generics;
import Frontend.Utils.Prompt;

/**
 * The type Mmusico.
 */
public class Mmusico{
    private static int option;

    /**
     * Mostrar menu.
     */
    public static void mostrarMenu() {

        Frontend.Utils.Generics.menuMusicianHeader();
        Frontend.Utils.Generics.menuMusicianContent();
 
        option = Prompt.checkOption("[?] - Introduza a opção: ");

        if(7 == Mmusico.option || 8 == Mmusico.option) {
            Frontend.Utils.UserHolder.setUser(null);

            if(8 == Mmusico.option) {
                Generics.setExit(true);
            }

            return; // log out
        }

        executeOption();
    }

    /**
     * Execute option.
     */
    public static void executeOption() {

        Frontend.Actions.MusicianAction.setUser(Frontend.Utils.UserHolder.getUser());

        switch (option) {
            case 1:
                Frontend.Actions.MusicianAction.editProfile();
                break;
            case 2:
                Frontend.Actions.MusicianAction.showAssociatedAlbums();
                break;
            case 3:
                Frontend.Actions.MusicianAction.showFutureRecordingSessions();
                break;
            case 4:
                Frontend.Actions.MusicianAction.requestInstrumentForSession();
                break;
            case 5:
                Frontend.Actions.MusicianAction.showStatOfAllRecordingSessions();
                break;
            case 6:
                Frontend.Actions.MusicianAction.checkData();
                break;
            default:
                Frontend.Utils.Prompt.outputError("Opção inválida!");
                break;
        }
    }
}