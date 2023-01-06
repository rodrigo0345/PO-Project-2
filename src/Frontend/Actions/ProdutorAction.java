package Frontend.Actions;

import Backend.Albums.Album;
import Backend.Albums.AlbumEditado;
import Backend.Sessions.Session;
import Backend.Tracks.Track;
import Frontend.Utils.Generics;
import Frontend.Utils.Prompt;
import Frontend.Utils.ReposHolder;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class ProdutorAction {
    private static Backend.Users.Produtor user;

    // setUser só é usado para inicializar o user em Mprodutor
    public static void setUser(Backend.Users.User user) {
        if (user instanceof Backend.Users.Produtor) {
            ProdutorAction.user = (Backend.Users.Produtor) user;
        } else {
            throw new IllegalArgumentException("Utilizador não é um produtor");
        }
    }

    // permite ao utilizador mudar variaveis como nome, email, password, etc
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
                    if (Prompt.goBack(name)) return;

                    user.setName(name);
                    break;
                case 2:
                    String surname = Prompt.readString("Novo username: ");
                    if (Prompt.goBack(surname)) return;

                    user.setUsername(surname); // throws exception
                    break;
                case 3:
                    String email = Prompt.readString("Novo email: ");
                    if (Prompt.goBack(email)) return;

                    user.setEmail(email);
                    break;
                case 4:
                    String password = Prompt.readString("Nova password: ");
                    if (Prompt.goBack(password)) return;

                    user.setPassword(password);
                    break;
                default:
                    Prompt.pressEnterToContinue("Opção inválida");
                    break;
            }
        } catch (Exception e) {
            Prompt.pressEnterToContinue(e.getMessage());
        }
    }

    // permite ao utilizador criar um album e ainda editar os seus albums
    public static void startOrCreateEditingAlbum() {

        System.out.println("1. Começar a editar um álbum");
        System.out.println("2. Editar as informações de um álbum");

        int option = Prompt.checkOption("Introduza a opção: ");

        if (1 == option) { // Criar album

            String albumName = Prompt.readString("Nome do álbum original: ");
            if (Prompt.goBack(albumName)) return;

            if(albumName == null || albumName.isEmpty()) {
                Prompt.pressEnterToContinue("Nome do álbum inválido");
                return;
            }

            String EditionAlbumName = Prompt.readString("Nome para a edição de álbum: ");
            if (Prompt.goBack(EditionAlbumName)) return;

            if(EditionAlbumName == null || EditionAlbumName.isEmpty()) {
                Prompt.pressEnterToContinue("Nome para a edição álbum inválido");
                return;
            }
            
            Backend.Albums.AlbumEditado album;

            try {
                user.createNewAlbumEdit(albumName, EditionAlbumName);
            } catch( ClassNotFoundException | IllegalArgumentException e) {
                Prompt.pressEnterToContinue(e.getMessage());
                return;
            }
            Prompt.pressEnterToContinue("Album" + albumName + "criado com sucesso!");

        } else if (2 == option) { // Editar album

            String albumName = Prompt.readString("Nome do álbum a editar: ");
            if (Prompt.goBack(albumName)) return;

            Backend.Albums.Album album = ReposHolder.getAlbums().getAlbum(albumName);
            if(album == null) {
                Prompt.pressEnterToContinue("Album não existe");
                return;
            }

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
            System.out.println("6. Encerrar a edição do álbum");
            System.out.println("7. Concluir sessão de gravação");

            int choice = Prompt.checkInt("Introduza a opção: ");

            switch(choice) {
                case 1: // adicionar nova sessão

                    LocalDateTime dataInicio = Generics.readDate("Hora de inicio da sessão(dd/MM/aaaa HH:mm): ");
                    LocalDateTime dataFim = Generics.readDate("Hora de fim da sessão(dd/MM/aaaa HH:mm): ");

                    if(dataInicio == null || dataFim == null) {
                        Prompt.pressEnterToContinue("Data inválida");
                        return;
                    }

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

                    if (sessionID == null) {
                        Prompt.pressEnterToContinue("ID inválido");
                        return;
                    }

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
                    if (idSession == null) {
                        Prompt.pressEnterToContinue("ID inválido");
                        return;
                    }
                    Session session = ((AlbumEditado) album).getSession(idSession);

                    if(null == session) {
                        Prompt.pressEnterToContinue("Sessão não encontrada");
                        return;
                    }

                    System.out.println("Adicionar um artista à sessão " + session + "? (s/n)");
                    String addMore = Prompt.readString("Introduza a opção: ");
                    if (Prompt.goBack(addMore)) return;

                    while("s".equals(addMore)) {
                        String artistName = Prompt.readString("Nome do artista: ");
                        if (Prompt.goBack(artistName)) return;

                        Backend.Users.User musician = ReposHolder.getUsers().getUser(artistName);
                        if(null == musician) {
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
                        if (Prompt.goBack(addMore)) return;
                    }
                    break;
                case 4: // Remover um artista
                    String username = Prompt.readString("Username do artista que pretende remover do album: ");
                    if(Prompt.goBack(username)) return;

                    boolean success = album.removeArtist(username);
                    if (!success) {
                        System.out.println();
                        Prompt.pressEnterToContinue("O sistema não foi capaz de eliminar o username: " + username + " introduzido!");
                    }
                    break;
                case 5: // Adicionar uma nova faixa

                    String addMore01 = "s";

                    while("s".equals(addMore01)) {
                        String trackName = Prompt.readString("Nome da faixa: ");
                        if (Prompt.goBack(trackName)) return;

                        String genre = Prompt.readString("Género da faixa: ");
                        if (Prompt.goBack(genre)) return;

                        int duration = Prompt.checkInt("Duração (segundos): ");

                        Backend.Tracks.Track newTrack = new Track(album, trackName, genre ,duration);
                        if(null == newTrack) {
                            Prompt.pressEnterToContinue("Faixa não criada");
                            return;
                        }

                        boolean success03 = album.addTrack(newTrack);
                        if (!success03) {
                            Prompt.pressEnterToContinue("O nome da faixa deve ser única dentro do álbum!");
                        }

                        System.out.println("Adicionar outra faixa ao album? (s/n)");
                        addMore01 = Prompt.readString("Introduza a opção: ");
                        if (Prompt.goBack(addMore01)) return;
                    }

                    break;
                case 6: // Encerrar sessão de gravação

                    // imprime todas as sessões para que o utilizador depois escolha a que quer
                    ((AlbumEditado) album).setAlbumAsComplete();

                    Prompt.pressEnterToContinue("Álbum encerrado com sucesso!");
                    break;

                case 7: // Concluir sessão de gravação

                    // mostrar sessoes de gravacao
                    ((AlbumEditado) album).getAllSessions().forEach(session01 -> {
                        if(!session01.isCompleted()) {
                            System.out.println(session01);
                        }
                    });

                    // escolher a sessao
                    UUID idSession01 = null;
                    try{
                        idSession01 = UUID.fromString(Prompt.readString("ID da sessão que pretende concluir: "));
                    } catch (IllegalArgumentException e) {
                        Prompt.pressEnterToContinue("ID inválido");
                        return;
                    }

                    if (idSession01 == null) {
                        Prompt.pressEnterToContinue("ID inválido");
                        return;
                    }

                    Session session01 = ((AlbumEditado) album).getSession(idSession01);
                    if(session01 == null) {
                        Prompt.pressEnterToContinue("Sessão não encontrada");
                        return;
                    }

                    try {
                        session01.setCompleted(true);
                    } catch (Exception e) {
                        Prompt.pressEnterToContinue(e.getMessage());
                        return;
                    }

                    Prompt.pressEnterToContinue("Sessão concluída com sucesso!");
                    break;
                default:
                    Prompt.pressEnterToContinue("Opção inválida");
            }

        } else {
            Prompt.pressEnterToContinue("Opção inválida");
        }
    }

    public static void endRecordingSession() {
        System.out.println("Terminar sessões de gravação...");
        boolean res = false;
        try {
            res = ReposHolder.getSessions().endRecordingSessions();
        } catch (Exception e) {
            Prompt.pressEnterToContinue(e.getMessage());
        }
        if (!res) {
            System.out.println("Nenhuma sessão de gravação foi terminada");
            Generics.sc.nextLine();
        }
    }

    public static void showStateOfAlbum() {
        String albumName = Prompt.readString("Selecione o álbum para examinar: ");
        if (Prompt.goBack(albumName)) return;

        Backend.Albums.Album album = ReposHolder.getAlbums().getAlbum(albumName);
        if(null == album) {
            Prompt.pressEnterToContinue("Álbum não encontrado");
            return;
        }

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
        Set<AlbumEditado> myEditedProjects = user.getNewAlbumsEdits();


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
        Prompt.pressEnterToContinue();
    }

    public static void showRecordingSessionsOfDay() {
        String day = Prompt.readString("Selecione o dia para inspecionar (dd/MM/yyyy): ");
        if (Prompt.goBack(day)) return;

        LocalDateTime dInicio = Frontend.Utils.Generics.stringToDate(day + " 00:00");
        LocalDateTime dFim = Frontend.Utils.Generics.stringToDate(day + " 23:59");
        if (dInicio == null || dFim == null) {
            Prompt.pressEnterToContinue("Data inválida");
            return;
        }

        Backend.Sessions.Session session = user.findSessionByDate(dInicio, dFim);
        System.out.println(session);
    }

    public static void checkData() {
        System.out.println("Nome: " + user.getName());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: " + user.getPassword());
        Prompt.pressEnterToContinue();
    }
}
