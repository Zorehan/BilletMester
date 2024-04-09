package GUI.Controller.UserView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class eventInfoController {
    @FXML
    private BorderPane borderPane;

    @FXML
    private void handleReturnBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/MainPage/mainView.fxml"));
            Parent mainView = loader.load();

            // Assuming the parent BorderPane is accessible through the borderPane field
            borderPane.setCenter(mainView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }
}
