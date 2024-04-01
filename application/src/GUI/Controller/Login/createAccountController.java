package GUI.Controller.Login;

import BE.Users.User;
import BE.Users.UserEnum;
import GUI.Model.UserModel;
import GUI.Model.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Encryption;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class createAccountController {
    @FXML
    private TextField txtUsername, txtFirstName, txtLastName , txtEmail;
    @FXML
    private PasswordField txtPassword, txtPasswordConfirm;
    private Stage stage;

    private UserModel userModel = UserModel.getInstance();
    private ViewModel viewModel = ViewModel.getInstance();


    public boolean validate() {
        //TODO String regex på email.
        if(txtUsername.getText().isBlank()
                || txtFirstName.getText().isBlank()
                || txtLastName.getText().isBlank()
                || txtEmail.getText().isBlank()
                || txtPassword.getText().isBlank()) {
            createAlert(Alert.AlertType.ERROR, "You must fill out all fields!");
            return false;
        }

        //Tjekker om der er nogen, som har taget username eller email.
        for(User user : userModel.getObservableUsers()) {
            if(Objects.equals(user.getUserName(), txtUsername.getText()) || Objects.equals(user.getUserEmail(), txtEmail.getText())) {
                createAlert(Alert.AlertType.ERROR, "Username or email is already taken!");
                return false;
            }
        }

        //Tjekker om password og confirm password er de samme.
        if(!Objects.equals(txtPassword.getText(), txtPasswordConfirm.getText())) {
            createAlert(Alert.AlertType.ERROR, "Passwords must match!");
            return false;
        }

        if(!emailValidation()) {
            createAlert(Alert.AlertType.ERROR, "Email isn't valid!");
            return false;
        }

        //Hvis der ikke opstod problemer ved de andre, returner den true så en bruger kan oprettes.
        return true;
    }

    private boolean emailValidation() {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; //Email regex
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(txtEmail.getText());
        return matcher.matches();
    }

    private void createAlert(Alert.AlertType alertType, String info) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Account Creation Failed");
        alert.setHeaderText(null);
        alert.setContentText(info);
        alert.showAndWait();
    }

    @FXML
    private void clickCancel(ActionEvent actionEvent) {
        this.stage.close();
    }

    @FXML
    private void clickCreate(ActionEvent actionEvent) {
        if(validate()) {
            Encryption enc = new Encryption();
            User user = new User(-1, UserEnum.USER
                    ,txtFirstName.getText()
                    ,txtLastName.getText()
                    ,txtUsername.getText()
                    ,enc.encryptPassword(txtPassword.getText()) //Password encryption.
                    ,txtEmail.getText());

            userModel.createUser(user);

            //Lukker denne stage.
            createAlert(Alert.AlertType.CONFIRMATION, "Account Created! \n returning to login page!" );
            this.stage.close();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
