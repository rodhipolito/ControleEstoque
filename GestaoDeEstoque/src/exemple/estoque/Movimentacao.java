package exemple.estoque;

import java.util.ArrayList;
import java.util.List;

public class Movimentacao {
    private List<String> historico;

    public Movimentacao() {
        this.historico = new ArrayList<>();
    }

    public void registrarMovimentacao(String tipo, String produto, int quantidade) {
        String registro = tipo + ": " + produto + " - " + quantidade + " unidades";
        historico.add(registro);
    }

    public List<String> getHistorico() {
        return historico;
    }
}
