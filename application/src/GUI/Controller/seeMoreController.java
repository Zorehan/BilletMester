package GUI.Controller;

import BE.Events;
import GUI.Model.EventModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class seeMoreController implements Initializable {
    public VBox vboxTicketInfo;

    @FXML
    private Label lblLocation, lblDescription, lblInformation, lblPrice;
    @FXML
    private BorderPane borderPane;
    private Events event;
    private EventModel eventModel = EventModel.getInstance();

    private void updateEvent() {
        if (event != null) {
            int eventPrice = event.getEventPrice();
            if (eventPrice > 0) {
                lblPrice.setText("Price per ticket (DKK): " + eventPrice);
                lblPrice.setVisible(true);
            } else {
                lblPrice.setVisible(false);
            }
            lblLocation.setText("Location: " + event.getEventLocation());
            lblDescription.setText("Description: " + event.getEventNotes());
            lblInformation.setText(event.getEventGuidance());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        event = eventModel.getEvent();
        updateEvent();
    }
}
