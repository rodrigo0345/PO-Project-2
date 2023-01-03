package Frontend.Actions;

import Backend.Albums.Album;
import Backend.Instruments.Instrument;
import Backend.Instruments.Repos;
import Backend.Sessions.Session;
import Frontend.Utils.Generics;
import Frontend.Utils.Prompt;
import Frontend.Utils.ReposHolder;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MusicianAction { //TRADUZIDO

        private static Backend.Users.Musician user;

        public static void setUser(Backend.Users.User user) {
            if (user instanceof Backend.Users.Musician) {
                MusicianAction.user = (Backend.Users.Musician) user;
            } else {
                throw new IllegalArgumentException("O utilizador não é um músico");
            }
        }

    public static void editProfile() {
        System.out.println("[1] - Editar nome");
        System.out.println("[2] - Editar username");
        System.out.println("[3] - Editar email");
        System.out.println("[4] - Editar password");
        System.out.println("[5] - Retroceder");

        try {
            int option = Prompt.checkOption("Introduza a opção: ");

            switch (option) {
                case 1:
                    String name = Prompt.readString("Novo nome: ");
                    user.setName(name);
                    break;
                case 2:
                    String surname = Prompt.readString("Novo username: ");
                    user.setUsername(surname);
                    break;
                case 3:
                    String email = Prompt.readString("Novo email: ");
                    user.setEmail(email);
                    break;
                case 4:
                    String password = Prompt.readString("Nova password: ");
                    user.setPassword(password);
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Opção inválida");
        }
    }

    public static void showAssociatedAlbums() {
        Set<Album> aux = user.getAlbums();

        for (Backend.Albums.Album album : aux) {
            if (album instanceof Backend.Albums.Album) {
                System.out.println(album);
            }
        }
        Generics.sc.nextLine();
    }

    public static void showFutureRecordingSessions() {
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

    public static void requestInstrumentForSession() {
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

        if (!relatedSessions.contains(selectedSession)) {
            Prompt.pressEnterToContinue("Id inválido");
            return;
        }

        // show available instruments
        for(Instrument i: ReposHolder.getInstruments().getInstruments().values()){
            System.out.println(i);
        }
        
        String instrumentName = Prompt.readString("Nome do instrumento: ");
        Instrument instrument = ReposHolder.getInstruments().getInstrument(instrumentName.toLowerCase());

        if (instrument == null) {
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
                    else if(quantidadeRequisitar<0){
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
            user.requestInstrument(instrument, selectedSession, quantidadeRequisitar);
        } catch (Exception e) {
            Prompt.pressEnterToContinue(e.getMessage());
        }

    }

    public static void showStatOfAllRecordingSessions() {
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

    public static void checkData() {
        System.out.println("Nome: " + user.getName());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: " + user.getPassword());
        //falta a lista de instrumentos que cada músico toca
        Generics.sc.nextLine();
    }
}
