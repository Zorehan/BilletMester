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
import javafx.scene.control.CheckBox;
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

    @FXML
    private CheckBox checkEntry, checkFood, checkDiscount, checkDrink;

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

        if(checkFood.isSelected())
        {
            createFoodTicket();
        }
        if(checkDiscount.isSelected())
        {
            createTicketDiscount();
        }

        if(checkDrink.isSelected())
        {
            createDrinkBillet();
        }

        if(checkEntry.isSelected())
        {
            createEntryTicket();
        }
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


    private void createFoodTicket() throws SQLException {
        HttpService httpService = new HttpService();
        Events currentEvent = availableEvents.getSelectionModel().getSelectedItem();
        User currentUser = listAvailableUsers.getSelectionModel().getSelectedItem();

        String body = "Here is your food Ticket to the event:" + currentEvent.getEventName();

        String ticketBarcode = httpService.generateBarcodeFood(currentEvent);
        Tickets ticket = new Tickets(-1, ticketBarcode, "Food", ticketBarcode, "");
        ticketModel.createTicket(ticket);
        Tickets currentTicket = ticketModel.getAllObservableTickets().getLast();

        UserTickets userTicket = new UserTickets(currentTicket.getId(), currentUser.getId());
        ticketModel.createUserTickets(userTicket);

        EventTickets eventTickets = new EventTickets(currentTicket.getId(), currentEvent.getId());
        eventModel.createEventTicket(eventTickets);

        System.out.println(ticket.getTicketQR());

        PDFGenerator pdfGenerator = new PDFGenerator();
        pdfGenerator.generateBarcodePDF("F" + ticket.getTicketQR(), ticket.getTicketQR(), currentEvent, currentUser);

        MailSender mailSender = new MailSender();
        mailSender.sendEmail(currentUser.getUserEmail(), currentEvent.getEventName() + "Ticket",body, ticket.getTicketQR());
    }

    private void createDrinkBillet() throws SQLException {
        HttpService httpService = new HttpService();
        Events currentEvent = availableEvents.getSelectionModel().getSelectedItem();
        User currentUser = listAvailableUsers.getSelectionModel().getSelectedItem();

        String body = "Here is your Ticket for a free beer at the event:" + currentEvent.getEventName();

        String ticketBarcode = httpService.generateBarcodeDrink(currentEvent);
        Tickets ticket = new Tickets(-1, ticketBarcode, "Drink", ticketBarcode, "");
        ticketModel.createTicket(ticket);
        Tickets currentTicket = ticketModel.getAllObservableTickets().getLast();

        UserTickets userTicket = new UserTickets(currentTicket.getId(), currentUser.getId());
        ticketModel.createUserTickets(userTicket);

        EventTickets eventTickets = new EventTickets(currentTicket.getId(), currentEvent.getId());
        eventModel.createEventTicket(eventTickets);

        System.out.println(ticket.getTicketQR());

        PDFGenerator pdfGenerator = new PDFGenerator();
        pdfGenerator.generateBarcodePDF("D" + ticket.getTicketQR(), ticket.getTicketQR(), currentEvent, currentUser);

        MailSender mailSender = new MailSender();
        mailSender.sendEmail(currentUser.getUserEmail(), currentEvent.getEventName() + "Ticket",body, ticket.getTicketQR());
    }

    private void createTicketDiscount() throws SQLException {
        HttpService httpService = new HttpService();
        Events currentEvent = availableEvents.getSelectionModel().getSelectedItem();
        User currentUser = listAvailableUsers.getSelectionModel().getSelectedItem();

        String body = "Here is your discount ticket for a half off drink at the bar, at the event:" + currentEvent.getEventName();

        String ticketBarcode = httpService.generateBarcodeDrink(currentEvent);
        Tickets ticket = new Tickets(-1, ticketBarcode, "Discount", ticketBarcode, "");
        ticketModel.createTicket(ticket);
        Tickets currentTicket = ticketModel.getAllObservableTickets().getLast();

        UserTickets userTicket = new UserTickets(currentTicket.getId(), currentUser.getId());
        ticketModel.createUserTickets(userTicket);

        EventTickets eventTickets = new EventTickets(currentTicket.getId(), currentEvent.getId());
        eventModel.createEventTicket(eventTickets);

        System.out.println(ticket.getTicketQR());

        PDFGenerator pdfGenerator = new PDFGenerator();
        pdfGenerator.generateBarcodePDF("T" + ticket.getTicketQR(), ticket.getTicketQR(), currentEvent, currentUser);

        MailSender mailSender = new MailSender();
        mailSender.sendEmail(currentUser.getUserEmail(), currentEvent.getEventName() + "Ticket",body, ticket.getTicketQR());
    }

    public void createTicketCustom(String ticketDescription) throws SQLException {
        HttpService httpService = new HttpService();
        Events currentEvent = availableEvents.getSelectionModel().getSelectedItem();
        User currentUser = listAvailableUsers.getSelectionModel().getSelectedItem();

        String body = ticketDescription;

        String ticketBarcode = httpService.generateBarcodeDrink(currentEvent);
        Tickets ticket = new Tickets(-1, ticketBarcode, "Custom", ticketBarcode, "");
        ticketModel.createTicket(ticket);
        Tickets currentTicket = ticketModel.getAllObservableTickets().getLast();

        UserTickets userTicket = new UserTickets(currentTicket.getId(), currentUser.getId());
        ticketModel.createUserTickets(userTicket);

        EventTickets eventTickets = new EventTickets(currentTicket.getId(), currentEvent.getId());
        eventModel.createEventTicket(eventTickets);

        System.out.println(ticket.getTicketQR());

        PDFGenerator pdfGenerator = new PDFGenerator();
        pdfGenerator.generateBarcodePDF("C" + ticket.getTicketQR(), ticket.getTicketQR(), currentEvent, currentUser);

        MailSender mailSender = new MailSender();
        mailSender.sendEmail(currentUser.getUserEmail(), currentEvent.getEventName() + "Ticket",body, ticket.getTicketQR());
    }

    private void createEntryTicket() throws SQLException {
        HttpService httpService = new HttpService();
        Events currentEvent = availableEvents.getSelectionModel().getSelectedItem();
        User currentUser = listAvailableUsers.getSelectionModel().getSelectedItem();

        String body = "Here is your entry ticket to the event:" + currentEvent.getEventName();

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
        mailSender.sendEmail(currentUser.getUserEmail(), currentEvent.getEventName() + "Ticket",body, ticket.getTicketQR());
    }

    public void clickCreateCustomTicket(ActionEvent actionEvent) {
    }
}
