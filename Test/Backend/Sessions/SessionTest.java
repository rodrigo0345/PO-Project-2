package Backend.Sessions;

import Backend.Albums.Album;
import Backend.Instruments.Instrument;
import Backend.Users.Admin;
import Backend.Users.Musician;
import org.junit.Test;

import static org.junit.Assert.*;

public class SessionTest {
    Album album = new Album("Random");
    Session session = new Session(Frontend.Utils.Generics.stringToDate("11/03/2050"), album.getSessionsRepo(),
                                            album.getUsersRepo(), album.getInstrumentsRepo(), album.getAlbumsRepo());

    @Test
    public void addInvitedMusician() {
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
        Instrument i = new Instrument("guitarra");

        try {
            session.addPendingInstrument(i);
        } catch (IllegalArgumentException e){
            assertEquals( "The instrument you requested does not exist in the studio yet.",e.getMessage());
        }

        Admin su = new Admin("Teste", "Teste@gmail.com", "admin", "admin", album.getInstrumentsRepo(),
                                                    album.getAlbumsRepo(), album.getUsersRepo(), album.getSessionsRepo() );
        su.addInstrument("Guitarra");
        session.addPendingInstrument(i);
    }

    @Test
    public void getPendentInstruments() {
    }

    @Test
    public void approveInstrument() {
    }

    @Test
    public void denyInstrument() {
    }
}