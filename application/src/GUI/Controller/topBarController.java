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
    private ViewModel viewModel = ViewModel.getInstance();
    private EventModel eventModel = EventModel.getInstance();

    @FXML
    private HBox topBar;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TopPanel topPanel = new TopPanel();
        topBar.getChildren().add(topPanel);
    }

    @FXML
    private void clickBack(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/MainPage/mainView.fxml"));
            Parent userView = loader.load();

            viewModel.getBorderPane().setCenter(userView);
            eventModel.setEvent(null);
            viewModel.setBanner();
            viewModel.disableSidePanel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
