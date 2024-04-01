package GUI;

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
        Parent root = FXMLLoader.load(getClass().getResource("View/loginView.fxml"));
        primaryStage.setTitle("Victor Gay");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

        viewModel.setStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
