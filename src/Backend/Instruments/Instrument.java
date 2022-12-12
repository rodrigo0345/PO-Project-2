package Backend.Instruments;

import java.io.Serializable;
import java.util.UUID;

public class Instrument implements Serializable {
    private static final long serialVersionUID = 6L;
    private final String name;
    private final UUID id;

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
