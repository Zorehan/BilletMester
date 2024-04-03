package GUI.Controller;

import GUI.Model.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class userViewController implements Initializable {
    @FXML
    private Label lblFullName;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblEmail;
    private UserModel userModel = UserModel.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblFullName.setText(userModel.getUser().getFullName());
        lblUsername.setText("(" + userModel.getUser().getUserName() + ")");
        lblEmail.setText(userModel.getUser().getUserEmail());
    }
}
