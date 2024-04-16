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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/UserView/userView.fxml"));
            Parent userView = loader.load();
            viewModel.getBorderPane().setCenter(userView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void clickTickets(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/UserView/ticketView.fxml"));
            Parent ticketView = loader.load();
            viewModel.getBorderPane().setCenter(ticketView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
