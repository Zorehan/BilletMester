package GUI.Controller;

import GUI.Model.ViewModel;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import BE.Events;
import GUI.Model.EventModel;
import GUI.Widgets.EventWidget;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.util.Pair;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class mainViewController implements Initializable {
    private BorderPane borderPane;
    @FXML
    private VBox centerVBox;
    EventModel eventModel;
    ViewModel viewModel;
    List<Events> eventsList = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventModel = EventModel.getInstance();
        viewModel = ViewModel.getInstance();
        borderPane = viewModel.getBorderPane();

        eventsList = eventModel.getObservableEvents();

        centerVBox.getChildren().addAll(createEvents(), createEvents());
        centerVBox.setSpacing(30);
    }

    public HBox createEvents() {
        HBox hBox = new HBox(100);
        hBox.setAlignment(Pos.CENTER);
        for(Events event : eventsList) {
            EventWidget widget = new EventWidget(event);
            hBox.getChildren().add(widget);
        }
        return hBox;
    }
}
