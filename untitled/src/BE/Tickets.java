package BE;

public class Tickets {

    private int id;
    private String ticketTitle;
    private String ticketType;
    private String ticketQR;
    private String ticketInformation;

    public Tickets(int id, String ticketTitle, String ticketType, String ticketQR, String ticketInformation)
    {
        this.id = id;
        this.ticketTitle = ticketTitle;
        this.ticketType = ticketType;
        this.ticketQR = ticketQR;
        this.ticketInformation = ticketInformation;
    }

    public int getId() {
        return id;
    }

    public String getTicketTitle() {
        return ticketTitle;
    }

    public String getTicketType() {
        return ticketType;
    }

    public String getTicketQR() {
        return ticketQR;
    }

    public String getTicketInformation() {
        return ticketInformation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTicketTitle(String ticketTitle) {
        this.ticketTitle = ticketTitle;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public void setTicketQR(String ticketQR) {
        this.ticketQR = ticketQR;
    }

    public void setTicketInformation(String ticketInformation) {
        this.ticketInformation = ticketInformation;
    }
}
