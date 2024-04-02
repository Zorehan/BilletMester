package GUI.Controller;

import BE.Events;
import BE.Users.UserEnum;
import GUI.Model.EventModel;
import GUI.Model.UserModel;
import GUI.Model.ViewModel;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
import javafx.util.Pair;

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

        initTopPanel();
        if(userModel.getUser().getUserType() == UserEnum.ADMIN) {
            initAdmin();
        }

        if(userModel.getUser().getUserType() == UserEnum.EVENTCOORDINATOR)
        {
            initEventCo();
        }
        if(eventModel.getObservableEvents().size() != 0) {
            event = eventModel.getObservableEvents().get(0);
            initEvent(event);
        }
    }

    private void initEventCo() {
        Button button = new Button("Event Manager");
        button.setId("userButton");
        button.setOnAction(this::clickEventCreator);
        topPanelBox.setPrefWidth(500);
        topPanelBox.setLayoutX(WIDTH - 500);
        topPanelBox.setAlignment(Pos.CENTER_RIGHT);
        topPanelBox.getChildren().addFirst(button);
    }

    private void initAdmin() {
        Button button = new Button("Admin Panel");
        button.setId("userButton");
        button.setOnAction(this::clickAdminView);
        topPanelBox.setPrefWidth(500);
        topPanelBox.setLayoutX(WIDTH - 500);
        topPanelBox.setAlignment(Pos.CENTER_RIGHT);
        topPanelBox.getChildren().addFirst(button);
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

    public void clickEventCreator(ActionEvent actionEvent) {
        // Load the new scene asynchronously
        Task<Pair<Parent, eventCreatorController>> task = new Task<>() {
            @Override
            protected Pair<Parent, eventCreatorController> call() throws IOException {
                // Inside the call method, we define the asynchronous task to be executed
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/eventCreatorView.fxml"));
                Parent parent = loader.load();
                eventCreatorController controller = loader.getController();
                return new Pair<>(parent, controller); // Return the loaded Parent and controller as a Pair
            }
        };

        // Define actions to be performed when the task succeeds
        task.setOnSucceeded(event -> {
            Pair<Parent, eventCreatorController> result = task.getValue();
            Parent p = result.getKey();
            eventCreatorController eventCreatorController = result.getValue();
            viewModel.getBorderPane().setCenter(p); // Set the loaded Parent (scene) to the BorderPane
        });

        // Define actions to be performed when the task fails
        task.setOnFailed(event -> {
            Throwable exception = task.getException();
            if (exception != null) {
                exception.printStackTrace(); // Print the stack trace of the exception
            }
        });

        // Start the task in a background thread
        Thread thread = new Thread(task);
        thread.setDaemon(true); // Set the thread as daemon to allow the application to exit if the task is not complete
        thread.start();
    }

    public void clickAdminView(ActionEvent actionEvent) {
        // Load the new scene asynchronously
        Task<Pair<Parent, adminViewController>> task = new Task<>() {
            @Override
            protected Pair<Parent, adminViewController> call() throws IOException {
                // Inside the call method, we define the asynchronous task to be executed
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/adminView.fxml"));
                Parent parent = loader.load();
                adminViewController controller = loader.getController();
                return new Pair<>(parent, controller); // Return the loaded Parent and controller as a Pair
            }
        };
        // Define actions to be performed when the task succeeds
        task.setOnSucceeded(event -> {
            Pair<Parent, adminViewController> result = task.getValue();
            Parent p = result.getKey();
            adminViewController adminViewController = result.getValue();
            viewModel.getBorderPane().setCenter(p); // Set the loaded Parent (scene) to the BorderPane
        });
        // Define actions to be performed when the task fails
        task.setOnFailed(event -> {
            Throwable exception = task.getException();
            if (exception != null) {
                exception.printStackTrace(); // Print the stack trace of the exception
            }
        });
        // Start the task in a background thread
        Thread thread = new Thread(task);
        thread.setDaemon(true); // Set the thread as daemon to allow the application to exit if the task is not complete
        thread.start();
    }
}
