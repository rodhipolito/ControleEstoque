package exemple.estoque;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DashboardController {
    private Estoque estoque;
    private Movimentacao movimentacao;

    @FXML
    private ListView<String> produtosListView;

    @FXML
    private TextArea historicoTextArea;

    @FXML
    private TextField nomeProdutoEntradaField; // Campo para nome do produto
    @FXML
    private TextField quantidadeProdutoEntradaField;

    @FXML
    private TextField nomeProdutoSaidaField; // Campo para nome do produto
    @FXML
    private TextField quantidadeProdutoSaidaField;

    public void inicializarDados(Estoque estoque, Movimentacao movimentacao) {
        this.estoque = estoque;
        this.movimentacao = movimentacao;
        atualizarVisaoGeral();
    }

    private void atualizarVisaoGeral() {
        produtosListView.getItems().clear();
        for (Produto produto : estoque.getProdutos()) {
            produtosListView.getItems().add("ID " + produto.getId() + " - " + produto.getNome() + ": " + produto.getQuantidade() + " unidades");
        }
    }

    @FXML
    private void registrarEntrada() {
        String nomeProduto = nomeProdutoEntradaField.getText().trim(); // Lê o nome do produto
        try {
            int quantidade = Integer.parseInt(quantidadeProdutoEntradaField.getText().trim());
            estoque.entradaProduto(nomeProduto, quantidade); // Passar apenas nome e quantidade

            // Somente registra a movimentação se a entrada for bem-sucedida
            movimentacao.registrarMovimentacao("Entrada", nomeProduto, quantidade);
            atualizarVisaoGeral();
            atualizarHistorico();
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Por favor, insira um número válido para quantidade.");
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao registrar entrada: " + e.getMessage());
        }
    }

    @FXML
    private void registrarSaida() {
        String nomeProduto = nomeProdutoSaidaField.getText().trim(); // Lê o nome do produto
        try {
            int quantidade = Integer.parseInt(quantidadeProdutoSaidaField.getText().trim());

            // Verifica se o produto existe antes de registrar a saída
            Produto produto = estoque.procurarProduto(nomeProduto);
            if (produto == null) {
                mostrarAlerta("Produto não encontrado!", "O produto \"" + nomeProduto + "\" não está disponível no estoque.");
                return; // Não adiciona nada ao histórico
            }

            if (produto.getQuantidade() < quantidade) {
                mostrarAlerta("Quantidade insuficiente!", "Não há quantidade suficiente para o produto \"" + nomeProduto + "\".");
                return; // Não adiciona nada ao histórico
            }

            estoque.saidaProduto(nomeProduto, quantidade); // Passar apenas nome e quantidade
            movimentacao.registrarMovimentacao("Saída", nomeProduto, quantidade);
            atualizarVisaoGeral();
            atualizarHistorico();
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Por favor, insira um número válido para quantidade.");
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao registrar saída: " + e.getMessage());
        }
    }

    private void atualizarHistorico() {
        historicoTextArea.clear();
        for (String registro : movimentacao.getHistorico()) {
            historicoTextArea.appendText(registro + "\n");
        }
    }

    public void mostrarAlerta(String titulo, String mensagem) { // Método para exibir alertas
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
