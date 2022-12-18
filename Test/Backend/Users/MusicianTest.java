package Backend.Users;

import Backend.Albums.Album;
import org.junit.Test;

import static org.junit.Assert.*;

public class MusicianTest {

    private Admin admin = new Admin("Teste", "Teste@gmail.com", "admin", "admin",
            new Backend.Instruments.Repos(), new Backend.Albums.Repos(), new Backend.Users.Repos(), new Backend.Sessions.Repos());

    private Produtor produtor = new Produtor("name", "email@email.com", "name", "name",
            admin.getUsersRepo(), admin.getInstrumentsRepo(), admin.getAlbumsRepo(), admin.getSessionsRepo());

    private Album original = new Album("original", "rock", Frontend.Utils.Generics.stringToDate("10/11/2000"), produtor,
            admin.getInstrumentsRepo(), admin.getAlbumsRepo(), admin.getUsersRepo(), admin.getSessionsRepo());

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
        Backend.Albums.AlbumEditado a = new Backend.Albums.AlbumEditado("R", "rock", original,
                admin.getInstrumentsRepo(), admin.getAlbumsRepo(), admin.getUsersRepo(), admin.getSessionsRepo(), produtor);

        Backend.Sessions.Session s = a.addSession(Frontend.Utils.Generics.stringToDate("21/12/2040"));
        admin.acceptSessionRequest(s.getId());

        produtor.addMusicianToSession(m, s);
        assertEquals(1, m.getAlbums().size());
    }

    @Test
    public void addArtistInstrument() {
        Backend.Albums.AlbumEditado a = new Backend.Albums.AlbumEditado("R", "rock", original,
                admin.getInstrumentsRepo(), admin.getAlbumsRepo(), admin.getUsersRepo(), admin.getSessionsRepo(), produtor);

        Backend.Sessions.Session s = a.addSession(Frontend.Utils.Generics.stringToDate("21/12/2040"));
        admin.acceptSessionRequest(s.getId());
        s.addInvitedMusician(m);
        admin.addInstrument("flute");
        m.addArtistInstrument(admin.getInstrumentsRepo().getInstrument("flute"));
        m.requestInstrument(admin.getInstrumentsRepo().getInstrument("flute"), s);
        assertTrue(m.getInstruments().contains(admin.getInstrumentsRepo().getInstrument("flute")));
        assertEquals(1, m.getInstruments().size());
    }

    @Test
    public void removeArtistInstrument() {
    }

    @Test
    public void getInstruments() {
        addArtistInstrument();
        assertEquals(1, m.getInstruments().size());
    }

    @Test
    public void addSession() {
        Backend.Albums.AlbumEditado a = new Backend.Albums.AlbumEditado("R", "rock", original,
                admin.getInstrumentsRepo(), admin.getAlbumsRepo(), admin.getUsersRepo(), admin.getSessionsRepo(), produtor);

        Backend.Sessions.Session s = a.addSession(Frontend.Utils.Generics.stringToDate("21/12/2040"));
        admin.acceptSessionRequest(s.getId());

        // already uses addSession
        s.addInvitedMusician(m);

        admin.addInstrument("flute");
        m.addArtistInstrument(admin.getInstrumentsRepo().getInstrument("flute"));
        m.requestInstrument(admin.getInstrumentsRepo().getInstrument("flute"), s);

        try{
            m.addSession(a,s);
        } catch (Exception e) {
            assertEquals("Musician was already in this session", e.getMessage());
        }

        int count = 0;
        for (Backend.Sessions.Session session : admin.getSessionsRepo().getSessions()) {
            if (session.getInvitedMusicians().containsKey(m.getUsername())) {
                count++;
            }
        }
        assertEquals(1, count);
    }

    @Test
    public void testEquals() {
        addSession();
        m.equals(admin.getAlbumsRepo().getAlbum("R").getArtist("teste3"));
    }
}