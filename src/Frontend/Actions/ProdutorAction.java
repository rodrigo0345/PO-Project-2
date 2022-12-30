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
        System.out.println("1. Start a new edit of an album");
        System.out.println("2. Edit an existing edit of an album");

        int option = Prompt.checkOption("Introduza a opção: ");

        if (option == 1) {
            String albumName = Prompt.readString("Nome do álbum: ");
            String EditionAlbumName = Prompt.readString("Nome para a edição de álbum: ");
            
            Backend.Albums.AlbumEditado album;

            try {
                album = user.createAlbumEdit(albumName, EditionAlbumName);
            } catch( ClassNotFoundException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
                Generics.sc.nextLine();
                return;
            }

            String choice = Prompt.readString("Planeia uma nova sessão de gravação? (y/n)");
            while(choice.equals("y")) {
                String newDate = Prompt.readString("Data para início da sessão (dd/mm/aaaa HH:mm): ");
                LocalDateTime newDateStart = Frontend.Utils.Generics.stringToDate(newDate);
                newDate = Prompt.readString("Data para conclusão da sessão (dd/mm/aaaa HH:mm): ");
                LocalDateTime newDateEnd = Frontend.Utils.Generics.stringToDate(newDate);
                Backend.Sessions.Session newSession = null;

                try {
                    album.addSession(newDateStart, newDateEnd);
                    newSession = album.getLastSessionAdded();
                } catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }

                String artist = Prompt.readString("Adicionar um novo artista? (y/n)");
                while(artist.equals("y")) {

                    artist = Prompt.readString("Username do artista: ");

                    Backend.Users.User aux = ReposHolder.getUsers().getUser(artist);
                    if (!(aux instanceof Backend.Users.Musician) || aux == null) {
                        System.out.println("O músico introduzido não existe");
                        return;
                    }

                    // add this session to the musician
                    Backend.Users.Musician artistInstance = (Musician) aux;
                    try {
                        artistInstance.addSession(album, newSession);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        Generics.sc.nextLine();
                        // no need to return
                    }

                    artist = Prompt.readString("Adicionar novo artista? (y/n)");
                }

                choice = Prompt.readString("Planeia uma nova sessão de gravação? (y/n)");
            }

        } else if (option == 2) {
            String albumName = Prompt.readString("Nome do álbum: ");
            Backend.Albums.Album album = ReposHolder.getAlbums().getAlbum(albumName);

            if (!(album instanceof Backend.Albums.AlbumEditado)){
                System.out.println("O álbum selecionado não é editável");
                Generics.sc.nextLine();
                return;
            } else if (!(user.equals(album.getProdutor()))) {
                System.out.println("Não tem premissão para editar este album! Permission recusada!");
                Generics.sc.nextLine();
                return;
            }

            System.out.println("1. Adicionar uma nova sessão de gravação");
            System.out.println("2. Remover uma sessão de gravação");
            System.out.println("3. Adicionar um novo artista");
            System.out.println("4. Remover um artista");
            System.out.println("5. Adicionar uma nova faixa");
            System.out.println("6. Encerrar todas as sessões de gravação anteriores");
            int choice = Prompt.checkInt("Introduza a opção: ");

            switch(choice) {
                case 1:
                    String dataInicio = Prompt.readString("Hora de inicio da sessão(dd/MM/aaaa HH:mm): ");
                    LocalDateTime novaDataInicio = Generics.stringToDate(dataInicio);
                    String dataFim = Prompt.readString("Hora de fim da sessão(dd/MM/aaaa HH:mm): ");
                    LocalDateTime novaDataFim = Generics.stringToDate(dataFim);

                    ((AlbumEditado) album).addSession(novaDataInicio, novaDataFim);

                    //System.out.println("Choose a date to the recording: ");
                    //LocalDate d = Frontend.Utils.Generics.readDate();
                    //((AlbumEditado) album).addSession(d);

                    break;
                case 2:
                    System.out.println();
                    String id = Prompt.readString("ID da sessão de gravação: ");
                    boolean success = ReposHolder.getSessions().deleteSession(UUID.fromString(id));
                    if (!success) { System.out.println("A sessão que está a tentar eliminar, não existe."); return;}
                    System.out.println("Sessão eliminada com sucesso.");
                    break;
                case 3:

                    String username = Prompt.readString("Username do artista: ");
                    Backend.Users.User artist = ReposHolder.getUsers().getUser(username);
                    if (!(artist instanceof Musician) || artist == null) {
                        System.out.println("Username inválido, o username " +
                                "introduzido ou não é artista ou não existe!");
                        Generics.sc.nextLine();
                        return;
                    }

                    // always need to add the album to the
                    // artist and then the artist to the album
                    ((Musician) artist).addAlbum(album);
                    album.addArtist((Musician) artist);
                    break;
                case 4:
                    String username2 = Prompt.readString("Username do artista: ");
                    boolean success2 = album.removeArtist(username2);
                    if (!success2) {
                        System.out.println("O sistema não foi capaz de eliminar o username" +
                                " introduzido!");
                        Generics.sc.nextLine();
                    }
                    break;
                case 5:

                    String trackName = Prompt.readString("Nome da faixa: ");
                    String genre = Prompt.readString("Género da faixa: ");
                    int duration = Prompt.checkInt("Duração: ");
                    String nameAlbum = Prompt.readString("Nome dos álbuns associados: ");
                    Album a = user.getProjeto(nameAlbum) == null? user.getProjeto(nameAlbum): user.getOldAlbum(nameAlbum);

                    Backend.Tracks.Track newTrack = new Track(a, trackName, genre ,duration);
                    boolean success3 = album.addTrack(newTrack);
                    if (!success3) {
                        System.out.println("O nome da faixa deve ser única dentro do álbum!");
                        Generics.sc.nextLine();
                    }
                    break;
                case 6:
                    System.out.println("Terminar sessões de gravação...");
                    boolean res = ReposHolder.getSessions().endRecordingSessions();
                    if (!res) {
                        System.out.println("Nenhuma sessão de gravação foi terminada");
                        Generics.sc.nextLine();
                    }
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
