package GUI.Controller;

import BE.Events;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class seeMoreController {
    @FXML
    private Label lblLocation, lblDescription, lblPrice;
    @FXML
    private BorderPane borderPane;
    private Events event;

    // Method to set the event and update the UI accordingly
    public void setEvent(Events event) {
        lblLocation.setText(event.getEventLocation() + " " + event.getEventStart().format(DateTimeFormatter.ofPattern("dd. MMMM")));
        lblDescription.setText(event.getEventNotes());
        lblPrice.setText(String.valueOf(event.getEventPrice())); // Formater prisen som en streng
        // Opdater yderligere UI-komponenter efter behov
    }

    // Update UI elements based on the current event
    private void updateEvent() {
        if (event != null) {
            lblLocation.setText(event.getEventLocation() + " " + event.getEventStart().format(DateTimeFormatter.ofPattern("dd. MMMM")));
            lblDescription.setText(event.getEventNotes());
            lblPrice.setText(String.valueOf(event.getEventPrice())); // Assuming getEventPrice() returns a String
        }
    }
}
