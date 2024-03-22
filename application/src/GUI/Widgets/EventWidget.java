package GUI.Widgets;

import BE.Events;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventWidget extends Region {

    private String IMG_URL = "/images/gun2.jpg";
    private String eventName;
    private String eventNotes;
    private String eventLocation;
    private LocalDateTime eventStart;
    private final int HEIGHT = 230;
    private final int WIDTH = 290;
    private final int INFO_HEIGHT = 80;

    /*
        Ærligt, ikke tænk for meget over denne klasse,
        den er relativt kompliceret sat sammen, da
        det med at lave dynamisk ui godt kan være ret
        kringlet, med at pakke vBoxe ind i andre vBoxe osv.
        Spørg hvis i er i tvivl - Nicklas
     */

    public EventWidget(Events event) {
        this.getStylesheets().add("GUI/Styling/EventWidget.css");

        eventName = event.getEventName();
        eventNotes = event.getEventNotes();
        eventLocation = event.getEventLocation();
        eventStart = event.getEventStart();

        this.setPrefSize(WIDTH, HEIGHT);
        this.setId("eventWidget");

        ImageView img = new ImageView(new Image(getClass().getResourceAsStream(IMG_URL)));
        img.setFitHeight(HEIGHT);
        img.setFitWidth(WIDTH);

        Rectangle clipRect = new Rectangle(WIDTH, HEIGHT);
        clipRect.setArcWidth(60);
        clipRect.setArcHeight(60);  //Kan simpelthen ikke få kanterne afrundet uden at hardcode der her... ????? fuck css og javafx
        clipRect.setId("clipRect");
        this.setClip(clipRect);

        StackPane titlePane = constructTitlePane();
        StackPane infoPane = constructInfoPane();
        Button button = constructButton();

        this.getChildren().addAll(img, button, titlePane, infoPane);
    }

    private Button constructButton() {
        Button button = new Button();

        //Hard coded hvor langt over Info felt den skal være
        int differential = INFO_HEIGHT + 30;

        button.setText("See more");
        button.setPrefWidth(80);
        button.setLayoutY(HEIGHT - differential);
        button.setLayoutX(WIDTH - 90);
        button.setId("button");

        return button;
    }

    private StackPane constructTitlePane() {
        StackPane titlePane = new StackPane();
        int differential = INFO_HEIGHT + 30;

        titlePane.setPrefHeight(35);
        titlePane.setPrefWidth(130);
        titlePane.setLayoutY(HEIGHT - differential);

        titlePane.setId("titleBox");
        Label titleLbl = new Label(eventName);
        titleLbl.setId("smallTitle");

        titlePane.getChildren().addAll(titleLbl);
        return titlePane;
    }

    private StackPane constructInfoPane() {
        StackPane stackPane = new StackPane();
        VBox backgroundBox = new VBox();
        HBox topInfoBox = new HBox();
        Label notesLbl = new Label(eventNotes);

        topInfoBox.getChildren().addAll(constructLocation(), constuctDateAndTime());
        backgroundBox.getChildren().addAll(topInfoBox, notesLbl);
        backgroundBox.setId("infoBox");


        notesLbl.setId("notes");
        notesLbl.setWrapText(true);
        notesLbl.setPrefWidth(WIDTH - 60);

        stackPane.setLayoutY(HEIGHT - INFO_HEIGHT);
        stackPane.setPrefWidth(WIDTH);
        stackPane.setPrefHeight(INFO_HEIGHT);

        stackPane.getChildren().addAll(backgroundBox);
        return stackPane;
    }

    private HBox constructLocation() {
        HBox hBox = new HBox(10);
        ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/icons/location.png")));
        Label locationLbl = new Label(eventLocation);

        img.setId("locationPin");
        img.setPreserveRatio(true);
        img.setFitWidth(15);

        locationLbl.setId("location");

        hBox.getChildren().addAll(img, locationLbl);
        return hBox;
    }

    private VBox constuctDateAndTime() {
        VBox box = new VBox();
        HBox dateBox = new HBox();
        HBox timeBox = new HBox();
        Label dateLbl = new Label(eventStart.format(DateTimeFormatter.ISO_DATE));
        Label timeLbl = new Label();

        dateBox.getChildren().add(dateLbl);

        box.getChildren().addAll(dateBox);

        return box;
    }
}
