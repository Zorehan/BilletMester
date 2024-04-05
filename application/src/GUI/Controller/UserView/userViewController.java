package GUI.Controller.UserView;

import GUI.Model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import util.Encryption;

import java.net.URL;
import java.util.ResourceBundle;

public class userViewController implements Initializable {
    @FXML
    private TextField txtFullName, txtPassword, txtEmail;
    @FXML
    private Label lblUsername;
    private UserModel userModel = UserModel.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtFullName.setText(userModel.getUser().getFullName());
        lblUsername.setText("(" + userModel.getUser().getUserName() + ")");
        txtEmail.setText(userModel.getUser().getUserEmail());
        txtPassword.setText(userModel.getUser().getPassword());
    }

    @FXML
    private void clickDeleteUser(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Account Deletion");
        alert.setHeaderText(null);
        alert.setContentText("You are about to delete your account!\n" +
                                "This cannot be undone, are you sure?");
        alert.showAndWait();

        if(alert.getResult() == ButtonType.OK) {
            userModel.deleteUser(userModel.getUser());
        }
    }

    @FXML
    private void clickSave(ActionEvent actionEvent) {
        String[] fullName = txtFullName.getText().split(" ");
        Encryption encryption = new Encryption();

        userModel.deepUpdateUser(userModel.getUser()
                                ,fullName[0]
                                ,fullName[fullName.length - 1]
                                ,encryption.encryptPassword(txtPassword.getText())
                                ,txtEmail.getText());

        txtFullName.setDisable(true);
        txtPassword.setDisable(true);
        txtEmail.setDisable(true);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("User Updated");
        alert.setContentText("User will be updated next time you open the program!");
        alert.showAndWait();
    }

    @FXML
    private void clickEditName(ActionEvent actionEvent) {
        txtFullName.setDisable(false);
    }

    @FXML
    private void clickEditPassword(ActionEvent actionEvent) {
        txtPassword.setDisable(false);
    }

    @FXML
    private void clickEditEmail(ActionEvent actionEvent) {
        txtEmail.setDisable(false);
    }
}
