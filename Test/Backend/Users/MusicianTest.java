package Backend.Users;

import Backend.Albums.Album;
import org.junit.Test;

import static org.junit.Assert.*;

public class MusicianTest {

    private Admin admin = new Admin("Teste", "Teste@gmail.com", "admin", "admin",
            new Backend.Instruments.Repos(), new Backend.Albums.Repos(), new Backend.Users.Repos(), new Backend.Sessions.Repos());

    private Produtor produtor = new Produtor("name", "email@email.com", "name", "name",
            admin.getUsersRepo(), admin.getInstrumentsRepo(), admin.getAlbumsRepo(), admin.getSessionsRepo());

    private Musician m = admin.addMusician("teste", "teste@gmail.com", "teste3", "teste");


    // not directly tested, but indirectly tested in AdminTest
    @Test
    public void addAlbum() {}

    // not directly tested, but indirectly tested in AdminTest
    @Test
    public void removeAlbum() {}

    @Test
    public void getAlbums() {
        assertEquals(0, m.getAlbums().size());
        Backend.Albums.AlbumEditado a = new Backend.Albums.AlbumEditado("R", "rock",
                admin.getInstrumentsRepo(), admin.getAlbumsRepo(), admin.getUsersRepo(), admin.getSessionsRepo(), produtor);

        Backend.Sessions.Session s = a.addSession(Frontend.Utils.Generics.stringToDate("21/12/2040"));
        admin.acceptSessionRequest(s.getId());

        produtor.addMusicianToSession(m, s);
        assertEquals(1, m.getAlbums().size());
    }

    @Test
    public void addInstrument() {
        Backend.Albums.AlbumEditado a = new Backend.Albums.AlbumEditado("R", "rock",
                admin.getInstrumentsRepo(), admin.getAlbumsRepo(), admin.getUsersRepo(), admin.getSessionsRepo(), produtor);

        Backend.Sessions.Session s = a.addSession(Frontend.Utils.Generics.stringToDate("21/12/2040"));
        admin.acceptSessionRequest(s.getId());
        admin.addInstrument("flute");
        m.requestInstrument(admin.getInstrumentsRepo().getInstrument("flute"), s);
        assertTrue(m.getInstruments().contains("flute"));
        assertEquals(1, m.getInstruments().size());
    }

    @Test
    public void removeInstrument() {
    }

    @Test
    public void getInstruments() {
    }

    @Test
    public void addSession() {
    }

    @Test
    public void testEquals() {
    }
}