package GUI.Controller.EventManagement;

import BE.Events;
import BE.Users.User;
import GUI.Model.EventModel;
import GUI.Model.UserModel;
import GUI.Model.ViewModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class eventManagerController implements Initializable {

    @FXML
    private ListView<Events> availableEvents;

    private ViewModel viewModel;
    private UserModel userModel;
    private EventModel eventModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userModel = UserModel.getInstance();
        eventModel = EventModel.getInstance();
        viewModel = ViewModel.getInstance();

        getAvailableEvents();
    }

    private void getAvailableEvents() {
        // Få nuværende logged-in user
        User loggedInUser = userModel.getUser();

        if (loggedInUser != null) {
            String currentUserFullName = loggedInUser.getFullName();
            ObservableList<Events> eventsList = eventModel.getObservableEvents();

            // Filtrere events baseret på det fulde navn af User. Dette er automatisk sat når de opretter events.
            ObservableList<Events> filteredEvents = eventsList.filtered(
                    event -> event.getEventHost().equals(currentUserFullName)
            );

            availableEvents.setItems(filteredEvents);
        }
    }

    public void clickEdit(ActionEvent actionEvent) {
    }


}
