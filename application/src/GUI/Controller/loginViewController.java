package GUI.Controller;

import BE.Users.User;
import GUI.Model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.Encryption;

public class loginViewController {

    private UserModel userModel = UserModel.getInstance();
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
                // sæt kode ind her til at blvie sendt videre til mainpagen
                loginSuccessful = true;
                break;
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