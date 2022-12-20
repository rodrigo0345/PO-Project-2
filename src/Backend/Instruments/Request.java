package Backend.Instruments;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Request implements Serializable{
    private UUID id;
    private int quantidade;
    private Date dataInicial;
    private Date dataFinal;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Date getDataInicial() {
        return dataInicial;
    }
    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }
    public Date getDataFinal() {
        return dataFinal;
    }
    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    @Override
    public String toString() {
        return "ID = " + id + ", quantidade = " + quantidade + ", data inicial = " + dataInicial + 
                ", data final = " + dataFinal;
    }
}