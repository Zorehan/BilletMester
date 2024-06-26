package GUI.Controller;

import BE.Events;
import GUI.Model.EventModel;
import GUI.Model.ViewModel;
import GUI.Widgets.EventWidget;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class mainViewController implements Initializable {
    public VBox eventsContainer; // Container for visning af event widgets
    public ScrollPane scrollPane;
    @FXML
    private VBox centerVBox; // Container for displaying event widgets

    private EventModel eventModel; // dette model giver adgang til event data
    private ViewModel viewModel = ViewModel.getInstance();
    List<Events> eventsList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        eventModel = EventModel.getInstance();
        // Henter og viser alle events ved initialisering
        displayEvents(eventModel.getObservableEvents());
    }


    /**
     * Handles action events for category buttons to filter events.
     * @param event The action event triggered by button clicks.
     */
    @FXML
    private void filterByCategory(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String category = null;
        //Fordi der ikke kan være spaces i ENUM, skal vi gøre dette hardcoded.
        switch (btn.getText()) {
            case "Friday Bar":
                category = "FridayBar";
                break;
            case "Thematic Evening":
                category = "ThematicEvening";
                break;
            default:
                category = btn.getText();
        }
        List<Events> filteredEvents = eventModel.getEventsByCategory(category); // dette er din metode til at hente events efter kategori
        displayEvents(filteredEvents);
    }

    /**
     * Handles the "Show All" button click to display all events.
     * @param event The action event triggered by the "Show All" button click.
     */
    @FXML
    public void showAllEvents(ActionEvent event) {
        displayEvents(eventModel.getObservableEvents());
    }

    private void displayEvents(List<Events> events) {
        eventsContainer.getChildren().clear(); // Ryd tidligere indhold
        HBox currentHBox = null;

        for (int i = 0; i < events.size(); i++) {
            if (i % 3 == 0) { // Hver 3. event, start en ny HBox
                currentHBox = new HBox(100);
                currentHBox.setAlignment(Pos.CENTER);
                eventsContainer.getChildren().add(currentHBox);
            }
            if (currentHBox != null) {
                EventWidget widget = new EventWidget(events.get(i));
                currentHBox.getChildren().add(widget);
            }
        }
    }
}
