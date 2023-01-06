package Backend.Albums;

import Backend.Sessions.Session;
import Backend.Users.Musician;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

/**
 * The type Album editado.
 */
public class AlbumEditado extends Album {//Traduzido
    /*
     Quando se trata de referenciar sessões é muito mais seguro utilizar o ID do que a data, a opção de data é apenas para
        facilitar a visualização do utilizador
     */

    private boolean isEdited;
    private final Set<Backend.Sessions.Session> sessions = new TreeSet<>();
    private Backend.Sessions.Session lastSessionAdded;

    // there are 2 produtores, one for the original album and one for the edited album
    private Backend.Users.Produtor produtor;

    /**
     * Instantiates a new Album editado.
     *
     * @param titulo      the titulo
     * @param genero      the genero
     * @param original    the original
     * @param instruments the instruments
     * @param albums      the albums
     * @param users       the users
     * @param sessions    the sessions
     * @param producer    the producer
     */
    public AlbumEditado(String titulo, String genero,
                        Album original,
                        Backend.Instruments.Repos instruments,
                        Backend.Albums.Repos albums,
                        Backend.Users.Repos users, Backend.Sessions.Repos sessions, Backend.Users.Produtor producer) {

        super(titulo, genero, null, // still being edited
                original.getProdutor(), instruments, albums, users, sessions);

        this.isEdited = false;
        producer.addNewAlbumEdit(this);
        this.setProdutor(producer);
    }

    /**
     * Instantiates a new Album editado.
     *
     * @param titulo the titulo
     */
    public AlbumEditado(String titulo){
        super(titulo);
        this.isEdited = false;
        this.setProdutor(null);
    }

    /**
     * Sets produtor.
     *
     * @param produtor the produtor
     */
    public void setProdutor(Backend.Users.Produtor produtor) {
        if (null == produtor) return;
        produtor.addNewAlbumEdit(this);
        this.produtor = produtor;
    }

    @Override
    public Backend.Users.Produtor getProdutor() {
        return this.produtor;
    }

    /**
     * Sets album as complete.
     */
    public void setAlbumAsComplete() {
        this.isEdited = true;
        this.setDate(LocalDateTime.now());
    }

    /**
     * Is edited boolean.
     *
     * @return the boolean
     */
    public boolean isEdited() {
        return isEdited;
    }

    /**
     * Add session session.
     *
     * @param dateInicio the date inicio
     * @param dateFim    the date fim
     * @return the session
     * @throws IllegalArgumentException the illegal argument exception
     */
// user na maioria das circunstâncias
    public Session addSession(LocalDateTime dateInicio, LocalDateTime dateFim) throws IllegalArgumentException {

        for(Session s: sessions){
            if(s.doesSessionOverlap(dateInicio, dateFim)){
                throw new IllegalArgumentException("Já existe uma sessão com essas datas");
            }
        }

        if(dateInicio.isAfter(dateFim)) throw new IllegalArgumentException("Data de inicio tem de ser antes da data de fim");
        if (this.isEdited) {
            throw new IllegalArgumentException("O álbum já está terminado");
        }
        if (dateInicio.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data de ínicio já foi extrapolada");
        }

        // the constructor of session is responsible for also adding the session into the specified album
        Backend.Sessions.Session session = new Backend.Sessions.Session(dateInicio, dateFim, this, getSessionsRepo(),
                getUsersRepo(), getInstrumentsRepo(), getAlbumsRepo());
        this.lastSessionAdded = session;
        return session;
    }

    /**
     * Add session boolean.
     *
     * @param s the s
     * @return the boolean
     */
// usado apenas no construtor de sessão
    public boolean addSession(Session s){
        if (this.isEdited) {
            throw new IllegalArgumentException("O álbum já está terminado");
        }
        if (s.getDataInicio().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data de ínicio já foi extrapolada");
        }
        // check if any musician in this session has another session at the same time
        for (Backend.Users.Musician musician : s.getInvitedMusicians().values()) {
            for (Backend.Sessions.Session session : musician.getSessions()) {
                if (session.getDataInicio().isBefore(s.getDataFim()) && session.getDataFim().isAfter(s.getDataInicio())) {
                    throw new IllegalArgumentException("The musician " + musician.getUsername() + " já xiste em outra sessão ao mesmo tempo");
                }
            }
        }
        this.lastSessionAdded = s;
        return sessions.add(s);
    }

    /**
     * Get last session added backend . sessions . session.
     *
     * @return the backend . sessions . session
     */
    public Backend.Sessions.Session getLastSessionAdded(){
        return this.lastSessionAdded;
    }

    /**
     * Remove session boolean.
     *
     * @param dateInicio the date inicio
     * @return the boolean
     * @throws IllegalArgumentException the illegal argument exception
     */
    public boolean removeSession(LocalDateTime dateInicio) throws IllegalArgumentException {
        if (this.isEdited) {
            throw new IllegalArgumentException("O álbum que está a tentar editar já está terminado");
        }
        if (dateInicio.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não pode eliminar uma sessão que já foi concluída.");
        }

        Backend.Sessions.Session found = null;
        for(Backend.Sessions.Session s: sessions){
            if(s.getDataInicio().equals(dateInicio)) {
                found = s;
            }
        }
        if (null == found) return false;
        return sessions.remove(found);
    }

    /**
     * Remove session boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws IllegalArgumentException the illegal argument exception
     */
    public boolean removeSession(UUID id) throws IllegalArgumentException {
        if (this.isEdited) {
            throw new IllegalArgumentException("O álbum que está a tentar editar já está terminado.");
        }
        Backend.Sessions.Session found = null;
        for (Backend.Sessions.Session s: sessions) {
            if (s.getId().equals(id)) {
                found = s;
                s.removeAllInvitedMusicians();
            }
        }
        if (null == found) return false;
        return sessions.remove(found);
    }

    /**
     * Mark session as completed.
     *
     * @param id the id
     * @throws Exception the exception
     */
    public void markSessionAsCompleted(UUID id) throws Exception {
        if (this.isEdited) {
            return;
        }
        for(Backend.Sessions.Session s: sessions){
            if(s.getId().equals(id)){
                s.setCompleted(true);
            }
        }
    }

    /**
     * Gets session.
     *
     * @param id the id
     * @return the session
     */
    public Session getSession(UUID id) {
        for(Backend.Sessions.Session s: sessions){
            if(s.getId().equals(id)){
                return s;
            }
        }
        return null;
    }

    @Override
    public boolean removeArtist(String username) {
        Musician aux = getArtist(username);
        if (null == aux)
            return false;

        // remove the artist from all the sessions
        for (Backend.Sessions.Session s : sessions) {
            s.removeInvitedMusician(aux);
        }

        return super.removeArtist(username);
    }

    /**
     * Gets all sessions.
     *
     * @return the all sessions
     */
    public Set<Backend.Sessions.Session> getAllSessions() {
        return sessions;
    }
}
