package BE;

public class EventTickets {

    private int ticketID;
    private int eventID;

    public EventTickets(int ticketID, int eventID)
    {
        this.ticketID = ticketID;
        this.eventID = eventID;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }
}
