package GUI.Controller.UserView;

import BE.EventTickets;
import BE.Events;
import BE.Tickets;
import BE.UserTickets;
import BE.Users.User;
import GUI.Model.EventModel;
import GUI.Model.TicketModel;
import GUI.Model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import util.HttpService;
import util.MailSender;
import util.PDFGenerator;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ticketViewController implements Initializable {
    @FXML
    private ListView<Events> listTickets;
    @FXML
    private Button btnDownload;
    private UserModel userModel = UserModel.getInstance();
    private TicketModel ticketModel = TicketModel.getInstance();
    private EventModel eventModel = EventModel.getInstance();
    private User user;

    /**
     * Denne klasse er ikke blevet kodet endnu.
     * Før dette kan gøres kræver det at man kan udstede en billet til en user.
     * Tror gerg ville tage sig af det, så sætter det til siden.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = userModel.getUser();
        listTickets.setItems(eventModel.getObservableUserEvents(String.valueOf(user.getId())));
    }

    @FXML
    private void clickDownload(ActionEvent actionEvent) throws SQLException {
        createTicket();
    }

    private void createTicket() throws SQLException {
        Events currentEvent = listTickets.getSelectionModel().getSelectedItem();
        Tickets ticket = ticketModel.getTicketBySpec(user.getId(), currentEvent.getEventName(), "Entry");

        String body = "Here is your entry ticket to the event:" + currentEvent.getEventName();
        PDFGenerator pdfGenerator = new PDFGenerator();
        pdfGenerator.generateBarcodePDF("E" + ticket.getTicketQR(), ticket.getTicketQR(), currentEvent, user);

        MailSender mailSender = new MailSender();
        mailSender.sendEmail(user.getUserEmail(), currentEvent.getEventName() + "Ticket",body, ticket.getTicketQR());
    }
}
