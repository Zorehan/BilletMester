package BE;

public class UserTickets {

    private int ticketID;
    private int userID;

    public UserTickets(int ticketID, int userID)
    {
        this.ticketID = ticketID;
        this.userID = userID;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
