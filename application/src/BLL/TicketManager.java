package BLL;

import BE.Tickets;
import DAL.TicketDAO;

import java.sql.SQLException;
import java.util.List;

public class TicketManager {

    private TicketDAO ticketDAO;

    public TicketManager() {
        ticketDAO = new TicketDAO();
    }

    public List<Tickets> getAllTickets() {
        try {
            return ticketDAO.getAllTickets();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Tickets createTicket(Tickets ticket) {
        return ticketDAO.createTicket(ticket);
    }

    public List<Tickets> getTicketsByUserId(int userID) {
        return ticketDAO.getTicketsByUserId(userID);
    }

    public void updateTicket(Tickets ticket)
    {
        ticketDAO.updateTicket(ticket);
    }

    public void deleteTicket(Tickets ticket)
    {
        ticketDAO.deleteTicket(ticket);
    }

    public void getTicketsByEventId(int eventId)
    {
        ticketDAO.getTicketsByEventId(eventId);
    }
}
