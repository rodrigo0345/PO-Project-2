package Backend.Instruments;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Repos implements Serializable {
    private final Map<String, Instrument> instruments = new HashMap<>();
    @Serial
    private static final long serialVersionUID = 5L;

    public void addInstrument(Instrument instrument) {
        this.instruments.put(instrument.getName().toLowerCase(), instrument);
    }

    public Instrument getInstrument(String name) {
        return this.instruments.get(name);
    }

    public void removeInstrument(String name) {
        this.instruments.remove(name);
    }

    public Map<String, Instrument> getInstruments() {
        return this.instruments;
    }
}
