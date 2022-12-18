package Backend.Users;

import Backend.Albums.Album;
import Backend.Albums.AlbumEditado;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProdutorTest {

    private Admin admin = new Admin("Teste", "Teste@gmail.com", "admin", "admin",
            new Backend.Instruments.Repos(), new Backend.Albums.Repos(), new Backend.Users.Repos(), new Backend.Sessions.Repos());

    private Produtor produtor = new Produtor("name", "email@email.com", "name", "name",
            admin.getUsersRepo(), admin.getInstrumentsRepo(), admin.getAlbumsRepo(), admin.getSessionsRepo());

    private Album original = new Album("original", "rock", Frontend.Utils.Generics.stringToDate("10/11/2000"), produtor,
            admin.getInstrumentsRepo(), admin.getAlbumsRepo(), admin.getUsersRepo(), admin.getSessionsRepo());

    private Musician m = admin.addMusician("teste", "teste@gmail.com", "teste3", "teste");

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
        //produtor.removeProjeto(a, p1);
        assertEquals(0, produtor.getProjetos().size());
        assertNull(produtor.getProjeto(a.getTitulo()));
        assertEquals(p1.getUsername(), a.getProdutor().getUsername());
    }

    @Test
    public void getProjetos() {
    }

    @Test
    public void addOldAlbum() {
    }

    @Test
    public void removeOldAlbum() {
    }

    @Test
    public void getOldAlbums() {
    }

    @Test
    public void getProjeto() {
    }

    @Test
    public void createAlbumEdit() {
    }

    @Test
    public void findSessionByDate() {
    }

    @Test
    public void getOldAlbum() {
    }
}