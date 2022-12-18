package Backend.Instruments;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Repos implements Serializable {
    private final Map<String, Instrument> instruments = new HashMap<>();
    private static final long serialVersionUID = 5L;

    public void addInstrument(Instrument instrument) {
        instruments.put(instrument.getName().toLowerCase(), instrument);
    }

    public Instrument getInstrument(String name) {
        return instruments.get(name);
    }

    public void removeInstrument(String name) {
        instruments.remove(name);
    }

    public Map<String, Instrument> getInstruments() {
        return instruments;
    }
}
