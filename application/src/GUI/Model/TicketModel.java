package GUI.Model;

import BE.Tickets;
import BE.UserTickets;
import BLL.TicketManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class TicketModel {

    private static TicketModel instance;

    private TicketManager ticketManager;

    private ObservableList<Tickets> allTickets;

    private ObservableList<Tickets> userTickets;

    private ObservableList<Tickets> eventSpecificTickets;



    //den her enestående fætter kan bruges til at holde styr på ting i controller
    //som hvilken en der er trykket ind eller noget lign. idk om den kan bruges
    private Tickets ticket;

    public TicketModel()
    {
        ticketManager = new TicketManager();

        allTickets = FXCollections.observableArrayList();
        userTickets = FXCollections.observableArrayList();
        allTickets.addAll(ticketManager.getAllTickets());
        eventSpecificTickets = FXCollections.observableArrayList();
    }

    public static TicketModel getInstance()
    {
        if(instance == null)
        {
            instance = new TicketModel();
        }
        return instance;
    }

    public UserTickets createUserTickets(UserTickets userTickets) throws SQLException {
        return ticketManager.createUserTickets(userTickets);
    }

    public ObservableList<Tickets> getAllObservableTickets()
    {
        return allTickets;
    }

    public void clearObservableTickets()
    {
        allTickets.clear();
    }

    public ObservableList<Tickets> getUserSpecificObservableTickets(int userID)
    {
        userTickets.addAll(ticketManager.getTicketsByUserId(userID));
        return userTickets;
    }

    public void clearObservableUserSpecificTickets()
    {
        userTickets.clear();
    }

    public ObservableList<Tickets> getEventSpecificTickets(int eventId)
    {
        eventSpecificTickets.addAll(ticketManager.getTicketsByEventId(eventId));
        return eventSpecificTickets;
    }

    public void clearEventSpecificTickets()
    {
        eventSpecificTickets.clear();
    }

    public Tickets createTicket(Tickets newTicket)
    {
        for(Tickets tickets : allTickets)
        {
            if(newTicket.getTicketQR().equals(tickets.getTicketQR()))
            {
                System.out.println("Sorry boss barcode/qr already exists");
                return null;
            }
        }
        Tickets ticket = ticketManager.createTicket(newTicket);
        allTickets.add(ticket);
        return ticket;
    }

    public void deleteTicket(Tickets ticket)
    {
        ticketManager.deleteTicket(ticket);
        allTickets.remove(ticket);
        if(userTickets.contains(ticket))
        {
            userTickets.remove(ticket);
        }
        if(eventSpecificTickets.contains(ticket))
        {
            eventSpecificTickets.remove(ticket);
        }
    }

    public void updateTicket(Tickets ticket)
    {
        ticketManager.updateTicket(ticket);
    }
}
