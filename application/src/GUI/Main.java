package GUI;

import GUI.Controller.eventCreatorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Parent eventCreatorParent;
    private eventCreatorController eventCreatorController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Preload the event creator view during application startup
        FXMLLoader eventCreatorLoader = new FXMLLoader(getClass().getResource("/GUI/View/eventCreatorView.fxml"));
        eventCreatorParent = eventCreatorLoader.load();
        eventCreatorController = eventCreatorLoader.getController();

        // Set up your main stage and show it
        primaryStage.setTitle("Event Creator View");
        primaryStage.setScene(new Scene(eventCreatorParent));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // Getter methods for the preloaded resources
    public Parent getEventCreatorParent() {
        return eventCreatorParent;
    }

    public eventCreatorController getEventCreatorController() {
        return eventCreatorController;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
