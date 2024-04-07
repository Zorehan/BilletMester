package GUI.Model;

import BE.Events;
import BLL.EventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewModel {
    private static ViewModel instance;
    private BorderPane borderPane;
    private Stage stage;

    public ViewModel() {}

    public static ViewModel getInstance()
    {
        if(instance == null)
        {
            instance = new ViewModel();
        }
        return instance;
    }

    public BorderPane getBorderPane(){
        return borderPane;
    }
    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    public double getWidth() {
        return borderPane.getWidth();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Disable any side panel if there were to be any.
     */
    public void disableSidePanel() {
        borderPane.setLeft(null);
    }

    /**
     * Sets the top bar, the default one that is not supposed to be
     * shown on the main page but every other page.
     * @throws IOException
     */
    public void setTopBar() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/MainPage/topBarView.fxml"));
        Parent parent = loader.load();
        borderPane.setTop(parent);
    }

    /**
     * Sets the banner, it picks the first event in the list of
     * observable events and makes a large banner of it.
     * This can be seen in the banner controller class, in the initialize
     * method.
     * @throws IOException
     */
    public void setBanner() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/MainPage/bannerView.fxml"));
        Parent parent = loader.load();
        borderPane.setTop(parent);
    }

    /**
     * Logs out of the current account.
     */
    public void logOut() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/Login/loginView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = getStage();
        stage.setScene(scene);
        setStage(stage);
    }
}
