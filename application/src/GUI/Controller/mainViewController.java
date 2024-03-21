package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class mainViewController {
    @FXML
    private BorderPane borderPane;

    public void clickEventCreator(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/eventCreatorView.fxml"));
        Parent p = loader.load();
        eventCreatorController eventCreatorController = loader.getController();
        Pane view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/GUI/View/eventCreatorView.fxml")));
        borderPane.setLeft(view);
    }
}
