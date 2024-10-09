package exemple.estoque;

import java.util.ArrayList;
import java.util.List;

public class Movimentacao {
    private List<String> historico;

    public Movimentacao() {
        this.historico = new ArrayList<>();
    }

    public void registrarMovimentacao(String tipo, String nomeProduto, int quantidade) {
        historico.add(tipo + ": " + nomeProduto + " - " + quantidade + " unidades");
    }

    public List<String> getHistorico() {
        return historico;
    }
}
