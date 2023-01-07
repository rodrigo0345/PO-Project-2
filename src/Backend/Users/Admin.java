package Backend.Users;

import Backend.Albums.Album;
import Backend.Albums.AlbumEditado;
import Backend.Instruments.Instrument;
import Backend.Sessions.Session;
import Backend.Tracks.Track;
import Backend.Useful.StringChecker;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

/**
 * The type Admin.
 */
public class Admin extends User {//Traduzidos

    /**
     * Instantiates a new Admin.
     *
     * @param name        the name
     * @param email       the email
     * @param username    the username
     * @param password    the password
     * @param instruments the instruments
     * @param albums      the albums
     * @param users       the users
     * @param sessions    the sessions
     */
    public Admin(String name, String email, String username, String password,
                 Backend.Instruments.Repos instruments, Backend.Albums.Repos albums,
                 Backend.Users.Repos users, Backend.Sessions.Repos sessions) {
        super(name, email, username, password, users, instruments, albums, sessions);
        this.getUsersRepo().addUser(this);
    }

    /**
     * Add instrument.
     *
     * @param name       the name
     * @param quantidade the quantidade
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void addInstrument(String name, int quantidade) throws IllegalArgumentException {
        if (0 > quantidade) { throw new IllegalArgumentException("Quantidade inválida"); }
        Instrument instrument = new Instrument(name, quantidade, null);
        this.getInstrumentsRepo().addInstrument(instrument);
    }

    /**
     * Add quantity to instrument.
     *
     * @param name       the name
     * @param quantidade the quantidade
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void addQuantityToInstrument(String name, int quantidade) throws IllegalArgumentException {
        if (0 > quantidade) { throw new IllegalArgumentException("Quantidade inválida"); }
        String nameLowerCase = name.toLowerCase();
        int quant = this.getInstrumentsRepo().getInstrument(nameLowerCase).getQuantidade();
        quant += quantidade;
        this.getInstrumentsRepo().getInstrument(nameLowerCase).setQuantidade(quant);
    }

    /**
     * Accept instrument request.
     *
     * @param name    the name
     * @param session the session
     */
    public void acceptInstrumentRequest(String name, Session session){
        if (null == this.getInstrumentsRepo().getInstrument(name)) return; // does not exist
        session.approveInstrument(this.getInstrumentsRepo().getInstrument(name));
    }

    /**
     * Accept instrument request.
     *
     * @param instrument the instrument
     * @param session    the session
     */
    public void acceptInstrumentRequest(Instrument instrument, Session session){
        if (null == instrument) return; // does not exist
        session.approveInstrument(instrument);
    }

    /**
     * Deny instrument request.
     *
     * @param name    the name
     * @param session the session
     */
    public void denyInstrumentRequest(String name, Session session){
        if (null == this.getInstrumentsRepo().getInstrument(name)) return; // does not exist
        session.denyInstrument(this.getInstrumentsRepo().getInstrument(name));
    }

    /**
     * Remove instrument.
     *
     * @param name the name
     */
    public void removeInstrument(String name) {
        Instrument ref = this.getInstrumentsRepo().getInstrument(name);

        // preciso eliminar todas as referencias do objeto que vai ser eliminado
        for(Backend.Sessions.Session s: this.getSessionsRepo().getSessions()){
            s.getPendentInstruments().remove(ref);
            s.getApprovedInstruments().remove(ref);
        }
        for(Backend.Sessions.Session s: this.getSessionsRepo().getPendingSessions()){
            s.getApprovedInstruments().remove(ref);
            s.getPendentInstruments().remove(ref);
        }
        this.getInstrumentsRepo().removeInstrument(name);
    }

    /**
     * Add musician backend . users . musician.
     *
     * @param name     the name
     * @param email    the email
     * @param username the username
     * @param password the password
     * @return the backend . users . musician
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Backend.Users.Musician addMusician(String name, String email, String username, String password)
            throws IllegalArgumentException {
        if (!this.getUsersRepo().isUsernameAvailable(username)) {
            throw new IllegalArgumentException("Username já existe");
        }
        StringChecker.validName(name);
        StringChecker.validEmail(email);
        // automatically adds it to the user repos
        return new Musician(name, email, username, password, this.getUsersRepo(), this.getInstrumentsRepo(),
                this.getAlbumsRepo(), this.getSessionsRepo());
    }

    /**
     * Add produtor produtor.
     *
     * @param name     the name
     * @param email    the email
     * @param username the username
     * @param password the password
     * @return the produtor
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Produtor addProdutor(String name, String email, String username, String password)
            throws IllegalArgumentException {
        if (!this.getUsersRepo().isUsernameAvailable(username)) {
            throw new IllegalArgumentException("Username já existe");
        }
        StringChecker.validName(name);
        StringChecker.validEmail(email);
        // automatically adds it to the user repos
        return new Produtor(name, email, username, password, this.getUsersRepo(), this.getInstrumentsRepo(),
                this.getAlbumsRepo(), this.getSessionsRepo());
    }

    /**
     * Remove user.
     *
     * @param username the username
     * @throws Exception the exception
     */
    public void removeUser(String username) throws Exception {
        // implement a way of removing all the associated albums and musics
        User user = this.getUsersRepo().getUser(username);

        if(null == user) throw new IllegalArgumentException("Username não existe");
        if(user instanceof Musician) {
            for(Album a: this.getAlbumsRepo().getAlbums().values()){
                a.removeArtist(username);

                for(Track t: a.getTracks().values()){
                    t.removeArtist((Musician) user);
                }
            }
        } else if(user instanceof Produtor){
            for(Album a : this.getAlbumsRepo().getAlbums().values()){
                if(a.getProdutor().getUsername().equals(username)) throw new Exception("O username que está a tentar remover " +
                        "possui álbuns associados a si.");
            }
        }

        this.getUsersRepo().removeUser(username);
    }

    /**
     * Gets all session requests.
     *
     * @return the all session requests
     */
    public Set<Session> getAllSessionRequests() {
        Set<Session> s = new TreeSet<>();
        if (0 == this.getSessionsRepo().getPendingSessions().size()) {
            return null;
        } else {
            for (Backend.Sessions.Session session : this.getSessionsRepo().getPendingSessions()) {
                if (!session.isAccepted() && !session.wasRejected()) {
                    s.add(session);
                }
            }
        }
        return s;
    }

    /**
     * Gets all recording sessions.
     *
     * @return the all recording sessions
     */
    public String getAllRecordingSessions() {
        StringBuilder s = new StringBuilder();
        for (Backend.Sessions.Session session : this.getSessionsRepo().getSessions()) {
            s.append(" ").append(session.toString());
        }
        return s.toString();
    }

    /**
     * Gets all albums being edited.
     *
     * @return the all albums being edited
     */
    public String getAllAlbumsBeingEdited() {
        StringBuilder s = new StringBuilder();
        for (Backend.Albums.Album album : this.getAlbumsRepo().getAlbums().values()) {
            if (album instanceof Backend.Albums.AlbumEditado && !((Backend.Albums.AlbumEditado) album).isEdited()) {
                s.append(" ").append(album);
            }
        }
        return s.toString();
    }

    /**
     * Gets stats.
     *
     * @return the stats
     */
    public String getStats() {
        return this.getStats(LocalDateTime.of(1920, 1, 1, 0, 0),
                            LocalDateTime.of(3000, 1, 1, 0, 0));
    }

    /**
     * Gets stats.
     *
     * @param inicio the inicio
     * @param fim    the fim
     * @return the stats
     */
    public String getStats(LocalDateTime inicio, LocalDateTime fim) {

        // total de albums em edição
        int countNotFinishedAlbums = 0, countFinishedAlbums = 0;
        for(Album a: this.getAlbumsRepo().getAlbums().values()){
            if(a instanceof AlbumEditado && !((AlbumEditado)a).isEdited()){
                countNotFinishedAlbums++;
            }
            if(a instanceof AlbumEditado && ((AlbumEditado)a).isEdited()){
                countFinishedAlbums++;
            }
        }

        int countSessionsCompleted = 0;
        for(Session s: this.getSessionsRepo().getSessions()){
            if(s.isCompleted() && s.doesSessionOverlap(inicio, fim)){
                countSessionsCompleted++;
            }
        }
        double percentage = (double)countSessionsCompleted /
                (this.getSessionsRepo().getSessions().size() + this.getSessionsRepo().getPendingSessions().size())
                * 100;
        if(Double.isNaN(percentage)) percentage = 0;

        return "Álbums não terminados: " + countNotFinishedAlbums + "\n" +
                "Albums terminados: " + countFinishedAlbums + "\n" +
                "Percentagem de sessões completas: " + percentage + "%";
    }

    /**
     * Accept session request.
     *
     * @param id the id
     */
    public void acceptSessionRequest(UUID id) {
        for(Session s: this.getSessionsRepo().getPendingSessions()){
            if (s.getId().equals(id)) {s.setAccepted(true); return; }
        }
    }

    /**
     * Reject session request.
     *
     * @param id the id
     */
    public void rejectSessionRequest(UUID id) {
        (this.getSessionsRepo().getSession(id)).setAccepted(false);
        (this.getSessionsRepo().getSession(id)).setRejected(true);
    }

    /**
     * Add album.
     *
     * @param name     the name
     * @param genre    the genre
     * @param date     the date
     * @param produtor the produtor
     */
    public void addAlbum(String name, String genre, LocalDateTime date, Backend.Users.Produtor produtor) {
        Backend.Albums.Album album = new Backend.Albums.Album(name, genre, date, produtor, this.getInstrumentsRepo(),
                this.getAlbumsRepo(), this.getUsersRepo(), this.getSessionsRepo());
        this.getAlbumsRepo().addAlbum(album);
        produtor.addOldAlbum(album);
    }

    /**
     * Add musician to album.
     *
     * @param username        the username
     * @param titleOfTheAlbum the title of the album
     */
    public void addMusicianToAlbum(String username, String titleOfTheAlbum) {
        Backend.Albums.Album album = this.getAlbumsRepo().getAlbum(titleOfTheAlbum);
        Backend.Users.Musician musician = (Backend.Users.Musician) this.getUsersRepo().getUser(username);
        album.addArtist(musician);
        musician.addAlbum(album);
    }

    // remove album
    public void removeAlbum(String title) throws Exception {
        if(this.getAlbumsRepo().getAlbum(title) == null) throw new Exception("O álbum que está a tentar remover não existe.");
        if(this.getAlbumsRepo().getAlbum(title) instanceof AlbumEditado){
            if(!((AlbumEditado)this.getAlbumsRepo().getAlbum(title)).isEdited()){
                throw new Exception("O álbum que está a tentar remover ainda não foi terminado.");
            }
        }

        // remove on producer
        this.getAlbumsRepo().getAlbum(title).getProdutor().removeNewAlbumEdit(
                (AlbumEditado) this.getAlbumsRepo().getAlbum(title)
        );


        // remove on musicians
        for(User m: this.getUsersRepo().getUsers().values()){
            if(m instanceof Musician){
                ((Musician)m).removeAlbum(this.getAlbumsRepo().getAlbum(title));
            }
        }

        this.getAlbumsRepo().removeAlbum(title);
    }

    /**
     * Add produtor to album.
     *
     * @param username        the username
     * @param titleOfTheAlbum the title of the album
     */
// aqui tratamos de adicionar albums que não foram editados na editora
    public void addProdutorToAlbum(String username, String titleOfTheAlbum) {
        Backend.Albums.Album album = this.getAlbumsRepo().getAlbum(titleOfTheAlbum);
        Backend.Users.Produtor produtor = (Backend.Users.Produtor) this.getUsersRepo().getUser(username);
        album.setProdutorOriginal(produtor);
        produtor.addOldAlbum(album);
    }

    /**
     * Add track to album.
     *
     * @param titleOfTheAlbum the title of the album
     * @param track           the track
     */
// automaticamente associamos o album aos artistas que participaram na track
    public void addTrackToAlbum(String titleOfTheAlbum, Backend.Tracks.Track track) {
        Backend.Albums.Album album = this.getAlbumsRepo().getAlbum(titleOfTheAlbum);
        album.addTrack(track);
        for (Backend.Users.Musician musician : track.getArtists()) {
            musician.addAlbum(album);
        }
    }

    /**
     * Get pendent instruments set.
     *
     * @return the set
     */
    public Set<Instrument> getPendentInstruments(){
        Set<Instrument> i = new TreeSet<>();
        for(Session s: this.getSessionsRepo().getSessions()){
            i.addAll(s.getPendentInstruments());
        }
        return i;
    }
}
