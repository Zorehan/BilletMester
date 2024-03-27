package GUI.Controller;

import GUI.Model.ViewModel;
import javafx.concurrent.Task;
import BE.Events;
import GUI.Model.EventModel;
import GUI.Widgets.EventWidget;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.util.Pair;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class mainViewController implements Initializable {
    private BorderPane borderPane;
    @FXML
    private VBox centerVBox;
    EventModel eventModel;
    ViewModel viewModel;
    List<Events> eventsList = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventModel = EventModel.getInstance();
        viewModel = ViewModel.getInstance();
        borderPane = viewModel.getBorderPane();

        eventsList = eventModel.getObservableEvents();

        HBox hBox = new HBox(40);
        hBox.setPadding(new Insets(10));
        for(Events event : eventsList) {
            EventWidget widget = new EventWidget(event);
            hBox.getChildren().add(widget);
        }

        centerVBox.getChildren().add(hBox);

        viewModel.setBorderPane(borderPane);
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
            borderPane.setCenter(p); // Set the loaded Parent (scene) to the BorderPane
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
            borderPane.setCenter(p); // Set the loaded Parent (scene) to the BorderPane
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
