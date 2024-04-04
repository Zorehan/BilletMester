package GUI.Controller.UserView;

import GUI.Model.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class sideBarController {
    private ViewModel viewModel = ViewModel.getInstance();
    @FXML
    private void clickAccount(ActionEvent actionEvent) {
    }

    @FXML
    private void clickTickets(ActionEvent actionEvent) {
    }

    @FXML
    private void clickLogout(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/Login/loginView.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = viewModel.getStage();
            stage.setScene(scene);
            viewModel.setStage(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
