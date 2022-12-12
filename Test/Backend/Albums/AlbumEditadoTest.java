package Backend.Albums;

import Backend.Users.Produtor;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class AlbumEditadoTest {
    AlbumEditado album = new AlbumEditado("Random");

    @Test
    public void setProducer() {
        Produtor p1 = new Produtor("Rodrigo", "rodrigo@gmail.com",
                            "rodrigo", "rodrigo", album.getUsersRepo(),
                                album.getInstrumentsRepo(), album.getAlbumsRepo(),
                                    album.getSessionsRepo());
        album.setProducer(p1);
        assertEquals(album.getProducer().toString(), p1.toString());
        album.setProducer(null);
        assertEquals(album.getProducer().toString(), p1.toString());
        assertEquals(p1.getProjeto("Random").toString(), album.toString());
    }

    @Test
    public void isEdited() {
        assertFalse(album.isEdited());
        album.setAlbumAsComplete();
        assertTrue(album.isEdited());
        assertEquals(album.getDate(), LocalDate.now());
    }

    @Test
    public void addSession() {
    }

    @Test
    public void removeSession() {
    }

    @Test
    public void markSessionAsCompleted() {
    }

    @Test
    public void getAllSessions() {
    }
}