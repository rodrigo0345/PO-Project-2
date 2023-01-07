package Frontend.Actions;

import Backend.Instruments.Instrument;
import Backend.Sessions.Session;
import Backend.Users.User;
import Frontend.Utils.ConsoleColors;
import Frontend.Utils.Generics;
import Frontend.Utils.Prompt;
import Frontend.Utils.ReposHolder;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * The type Admin action.
 */
public class AdminAction {

    private static Backend.Users.Admin user;

    /**
     * Sets user.
     *
     * @param user the user
     */
// setUser só é usado para inicializar o user em Madmin
    public static void setUser(Backend.Users.User user) {
        ConsoleColors color = new ConsoleColors();
        if(user == null) {

            System.out.println(color.getRED());
            throw new IllegalArgumentException("[!] - ERRO: O utilizador não pode ser null");
        }
        if (user instanceof Backend.Users.Admin) {
            AdminAction.user = (Backend.Users.Admin) user;
        } else {
            System.out.println(color.getRED());
            throw new IllegalArgumentException("[!] - ERRO: O utilizador não é um produtor");
        }
    }

    /**
     * Add producer.
     */
    public static void addProducer() {

        Frontend.Utils.Generics.menuAdminHeader();
        
        String name = Prompt.readString("Nome: ");
        if(Prompt.goBack(name)) return;

        final String username = Prompt.readString("Username: ");
        if(Prompt.goBack(username)) return;

        String password = Prompt.readString("Password: ");
        if(Prompt.goBack(password)) return;

        final String email = Prompt.readString("Email: ");
        if(Prompt.goBack(email)) return;


        try {
            user.addProdutor(name, email, username, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Generics.sc.nextLine();
        }
    }

    /**
     * Add musician.
     */
    public static void addMusician() {

        Frontend.Utils.Generics.menuAdminHeader();

        String name = Prompt.readString("Nome: ");
        if(Prompt.goBack(name)) return;

        final String username = Prompt.readString("Username: ");
        if(Prompt.goBack(username)) return;

        String password = Prompt.readString("Password: ");
        if(Prompt.goBack(password)) return;

        final String email = Prompt.readString("Email: ");
        if(Prompt.goBack(email)) return;

        try {
            user.addMusician(name, email, username, password);
        } catch (Exception e) {
            Prompt.pressEnterToContinue(e.getMessage());
        }
    }

    /**
     * Remove user.
     */
    public static void removeUser() {

        Frontend.Utils.Generics.menuAdminHeader();

        String username = Prompt.readString("Username: ");
        if(Prompt.goBack(username)) return;

        try {
            user.removeUser(username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Add instrument.
     */
    public static void addInstrument() {
        
        Frontend.Utils.Generics.menuAdminHeader();

        String name = Prompt.readString("Nome do instrumento: ");
        if(Prompt.goBack(name)) return;

        if(name == null || name.isEmpty()) {
            Prompt.pressEnterToContinue("Nome inválido");
        }

        String nameLowerCase = name.toLowerCase();

        try{
            Instrument i = ReposHolder.getInstruments().getInstrument(nameLowerCase);
            if(i == null){
                ReposHolder.getInstruments();
                user.getInstrumentsRepo();
                int quantidade = Prompt.checkInt("Introduza a quantidade: ");
                user.addInstrument(name, quantidade);
            }
            else{
                int quant = Prompt.checkInt("Introduza a quantidade a adicionar: ");
                user.addQuantityToInstrument(name, quant);
            }
        }catch(Exception e){
            Prompt.pressEnterToContinue(e.getMessage());
        }
    }

    /**
     * Show all session requests.
     */
    public static void showAllSessionRequests() {

        Frontend.Utils.Generics.menuAdminHeader();

        if (null == AdminAction.user.getAllSessionRequests()) {
            Prompt.pressEnterToContinue("Não existem pedidos de sessão");
        }
        else{
            for(Session s: user.getAllSessionRequests()){
                System.out.println(s);
            }
        } 
            
        System.out.print("Selecione um pedido de sessão: ");
        UUID id = null;
        try{
            id = UUID.fromString(Generics.sc.nextLine());
        } catch (IllegalArgumentException e) {
            Prompt.pressEnterToContinue("ID inválido");
        }

        String answer = Prompt.readString("Aceitar ou rejeitar? (y/n)");
        if(Prompt.goBack(answer)) return;

        if ("y".equals(answer)) {
            try {
                user.acceptSessionRequest(id);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if ("n".equals(answer)) {
            try {
                user.rejectSessionRequest(id);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Opção inválido");
        }

    }

    /**
     * Show all sessions.
     */
    public static void showAllSessions() {

        Frontend.Utils.Generics.menuAdminHeader();

        System.out.println(user.getAllRecordingSessions());
        Prompt.pressEnterToContinue();
    }

    /**
     * Show all albums being edited.
     */
    public static void showAllAlbumsBeingEdited() {
        
        Frontend.Utils.Generics.menuAdminHeader();
        
        System.out.println(user.getAllAlbumsBeingEdited());
        Prompt.pressEnterToContinue();
    }

    /**
     * Stats.
     */
    public static void stats() {
        
        Frontend.Utils.Generics.menuAdminHeader();

        System.out.println("1. Estatisticas globais");
        System.out.println("2. Estatisticas de um certo periodo de tempo");

        int option = Prompt.checkInt("Opção: ");
        if (option == 1){
            System.out.println(user.getStats());
            Prompt.pressEnterToContinue();
        }
        else if(option == 2) {
            LocalDateTime start = Generics.readDate("Data de inicio (dd/MM/yyyy HH:mm): ");
            LocalDateTime end = Generics.readDate("Data de fim (dd/MM/yyyy HH:mm): ");
            System.out.println(user.getStats(start, end));
            Prompt.pressEnterToContinue();
        }
        else {
            Prompt.pressEnterToContinue("Opção inválida");
        }
    }

    /**
     * Show all users.
     */
    public static void showAllUsers() {
        
        Frontend.Utils.Generics.menuAdminHeader();

        Map<String, User> list = ReposHolder.getUsers().getUsers();
        for (Map.Entry<String, Backend.Users.User> entry : list.entrySet()) {
            System.out.println(entry.getValue().toString());
        }
        Prompt.pressEnterToContinue();
    }

    /**
     * Show all instruments.
     */
    public static void showAllInstruments() {
        
        Frontend.Utils.Generics.menuAdminHeader();

        Map<String, Backend.Instruments.Instrument> list2 = ReposHolder.getInstruments().getInstruments();
        for (Map.Entry<String, Backend.Instruments.Instrument> entry : list2.entrySet()) {
            System.out.println(entry.getValue().toString());
        }
        Prompt.pressEnterToContinue();
    }

    /**
     * Show all albums.
     */
    public static void showAllAlbums() {
        
        Frontend.Utils.Generics.menuAdminHeader();

        Map<String, Backend.Albums.Album> list3 = ReposHolder.getAlbums().getAlbums();
        for (Backend.Albums.Album album : list3.values()) {
            System.out.println(album.toString());
        }
        Prompt.pressEnterToContinue();
    }

    /**
     * Check data.
     */
    public static void checkData() {
        
        Frontend.Utils.Generics.menuAdminHeader();

        System.out.println("Nome: " + user.getName());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: " + user.getPassword());
        Prompt.pressEnterToContinue();
    }

    /**
     * Add album.
     */
    public static void addAlbum() {
        
        Frontend.Utils.Generics.menuAdminHeader();

        String titleOfTheAlbum = Prompt.readString("Nome do album: ");
        if(Prompt.goBack(titleOfTheAlbum)) return;

        String producer = Prompt.readString("Produtor: ");
        if(Prompt.goBack(producer)) return;

        String genre = Prompt.readString("Género: ");
        if(Prompt.goBack(genre)) return;

        LocalDateTime date = Generics.readDate("Data de lançamento (dd MM aaaa): ");

        // verify that the inserted producer is valid
        try {
            Backend.Users.User aux = ReposHolder.getUsers().getUser(producer);
            if (aux instanceof Backend.Users.Produtor prod) {
                user.addAlbum(titleOfTheAlbum, genre, date, prod);
            } else {
                System.out.println("Produtor inválido");
                Generics.sc.nextLine();
                return;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Generics.sc.nextLine();
            return;
        }

        // adicionar musicas ao album
        String answer2 = "y";
        while ("y".equals(answer2)) {
            answer2 = Prompt.readString("Adicionar uma música album? (y/n)");
            addTrackToAlbum(answer2, titleOfTheAlbum);
        }
    }

    private static void addTrackToAlbum(String ans, String titleOfTheAlbum) {
        
        Frontend.Utils.Generics.menuAdminHeader();

        Backend.Albums.Album a = user.getAlbumsRepo().getAlbum(titleOfTheAlbum);
        if ("y".equals(ans)) {
            String titleOfTheSong = Prompt.readString("Título da música: ");
            if(Prompt.goBack(titleOfTheSong)) return;

            int duration = Prompt.checkInt("Duração: ");

            String genre = Prompt.readString("Género: ");
            if(Prompt.goBack(genre)) return;

            String musician = Prompt.readString("Músico: ");
            if(Prompt.goBack(musician)) return;

            try {
                Backend.Tracks.Track t = new Backend.Tracks.Track(a, titleOfTheSong,
                        genre, duration);
                t.addArtist((Backend.Users.Musician) ReposHolder.getUsers().getUser(musician));
                user.addTrackToAlbum(titleOfTheAlbum, t);

                // add musicians to the track
                String answer3 = "y";
                while ("y".equals(answer3)) {
                    answer3 = Prompt.readString("Adicionar um novo músico à faixa? (y/n)");
                    addArtistToTrack(answer3, t);
                }

                user.addTrackToAlbum(titleOfTheAlbum, t);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void addArtistToTrack(String ans, Backend.Tracks.Track t) {
        
        Frontend.Utils.Generics.menuAdminHeader();

        if ("y".equals(ans)) {
            // mostrar todos os musicos
            for(Backend.Users.User u : ReposHolder.getUsers().getUsers().values()) {
                if(u instanceof Backend.Users.Musician) {
                    System.out.println(u.toString());
                }
            }

            String musician2 = Prompt.readString("Músico: ");
            if(Prompt.goBack(musician2)) return;

            t.addArtist((Backend.Users.Musician) ReposHolder.getUsers().getUser(musician2));
        }
    }


    /**
     * Show all instruments requests.
     */
    public static void showAllInstrumentsRequests(){
        
        Frontend.Utils.Generics.menuAdminHeader();

        // basta percorrer todas as sessões e mostrar todos os instrumentos pendentes
        // em conjunto com a sessao que os requisitou
        Set<Session> sessions = user.getSessionsRepo().getSessions();

        for(Session s : sessions){
            for (Instrument i : s.getPendentInstruments()){
                System.out.println(s.getId() + ": " + i.toString() + " Data Requisição = " + i.getDataRequisicao());
            }
        }

        //
        // apenas auxiliar
        Set<Backend.Instruments.Instrument> pendentInstruments = user.getPendentInstruments();

        if (0 == pendentInstruments.size()) {
            Prompt.pressEnterToContinue("Sem requisições de instrumentos");
            return;
        }
        // para mostrar uma mensagem caso não haja instrumentos pendentes
        //

        UUID idSessao = null;
        try {
            idSessao = UUID.fromString(Prompt.readString("ID da sessão: "));
        } catch( IllegalArgumentException e ){
            Prompt.pressEnterToContinue("ID inválido");
            return;
        }

        String name = Prompt.readString("Nome do instrumento: ");
        if(Prompt.goBack(name)) return;

        Session s = ReposHolder.getSessions().getSession(idSessao);
        if(s == null){
            Prompt.pressEnterToContinue("Sessão inválida");
            return;
        }
        
        String answer = Prompt.readString("Aceitar ou rejeitar? (y/n)");
        if(Prompt.goBack(answer)) return;

        if ("y".equals(answer)) {
            try {
                user.acceptInstrumentRequest(name, s);
            } catch (Exception e) {
                Prompt.outputError(e.getMessage());
            }
        } else if ("n".equals(answer)) {
            try {
                user.denyInstrumentRequest(name, s);
            } catch (Exception e) {
                Prompt.outputError(e.getMessage());
            }
        } else {
            Prompt.outputError("Opção inválida");
        }
    }

    /**
     * Remove album.
     */
    public static void removeAlbum() {
        
        Frontend.Utils.Generics.menuAdminHeader();
        
        System.out.println("Em construção...");
    }
}
