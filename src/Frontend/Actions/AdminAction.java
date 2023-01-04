package Frontend.Actions;

import Backend.Instruments.Instrument;
import Backend.Instruments.Repos;
import Backend.Sessions.Session;
import Backend.Users.User;
import Frontend.Utils.Generics;
import Frontend.Utils.Prompt;
import Frontend.Utils.ReposHolder;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

public class AdminAction { // TRADUZIDO

    private static Backend.Users.Admin user;

    public static void setUser(Backend.Users.User user) {
        if (user instanceof Backend.Users.Admin) {
            AdminAction.user = (Backend.Users.Admin) user;
        } else {
            throw new IllegalArgumentException("O utilizador não é um produtor");
        }
    }

    public static void addProducer() {

        String name, username, email, password;

        try {
            name = Prompt.readString("Nome: ");
            username = Prompt.readString("Username: ");
            password = Prompt.readString("Password: ");
            email = Prompt.readString("Email: ");
        } catch(Exception e) {
            System.out.println("Input inválido");
            return; // exit
        }


        try {
            user.addProdutor(name, email, username, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Generics.sc.nextLine();
        }
    }

    public static void addMusician() {

        String name, username, email, password;

        try {
            name = Prompt.readString("Nome: ");
            username = Prompt.readString("Username: ");
            password = Prompt.readString("Password: ");
            email = Prompt.readString("Email: ");
        } catch(Exception e) {
            Prompt.outputError("Input inválido");
            return; // exit
        }

        try {
            user.addMusician(name, email, username, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Generics.sc.nextLine();
        }
    }

    public static void removeUser() {

        String username;

        try {
            username = Prompt.readString("Username: ");
        } catch(Exception e) {
            System.out.println("Input inválido");
            return;
        }

        try {
            user.removeUser(username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    
    public static void addInstrument() {

        String name;

        try {
            name = Prompt.readString("Nome do instrumento: ");
        }
        catch(Exception e) {
            Prompt.outputError("Nome inválido");
            return;
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
            System.out.println(e.getMessage());
        }
    }

    public static void showAllSessionRequests() {
        if (user.getAllSessionRequests() == null) {
            System.out.println("Não existem pedidos de sessão");
            return;
        }
        else{
            for(Session s: user.getAllSessionRequests()){
                System.out.println(s);
            }
        } 
            
        System.out.print("Selecione um pedido de sessão: ");
        UUID id = UUID.fromString(Generics.sc.nextLine()); //FALTA ALTERAR
        String answer = Prompt.readString("Aceitar ou rejeitar? (y/n)");
        if (answer.equals("y")) {
            try {
                user.acceptSessionRequest(id);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (answer.equals("n")) {
            try {
                user.rejectSessionRequest(id);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Opção inválido");
        }

    }

    public static void showAllSessions() {
        System.out.println(user.getAllRecordingSessions());
        Generics.sc.nextLine();
    }

    public static void showAllAlbumsBeingEdited() {
        System.out.println(user.getAllAlbumsBeingEdited());
        Generics.sc.nextLine();
    }

    public static void stats() {
        System.out.println(user.getStats());
    }

    public static void showAllUsers() {
        Map<String, User> list = ReposHolder.getUsers().getUsers();
        for (Map.Entry<String, Backend.Users.User> entry : list.entrySet()) {
            System.out.println(entry.getValue().toString());
        }
        Generics.sc.nextLine();
    }

    public static void showAllInstruments() {
        Map<String, Backend.Instruments.Instrument> list2 = ReposHolder.getInstruments().getInstruments();
        for (Map.Entry<String, Backend.Instruments.Instrument> entry : list2.entrySet()) {
            System.out.println(entry.getValue().toString());
        }
        Generics.sc.nextLine();
    }

    public static void showAllAlbums() {
        Map<String, Backend.Albums.Album> list3 = ReposHolder.getAlbums().getAlbums();
        for (Backend.Albums.Album album : list3.values()) {
            System.out.println(album.toString());
        }
        Generics.sc.nextLine();
    }

    public static void checkData() {
        System.out.println("Nome: " + user.getName());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: " + user.getPassword());
        Generics.sc.nextLine();
    }

    public static void addAlbum() {
        String titleOfTheAlbum = Prompt.readString("Nome do album: ");
        String producer = Prompt.readString("Produtor: ");
        String genre = Prompt.readString("Género: ");
        //String d = Prompt.readString("Data de lançamento (dd MM aaaa): ");
        //LocalDateTime date = Generics.stringToDate(d);
        LocalDateTime date = Generics.readDate("Data de lançamento (dd MM aaaa): ");
        
        // verify that the inserted date is valid

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
        while (answer2.equals("y")) {
            answer2 = Prompt.readString("Adicionar uma música album? (y/n)");
            addTrackToAlbum(answer2, titleOfTheAlbum);
        }
    }

    private static void addTrackToAlbum(String ans, String titleOfTheAlbum) {

        Backend.Albums.Album a = user.getAlbumsRepo().getAlbum(titleOfTheAlbum);
        if (ans.equals("y")) {
            String titleOfTheSong = Prompt.readString("Título da música: ");
            int duration = Prompt.checkInt("Duração: ");
            Generics.sc.nextLine(); // flush
            String genre = Prompt.readString("Género: ");
            String musician = Prompt.readString("Músico: ");
            try {
                Backend.Tracks.Track t = new Backend.Tracks.Track(a, titleOfTheSong,
                        genre, duration);
                t.addArtist((Backend.Users.Musician) ReposHolder.getUsers().getUser(musician));
                user.addTrackToAlbum(titleOfTheAlbum, t);

                // add musicians to the track
                String answer3 = "y";
                while (answer3.equals("y")) {
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
        if (ans.equals("y")) {
            System.out.println();
            String musician2 = Prompt.readString("Músico: ");
            t.addArtist((Backend.Users.Musician) ReposHolder.getUsers().getUser(musician2));
        }
    }

    
    public static void showAllInstrumentsRequests(){

        // basta percorrer todas as sessões e mostrar todos os instrumentos pendentes
        // em conjunto com a sessao que os requisitou
        Set<Session> sessions = user.getSessionsRepo().getSessions();

        for(Session s : sessions){
            for (Instrument i : s.getPendentInstruments()){
                System.out.println(s.getId() + ": " + i.toString());
            }
        }

        //
        // apenas auxiliar
        Set<Backend.Instruments.Instrument> pendentInstruments = user.getPendentInstruments();

        if (pendentInstruments.size() == 0) {
            Prompt.outputError("Sem requisições de instrumentos");
            Prompt.pressEnterToContinue();
            return;
        }
        // para mostrar uma mensagem caso não haja instrumentos pendentes
        //

        UUID idSessao = UUID.fromString(Prompt.readString("ID da sessão: "));
        String name = Prompt.readString("Nome do instrumento: ");

        Session s = ReposHolder.getSessions().getSession(idSessao);
        
        String answer = Prompt.readString("Aceitar ou rejeitar? (y/n)");
        if (answer.equals("y")) {
            try {
                user.acceptInstrumentRequest(name, s);
            } catch (Exception e) {
                Prompt.outputError(e.getMessage());
            }
        } else if (answer.equals("n")) {
            try {
                user.denyInstrumentRequest(name, s);
            } catch (Exception e) {
                Prompt.outputError(e.getMessage());
            }
        } else {
            Prompt.outputError("Opção inválida");
        }
    }

    public static void removeAlbum(){
        System.out.println("Em construção...");
    }
}
