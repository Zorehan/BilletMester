package DAL;

import BE.Tickets;
import BE.UserTickets;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {

    private DatabaseConnector databaseConnector;

    public TicketDAO()
    {
        databaseConnector = new DatabaseConnector();
    }

    public List<Tickets> getAllTickets() throws SQLException {
        ArrayList<Tickets> allTickets = new ArrayList<>();

        try(Connection conn = databaseConnector.getConnection();
            Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM dbo.Tickets;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next())
            {
                int id = rs.getInt("id");
                String ticketTitle = rs.getString("ticketTitle");
                String ticketType = rs.getString("ticketType");
                String ticketQR = rs.getString("ticketQR");
                String ticketInformation = rs.getString("ticketInformation");

                Tickets ticket = new Tickets(id, ticketTitle, ticketType, ticketQR, ticketInformation);
                allTickets.add(ticket);
            }
            return allTickets;
        }
    }

    public Tickets createTicket(Tickets ticket)
    {
        String sql = "INSERT INTO dbo.Tickets (ticketTitle, ticketType, ticketQR, ticketInformation) VALUES(?,?,?,?);";
        try(Connection conn = databaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, ticket.getTicketTitle());
            stmt.setString(2, ticket.getTicketType());
            stmt.setString(3, ticket.getTicketQR());
            stmt.setString(4, ticket.getTicketInformation());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if(rs.next())
            {
                id = rs.getInt(1);
            }

            Tickets createdTicket = new Tickets(id, ticket.getTicketTitle(), ticket.getTicketType(), ticket.getTicketQR(), ticket.getTicketInformation());
            return createdTicket;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Tickets> getTicketsByUserId(int userId)
    {
        List<Tickets> tickets = new ArrayList<>();
        String sql = "SELECT Ticket.* FROM Ticket" + "JOIN UserTickets ON Ticket.id = UserTickets.userID" + "WHERE UserTickets.userID = ?;";
        try(Connection conn = databaseConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, userId);

            try(ResultSet rs = stmt.executeQuery())
            {
                while(rs.next())
                {
                    int id = rs.getInt("id");
                    String ticketTitle = rs.getString("ticketTitle");
                    String ticketType = rs.getString("ticketType");
                    String ticketQR = rs.getString("ticketQR");
                    String ticketInformation = rs.getString("ticketInformation");

                    Tickets ticket = new Tickets(id, ticketTitle, ticketType, ticketQR, ticketInformation);
                    tickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }


    public UserTickets createUserTicket(UserTickets userTicket) throws SQLException {
        
        String sql = "INSERT INTO dbo.UserTickets (ticketID, userID) VALUES(?,?);";
        try(Connection conn = databaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setInt(1, userTicket.getTicketID());
            stmt.setInt(2, userTicket.getUserID());

            stmt.executeUpdate();


            UserTickets newUserTicket = new UserTickets(userTicket.getTicketID(), userTicket.getUserID());

            return  newUserTicket;
        }
    }

    public void updateTicket(Tickets ticket)
    {
        String sql = "UPDATE dbo.Ticket SET ticketTitle = ? ticketType = ?, ticketQR = ?, ticketInformation = ? WHERE id = ?;";
        try(Connection conn = databaseConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, ticket.getTicketTitle());
            stmt.setString(2, ticket.getTicketType());
            stmt.setString(3, ticket.getTicketQR());
            stmt.setString(4, ticket.getTicketInformation());
            stmt.setInt(5, ticket.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTicket(Tickets ticket)
    {
        String sql = "DELETE FROM dbo.Ticket WHERE id = ?;";
        try(Connection conn = databaseConnector.getConnection())
        {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, ticket.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Tickets> getTicketsByEventId(int eventId)
    {
        List<Tickets> tickets = new ArrayList<>();
        String sql = "SELECT Ticket.* FROM Ticket" + "JOIN EventTicket ON Ticket.id = EventTicket.TicketID" + "WHERE EventTickets.eventID = ?;";
        try(Connection conn = databaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, eventId);

            try(ResultSet rs = stmt.executeQuery())
            {
                while(rs.next())
                {
                    int id = rs.getInt("id");
                    String ticketTitle = rs.getString("ticketTitle");
                    String ticketType = rs.getString("ticketType");
                    String ticketQR = rs.getString("ticketQR");
                    String ticketInformation = rs.getString("ticketInformation");

                    Tickets ticket = new Tickets(id, ticketTitle, ticketType, ticketQR, ticketInformation);
                    tickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }
}
