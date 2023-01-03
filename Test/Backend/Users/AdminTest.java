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

    private final Admin admin = new Admin("Teste", "Teste@gmail.com", "admin", "admin",
            new Backend.Instruments.Repos(), new Backend.Albums.Repos(), new Backend.Users.Repos(), new Backend.Sessions.Repos());

    private final Produtor produtor = new Produtor("name", "email@email.com", "name", "name",
            admin.getUsersRepo(), admin.getInstrumentsRepo(), admin.getAlbumsRepo(), admin.getSessionsRepo());

    private final Album original = new Album("original", "rock", Frontend.Utils.Generics.stringToDate("10/11/2000 15:30"), produtor,
            admin.getInstrumentsRepo(), admin.getAlbumsRepo(), admin.getUsersRepo(), admin.getSessionsRepo());
    private final AlbumEditado album = new AlbumEditado("R", "rock", original,
                admin.getInstrumentsRepo(), admin.getAlbumsRepo(), produtor.getUsersRepo(), admin.getSessionsRepo(), produtor);
    @Test
    public void addInstrument() {
        admin.addInstrument("flute", 5);
        assertNotNull(admin.getInstrumentsRepo().getInstrument("flute"));
        assertEquals(1, admin.getInstrumentsRepo().getInstruments().size());
    }

    @Test
    public void removeInstrument() {
        addInstrument();
        Session s = new Session(Frontend.Utils.Generics.stringToDate("21/12/2040 10:00"), Frontend.Utils.Generics.stringToDate("21/12/2040 12:30"), album, album.getSessionsRepo(),
                album.getUsersRepo(), album.getInstrumentsRepo(), album.getAlbumsRepo());

        admin.acceptSessionRequest(s.getId()); // needs to be approved first

        s.addPendingInstrument(new Instrument("flute", 6), 1);
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
        assertEquals("Username já existe", error);

        admin.addMusician("Teste", "teste@gmail.com", "teste3", "teste");
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
        assertEquals("Username já existe", error);

        admin.addProdutor("Teste", "teste@gmail.com", "teste3", "teste");
        assertNotNull(admin.getUsersRepo().getUser("teste3"));
        assertEquals(3, admin.getUsersRepo().getUsers().size());
    }

    @Test
    public void removeUser() {
        addProdutor();
        Album album = new Album(
                            "Something",
                            "Rock",
                            Frontend.Utils.Generics.stringToDate("11/12/2000 15:30"),
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
        admin.addProdutor("R", "teste@gmail.com", "rodrigo", "0");
        album.setProdutorOriginal((Produtor)admin.getUsersRepo().getUser("rodrigo", "0"));
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
        Session s1 = new Session(Frontend.Utils.Generics.stringToDate("11/12/2023 10:00"), Frontend.Utils.Generics.stringToDate("11/12/2023 12:30"), album, album.getSessionsRepo(),
                album.getUsersRepo(), album.getInstrumentsRepo(), album.getAlbumsRepo());
        Session s2 = new Session(Frontend.Utils.Generics.stringToDate("14/12/2023 10:00"), Frontend.Utils.Generics.stringToDate("14/12/2023 12:30"), album, album.getSessionsRepo(),
                album.getUsersRepo(), album.getInstrumentsRepo(), album.getAlbumsRepo());

        Set<Session> aux = new TreeSet<>();
        aux.add(s1); aux.add(s2);
        Set<Session> s = admin.getAllSessionRequests();
        assertEquals(aux, s);
    }

    @Test
    public void getStats() {
        AlbumEditado album = new AlbumEditado(
                "Something",
                "Rock",
                original,
                admin.getInstrumentsRepo(),
                admin.getAlbumsRepo(),
                admin.getUsersRepo(),
                admin.getSessionsRepo(),
                produtor
        ); // not finished
        getAllSessionRequests();
        this.album.setAlbumAsComplete();
        admin.acceptSessionRequest(this.album.getLastSessionAdded().getId());
        this.album.getLastSessionAdded().setCompleted(true);
        assertEquals( "Álbums não terminados: 1\n" +
                "Albums terminados: 1\n" +
                "Percentagem de sessões completas: 50.0%",admin.getStats());
    }

    @Test
    public void addAlbum() {
        admin.addAlbum("Hello", "Rock", Frontend.Utils.Generics.stringToDate("11/12/2030 20:00"), produtor);
        assertNotNull(produtor.getOldAlbum("Hello"));
        assertNotNull(admin.getAlbumsRepo().getAlbum("Hello"));
    }

    @Test
    public void addMusicianToAlbum() {
        addAlbum();
        new Musician("teste", "test@gmail.com", "test003", "test",
                admin.getUsersRepo(), admin.getInstrumentsRepo(), admin.getAlbumsRepo(), admin.getSessionsRepo());
        admin.addMusicianToAlbum("test003", "R");
        assertNotNull(admin.getAlbumsRepo().getAlbum("R"));
    }

    @Test
    public void addProdutorToAlbum() {
        addAlbum();
        admin.addProdutorToAlbum("name", "R");
        assertNotNull(produtor.getOldAlbum("R"));
    }

    @Test
    public void addTrackToAlbum() {
        // already adds the track to a given album
        Backend.Tracks.Track t = new Backend.Tracks.Track(album, "Something", "Rock", 140);
        admin.addTrackToAlbum(album.getTitulo(), t);
        assertNotNull(album.getTracks().get("Something"));
    }

    @Test
    public void acceptInstrumentRequest() {
        Musician m = new Musician("Teste", "test@gmail.com", "tes", "tes", admin.getUsersRepo(),
                    admin.getInstrumentsRepo(), admin.getAlbumsRepo(), admin.getSessionsRepo());


        // new session
        Session s = new Session(Frontend.Utils.Generics.stringToDate("11/12/2030 10:00"), Frontend.Utils.Generics.stringToDate("11/12/2030 12:30"), album, album.getSessionsRepo(),
                album.getUsersRepo(), album.getInstrumentsRepo(), album.getAlbumsRepo());

        admin.acceptSessionRequest(s.getId());
        admin.addInstrument("guitarra", 10);

        s.addInvitedMusician(m);

        Instrument ins = s.addPendingInstrument("Guitarra");

        Set<Instrument> instruments = admin.getPendentInstruments();
        System.out.println(instruments);
        m.addArtistInstrument(ins);
        m.requestInstrument(ins, s, 1); // make sure the musician is in that session
        admin.acceptInstrumentRequest(ins, s);
        assertNotNull(s.getApprovedInstruments());
    }

    @Test
    public void denyInstrumentRequest() {
        Musician m = new Musician("Teste", "test@gmail.com", "tes", "tes", admin.getUsersRepo(),
                admin.getInstrumentsRepo(), admin.getAlbumsRepo(), admin.getSessionsRepo());


        // new session
        Session s = new Session(Frontend.Utils.Generics.stringToDate("11/12/2030 10:00"), Frontend.Utils.Generics.stringToDate("11/12/2030 12:30"), album, album.getSessionsRepo(),
                album.getUsersRepo(), album.getInstrumentsRepo(), album.getAlbumsRepo());

        admin.acceptSessionRequest(s.getId());
        admin.addInstrument("guitarra", 10);

        s.addInvitedMusician(m);

        Instrument ins = s.addPendingInstrument("Guitarra");

        Set<Instrument> instruments = admin.getPendentInstruments();
        System.out.println(instruments);

        m.addArtistInstrument(ins);
        m.requestInstrument(ins, s, 1); // make sure the musician is in that session
        admin.denyInstrumentRequest(ins.getName(), s);
        assertEquals(0, s.getApprovedInstruments().size());
    }
}