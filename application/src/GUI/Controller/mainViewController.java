package GUI.Controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.util.Pair;

import java.io.IOException;

public class mainViewController {
    @FXML
    private BorderPane borderPane;

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
            borderPane.setLeft(p); // Set the loaded Parent (scene) to the BorderPane
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
