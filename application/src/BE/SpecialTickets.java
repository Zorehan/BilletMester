package BE;

public class SpecialTickets {

    private int id;
    private String ticketTitle;
    private String ticketType;
    private String ticketQR;
    private String ticketInformation;

    public SpecialTickets(int id, String ticketTitle, String ticketType, String ticketQR, String ticketInformation)
    {
        this.id = id;
        this.ticketTitle = ticketTitle;
        this.ticketType = ticketType;
        this.ticketQR = ticketQR;
        this.ticketInformation =ticketInformation;
    }
}
