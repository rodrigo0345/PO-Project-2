package Backend.Instruments;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Repos.
 */
public class Repos implements Serializable {
    private final Map<String, Instrument> instruments = new HashMap<>();
    @Serial
    private static final long serialVersionUID = 5L;

    /**
     * Add instrument.
     *
     * @param instrument the instrument
     */
    public void addInstrument(Instrument instrument) {
        this.instruments.put(instrument.getName().toLowerCase(), instrument);
    }

    /**
     * Gets instrument.
     *
     * @param name the name
     * @return the instrument
     */
    public Instrument getInstrument(String name) {
        return this.instruments.get(name);
    }

    /**
     * Remove instrument.
     *
     * @param name the name
     */
    public void removeInstrument(String name) {
        this.instruments.remove(name);
    }

    /**
     * Gets instruments.
     *
     * @return the instruments
     */
    public Map<String, Instrument> getInstruments() {
        return this.instruments;
    }
}
