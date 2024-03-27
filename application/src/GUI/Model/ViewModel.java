package GUI.Model;

import BE.Events;
import BLL.EventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;

public class ViewModel {
    private static ViewModel instance;
    private BorderPane borderPane;

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
}
