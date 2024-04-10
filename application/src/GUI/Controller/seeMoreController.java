package GUI.Controller;

import BE.Events;
import GUI.Model.EventModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class seeMoreController implements Initializable {
    @FXML
    private Label lblLocation, lblDescription, lblPrice;
    private Events event;
    private EventModel eventModel = EventModel.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        event = eventModel.getEvent();
        lblLocation.setText(event.getEventLocation() + " " + event.getEventStart().format(DateTimeFormatter.ofPattern("dd. MMMM")));
        lblDescription.setText(event.getEventNotes());
        lblPrice.setText(String.valueOf(event.getEventPrice()));
    }
}
