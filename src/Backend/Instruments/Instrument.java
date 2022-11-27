package Backend.Instruments;

import java.util.UUID;

public class Instrument {
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
}
