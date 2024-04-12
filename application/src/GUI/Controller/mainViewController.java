package GUI.Controller;

import BE.Events;
import GUI.Model.EventModel;
import GUI.Model.ViewModel;
import GUI.Widgets.EventWidget;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class mainViewController implements Initializable {
    @FXML
    private VBox centerVBox; // Container for displaying event widgets

    private EventModel eventModel; // Assuming this model provides access to event data
    private ViewModel viewModel; // Assuming this is used for shared application state, not directly relevant here

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize your model and view model here (details omitted for brevity)
        eventModel = EventModel.getInstance();
        viewModel = ViewModel.getInstance();

        // Fetch and display all events upon initialization
        List<Events> allEvents = eventModel.getObservableEvents(); // Replace with your method to get all events
        populateEvents(allEvents);
    }

    /**
     * Updates the display with a list of events.
     * @param events The events to display.
     */
    private void populateEvents(List<Events> events) {
        centerVBox.getChildren().clear(); // Clear existing content
        for (Events event : events) {
            EventWidget widget = new EventWidget(event); // Assuming EventWidget takes an Events object
            centerVBox.getChildren().add(widget);
        }
    }

    /**
     * Handles action events for category buttons to filter events.
     * @param event The action event triggered by button clicks.
     */
    @FXML
    private void filterByCategory(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String category = btn.getText();

        // Implement your logic to filter events by category
        // This is a placeholder; replace with actual logic
        List<Events> filteredEvents = eventModel.getEventsByCategory(category); // Your method to get events by category

        populateEvents(filteredEvents);
    }

    /**
     * Handles the "Show All" button click to display all events.
     * @param event The action event triggered by the "Show All" button click.
     */
    @FXML
    public void showAllEvents(ActionEvent event) {
        List<Events> allEvents = eventModel.getObservableEvents(); // Replace with your method to get all events
        populateEvents(allEvents);
    }
}
