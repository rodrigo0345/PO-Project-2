package Backend.Users;

import Backend.Instruments.*;

public class Admin extends User {

    public Admin(String name, String email, String username, String password) {
        super(name, email, username, password);
    }

    public void addInstrument(String name) {
        Instrument instrument = new Instrument(name);
        Backend.Instruments.Repos.addInstrument(instrument);
    }

    public void removeInstrument(String name) {
        Backend.Instruments.Repos.removeInstrument(name);
    }

    public void addMusician(String name, String email, String username, String password) {
        Musician musician = new Musician(name, email, username, password);
        Backend.Users.Repos.addUser(musician);
    }

    public void addProdutor(String name, String email, String username, String password) {
        Produtor produtor = new Produtor(name, email, username, password);
        Backend.Users.Repos.addUser(produtor);
    }

    public void removeUser(String username) {
        Backend.Users.Repos.removeUser(username);
    }
}
