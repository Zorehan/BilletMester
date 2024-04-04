package GUI.Widgets;

import GUI.Controller.EventManagement.eventCreatorController;
import GUI.Controller.EventManagement.eventManagerController;
import GUI.Controller.adminViewController;
import GUI.Model.ViewModel;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

import java.io.IOException;

/**
 * This is the top right panel of both the banner on the main page
 * and the top bar on all the other pages.
 */
public class TopPanel extends HBox {
    private ViewModel viewModel = ViewModel.getInstance();
    private final int WIDTH = 400;
    public TopPanel() {
        this.setId("topPanel");
        this.getStylesheets().add("/GUI/Styling/BannerAndTopBar.css");
        initTopPanel();

        System.out.println("init");
    }

    public void initTopPanel() {
        this.setMaxWidth(1200);
        this.setMinWidth(800);
        this.setPrefWidth(1200);
        this.setAlignment(Pos.CENTER_RIGHT);

        //Setup images for buttons
        Image user = new Image(getClass().getResourceAsStream("/icons/user.png"));
        ImageView userView = new ImageView(user);
        Button btnUser = new Button();

        btnUser.setGraphic(userView);
        btnUser.setOnAction(this::clickUser);
        btnUser.setId("btnUser");

        userView.setPreserveRatio(true);
        userView.setFitWidth(30);

        //Setup Search Bar
        HBox searchBarBox = new HBox();
        searchBarBox.setId("searchBarBox");
        searchBarBox.setPrefWidth(200);

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search");
        searchBar.setId("txtSearch");
        searchBarBox.setAlignment(Pos.CENTER_LEFT);

        Image search = new Image(getClass().getResourceAsStream("/icons/search.png"));
        ImageView imgSearch = new ImageView(search);
        imgSearch.setId("imgSearch");

        imgSearch.setPreserveRatio(true);
        imgSearch.setFitWidth(20);
        searchBarBox.getChildren().addAll(imgSearch, searchBar);

        this.getChildren().addAll(searchBarBox, btnUser);
    }

    private void initEventManager() {
        Button button = new Button("Event Manager");
        button.setId("userButton");
        button.setOnAction(this::clickEventManager);
        this.setPrefWidth(600);
        this.setLayoutX(WIDTH - 600);
        this.setAlignment(Pos.CENTER_RIGHT);
        this.getChildren().addFirst(button);
    }

    private void initEventCreator() {
        Button button = new Button("Event Creator");
        button.setId("userButton");
        button.setOnAction(this::clickEventCreator);
        this.setPrefWidth(600);
        this.setLayoutX(WIDTH - 600);
        this.setAlignment(Pos.CENTER_RIGHT);
        this.getChildren().addFirst(button);
    }


    private void initAdmin() {
        Button button = new Button("Admin Panel");
        button.setId("userButton");
        button.setOnAction(this::clickAdminView);
        this.setPrefWidth(500);
        this.setLayoutX(WIDTH - 500);
        this.setAlignment(Pos.CENTER_RIGHT);
        this.getChildren().addFirst(button);
    }

    public void clickUser(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/UserView/userView.fxml"));
            Parent userView = loader.load();
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/GUI/View/UserView/sideBarView.fxml"));
            Parent sideBarView = loader1.load();

            viewModel.getBorderPane().setCenter(userView);
            viewModel.getBorderPane().setLeft(sideBarView);

            viewModel.setTopBar();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public void clickEventCreator(ActionEvent actionEvent) {
        // Load the new scene asynchronously
        Task<Pair<Parent, eventCreatorController>> task = new Task<>() {
            @Override
            protected Pair<Parent, eventCreatorController> call() throws IOException {
                // Inside the call method, we define the asynchronous task to be executed
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/EventManagement/eventCreatorView.fxml"));
                Parent parent = loader.load();
                eventCreatorController controller = loader.getController();
                return new Pair<>(parent, controller); // Return the loaded Parent and controller as a Pair
            }
        };

        // Define actions to be performed when the task succeeds
        task.setOnSucceeded(event -> {
            Pair<Parent, eventCreatorController> result = task.getValue();
            Parent p = result.getKey();
            eventCreatorController eventManagerController = result.getValue();
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

    public void clickEventManager(ActionEvent actionEvent) {
        // Load the new scene asynchronously
        Task<Pair<Parent, eventManagerController>> task = new Task<>() {
            @Override
            protected Pair<Parent, eventManagerController> call() throws IOException {
                // Inside the call method, we define the asynchronous task to be executed
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/EventManagement/eventManagerView.fxml"));
                Parent parent = loader.load();
                eventManagerController controller = loader.getController();
                return new Pair<>(parent, controller); // Return the loaded Parent and controller as a Pair
            }
        };

        // Define actions to be performed when the task succeeds
        task.setOnSucceeded(event -> {
            Pair<Parent, eventManagerController> result = task.getValue();
            Parent p = result.getKey();
            eventManagerController eventManagerController = result.getValue();
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
