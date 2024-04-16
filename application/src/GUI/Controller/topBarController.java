package GUI.Controller;


import GUI.Model.EventModel;
import GUI.Model.UserModel;
import GUI.Model.ViewModel;
import GUI.Widgets.TopPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class topBarController implements Initializable {
    @FXML
    private HBox topBar;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TopPanel topPanel = new TopPanel();
        topBar.getChildren().add(topPanel);
    }
}
