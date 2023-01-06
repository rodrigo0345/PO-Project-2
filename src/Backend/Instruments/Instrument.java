package Backend.Instruments;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class Instrument implements Serializable, Comparable<Instrument>, Cloneable {
    @Serial
    private static final long serialVersionUID = 6L;
    private final String name;
    private UUID id;
    private int quantidade;

    public Instrument(String name, int quantidade) {
        this.name = name;
        this.quantidade = quantidade;
        this.id = UUID.randomUUID();
    }

    public String getName() {
        return this.name;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    
    public int getQuantidade() {
            return this.quantidade;
        }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Instrument=" + this.name + ", id=" + this.id + ", quantidade=" + this.quantidade;
    }

    @Override
    public int compareTo(Instrument o) {
        return o.name.toLowerCase().compareTo(this.name.toLowerCase());
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
