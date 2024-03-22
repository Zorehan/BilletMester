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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/eventCreatorView.fxml"));
                Parent parent = loader.load();
                eventCreatorController controller = loader.getController();
                return new Pair<>(parent, controller);
            }
        };

        task.setOnSucceeded(event -> {
            Pair<Parent, eventCreatorController> result = task.getValue();
            Parent p = result.getKey();
            eventCreatorController eventCreatorController = result.getValue();
            borderPane.setLeft(p);
        });

        task.setOnFailed(event -> {
            Throwable exception = task.getException();
            if (exception != null) {
                exception.printStackTrace();
            }
        });

        // Start the task in a background thread
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
