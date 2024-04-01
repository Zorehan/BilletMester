package GUI;

import GUI.Controller.Login.loginViewController;
import GUI.Model.ViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/Login/loginView.fxml"));
        Parent root = loader.load();

        loginViewController controller = loader.getController();
        controller.setStage(primaryStage);

        primaryStage.setTitle("Victor Gay");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
