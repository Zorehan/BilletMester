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
            viewModel.logOut();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
