package exemple.estoque;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DashboardController {
    private Estoque estoque;
    private Movimentacao movimentacao;

    @FXML
    private ListView<String> produtosListView;

    @FXML
    private Label estoqueStatusLabel;

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

    // Método chamado para inicializar os dados do estoque e movimentação
    public void inicializarDados(Estoque estoque, Movimentacao movimentacao) {
        this.estoque = estoque;
        this.movimentacao = movimentacao;
        atualizarVisaoGeral();  // Atualiza a visualização ao inicializar
        configurarPlaceholders();
    }

    // Configura placeholders nos TextFields
    private void configurarPlaceholders() {
        configurarPlaceholder(nomeProdutoEntradaField, "Nome do Produto");
        configurarPlaceholder(quantidadeProdutoEntradaField, "Quantidade");
        configurarPlaceholder(nomeProdutoSaidaField, "Nome do Produto");
        configurarPlaceholder(quantidadeProdutoSaidaField, "Quantidade");
    }

    // Configura um TextField para ter um placeholder que desaparece ao digitar
    private void configurarPlaceholder(TextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setStyle("-fx-opacity: 0.5;"); // Opcional: mudar a opacidade do texto de exemplo

        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                if (textField.getText().equals(placeholder)) {
                    textField.clear();
                    textField.setStyle("-fx-opacity: 1;"); // Restaura opacidade normal
                }
            } else {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setStyle("-fx-opacity: 0.5;"); // Restaura opacidade do texto de exemplo
                }
            }
        });

        // Listener para limpar o placeholder ao digitar
        textField.setOnKeyTyped(event -> {
            if (textField.getText().equals(placeholder)) {
                textField.clear();
                textField.setStyle("-fx-opacity: 1;"); // Restaura opacidade normal
            }
        });
    }

    // Atualiza a lista de produtos e o status do estoque
    private void atualizarVisaoGeral() {
        produtosListView.getItems().clear(); // Limpa a lista atual
        boolean estoqueBaixo = false; // Flag para verificar se o estoque está baixo
        for (Produto produto : estoque.getProdutos()) {
            produtosListView.getItems().add(produto.getNome() + " - " + produto.getQuantidade() + " unidades");
            if (produto.precisaReposicao()) {
                estoqueBaixo = true; // Se algum produto precisa de reposição, ativa a flag
            }
        }
        // Atualiza o status do estoque baseado na flag
        if (estoqueBaixo) {
            estoqueStatusLabel.setText("Alerta: Estoque baixo de um ou mais produtos.");
        } else {
            estoqueStatusLabel.setText("Estoque estável.");
        }
    }

    // Registra a entrada de um produto no estoque
    @FXML
    private void registrarEntrada() {
        String nomeProduto = nomeProdutoEntradaField.getText().trim(); // Remove espaços em branco
        if (nomeProduto.isEmpty() || nomeProduto.equals("Nome do Produto")) {
            mostrarAlerta("Nome do produto inválido!", "Por favor, insira o nome do produto.");
            return;
        }
        try {
            int quantidade = Integer.parseInt(quantidadeProdutoEntradaField.getText().trim());
            estoque.entradaProduto(nomeProduto, quantidade);
            movimentacao.registrarMovimentacao("Entrada", nomeProduto, quantidade);
            atualizarVisaoGeral();
            atualizarHistorico();
        } catch (NumberFormatException e) {
            mostrarAlerta("Quantidade inválida!", "Por favor, insira um número válido.");
        }
    }

    // Registra a saída de um produto do estoque
    @FXML
    private void registrarSaida() {
        String nomeProduto = nomeProdutoSaidaField.getText().trim(); // Remove espaços em branco
        if (nomeProduto.isEmpty() || nomeProduto.equals("Nome do Produto")) {
            mostrarAlerta("Nome do produto inválido!", "Por favor, insira o nome do produto.");
            return;
        }
        try {
            int quantidade = Integer.parseInt(quantidadeProdutoSaidaField.getText().trim());
            estoque.saidaProduto(nomeProduto, quantidade);
            movimentacao.registrarMovimentacao("Saída", nomeProduto, quantidade);
            atualizarVisaoGeral();
            atualizarHistorico();
        } catch (NumberFormatException e) {
            mostrarAlerta("Quantidade inválida!", "Por favor, insira um número válido.");
        }
    }

    // Atualiza o histórico de movimentações exibido na TextArea
    private void atualizarHistorico() {
        historicoTextArea.clear(); // Limpa o histórico atual
        for (String registro : movimentacao.getHistorico()) {
            historicoTextArea.appendText(registro + "\n");
        }
    }

    // Mostra uma caixa de alerta com mensagem de erro
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
