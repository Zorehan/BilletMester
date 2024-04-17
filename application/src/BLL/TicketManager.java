package BLL;

import BE.Tickets;
import BE.UserTickets;
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
    public UserTickets createUserTickets(UserTickets userTickets) throws SQLException {

        return ticketDAO.createUserTicket(userTickets);

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

    public List<Tickets> getTicketsByEventId(int eventId)
    {

        return ticketDAO.getTicketsByEventId(eventId);
    }
}
