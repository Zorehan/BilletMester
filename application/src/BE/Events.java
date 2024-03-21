package BE;
import java.time.LocalDateTime;

public class Events {
private int id;
private String eventName;
private String eventHost;
private LocalDateTime eventStart;
private LocalDateTime eventEnd;
private String eventLocation;
private String eventNotes;
private String eventGuidance;
private String eventBanner;
private String eventPreview;

    public Events(int id, String eventName, String eventHost, LocalDateTime eventStart, LocalDateTime eventEnd, String eventLocation, String eventNotes, String eventGuidance, String eventBanner, String eventPreview)
    {
        this.id = id;
        this.eventName = eventName;
        this.eventHost = eventHost;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.eventLocation = eventLocation;
        this.eventNotes = eventNotes;
        this.eventGuidance = eventGuidance;
        this.eventBanner = eventBanner;
        this.eventPreview = eventPreview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventHost() {
        return eventHost;
    }

    public void setEventHost(String eventHost) {
        this.eventHost = eventHost;
    }

    public LocalDateTime getEventStart() {
        return eventStart;
    }

    public void setEventStart(LocalDateTime eventStart) {
        this.eventStart = eventStart;
    }

    public LocalDateTime getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(LocalDateTime eventEnd) {
        this.eventEnd = eventEnd;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventNotes() {
        return eventNotes;
    }

    public void setEventNotes(String eventNotes) {
        this.eventNotes = eventNotes;
    }

    public String getEventGuidance() {
        return eventGuidance;
    }

    public void setEventGuidance(String eventGuidance) {
        this.eventGuidance = eventGuidance;
    }

    public String getEventBanner() {
        return eventBanner;
    }

    public void setEventBanner(String eventBanner) {
        this.eventBanner = eventBanner;
    }

    public String getEventPreview() {
        return eventPreview;
    }

    public void setEventPreview(String eventPreview) {
        this.eventPreview = eventPreview;
    }
}
