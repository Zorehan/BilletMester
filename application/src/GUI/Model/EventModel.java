package GUI.Model;

import BE.Events;
import BLL.EventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

public class EventModel {

    private static EventModel instance;

    private EventManager eventManager;

    private ObservableList<Events> allEvents;

    private Events event;

    public EventModel()
    {
        eventManager = new EventManager();

        allEvents = FXCollections.observableArrayList();
        allEvents.addAll(eventManager.getAllEvents());
    }

    public static EventModel getInstance()
    {
        if(instance == null)
        {
            instance = new EventModel();
        }
        return instance;
    }

    public ObservableList<Events> getObservableEvents()
    {
        return allEvents;
    }

    public void clearObservableEvents()
    {
        allEvents.clear();
    }

    public Events createEvent(Events newEvent)
    {
        Events event = eventManager.createEvent(newEvent);
        allEvents.add(event);
        return event;
    }

    public void deleteEvent(Events event)
    {
        eventManager.deleteEvent(event);
        allEvents.remove(event);
    }

    public void setEvent(Events event) {
        this.event = event;
    }

    public Events getEvent() {
        return event;
    }

    public void updateEvent(Events event)
    {
        eventManager.updateEvent(event);
    }

    public List<Events> getEventsByCategory(String category) {
        return allEvents.stream()
                .filter(event -> event.getEventCategory().toString().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
}
