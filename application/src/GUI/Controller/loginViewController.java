package GUI.Controller;

import BE.Users.User;
import GUI.Model.UserModel;
import GUI.Model.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Encryption;

import java.io.IOException;

public class loginViewController {

    private UserModel userModel = UserModel.getInstance();
    private ViewModel viewModel = ViewModel.getInstance();
    private Encryption encryption = new Encryption();

    @FXML
    private TextField TxtUsername;

    @FXML
    private PasswordField PswPassword;

    public void BtnPressLogin(ActionEvent actionEvent) {
        boolean loginSuccessful = false;

        for (User user : userModel.getObservableUsers()) {
            if (TxtUsername.getText().equals(user.getUserName()) &&
                    encryption.checkPassword(PswPassword.getText(), user.getPassword())) {
                try {
                    userModel.setUser(user);
                    Parent root = FXMLLoader.load(getClass().getResource("/GUI/View/borderPaneView.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Victor Gay");
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.show();
                    viewModel.getStage().close();
                    viewModel.setStage(stage);
                    loginSuccessful = true;
                    break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (!loginSuccessful) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Login information incorrect.");
            alert.showAndWait();
        }
    }
}