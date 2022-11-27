package Backend.Instruments;

import java.util.HashMap;
import java.util.Map;

public class Repos {
    private static Map<String, Instrument> instruments = new HashMap<>();

    public static void addInstrument(Instrument instrument) {
        instruments.put(instrument.getName(), instrument);
    }

    public static Instrument getInstrument(String name) {
        return instruments.get(name);
    }

    public static void removeInstrument(String name) {
        instruments.remove(name);
    }
}
