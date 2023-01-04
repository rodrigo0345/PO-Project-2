package Backend.Users;

import Backend.Albums.Album;
import Backend.Albums.AlbumEditado;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProdutorTest {

    private final Admin admin = new Admin("Teste", "Teste@gmail.com", "admin", "admin",
            new Backend.Instruments.Repos(), new Backend.Albums.Repos(), new Backend.Users.Repos(), new Backend.Sessions.Repos());

    private final Produtor produtor = new Produtor("Name", "email@email.com", "name", "name",
            admin.getUsersRepo(), admin.getInstrumentsRepo(), admin.getAlbumsRepo(), admin.getSessionsRepo());

    private final Album original = new Album("original", "rock", Frontend.Utils.Generics.stringToDate("10/11/2000 15:30"), produtor,
            admin.getInstrumentsRepo(), admin.getAlbumsRepo(), admin.getUsersRepo(), admin.getSessionsRepo());

    private final Musician m = admin.addMusician("Teste", "teste@gmail.com", "teste3", "teste");

    AlbumEditado a = new AlbumEditado("R", "rock", original,
            admin.getInstrumentsRepo(), admin.getAlbumsRepo(), admin.getUsersRepo(), admin.getSessionsRepo(), produtor);
    @Test
    public void addProjeto() {
        try{
            produtor.addProjeto(a);
        } catch (Exception e) {
            assertEquals("This producer is already the producer of the given project!", e.getMessage());
        }
        assertEquals(1, produtor.getProjetos().size());
    }

    @Test
    public void removeProjeto() {
        addProjeto();
        assertEquals(1, produtor.getProjetos().size());
        Produtor p1 = admin.addProdutor("Rodrigo", "rrr@gmail.com", "rrr", "rrr");
        produtor.removeProjeto(a, p1);
        assertEquals(0, produtor.getProjetos().size());
        assertNull(produtor.getProjeto(a.getTitulo()));
        assertEquals(p1.getUsername(), a.getProdutor().getUsername());
    }

    @Test
    public void getProjetos() {
        removeProjeto();
        assertEquals(0, produtor.getProjetos().size());
        addProjeto();
        assertEquals(1, produtor.getProjetos().size());
    }

    @Test
    public void addOldAlbum() {
        try{
            produtor.addOldAlbum(original);
        } catch (Exception e) {
            assertEquals("This producer is already the producer of the given project!", e.getMessage());
        }

        assertEquals(1, produtor.getOldAlbums().size());

        new Album("Reee", "rock", Frontend.Utils.Generics.stringToDate("10/11/2000 16:40"), produtor,
                admin.getInstrumentsRepo(), admin.getAlbumsRepo(), admin.getUsersRepo(), admin.getSessionsRepo());
        assertEquals(2, produtor.getOldAlbums().size());
    }

    @Test
    public void removeOldAlbum() {
        addOldAlbum();
        assertEquals(2, produtor.getOldAlbums().size());
        produtor.removeOldAlbum(original);
        assertEquals(1, produtor.getOldAlbums().size());
    }

    @Test
    public void getOldAlbums() {
        removeOldAlbum();
        assertEquals(1, produtor.getOldAlbums().size());
    }

    @Test
    public void getProjeto() {
        addProjeto();
        assertEquals(a.getTitulo(), produtor.getProjeto(a.getTitulo()).getTitulo());
    }

    @Test
    public void createAlbumEdit() {
        try{
            produtor.createAlbumEdit("R", "rock");
        } catch (Exception e) {
            assertEquals("O produtor já é o produtor do dado projeto!", e.getMessage());
        }
        assertEquals(1, produtor.getProjetos().size());
    }

    @Test
    public void findSessionByDate() {
        try{
            produtor.findSessionByDate(Frontend.Utils.Generics.stringToDate("10/11/2000 10:00"), Frontend.Utils.Generics.stringToDate("10/11/2000 23:00"));
        } catch (Exception e) {
            assertEquals("No sessions found for the given date.", e.getMessage());
        }

        new Backend.Sessions.Session(Frontend.Utils.Generics.stringToDate("10/11/2030 10:30"), Frontend.Utils.Generics.stringToDate("10/11/2030 11:30"), a,
                a.getSessionsRepo(), a.getUsersRepo(), a.getInstrumentsRepo(), a.getAlbumsRepo());

        System.out.println(produtor.getProjeto("R").getLastSessionAdded().getDataInicio());
        assertEquals(produtor.getProjeto("R").getLastSessionAdded(), produtor.findSessionByDate(Frontend.Utils.Generics.stringToDate("10/11/2030 10:29"), Frontend.Utils.Generics.stringToDate("10/11/2030 11:30")));
    }

    @Test
    public void getOldAlbum() {
        addOldAlbum();
        assertEquals(original.getTitulo(), produtor.getOldAlbum(original.getTitulo()).getTitulo());
    }
}