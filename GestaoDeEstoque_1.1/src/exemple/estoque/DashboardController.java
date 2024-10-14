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
    private TextField nomeProdutoEntradaField;
    @FXML
    private TextField quantidadeProdutoEntradaField;
    @FXML
    private TextField nomeProdutoSaidaField;
    @FXML
    private TextField quantidadeProdutoSaidaField;
    @FXML
    private TextField pesquisaProdutoField;
    @FXML
    private TextField nomeProdutoField;
    @FXML
    private TextField quantidadeProdutoField;
    @FXML
    private TextField precoProdutoField;

    public void inicializarDados(Estoque estoque, Movimentacao movimentacao) {
        this.estoque = estoque;
        this.movimentacao = movimentacao;
        atualizarVisaoGeral();
        atualizarHistorico(); // Atualiza o histórico ao iniciar
    }

    private void atualizarVisaoGeral() {
        produtosListView.getItems().clear();
        for (Produto produto : estoque.getProdutos()) {
            produtosListView.getItems().add("ID " + produto.getId() + " - " + produto.getNome() + ": " + produto.getQuantidade() + " unidades, Preço: " + produto.getPreco() + "€");
        }
    }

    @FXML
    private void registrarEntrada() {
        String nomeProduto = nomeProdutoEntradaField.getText().trim();
        try {
            int quantidade = Integer.parseInt(quantidadeProdutoEntradaField.getText().trim());

            // Verifica se o produto já existe no estoque
            Produto produto = estoque.procurarProduto(nomeProduto);
            if (produto != null) {
                // Se o produto existe, adiciona a quantidade
                estoque.entradaProduto(nomeProduto, quantidade, produto.getPreco());
                movimentacao.registrarMovimentacao("Entrada", nomeProduto, quantidade);
                atualizarVisaoGeral();
                atualizarHistorico(); // Atualiza o histórico
                mostrarAlerta("Sucesso", "Entrada registrada com sucesso para o produto \"" + nomeProduto + "\".");
            } else {
                // Caso o produto não exista, exibe um alerta
                mostrarAlerta("Erro", "O produto \"" + nomeProduto + "\" não existe no estoque. Para adicionar um novo produto, use o botão 'Adicionar'.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Por favor, insira um número válido para quantidade.");
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao registrar entrada: " + e.getMessage());
        }
    }


    @FXML
    private void registrarSaida() {
        String nomeProduto = nomeProdutoSaidaField.getText().trim();
        try {
            int quantidade = Integer.parseInt(quantidadeProdutoSaidaField.getText().trim());
            Produto produto = estoque.procurarProduto(nomeProduto);
            if (produto == null) {
                mostrarAlerta("Produto não encontrado!", "O produto \"" + nomeProduto + "\" não está disponível no estoque.");
                return;
            }
            if (produto.getQuantidade() < quantidade) {
                mostrarAlerta("Quantidade insuficiente!", "Não há quantidade suficiente para o produto \"" + nomeProduto + "\".");
                return;
            }

            estoque.saidaProduto(nomeProduto, quantidade);
            movimentacao.registrarMovimentacao("Saída", nomeProduto, quantidade);
            atualizarVisaoGeral();
            atualizarHistorico(); // Atualiza o histórico após a saída
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Por favor, insira um número válido para quantidade.");
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao registrar saída: " + e.getMessage());
        }
    }

    @FXML
    private void adicionarNovoItem() {
        String nomeProduto = nomeProdutoField.getText().trim();
        String quantidadeStr = quantidadeProdutoField.getText().trim();
        String precoStr = precoProdutoField.getText().trim();

        if (nomeProduto.isEmpty() || quantidadeStr.isEmpty() || precoStr.isEmpty()) {
            mostrarAlerta("Erro", "Todos os campos devem ser preenchidos.");
            return;
        }

        try {
            int quantidade = Integer.parseInt(quantidadeStr);
            double preco = Double.parseDouble(precoStr);

            estoque.entradaProduto(nomeProduto, quantidade, preco);
            atualizarVisaoGeral();
            atualizarHistorico(); // Atualiza o histórico após adicionar novo item
            mostrarAlerta("Sucesso", "Produto adicionado com sucesso!");
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Por favor, insira números válidos para quantidade e preço.");
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao adicionar produto: " + e.getMessage());
        }
    }

    @FXML
    private void eliminarItem() {
        String selectedItem = produtosListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            mostrarAlerta("Erro", "Por favor, selecione um produto para eliminar.");
            return;
        }

        // Extrai o nome do produto da string selecionada
        String nomeProduto = selectedItem.split(" - ")[1].split(":")[0];

        // Elimina o produto do estoque
        estoque.removerProduto(nomeProduto);
        movimentacao.registrarMovimentacao("Remoção", nomeProduto, 0);
        atualizarVisaoGeral();
        atualizarHistorico(); // Atualiza o histórico após a remoção
        mostrarAlerta("Sucesso", "Produto \"" + nomeProduto + "\" eliminado com sucesso!");
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void atualizarHistorico() {
        historicoTextArea.clear();
        for (String registro : movimentacao.getHistorico()) {
            historicoTextArea.appendText(registro + "\n");
        }
    }

    @FXML
    private void pesquisarProduto() {
        String pesquisa = pesquisaProdutoField.getText().trim();
        produtosListView.getItems().clear();
        for (Produto produto : estoque.getProdutos()) {
            if (produto.getNome().toLowerCase().contains(pesquisa.toLowerCase())) {
                produtosListView.getItems().add("ID " + produto.getId() + " - " + produto.getNome() + ": " + produto.getQuantidade() + " unidades, Preço: " + produto.getPreco() + "€");
            }
        }
    }
}
