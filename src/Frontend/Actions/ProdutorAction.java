package Frontend.Actions;

import Backend.Albums.Album;
import Backend.Albums.AlbumEditado;
import Backend.Sessions.Session;
import Backend.Tracks.Track;
import Frontend.Utils.ConsoleColors;
import Frontend.Utils.Generics;
import Frontend.Utils.Prompt;
import Frontend.Utils.ReposHolder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * The type Produtor action.
 */
public class ProdutorAction {
    private static Backend.Users.Produtor user;
    /**
     * Sets user.
     *
     * @param user the user
     */
// setUser só é usado para inicializar o user em Mprodutor
    public static void setUser(Backend.Users.User user) {
        Frontend.Utils.Generics.menuProducerHeader();
        if (user instanceof Backend.Users.Produtor) {
            ProdutorAction.user = (Backend.Users.Produtor) user;
        } else {
            throw new IllegalArgumentException("Utilizador não é um produtor");
        }
    }

    /**
     * Edit profile.
     */
// permite ao utilizador mudar variaveis como nome, email, password, etc
    public static void editProfile() {
        Frontend.Utils.Generics.menuProducerHeader();

        ConsoleColors color = new ConsoleColors();

        System.out.println(color.getYELLOW());
        System.out.print("[1] - ");
        System.out.print(color.getWHITE());
        System.out.print("Editar nome");
        System.out.println(color.getYELLOW());
        System.out.print("[2] - ");
        System.out.print(color.getWHITE());
        System.out.print("Editar username");
        System.out.println(color.getYELLOW());
        System.out.print("[3] - ");
        System.out.print(color.getWHITE());
        System.out.print("Editar email");
        System.out.println(color.getYELLOW());
        System.out.print("[4] - ");
        System.out.print(color.getWHITE());
        System.out.println("Editar password");
        System.out.print(color.getYELLOW());
        System.out.print("[5] - ");
        System.out.print(color.getWHITE());
        System.out.println("Retroceder");        

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
     * Start or create editing album.
     */
// permite ao utilizador criar um album e ainda editar os seus albums
    public static void startOrCreateEditingAlbum() {
        Frontend.Utils.Generics.menuProducerHeader();

        ConsoleColors color = new ConsoleColors();
        System.out.println(color.getYELLOW());
        System.out.print("[0] - ");
        System.out.print(color.getWHITE());
        System.out.print("Retroceder");
        System.out.println(color.getYELLOW());
        System.out.print("[1] - ");
        System.out.print(color.getWHITE());
        System.out.print("Começar a editar um álbum");
        System.out.println(color.getYELLOW());
        System.out.print("[2] - ");
        System.out.print(color.getWHITE());
        System.out.print("Editar as informações de um álbum");

        int option = Prompt.checkOption("Introduza a opção: ");

        if(0 == option){
            return;
        }

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
            Prompt.pressEnterToContinue("Album " + EditionAlbumName + "criado com sucesso!");

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

            System.out.println(color.getYELLOW());
            System.out.print("[1] - ");
            System.out.print(color.getWHITE());
            System.out.print("Adicionar uma nova sessão de gravação");
            System.out.println(color.getYELLOW());
            System.out.print("[2] - ");
            System.out.print(color.getWHITE());
            System.out.print("Remover uma sessão de gravação");
            System.out.println(color.getYELLOW());
            System.out.print("[3] - ");
            System.out.print(color.getWHITE());
            System.out.print("Adicionar artistas à sessão de gravação");
            System.out.println(color.getYELLOW());
            System.out.print("[4] - ");
            System.out.print(color.getWHITE());
            System.out.print("Remover um artista");
            System.out.println(color.getYELLOW());
            System.out.print("[5] - ");
            System.out.print(color.getWHITE());
            System.out.print("Adicionar uma nova faixa");
            System.out.println(color.getYELLOW());
            System.out.print("[6] - ");
            System.out.print(color.getWHITE());
            System.out.print("Encerrar a edição do álbum");
            System.out.println(color.getYELLOW());
            System.out.print("[7] - ");
            System.out.print(color.getWHITE());
            System.out.print("Concluir sessão de gravação");

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

    /**
     * End recording session.
     */
    public static void endRecordingSession() {
        Frontend.Utils.Generics.menuProducerHeader();
        ConsoleColors color = new ConsoleColors();
        System.out.println(color.getWHITE());
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

    /**
     * Show state of album.
     */
    public static void showStateOfAlbum() {
        Frontend.Utils.Generics.menuProducerHeader();

        Set<Album> oldProjects = user.getOldAlbums();
        Set<AlbumEditado> myEditedProjects = user.getNewAlbumsEdits();

        if (oldProjects.isEmpty()) { System.out.println("Sem álbuns...");}
        else {
            for (Album album1: oldProjects){
                System.out.println(album1);
            }
        }

        if(myEditedProjects.isEmpty()) {
            System.out.println("Sem álbuns...");
        } else {
            for (AlbumEditado album1 : myEditedProjects) {
                System.out.println(album1);
            }
        }

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

    /**
     * Show your albums.
     */
    public static void showYourAlbums() {
        Frontend.Utils.Generics.menuProducerHeader();
        Set<Album> oldProjects = user.getOldAlbums();
        Set<AlbumEditado> myEditedProjects = user.getNewAlbumsEdits();

        ConsoleColors color = new ConsoleColors();
        System.out.println(color.getYELLOW());
        System.out.println("--- Álbuns antigos ---");
        System.out.println(color.getWHITE());
        if (oldProjects.isEmpty()) { System.out.println("Sem álbuns...");}
        else {
            for (Album album1: oldProjects){
                System.out.println(album1);
            }
        }
       
        System.out.println(color.getYELLOW());
        System.out.println("--- Novos Álbuns ---");
        System.out.println(color.getWHITE());
        if(myEditedProjects.isEmpty()) {
            System.out.println("Sem álbuns...");
        } else {
            for (AlbumEditado album1 : myEditedProjects) {
                System.out.println(album1);
            }
        }
        Prompt.pressEnterToContinue();
    }

    /**
     * Show recording sessions of day.
     */
    public static void showRecordingSessionsOfDay() {
        Frontend.Utils.Generics.menuProducerHeader();
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
        Prompt.pressEnterToContinue();
    }

    /**
     * Check data.
     */
    public static void checkData() {
        Frontend.Utils.Generics.menuProducerHeader();
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
    }
}
