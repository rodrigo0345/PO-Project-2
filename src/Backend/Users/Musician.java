package Backend.Users;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import Backend.Albums.AlbumEditado;
import Backend.Instruments.Instrument;
import Backend.Sessions.Session;

public class Musician extends User {
    private final Set<Backend.Albums.Album> albums = new TreeSet<>();
    private final Set<Instrument> instruments = new TreeSet<>();
    private final Set<Backend.Sessions.Session> sessions = new TreeSet<>();

    public Musician(String name, String email, String username, String password, Backend.Users.Repos users,
            Backend.Instruments.Repos instruments, Backend.Albums.Repos albums, Backend.Sessions.Repos sessions) {
        super(name, email, username, password, users, instruments, albums, sessions);
        super.getUsersRepo().addUser(this);
    }

    public boolean addAlbum(Backend.Albums.Album album) {
        return albums.add(album);
    }

    // do not use it yet
    public void removeAlbum(Backend.Albums.Album album) {
        albums.remove(album);
    }

    public Set<Backend.Albums.Album> getAlbums() {
        return albums;
    }

    //não está a funcionar
    public void requestInstrument(Instrument instrument, Session s) throws IllegalArgumentException {
        if(!instruments.contains(instrument)) {
            throw new IllegalArgumentException("This musician does not play the given instrument!");
        }
        if(!s.getInvitedMusicians().containsKey(this.getUsername()))
            throw new IllegalArgumentException("This musician was not invited to the specified session!");
        
        //possibilidade de resolucao, não muito convincente
        UUID idInstrumento = instrument.getId();
        instrument.setId(s.getId());
        s.addPendingInstrument(instrument);
        instrument.setId(idInstrumento);
    }

    public void addArtistInstrument(Instrument instrument) {
        instruments.add(instrument);
    }

    public void removeArtistInstrument(Instrument instrument) {
        instruments.remove(instrument);
    }

    public Set<Instrument> getInstruments() {
        return instruments;
    }

    public void addSession(AlbumEditado album, Session newSession)
        throws IllegalArgumentException{
        this.addAlbum(album);
        boolean add = this.sessions.add(newSession);
        if (add == false){
            throw new IllegalArgumentException("Musician was already in this session");
        }
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    @Override
    public boolean equals(Object a) {
        if (a instanceof Musician) {
            return this.getUsername() == ((Musician) a).getUsername();
        }
        return false;
    }
}
