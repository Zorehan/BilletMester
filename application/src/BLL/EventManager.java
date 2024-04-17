package BLL;
import BE.EventTickets;
import BE.Events;
import DAL.EventDAO;

import java.sql.SQLException;
import java.util.List;

public class EventManager {

    private EventDAO eventDAO;

    public EventManager()
    {
        eventDAO = new EventDAO();
    }

    public List<Events> getAllEvents()
    {
        return eventDAO.getAllEvents();
    }

    public Events createEvent(Events event)
    {
        return eventDAO.createEvent(event);
    }

    public void updateEvent(Events event)
    {
        eventDAO.updateEvent(event);
    }

    public void deleteEvent(Events event)
    {
        eventDAO.deleteEvent(event);
    }

    public EventTickets createEventTicket(EventTickets eventTickets) throws SQLException {
        return eventDAO.createEventTicket(eventTickets);
    }
}
