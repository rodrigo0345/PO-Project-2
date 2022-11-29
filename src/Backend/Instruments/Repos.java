package Backend.Instruments;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Repos implements Serializable {
    private Map<String, Instrument> instruments = new HashMap<>();

    public void addInstrument(Instrument instrument) {
        instruments.put(instrument.getName(), instrument);
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
