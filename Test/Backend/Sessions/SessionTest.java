package Backend.Sessions;

import Backend.Albums.AlbumEditado;
import Backend.Instruments.Instrument;
import Backend.Users.Admin;
import Backend.Users.Musician;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.*;

public class SessionTest {
    AlbumEditado album = new AlbumEditado("Random");
    Session session = new Session(Frontend.Utils.Generics.stringToDate("11/03/2050 10:00"), Frontend.Utils.Generics.stringToDate("11/03/2050 12:30"), album, album.getSessionsRepo(),
            album.getUsersRepo(), album.getInstrumentsRepo(), album.getAlbumsRepo());

    private final Admin admin = new Admin("Teste", "Teste@gmail.com", "admin", "admin",
            album.getInstrumentsRepo(), album.getAlbumsRepo(), album.getUsersRepo(), album.getSessionsRepo());

    @Test
    public void addInvitedMusician() {
        admin.acceptSessionRequest(session.getId());

        Backend.Users.Musician m = new Musician("Teste", "Teste@gmail.com", "teste", "teste",
                album.getUsersRepo(), album.getInstrumentsRepo(), album.getAlbumsRepo(), album.getSessionsRepo());

        session.addInvitedMusician(m);
        assertEquals(1, session.getInvitedMusicians().size());
    }

    @Test
    public void getInvitedMusician() {
        addInvitedMusician();
        Backend.Users.Musician m = new Musician("novoTeste", "Teste@gmail.com", "nteste", "teste",
                album.getUsersRepo(), album.getInstrumentsRepo(), album.getAlbumsRepo(), album.getSessionsRepo());
        session.addInvitedMusician(m);
        assertEquals("teste", session.getInvitedMusician("teste").getUsername());
        assertEquals("nteste", session.getInvitedMusician(m).getUsername());
        assertEquals(2, session.getInvitedMusicians().size());
    }

    @Test
    public void addPendendingInstrument() {
        admin.acceptSessionRequest(session.getId());

        Instrument i = new Instrument("guitarra", 4, LocalDateTime.now());

        try {
            session.addPendingInstrument(i, 1);
        } catch (IllegalArgumentException e){
            assertEquals( "O intrumento que pretende requisitar ainda não existe em estúdio.",e.getMessage());
        }

        Admin su = new Admin("Teste", "Teste@gmail.com", "admin", "admin", album.getInstrumentsRepo(),
                album.getAlbumsRepo(), album.getUsersRepo(), album.getSessionsRepo() );
        su.addInstrument("Guitarra", 10);
        su.addInstrument("Violino", 4);
        session.addPendingInstrument(i, 1);
    }

    @Test
    public void getPendentInstruments() {
        admin.acceptSessionRequest(session.getId());

        addPendendingInstrument();
        session.addPendingInstrument(new Instrument("violino", 4, LocalDateTime.now()), 1);
        assertEquals(2, session.getPendentInstruments().size());
    }

    @Test
    public void approveInstrument() {
        admin.acceptSessionRequest(session.getId());

        getPendentInstruments();
        Admin su = new Admin("Teste", "Teste@gmail.com", "admin", "admin", album.getInstrumentsRepo(),
                album.getAlbumsRepo(), album.getUsersRepo(), album.getSessionsRepo() );
        su.acceptInstrumentRequest("guitarra", session);
        assertEquals(1, session.getApprovedInstruments().size());
        assertEquals(1, session.getPendentInstruments().size());

        su.denyInstrumentRequest("violino", session);
        assertEquals(0, session.getPendentInstruments().size());
        assertEquals(1, session.getApprovedInstruments().size());

        su.denyInstrumentRequest("random", session);
    }

    @Test
    public void denyInstrument() {
        admin.acceptSessionRequest(session.getId());

        getPendentInstruments();
        Admin su = new Admin("Teste", "Teste@gmail.com", "admin", "admin", album.getInstrumentsRepo(),
                album.getAlbumsRepo(), album.getUsersRepo(), album.getSessionsRepo() );
        su.denyInstrumentRequest("guitarra", session);
        assertEquals(0, session.getApprovedInstruments().size());
        assertEquals(1, session.getPendentInstruments().size());

        su.denyInstrumentRequest("violino", session);
        assertEquals(0, session.getPendentInstruments().size());
        assertEquals(0, session.getApprovedInstruments().size());

        su.denyInstrumentRequest("random", session);
    }

    @Test
    public  void doesSessionOverlap(){
        Session s = new Session(Frontend.Utils.Generics.stringToDate("11/03/2050 10:00"), Frontend.Utils.Generics.stringToDate("11/03/2050 12:20"), album, album.getSessionsRepo(),
                album.getUsersRepo(), album.getInstrumentsRepo(), album.getAlbumsRepo());
        assertTrue(session.doesSessionOverlap(s));
    }
}