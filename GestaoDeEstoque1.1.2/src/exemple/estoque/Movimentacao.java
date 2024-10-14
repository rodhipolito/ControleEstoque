package exemple.estoque;

import java.util.ArrayList;
import java.util.List;

public class Movimentacao {
    private List<String> historico;

    public Movimentacao() {
        historico = new ArrayList<>();
    }

    public void registrarMovimentacao(String tipo, String nomeProduto, int quantidade) {
        String registro = tipo + ": " + nomeProduto + " - " + quantidade + " unidades.";
        historico.add(registro);
    }

    public List<String> getHistorico() {
        return historico;
    }
}
