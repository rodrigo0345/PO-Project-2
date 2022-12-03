package Backend.Instruments;

import java.io.Serializable;
import java.util.UUID;

public class Instrument implements Serializable {
    private static long serialVersionUID = 6L;
    private String name;
    private UUID id;

    public Instrument(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Instrument=" + name + ", id=" + id;
    }
}
