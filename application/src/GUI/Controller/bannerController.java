package GUI.Controller;

import BE.Events;
import BE.Users.UserEnum;
import GUI.Controller.EventManagement.eventCreatorController;
import GUI.Controller.EventManagement.eventManagerController;
import GUI.Model.EventModel;
import GUI.Model.UserModel;
import GUI.Model.ViewModel;
import GUI.Widgets.TopPanel;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class bannerController implements Initializable {

    @FXML
    private Pane mainPane;
    @FXML
    private StackPane stackPane;
    private EventModel eventModel = EventModel.getInstance();
    private UserModel userModel = UserModel.getInstance();
    private ViewModel viewModel = ViewModel.getInstance();
    private Events event;
    private final int HEIGHT = 200;
    private final int WIDTH = 1200;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stackPane.setPrefHeight(HEIGHT);
        Rectangle clipRect = new Rectangle(WIDTH, HEIGHT);
        stackPane.setClip(clipRect);

        if(!eventModel.getObservableEvents().isEmpty()) {
            event = eventModel.getObservableEvents().get(0); //Tager første event til at blive banner.
            initEvent(event);
        }

        HBox topPanel = new TopPanel();
        mainPane.getChildren().addLast(topPanel);
    }

    public void initEvent(Events event) {
        //Lang snippet, im sorry
        int VBOX_HEIGHT = 80;
        VBox vbox = new VBox(-20);
        HBox top = new HBox();
        HBox bottom = new HBox();
        Label genre = new Label("Concert");
        Label date = new Label(event.getEventStart().format(DateTimeFormatter.ofPattern("dd. MMMM")));
        Label title = new Label(event.getEventName());


        vbox.setPadding(new Insets(0, 10, 0, 0));

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

        mainPane.getChildren().addFirst(vbox);
        mainPane.getChildren().addFirst(initBannerImage(event));
    }

    public ImageView initBannerImage(Events event){
        Image imgBanner = new Image(getClass().getResourceAsStream("/images/banner.jpg"));
        ImageView banner = new ImageView(imgBanner);

        return banner;
    }
}
