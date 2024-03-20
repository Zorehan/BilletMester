package DAL;

import BE.SpecialTickets;
import BE.Tickets;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecialTicketDAO {

    private DatabaseConnector databaseConnector;

    public SpecialTicketDAO()
    {
        databaseConnector = new DatabaseConnector();
    }

    public List<SpecialTickets> getAllTickets() throws SQLException {
        ArrayList<SpecialTickets> allTickets = new ArrayList<>();

        try(Connection conn = databaseConnector.getConnection();
            Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM dbo.SpecialTickets;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next())
            {
                int id = rs.getInt("id");
                String ticketTitle = rs.getString("ticketTitle");
                String ticketType = rs.getString("ticketType");
                String ticketQR = rs.getString("ticketQR");
                String ticketInformation = rs.getString("ticketInformation");

                SpecialTickets specialTickets = new SpecialTickets(id, ticketTitle, ticketType, ticketQR, ticketInformation);
                allTickets.add(specialTickets);
            }
            return allTickets;
        }
    }

    public SpecialTickets createTicket(SpecialTickets ticket)
    {
        String sql = "INSERT INTO dbo.SpecialTickets (ticketTitle, ticketType, ticketQR, ticketInformation) VALUES(?,?,?,?);";
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

            SpecialTickets CreatedSpecialTickets = new SpecialTickets(id, ticket.getTicketTitle(), ticket.getTicketType(), ticket.getTicketQR(), ticket.getTicketInformation());
            return CreatedSpecialTickets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTicket(SpecialTickets ticket)
    {
        String sql = "UPDATE dbo.SpecialTicket SET ticketTitle = ? ticketType = ?, ticketQR = ?, ticketInformation = ? WHERE id = ?;";
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

    public void deleteTicket(SpecialTickets ticket)
    {
        String sql = "DELETE FROM dbo.SpecialTickets WHERE id = ?;";
        try(Connection conn = databaseConnector.getConnection())
        {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, ticket.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
}
