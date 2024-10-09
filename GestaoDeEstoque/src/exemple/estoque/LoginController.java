package exemple.estoque;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    private Main mainApp;  // Variável para a instância principal (Main)

    // Método para injetar a instância principal no controlador
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Verificação básica de login
        if ("admin".equals(username) && "123".equals(password)) {
            // Carregar o Dashboard após login bem-sucedido
            Stage stage = (Stage) loginButton.getScene().getWindow();
            mainApp.loadDashboard(stage);
        } else {
            mostrarAlerta("Login incorreto", "Usuário ou senha incorretos. Tente novamente.");
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
