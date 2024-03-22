package GUI.Controller;

import BE.Events;
import GUI.Model.EventModel;
import GUI.Widgets.EventWidget;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class mainViewController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private VBox centerVBox;
    EventModel eventModel  = new EventModel();
    List<Events> eventsList = eventModel.getObservableEvents();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        HBox hBox = new HBox(40);
        hBox.setPadding(new Insets(10));
        for(Events event : eventsList) {
            EventWidget widget = new EventWidget(event);
            hBox.getChildren().add(widget);
        }

        centerVBox.getChildren().add(hBox);
    }

    public void clickEventCreator(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/eventCreatorView.fxml"));
        Parent p = loader.load();
        eventCreatorController eventCreatorController = loader.getController();
        Pane view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/GUI/View/eventCreatorView.fxml")));
        borderPane.setCenter(view);
    }
}
