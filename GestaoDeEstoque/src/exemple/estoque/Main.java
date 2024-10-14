package exemple.estoque;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Carregar a tela de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            // Obter o controlador da tela de login
            LoginController loginController = loader.getController();
            // Passar a instância principal (Main) para o controlador de login
            loginController.setMainApp(this);

            // Definir a cena e exibir a tela de login
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Sistema de Gestão de Estoque");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para carregar a tela do dashboard após o login
    public void loadDashboard(Stage stage) {
        try {
            // Carregar o arquivo FXML do dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
            Parent root = loader.load();

            // Inicializar os dados do estoque e movimentação
            Estoque estoque = new Estoque();
            Movimentacao movimentacao = new Movimentacao();

            // Passar o estoque e a movimentação para o controlador do dashboard
            DashboardController controller = loader.getController();
            controller.inicializarDados(estoque, movimentacao);

            // Definir a nova cena para o dashboard e exibir
            stage.setScene(new Scene(root));
            stage.setTitle("Painel de Controle");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
