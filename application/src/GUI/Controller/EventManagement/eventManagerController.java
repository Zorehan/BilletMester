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
    @FXML
    private ListView<User> listAvailableUsers;

    private ViewModel viewModel;
    private UserModel userModel;
    private EventModel eventModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userModel = UserModel.getInstance();
        eventModel = EventModel.getInstance();
        viewModel = ViewModel.getInstance();

        getAvailableEvents();
        getAvailableUsers();
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

    private void getAvailableUsers() {
        listAvailableUsers.setItems(userModel.getObservableUsers());
    }

    public void clickEdit(ActionEvent actionEvent) {
    }


    @FXML
    private void clickCreateEvent(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/EventManagement/eventCreatorView.fxml"));
            Parent userView = loader.load();
            viewModel.getBorderPane().setCenter(userView);
            viewModel.setTopBar();
            viewModel.disableSidePanel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void clickCreateTicket(ActionEvent actionEvent) {
    }
}
