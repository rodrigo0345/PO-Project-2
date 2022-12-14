package Backend.Users;

import Backend.Albums.Album;
import Backend.Albums.AlbumEditado;
import Backend.Instruments.Instrument;
import Backend.Sessions.Session;
import org.junit.Test;

import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class AdminTest {

    private Admin admin = new Admin("Teste", "Teste@gmail.com", "admin", "admin",
            new Backend.Instruments.Repos(), new Backend.Albums.Repos(), new Backend.Users.Repos(), new Backend.Sessions.Repos());

    private Produtor produtor = new Produtor("name", "email@email.com", "name", "name",
            admin.getUsersRepo(), admin.getInstrumentsRepo(), admin.getAlbumsRepo(), admin.getSessionsRepo());
    private AlbumEditado album = new AlbumEditado("R", "rock",
                admin.getInstrumentsRepo(), admin.getAlbumsRepo(), produtor.getUsersRepo(), admin.getSessionsRepo(), produtor);
    @Test
    public void addInstrument() {
        admin.addInstrument("flute");
        assertNotNull(admin.getInstrumentsRepo().getInstrument("flute"));
        assertEquals(1, admin.getInstrumentsRepo().getInstruments().size());
    }

    @Test
    public void removeInstrument() {
        addInstrument();
        Session s = new Session(Frontend.Utils.Generics.stringToDate("21/12/2040"), album, admin.getSessionsRepo(), admin.getUsersRepo(),
                admin.getInstrumentsRepo(), admin.getAlbumsRepo());
        s.addPendingInstrument(new Instrument("flute"));
        admin.removeInstrument("flute");
        assertFalse(s.getApprovedInstruments().contains(s));
        assertEquals(0, s.getApprovedInstruments().size());
        assertFalse(admin.getInstrumentsRepo().getInstruments().containsKey("flute"));
    }

    @Test
    public void addMusician() {
        String error = null;
        try{
            admin.addMusician("teste", "teste@gmail.com", "admin", "teste");
        } catch(IllegalArgumentException e){
            error = e.getMessage();
        }
        assertEquals("Username already exists", error);

        admin.addMusician("teste", "teste@gmail.com", "teste3", "teste");
        assertNotNull(admin.getUsersRepo().getUser("teste3"));
        assertEquals(3, admin.getUsersRepo().getUsers().size());
    }

    @Test
    public void addProdutor() {
        String error = null;
        try{
            admin.addProdutor("teste", "teste@gmail.com", "admin", "teste");
        } catch(IllegalArgumentException e){
            error = e.getMessage();
        }
        assertEquals("Username already exists", error);

        admin.addProdutor("teste", "teste@gmail.com", "teste3", "teste");
        assertNotNull(admin.getUsersRepo().getUser("teste3"));
        assertEquals(3, admin.getUsersRepo().getUsers().size());
    }

    @Test
    public void removeUser() {
        addProdutor();
        Album album = new Album(
                            "Something",
                            "Rock",
                            Frontend.Utils.Generics.stringToDate("11/12/2000"),
                            (Produtor) admin.getUsersRepo().getUser("teste3"),
                            admin.getInstrumentsRepo(),
                            admin.getAlbumsRepo(),
                            admin.getUsersRepo(),
                            admin.getSessionsRepo()
                );
        assertNotNull(album.getProdutor());
        String error = null;
        try {
            admin.removeUser("teste3");
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNotNull(error);
        admin.addProdutor("r", "teste@gmail.com", "rodrigo", "0");
        album.setProdutor((Produtor)admin.getUsersRepo().getUser("rodrigo", "0"));
        try {
            admin.removeUser("teste3");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertNull(admin.getUsersRepo().getUser("teste3"));
        assertEquals(3, admin.getUsersRepo().getUsers().size());
    }

    @Test
    public void getAllSessionRequests() {
        AlbumEditado album = new AlbumEditado(
                "Something",
                "Rock",
                admin.getInstrumentsRepo(),
                admin.getAlbumsRepo(),
                admin.getUsersRepo(),
                admin.getSessionsRepo(),
                produtor
        );
        Session s1 = new Session(Frontend.Utils.Generics.stringToDate("11/12/2023"), album, admin.getSessionsRepo(),
                admin.getUsersRepo(), admin.getInstrumentsRepo(), admin.getAlbumsRepo());
        Session s2 = new Session(Frontend.Utils.Generics.stringToDate("14/12/2023"), album, admin.getSessionsRepo(),
                admin.getUsersRepo(), admin.getInstrumentsRepo(), admin.getAlbumsRepo());

        Set<Session> aux = new TreeSet<>();
        aux.add(s1); aux.add(s2);
        Set<Session> s = admin.getAllSessionRequests();
        assertEquals(aux, s);
    }

    @Test
    public void showAllRecordingSessions() {
    }

    @Test
    public void showAllAlbumsBeingEdited() {
    }

    @Test
    public void showAllAlbumsEdited() {
    }

    @Test
    public void showStats() {
    }

    @Test
    public void acceptSessionRequest() {
    }

    @Test
    public void rejectSessionRequest() {
    }

    @Test
    public void addAlbum() {
    }

    @Test
    public void addMusicianToAlbum() {
    }

    @Test
    public void setProdutorToAlbum() {
    }

    @Test
    public void addTrackToAlbum() {
    }

    @Test
    public void acceptInstrumentRequest() {

    }

    @Test
    public void denyInstrumentRequest() {

    }
}