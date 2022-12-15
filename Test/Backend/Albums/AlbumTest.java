package Backend.Albums;

import Backend.Tracks.Track;
import Backend.Users.Musician;
import Backend.Users.Produtor;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class AlbumTest {
    private Album album = new Album("Random");

    @Test
    public void addArtist() {
        Musician m = new Musician("Test", "Test@gmail.com", "test",
                                    "test", album.getUsersRepo(), album.getInstrumentsRepo(),
                                        album.getAlbumsRepo(), album.getSessionsRepo());
        boolean success = album.addArtist(m);

        assertTrue("Add artist with success", success);

        // expect to see the new album in the artist and in the album the artist
        assertTrue("The artist is associated with the album", m.getAlbums().contains(album));
        assertTrue("The album contains the artist", album.getArtist(m.getUsername()).equals(m));
    }

    @Test
    public void getArtist() {
        addArtist(); // dependency
        assertEquals("Find the artist with username 'test'",
                        album.getArtist("test").getUsername(), "test");
        assertEquals("Expect null", album.getArtist("r"), null);
    }

    @Test
    public void deleteArtist() {
        addArtist();
        boolean success = album.removeArtist("test");
        assertTrue("Remove artist with success", success);
        assertNull("Remove artist", album.getArtist("test"));
        assertEquals("List of artists in the set should have 0 elements", album.getArtists().size(), 0);
        boolean success2 = album.removeArtist("test");
        assertFalse("Remove a non existing artist", success2);
    }

    @Test
    // already tests the setDate
    public void getDate() {

        album.getDate();
        assertNull("Expect the date to be empty", album.getDate());

        album.setDate(Frontend.Utils.Generics.stringToDate("11/12/2022"));
        assertNotNull("Expect the date to not be empty", album.getDate());
        assertEquals(album.getDate().toString(), "2022-12-11");
    }

    @Test
    public void getTitulo() {
        assertNotNull(album.getTitulo());
        assertEquals(album.getTitulo(), "Random");
    }

    @Test
    public void setTitulo() {
        // cannot have albums with the same title
        try{
            Album album1 = new Album("Teste", "Rock",
                    Frontend.Utils.Generics.stringToDate("11/12/2000"),null,
                    album.getInstrumentsRepo(), album.getAlbumsRepo(), album.getUsersRepo(),
                    album.getSessionsRepo());
            boolean success = album.setTitulo("Teste");
            assertFalse(success);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        boolean success;
        success = album.setTitulo("Rodrigo");
        assertTrue(success);
    }

    @Test
    public void getGenero() {
        assertNull(album.getGenero());
        album.setGenero("Rock");
        assertEquals(album.getGenero(), "Rock");
    }

    @Test
    public void addTrack() {
        Backend.Tracks.Track track = new Track(album, "Track1", "Pop", 120);
        Backend.Tracks.Track track2 = new Track(album, "Track1", "Rock", 320);
        boolean success = album.addTrack(track);
        assertFalse(success); // bc the track already exists
        success = album.addTrack(track2);
        assertFalse(success);
        assertEquals(album.getTracks().size(), 1);
    }

    @Test
    public void removeTrack() {
        addTrack();
        boolean success = album.removeTrack("Track1");
        assertTrue(success);
        success = album.removeTrack("Track1");
        assertFalse(success);
        assertEquals(album.getTracks().size(), 0);
    }

    @Test
    public void setProdutor() {
        Produtor p = new Produtor("Nome", "Nome@gmail.com", "nome", "nome",
                                    album.getUsersRepo(), album.getInstrumentsRepo(), album.getAlbumsRepo(),
                                        album.getSessionsRepo());

        album.setProdutor(p);
        assertEquals(album.getProdutor().getUsername(), "nome");
        assertEquals(p.getOldAlbums().size(), 1);
        assertEquals(p.getOldAlbum("Random").toString(), album.toString());
    }

    @Test
    public void getProdutor() {
        setProdutor();
        assertEquals(album.getProdutor().getUsername(), "nome");
        album.setProdutor(null);
        assertNotNull(album.getProdutor());
    }

    @Test
    public void compareTo() {
        try {
            Album album1 = new Album("Random", "Rock",
                    Frontend.Utils.Generics.stringToDate("11/12/2000"),null,
                    album.getInstrumentsRepo(), album.getAlbumsRepo(), album.getUsersRepo(),
                    album.getSessionsRepo());
            assertEquals(album.compareTo(album1), 0);
        } catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            assertEquals(album.compareTo(null), -1);
        }
    }
}