package Frontend.Actions;

import Backend.Albums.Album;
import Backend.Albums.AlbumEditado;
import Backend.Albums.Repos;
import Backend.Sessions.Session;
import Backend.Tracks.Track;
import Backend.Users.Musician;
import Frontend.Utils.Generics;
import Frontend.Utils.Prompt;
import Frontend.Utils.ReposHolder;

import java.rmi.StubNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class ProdutorAction { //Traduzido
    private static Backend.Users.Produtor user;

    public static void setUser(Backend.Users.User user) {
        if (user instanceof Backend.Users.Produtor) {
            ProdutorAction.user = (Backend.Users.Produtor) user;
        } else {
            throw new IllegalArgumentException("Utilizador não é um produtor");
        }
    }

    public static void editProfile() {
        System.out.println("[1] - Editar nome");
        System.out.println("[2] - Editar username");
        System.out.println("[3] - Editar email");
        System.out.println("[4] - Editar password");

        int option = Prompt.checkOption("Introduza a opção: ");

        try {
            switch (option) {
                case 1:
                    String name = Prompt.readString("Nome: ");
                    user.setName(name);
                    break;
                case 2:
                    String surname = Prompt.readString("Novo username: ");
                    user.setUsername(surname); // throws exception
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
                    Prompt.outputError("Opção inválida");
                    break;
            }
        } catch (Exception e) {
            Prompt.outputError(e.getMessage());
        }
    }

    // demasiado grande, tem de ser dividido em vários métodos
    public static void startOrCreateEditingAlbum() {

        System.out.println("1. Começar a editar um álbum");
        System.out.println("2. Editar as informações de um álbum");

        int option = Prompt.checkOption("Introduza a opção: ");

        if (option == 1) { // Criar album

            String albumName = Prompt.readString("Nome do álbum original: ");
            String EditionAlbumName = Prompt.readString("Nome para a edição de álbum: ");
            
            Backend.Albums.AlbumEditado album;

            try {
                user.createAlbumEdit(albumName, EditionAlbumName);
            } catch( ClassNotFoundException | IllegalArgumentException e) {
                Prompt.pressEnterToContinue(e.getMessage());
                return;
            }
            Prompt.pressEnterToContinue("Album" + albumName + "criado com sucesso!");

        } else if (option == 2) { // Editar album

            String albumName = Prompt.readString("Nome do álbum a editar: ");
            Backend.Albums.Album album = ReposHolder.getAlbums().getAlbum(albumName);

            if (!(album instanceof Backend.Albums.AlbumEditado)){
                Prompt.pressEnterToContinue("O álbum selecionado não é editável");
                return;
            } else if (!(user.equals(album.getProdutor()))) {
                Prompt.pressEnterToContinue("Não tem premissão para editar este album! Permissão recusada!");
                return;
            }

            System.out.println("1. Adicionar uma nova sessão de gravação");
            System.out.println("2. Remover uma sessão de gravação");
            System.out.println("3. Adicionar artistas à sessão de gravação");
            System.out.println("4. Remover um artista");
            System.out.println("5. Adicionar uma nova faixa");
            System.out.println("6. Encerrar todas as sessões de gravação anteriores");

            int choice = Prompt.checkInt("Introduza a opção: ");

            switch(choice) {
                case 1: // adicionar nova sessão

                    LocalDateTime dataInicio = Generics.readDate("Hora de inicio da sessão(dd/MM/aaaa HH:mm): ");
                    LocalDateTime dataFim = Generics.readDate("Hora de fim da sessão(dd/MM/aaaa HH:mm): ");

                    try {
                        ((AlbumEditado) album).addSession(dataInicio, dataFim);
                    } catch (IllegalArgumentException e) {
                        Prompt.pressEnterToContinue(e.getMessage());
                        return;
                    }

                    Prompt.pressEnterToContinue("Sessão criada com sucesso!");
                    break;
                case 2: // remover uma sessão de gravação

                    // imprime todas as sessões para que o utilizador depois escolha a que quer remover
                    ((AlbumEditado) album).getAllSessions().forEach((session) -> System.out.println(session));

                    UUID sessionID = UUID.fromString(Prompt.readString("ID da sessão a remover: "));

                    try {
                        ((AlbumEditado) album).removeSession(sessionID);
                    } catch (IllegalArgumentException e) {
                        Prompt.pressEnterToContinue(e.getMessage());
                        return;
                    }

                    Prompt.pressEnterToContinue("Sessão removida com sucesso!");
                    break;
                case 3: // Adicionar um novo artista

                    // imprime todas as sessões para que o utilizador depois escolha a que quer
                    ((AlbumEditado) album).getAllSessions().forEach((session) -> System.out.println(session));

                    // escolher a sessao
                    UUID idSession = UUID.fromString(Prompt.readString("ID da sessão à qual adicionará um artista: "));
                    Session session = ((AlbumEditado) album).getSession(idSession);

                    if(session == null) {
                        Prompt.pressEnterToContinue("Sessão não encontrada");
                        return;
                    }

                    String addMore;
                    System.out.println("Adicionar um artista à sessão " + session + "? (s/n)");
                    addMore = Prompt.readString("Introduza a opção: ");

                    while(addMore.equals("s")) {
                        String artistName = Prompt.readString("Nome do artista: ");
                        Backend.Users.User musician = ReposHolder.getUsers().getUser(artistName);

                        if(musician == null) {
                            Prompt.pressEnterToContinue("Artista não encontrado");
                            return;
                        }
                        if (!(musician instanceof Backend.Users.Musician)) {
                            Prompt.pressEnterToContinue("O utilizador não é um músico");
                            return;
                        }

                        try {
                            session.addInvitedMusician((Backend.Users.Musician) musician);
                        } catch (IllegalArgumentException e) {
                            Prompt.pressEnterToContinue(e.getMessage());
                            return;
                        }

                        System.out.println("Adicionar mais um artista à sessão " + session + "? (s/n)");
                        addMore = Prompt.readString("Introduza a opção: ");
                    }
                    break;
                case 4: // Remover um artista
                    String username = Prompt.readString("Username do artista que pretende remover do album: ");
                    boolean success = ((AlbumEditado)album).removeArtist(username);
                    if (!success) {
                        System.out.println();
                        Prompt.pressEnterToContinue("O sistema não foi capaz de eliminar o username" + username + " introduzido!");
                    }
                    break;
                case 5: // Adicionar uma nova faixa

                    String addMore01 = "s";


                    while(addMore01.equals("s")) {
                        String trackName = Prompt.readString("Nome da faixa: ");
                        String genre = Prompt.readString("Género da faixa: ");

                        // ainda sem verificação
                        int duration = Prompt.checkInt("Duração (minutos:segundos): ");

                        Backend.Tracks.Track newTrack = new Track(album, trackName, genre ,duration);
                        boolean success03 = album.addTrack(newTrack);
                        if (!success03) {
                            Prompt.pressEnterToContinue("O nome da faixa deve ser única dentro do álbum!");
                        }

                        System.out.println("Adicionar outra faixa ao album? (s/n)");
                        addMore01 = Prompt.readString("Introduza a opção: ");
                    }

                    break;
                case 6: // Encerrar sessão de gravação

                    // imprime todas as sessões para que o utilizador depois escolha a que quer
                    ((AlbumEditado) album).getAllSessions().forEach((session01) -> System.out.println(session01));

                    // escolher a sessao
                    UUID idSession01 = UUID.fromString(Prompt.readString("ID da sessão à qual adicionará um artista: "));
                    ((AlbumEditado)album).removeSession(idSession01);
                    break;
                default:
                    System.out.println("Seleção inválida");
            }

        } else {
            System.out.println("Opção inválida");
        }
    }

    public static void endRecordingSession() {
        System.out.println("Terminar sessões de gravação...");
        boolean res = ReposHolder.getSessions().endRecordingSessions();
        if (!res) {
            System.out.println("Nenhuma sessão de gravação foi terminadaNenhuma sessão de gravação foi terminada");
            Generics.sc.nextLine();
        }
    }

    public static void showStateOfAlbum() {
        String albumName = Prompt.readString("Selecione o álbum para examinar: ");
        Backend.Albums.Album album = ReposHolder.getAlbums().getAlbum(albumName);

        if (!(album instanceof AlbumEditado)){
            System.out.println(albumName + " nunca foi editado aqui!");
            Generics.sc.nextLine();
            return;
        }
        if (((Backend.Albums.AlbumEditado) album).isEdited()) {
            System.out.println(albumName + " o álbum está completo! Data de conclusão: "
                    + album.getDate());
        } else {
            System.out.println(albumName + " continua a ser editado.");
            Generics.sc.nextLine();
        }
    }

    public static void showYourAlbums() {
        Set<Album> oldProjects = user.getOldAlbums();
        Set<AlbumEditado> myEditedProjects = user.getProjetos();


        System.out.println("--- Álbuns antigos ---");
        if (oldProjects.isEmpty()) { System.out.println("Sem álbuns...");}
        else {
            for (Album album1: oldProjects){
                System.out.println(album1);
            }
        }

        System.out.println("--- Novos Álbuns ---");
        if(myEditedProjects.isEmpty()) {
            System.out.println("Sem álbuns...");
        } else {
            for (AlbumEditado album1 : myEditedProjects) {
                System.out.println(album1);
            }
        }
        Generics.sc.nextLine();
    }

    public static void showRecordingSessionsOfDay() {
        String day = Prompt.readString("Selecione o dia para inspecionar D: ");
        LocalDateTime dInicio = Frontend.Utils.Generics.stringToDate(day + " 00:00");
        LocalDateTime dFim = Frontend.Utils.Generics.stringToDate(day + " 23:59");
        Backend.Sessions.Session session = user.findSessionByDate(dInicio, dFim);
        System.out.println(session);
    }

    public static void checkData() {
        System.out.println("Nome: " + user.getName());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: " + user.getPassword());
        Generics.sc.nextLine();
    }
}
