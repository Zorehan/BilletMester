package GUI.Widgets;

import BE.Events;
import GUI.Model.EventModel;
import GUI.Model.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class BannerWidget extends Pane{
    private final int HEIGHT = 200;
    private final int WIDTH = 1200;
    private Events event;
    private EventModel eventModel = EventModel.getInstance();
    private ViewModel viewModel = ViewModel.getInstance();

    public BannerWidget() {
        this.getStylesheets().add("GUI/Styling/BannerAndTopBar.css");
        if(!eventModel.getObservableEvents().isEmpty()) {
            event = eventModel.getObservableEvents().get(0); //Tager første event til at blive banner.
            initBanner(event);
        }
    }

    public BannerWidget(Events event) {
        StackPane stackPane = new StackPane();
        stackPane.setPrefHeight(HEIGHT);
        Rectangle clipRect = new Rectangle(WIDTH, HEIGHT);
        stackPane.setClip(clipRect);
        initBanner(event);
    }

    public void initBanner(Events event) {
        //Lang snippet, im sorry
        int VBOX_HEIGHT = 80;

        VBox vbox = new VBox(-20);
        HBox top = new HBox();
        HBox bottom = new HBox();
        Label genre = new Label("Concert");
        Label date = new Label(event.getEventStart().format(DateTimeFormatter.ofPattern("dd. MMMM")));
        Label title = new Label(event.getEventName());


        vbox.setPadding(new Insets(0, 10, 0, 15));

        top.getChildren().addAll(genre, date);
        top.setAlignment(Pos.BOTTOM_LEFT);
        date.setPrefHeight(80);
        genre.setPrefHeight(80);

        bottom.getChildren().add(title);
        bottom.setAlignment(Pos.TOP_LEFT);
        top.setAlignment(Pos.BOTTOM_LEFT);

        vbox.getChildren().addAll(top, bottom);

        vbox.setId("titleBg");
        genre.setId("lblGenre");
        date.setId("lblDate");
        title.setId("lblTitle");

        vbox.setLayoutY(HEIGHT - VBOX_HEIGHT);
        vbox.setPrefHeight(VBOX_HEIGHT);

        this.getChildren().add(vbox);
        this.getChildren().addFirst(initBannerImage(event));
    }

    public ImageView initBannerImage(Events event){
        Image imgBanner = new Image(getClass().getResourceAsStream("/images/banner.jpg"));
        ImageView banner = new ImageView(imgBanner);

        return banner;
    }
}
