package Backend.Users;

import Backend.Albums.Album;
import Backend.Instruments.Instrument;
import Backend.Sessions.Session;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdminTest {

    Admin admin = new Admin("Teste", "Teste@gmail.com", "admin", "admin",
            new Backend.Instruments.Repos(), new Backend.Albums.Repos(), new Backend.Users.Repos(), new Backend.Sessions.Repos());
    @Test
    public void addInstrument() {
        admin.addInstrument("flute");
        assertNotNull(admin.getInstrumentsRepo().getInstrument("flute"));
        assertEquals(1, admin.getInstrumentsRepo().getInstruments().size());
    }

    @Test
    public void removeInstrument() {
        addInstrument();
        Session s = new Session(Frontend.Utils.Generics.stringToDate("21/12/2000"), admin.getSessionsRepo(), admin.getUsersRepo(),
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
        assertEquals(2, admin.getUsersRepo().getUsers().size());
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
        assertEquals(2, admin.getUsersRepo().getUsers().size());
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

        assertNull(admin.getUsersRepo().getUser("teste3"));
        assertEquals(1, admin.getUsersRepo().getUsers().size());
        assertNull(album.getProdutor());
    }

    @Test
    public void showAllSessionRequests() {
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