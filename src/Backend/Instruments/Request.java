package Backend.Instruments;

import java.io.Serializable;
import java.util.UUID;

public class Request implements Serializable{
    private UUID id;
    private int quantidade;
    private UUID id_sessao;

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

    public UUID getId_sessao() {
        return id_sessao;
    }
    public void setId_sessao(UUID id_sessao) {
        this.id_sessao = id_sessao;
    }

    @Override
    public String toString() {
        return "ID = " + id + ", quantidade = " + quantidade + ", id_sessao = " + id_sessao;
    }
}