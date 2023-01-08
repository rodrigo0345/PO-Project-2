package Frontend.Actions;
import Backend.Albums.Album;
import Backend.Instruments.Instrument;
import Backend.Sessions.Session;
import Frontend.Utils.ConsoleColors;
import Frontend.Utils.Generics;
import Frontend.Utils.Prompt;
import Frontend.Utils.ReposHolder;
import java.util.Set;
import java.util.UUID;

/**
 * The type Musician action.
 */
public class MusicianAction {

    private static Backend.Users.Musician user;

    /**
     * Sets user.
     *
     * @param user the user
     */
// setUser só é usado para inicializar o user em Mmusico
    public static void setUser(Backend.Users.User user) {
        if (user instanceof Backend.Users.Musician) {
            MusicianAction.user = (Backend.Users.Musician) user;
        } else {
            throw new IllegalArgumentException("O utilizador não é um músico");
        }
    }


    /**
     * Edit profile.
     */
// permite ao utilizador mudar variaveis como nome, email, password, etc
    public static void editProfile() {

        Frontend.Utils.Generics.menuMusicianHeader();
        ConsoleColors color = new ConsoleColors();
        
        System.out.println(color.getYELLOW());
        System.out.print("[1] -");
        System.out.print(color.getWHITE());
        System.out.print(" Editar nome");
        System.out.println(color.getYELLOW());
        System.out.print("[2] -");
        System.out.print(color.getWHITE());
        System.out.print(" Editar username");
        System.out.println(color.getYELLOW());
        System.out.print("[3] - ");
        System.out.print(color.getWHITE());
        System.out.print("Editar email");
        System.out.println(color.getYELLOW());
        System.out.print("[4] -");
        System.out.print(color.getWHITE());
        System.out.print(" Editar password");
        System.out.println(color.getYELLOW());
        System.out.print("[5] - ");
        System.out.print(color.getWHITE());
        System.out.println("Retroceder");

        try {
            int option = Prompt.checkOption("Introduza a opção: ");

            switch (option) {
                case 1:
                    String name = Prompt.readString("Novo nome: ");
                    if(Prompt.goBack(name)) return;

                    user.setName(name);
                    break;
                case 2:
                    String surname = Prompt.readString("Novo username: ");
                    if(Prompt.goBack(surname)) return;

                    user.setUsername(surname);
                    break;
                case 3:
                    String email = Prompt.readString("Novo email: ");
                    if(Prompt.goBack(email)) return;

                    user.setEmail(email);
                    break;
                case 4:
                    String password = Prompt.readString("Nova password: ");
                    if(Prompt.goBack(password)) return;

                    user.setPassword(password);
                    break;
                case 5:
                    return;                   
                default:
                    Prompt.pressEnterToContinue("Opção inválida");
                    break;
            }
        } catch (Exception e) {
            Prompt.pressEnterToContinue(e.getMessage());
        }
    }


    /**
     * Show associated albums.
     */
// Usado para mostrar ao utilizador os albums em que já participou
    public static void showAssociatedAlbums() {
        Frontend.Utils.Generics.menuMusicianHeader();
        Set<Album> aux = user.getAlbums();

        for (Backend.Albums.Album album : aux) {
            if (album instanceof Backend.Albums.Album) {
                System.out.println(album);
            }
        }

        Prompt.pressEnterToContinue();
    }

    /**
     * Show future recording sessions.
     */
    public static void showFutureRecordingSessions() {

        Frontend.Utils.Generics.menuMusicianHeader();
        // get all the sessions with the musician in it
        Set<Session> relatedSessions = ReposHolder.getSessions().getMusicianSessions(user);

        if (relatedSessions.isEmpty()) {
            Prompt.pressEnterToContinue("Não tem sessões futuras");
            return;
        }
        for (Session s: relatedSessions){
            System.out.println(s);
        }

        Prompt.pressEnterToContinue();
    }

    /**
     * Request instrument for session.
     */
    public static void requestInstrumentForSession() {

        Frontend.Utils.Generics.menuMusicianHeader();

        Set<Session> relatedSessions = ReposHolder.getSessions().getMusicianSessions(user);

        if (relatedSessions.isEmpty()) {
            Prompt.pressEnterToContinue("Não tem sessões futuras");
            return;
        }
        for (Session s: relatedSessions){
            System.out.println(s);
        }

        UUID idSessao = null;
        Session selectedSession;
        try {
            idSessao = UUID.fromString(Frontend.Utils.Prompt.readString("Id da sessão: "));
            selectedSession = ReposHolder.getSessions().getSession(idSessao);
        } catch (Exception e) {
            Prompt.pressEnterToContinue("Id inválido");
            return; 
        }

        if (selectedSession == null) {
            Prompt.pressEnterToContinue("Sessão não encontrada");
            return;
        }

        if (!relatedSessions.contains(selectedSession)) {
            Prompt.pressEnterToContinue("Id inválido");
            return;
        }

        // show available instruments
        for(Instrument i: ReposHolder.getInstruments().getInstruments().values()){
            System.out.println(i);
        }
        
        String instrumentName = Prompt.readString("Nome do instrumento: ");
        if(Prompt.goBack(instrumentName)) return;

        if(instrumentName == null || instrumentName.isEmpty()){
            Prompt.pressEnterToContinue("Nome inválido");
            return;
        }

        Instrument instrument = ReposHolder.getInstruments().getInstrument(instrumentName.toLowerCase());

        if (null == instrument) {
            Prompt.pressEnterToContinue("Instrumento não existe");
            return;
        }

        int quantidadeRequisitar = Prompt.checkInt("Quantidade a requisitar ");
        
        for(Session s: ReposHolder.getSessions().getSessions()){
            for(Instrument i : s.getApprovedInstruments()){
                if(i.getName().equalsIgnoreCase(instrumentName)){
                    if(i.getQuantidade() > quantidadeRequisitar){
                        System.out.println("Quantidade indisponivel");
                    }
                    else if(0 > quantidadeRequisitar){
                        System.out.println("A quantidade deverá ser maior que 0");
                    }
                }
            }
        }


        // verifica se ainda existem instrumentos disponiveis numa dada altura
        int sumQuantidadeRequisitar = 0;
        for(Session s: ReposHolder.getSessions().getSessions()){
            if(s.doesSessionOverlap(selectedSession)){

                // find the same instrument in this session
                for(Instrument i : s.getApprovedInstruments()){
                    if(i.getName().equalsIgnoreCase(instrumentName)){
                        sumQuantidadeRequisitar += i.getQuantidade();
                    }
                }
                for(Instrument i : s.getPendentInstruments()){
                    if(i.getName().equalsIgnoreCase(instrumentName)){
                        sumQuantidadeRequisitar += i.getQuantidade();
                    }
                }
            }
        }

        if (sumQuantidadeRequisitar + quantidadeRequisitar > instrument.getQuantidade()) {
            Prompt.pressEnterToContinue("Quantidade indisponivel, apenas estão disponiveis " + (instrument.getQuantidade() - sumQuantidadeRequisitar));
            return;
        }

        // the admin will then be able to accept or deny the request

        try{
            user.requestInstrumentForSession(instrument, selectedSession, quantidadeRequisitar);
        } catch (Exception e) {
            Prompt.pressEnterToContinue(e.getMessage());
        }

    }

    /**
     * Show stat of all recording sessions.
     */
    public static void showStatOfAllRecordingSessions() {

        Frontend.Utils.Generics.menuMusicianHeader();

        System.out.println("Estado de todas as sessões de gravação");
        for(Session s: ReposHolder.getSessions().getPendingSessions()){
            System.out.println("Pendente: " + s);
        }

        for(Session s: ReposHolder.getSessions().getSessions()){
            if (!s.isCompleted()){
                System.out.println("Aceite: " + s);
            }
            else {
                System.out.println("Concluído: " + s);
            }
        }
        Generics.sc.nextLine();
    }

    /**
     * Check data.
     */
    public static void checkData() {

        Frontend.Utils.Generics.menuMusicianHeader();
        ConsoleColors color = new ConsoleColors();

        System.out.print(color.getYELLOW());
        System.out.print("Nome: ");
        System.out.print(color.getWHITE());
        System.out.print(""+user.getName());
        System.out.println(color.getYELLOW());
        System.out.print("Username: " );
        System.out.print(color.getWHITE());
        System.out.print(""+ user.getUsername());
        System.out.println(color.getYELLOW());
        System.out.print("Email: " );
        System.out.print(color.getWHITE());
        System.out.print(""+ user.getEmail());
        System.out.println(color.getYELLOW());
        System.out.print("Password: " );
        System.out.print(color.getWHITE());
        System.out.print(""+ user.getPassword());
        Prompt.pressEnterToContinue();

        //falta a lista de instrumentos que cada músico toca
        Prompt.pressEnterToContinue();
    }
}
