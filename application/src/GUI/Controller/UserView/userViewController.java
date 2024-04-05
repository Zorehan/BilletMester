package GUI.Controller.UserView;

import GUI.Model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.Encryption;

import java.net.URL;
import java.util.ResourceBundle;

public class userViewController implements Initializable {
    @FXML
    private TextField txtFullName, txtPassword, txtEmail;
    @FXML
    private Label lblUsername;
    @FXML
    private ImageView imgUser, imgPassword, imgEmail;
    @FXML
    private Button btnEdit, btnEdit1, btnEdit2;
    private UserModel userModel = UserModel.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initImages();

        txtFullName.setText(userModel.getUser().getFullName());
        lblUsername.setText("(" + userModel.getUser().getUserName() + ")");
        txtEmail.setText(userModel.getUser().getUserEmail());
        txtPassword.setText(userModel.getUser().getPassword());
    }

    public void initImages() {
        Image userImg = new Image(getClass().getResourceAsStream("/icons/user.png"));
        Image passwordImg = new Image(getClass().getResourceAsStream("/icons/key.png"));
        Image emailImg = new Image(getClass().getResourceAsStream("/icons/email.png"));
        Image editImg = new Image(getClass().getResourceAsStream("/icons/edit.png"));

        imgUser.setImage(userImg);
        imgPassword.setImage(passwordImg);
        imgEmail.setImage(emailImg);

        ImageView edit = new ImageView(editImg);
        edit.setPreserveRatio(true);
        edit.setFitHeight(20);
        ImageView edit1 = new ImageView(editImg);
        edit1.setPreserveRatio(true);
        edit1.setFitHeight(20);
        ImageView edit2 = new ImageView(editImg);
        edit2.setPreserveRatio(true);
        edit2.setFitHeight(20);

        btnEdit.setGraphic(edit);
        btnEdit1.setGraphic(edit1);
        btnEdit2.setGraphic(edit2);
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

    @FXML
    private void clickCancel(ActionEvent actionEvent) {
        txtFullName.setText(userModel.getUser().getFullName());
        txtPassword.setText(userModel.getUser().getPassword());
        txtEmail.setText(userModel.getUser().getUserEmail());

        txtFullName.setDisable(true);
        txtPassword.setDisable(true);
        txtEmail.setDisable(true);
    }
}
