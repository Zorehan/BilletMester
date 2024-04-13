package GUI;

import GUI.Controller.Login.loginViewController;
import GUI.Model.ViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private ViewModel viewModel = ViewModel.getInstance();
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/Login/loginView.fxml"));
        Parent root = loader.load();

        viewModel.setStage(primaryStage);

        primaryStage.setTitle("Nicklas Gay");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
