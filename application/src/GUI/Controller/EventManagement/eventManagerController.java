package GUI.Controller.EventManagement;

import BE.EventTickets;
import BE.Events;
import BE.Tickets;
import BE.UserTickets;
import BE.Users.User;
import GUI.Model.EventModel;
import GUI.Model.TicketModel;
import GUI.Model.UserModel;
import GUI.Model.ViewModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import util.HttpService;
import util.MailSender;
import util.PDFGenerator;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class eventManagerController implements Initializable {

    @FXML
    private ListView<Events> availableEvents;
    @FXML
    private ListView<User> listAvailableUsers;

    private ViewModel viewModel;
    private UserModel userModel;
    private EventModel eventModel;
    private TicketModel ticketModel;

    private Events selectedEvent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initModels();
        getAvailableEvents();
        getAvailableUsers();
        ticketModel = TicketModel.getInstance();
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

    @FXML
    private void clickEdit(ActionEvent actionEvent) {
        // Get the selected event from wherever it's stored or retrieved
        Events selectedEvent = availableEvents.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/EventManagement/eventEditView.fxml"));
                Parent userView = loader.load();
                viewModel.getBorderPane().setCenter(userView);
                viewModel.setTopBar();
                viewModel.disableSidePanel();

                eventEditController controller = loader.getController();
                controller.setSelectedEvent(selectedEvent); // Pass the selected event to the controller

                controller.initData(selectedEvent); // Initialize data in the controller

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            showAlert("Please select an event to edit.");
        }
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
    private void clickCreateTicket(ActionEvent actionEvent) throws SQLException {
        
        HttpService httpService = new HttpService();
        Events currentEvent = availableEvents.getSelectionModel().getSelectedItem();
        User currentUser = listAvailableUsers.getSelectionModel().getSelectedItem();

        String ticketBarcode = httpService.generateBarcode(currentEvent);
        Tickets ticket = new Tickets(-1, ticketBarcode, "Entry", ticketBarcode, "");
        ticketModel.createTicket(ticket);
        Tickets currentTicket = ticketModel.getAllObservableTickets().getLast();

        UserTickets userTicket = new UserTickets(currentTicket.getId(), currentUser.getId());
        ticketModel.createUserTickets(userTicket);

        EventTickets eventTickets = new EventTickets(currentTicket.getId(), currentEvent.getId());
        eventModel.createEventTicket(eventTickets);

        System.out.println(ticket.getTicketQR());

        PDFGenerator pdfGenerator = new PDFGenerator();
        pdfGenerator.generateBarcodePDF("E" + ticket.getTicketQR(), ticket.getTicketQR(), currentEvent, currentUser);

        MailSender mailSender = new MailSender();
        mailSender.sendEmail(currentUser.getUserEmail(), currentEvent.getEventName() + "Ticket", ticket.getTicketQR());
    }

    private void initModels() {
        userModel = UserModel.getInstance();
        eventModel = EventModel.getInstance();
        viewModel = ViewModel.getInstance();
    }

    // Alert Box
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Selection Error");
        alert.setHeaderText("Selection Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void clickCreateCustomTicket(ActionEvent actionEvent) {
    }
}
