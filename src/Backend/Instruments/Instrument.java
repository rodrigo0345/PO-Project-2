package Backend.Instruments;

import Frontend.Utils.ConsoleColors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The type Instrument.
 */
public class Instrument implements Serializable, Comparable<Instrument>, Cloneable {
    @Serial
    private static final long serialVersionUID = 6L;
    private final String name;
    private UUID id;
    private int quantidade;
    private LocalDateTime dataRequisicao;

    
    /**
     * Instantiates a new Instrument.
     * 
     * @param name
     * @param quantidade
     * @param dataRequisicao
     */
    public Instrument(String name, int quantidade, LocalDateTime dataRequisicao) {
        this.name = name;
        this.quantidade = quantidade;
        this.dataRequisicao = dataRequisicao;
        this.id = UUID.randomUUID();
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets quantidade.
     *
     * @return the quantidade
     */
    public int getQuantidade() {
            return this.quantidade;
        }

    /**
     * Sets quantidade.
     *
     * @param quantidade the quantidade
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return
     */
    public LocalDateTime getDataRequisicao() {
        return dataRequisicao;
    }

    /**
     * @param dataRequisicao
     */
    public void setDataRequisicao(LocalDateTime dataRequisicao) {
        this.dataRequisicao = dataRequisicao;
    }

    @Override
    public String toString() {

        ConsoleColors color = new ConsoleColors();
        System.out.println(color.getWHITE());
        
        return "Instrumento: " + this.name + " | ID: " + this.id + " | Quantidade: " + this.quantidade;
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
