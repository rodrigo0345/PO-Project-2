package Backend.Albums;

import Backend.Sessions.Session;
import Backend.Users.Produtor;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class AlbumEditadoTest {
    AlbumEditado album = new AlbumEditado("Random");

    @Test
    public void setProducer() {
        Produtor p1 = new Produtor("Rodrigo", "rodrigo@gmail.com",
                            "rodrigo", "rodrigo", album.getUsersRepo(),
                album.getInstrumentsRepo(), album.getAlbumsRepo(),
                album.getSessionsRepo());
        album.setProdutor(p1);
        assertEquals(album.getProdutor().toString(), p1.toString());
        album.setProdutor(null);
        assertEquals(album.getProdutor().toString(), p1.toString());
        assertEquals(p1.getProjeto("Random").toString(), album.toString());
    }

    @Test
    public void isEdited() {
        assertFalse(album.isEdited());
        album.setAlbumAsComplete();
        assertTrue(album.isEdited());
        assertEquals(album.getDate(), (LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)));
    }

    @Test
    public void addSession() {
        album.addSession(Frontend.Utils.Generics.stringToDate("15/01/2023 15:30"), Frontend.Utils.Generics.stringToDate("15/01/2023 16:30"));

        try{
            album.addSession(Frontend.Utils.Generics.stringToDate("10/01/2020 15:30"), Frontend.Utils.Generics.stringToDate("15/01/2023 16:30"));
        } catch(IllegalArgumentException e){
            assertEquals(e.getMessage(), "A data de ínicio já foi extrapolada");
        }

        // testes para quando o album já tenha sido editado
        isEdited();

        try{
            album.addSession(Frontend.Utils.Generics.stringToDate("10/01/2023 15:30"),  Frontend.Utils.Generics.stringToDate("10/01/2023 16:30"));
        } catch(IllegalArgumentException e){
            assertEquals(e.getMessage(), "O álbum já está terminado");
        }

        assertEquals(1, album.getAllSessions().size());
        assertTrue(album.getAllSessions().contains(album.getLastSessionAdded()));
    }

    @Test
    public void removeSession() {
        album.addSession(Frontend.Utils.Generics.stringToDate("10/01/2023 15:30"), Frontend.Utils.Generics.stringToDate("10/01/2023 16:30"));
        boolean success = album.removeSession(album.getLastSessionAdded().getId());
        assertTrue(success);
        assertEquals(0, album.getAllSessions().size());

        album.addSession(Frontend.Utils.Generics.stringToDate("28/01/2023 15:30"), Frontend.Utils.Generics.stringToDate("28/01/2023 16:30"));
        success = album.removeSession(Frontend.Utils.Generics.stringToDate("28/01/2023 15:30"));
        assertTrue(success);

        success = album.removeSession(Frontend.Utils.Generics.stringToDate("30/01/2023 15:30"));
        assertFalse(success);
    }

    @Test
    public void markSessionAsCompleted() {
        album.addSession(Frontend.Utils.Generics.stringToDate("10/01/2023 15:30"), Frontend.Utils.Generics.stringToDate("10/01/2023 16:30"));
        album.markSessionAsCompleted(album.getLastSessionAdded().getId());
        Backend.Sessions.Session s = album.getLastSessionAdded();
        assertTrue(s.isCompleted());
    }

}