package GUI.Controller;

import BE.Events;
import GUI.Model.EventModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class bannerController implements Initializable {

    @FXML
    private Button btnCart, btnUser;
    @FXML
    private TextField txtSearch;
    @FXML
    private ImageView imgSearch;
    @FXML
    private HBox searchBarBox, topPanelBox;
    @FXML
    private Pane mainPane;
    @FXML
    private StackPane stackPane;
    private EventModel eventModel = EventModel.getInstance();
    private Events event;
    private final int HEIGHT = 200;
    private final int WIDTH = 1200;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stackPane.setPrefHeight(HEIGHT);
        Rectangle clipRect = new Rectangle(WIDTH, HEIGHT);
        stackPane.setClip(clipRect);

        initTopPanel();
        event = eventModel.getObservableEvents().get(0);
        initEvent(event);
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
        top.setId("topBar");

        vbox.setLayoutY(HEIGHT - VBOX_HEIGHT);
        vbox.setPrefHeight(VBOX_HEIGHT);
        System.out.println(mainPane.getHeight());


        mainPane.getChildren().addFirst(initBannerImage(event));
        mainPane.getChildren().addAll(vbox);
    }

    public void initTopPanel() {
        topPanelBox.setAlignment(Pos.CENTER_LEFT);

        //Setup images for buttons
        Image cart = new Image(getClass().getResourceAsStream("/icons/shopping.png"));
        Image user = new Image(getClass().getResourceAsStream("/icons/user.png"));
        ImageView cartView = new ImageView(cart);
        ImageView userView = new ImageView(user);

        btnCart.setGraphic(cartView);
        btnUser.setGraphic(userView);

        cartView.setPreserveRatio(true);
        cartView.setFitWidth(30);
        userView.setPreserveRatio(true);
        userView.setFitWidth(30);

        //Setup Search Bar
        searchBarBox.setAlignment(Pos.CENTER_LEFT);

        Image search = new Image(getClass().getResourceAsStream("/icons/search.png"));
        imgSearch.setImage(search);

        imgSearch.setPreserveRatio(true);
        imgSearch.setFitWidth(20);
    }

    public ImageView initBannerImage(Events event){
        Image imgBanner = new Image(getClass().getResourceAsStream("/images/banner.jpg"));
        ImageView banner = new ImageView(imgBanner);

        return banner;
    }
}
