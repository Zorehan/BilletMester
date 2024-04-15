package DAL;

import BE.Events;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    private DatabaseConnector databaseConnector;

    public EventDAO()
    {
        databaseConnector = new DatabaseConnector();
    }

    public List<Events> getAllEvents()
    {
        ArrayList<Events> allEvents = new ArrayList<>();

        try(Connection conn = databaseConnector.getConnection();
            Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM dbo.Events;";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next())
            {
                int id = rs.getInt("id");
                String eventName = rs.getString("eventName");
                String eventHost = rs.getString("eventHost");
                Date startSqlDate = rs.getDate("eventStart");
                Date endSqlDate = rs.getDate("eventEnd");
                LocalDateTime eventStart = startSqlDate.toLocalDate().atStartOfDay();
                LocalDateTime eventEnd = endSqlDate.toLocalDate().atStartOfDay();
                String eventLocation = rs.getString("eventLocation");
                String eventNotes = rs.getString("eventNotes");
                String eventGuidance = rs.getString("eventGuidance");
                String eventBanner = rs.getString("eventBanner");
                String eventPreview = rs.getString("eventPreview");
                Events.Categories eventCategory = Events.Categories.valueOf(rs.getString("eventCategory"));
                Integer eventPrice = rs.getInt("eventPrice");

                Events event = new Events(id,eventName,eventHost,eventStart,eventEnd,eventLocation,eventNotes,eventGuidance,eventBanner,eventPreview, eventCategory, eventPrice);
                allEvents.add(event);
            }
            return allEvents;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Events createEvent(Events event)
    {
        String sql = "INSERT INTO dbo.Events (eventName, eventHost, eventStart, eventEnd, eventLocation, eventNotes, eventGuidance,eventBanner,eventPreview, eventCategory, eventPrice) VALUES(?,?,?,?,?,?,?,?,?,?,?);";
        try(Connection conn = databaseConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, event.getEventName());
            stmt.setString(2, event.getEventHost());
            stmt.setTimestamp(3, Timestamp.valueOf(event.getEventStart()));
            stmt.setTimestamp(4, Timestamp.valueOf(event.getEventEnd()));
            stmt.setString(5, event.getEventLocation());
            stmt.setString(6, event.getEventNotes());
            stmt.setString(7, event.getEventGuidance());
            stmt.setString(8,event.getEventBanner());
            stmt.setString(9,event.getEventPreview());
            stmt.setString(10,event.getEventCategory().toString());
            stmt.setInt(11, event.getEventPrice());

            stmt.executeUpdate();

            ResultSet rs= stmt.getGeneratedKeys();
            int id = 0;

            if(rs.next())
            {
                id = rs.getInt(1);
            }

            Events createdEvent = new Events(id, event.getEventName(), event.getEventHost(), event.getEventStart(), event.getEventEnd(), event.getEventLocation(), event.getEventNotes(), event.getEventGuidance(), event.getEventBanner(), event.getEventPreview(), event.getEventCategory(), event.getEventPrice());
            return createdEvent;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateEvent(Events event)
    {
        String sql = "UPDATE dbo.Events SET eventName = ?, eventHost = ?, eventStart = ?, eventEnd = ?, eventLocation = ?, eventNotes = ?, eventGuidance = ?, eventBanner = ?, eventPreview = ?, eventCategory = ?, eventPrice = ? WHERE id = ?;";

        try(Connection conn = databaseConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, event.getEventName());
            stmt.setString(2, event.getEventHost());
            stmt.setTimestamp(3, Timestamp.valueOf(event.getEventStart()));
            stmt.setTimestamp(4, Timestamp.valueOf(event.getEventEnd()));
            stmt.setString(5, event.getEventLocation());
            stmt.setString(6, event.getEventNotes());
            stmt.setString(7, event.getEventGuidance());
            stmt.setString(8, event.getEventBanner());
            stmt.setString(9,event.getEventPreview());
            stmt.setString(10,event.getEventCategory().toString());
            stmt.setInt(11, event.getEventPrice());

            stmt.setInt(12, event.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEvent(Events event)
    {
        String sql = "DELETE FROM dbo.Events WHERE id = ?;";
        try(Connection conn = databaseConnector.getConnection())
        {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, event.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
