package GUI.Controller;

import BE.Events;
import BE.Users.UserEnum;
import GUI.Controller.EventManagement.eventCreatorController;
import GUI.Controller.EventManagement.eventManagerController;
import GUI.Model.EventModel;
import GUI.Model.UserModel;
import GUI.Model.ViewModel;
import GUI.Widgets.BannerWidget;
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

        Pane banner;
        if(eventModel.getEvent() != null) {
            banner = new BannerWidget(eventModel.getEvent());
        }
        else {
            banner = new BannerWidget();
        }
        mainPane.getChildren().add(banner);

        HBox topPanel = new TopPanel();
        mainPane.getChildren().add(topPanel);
    }
}
