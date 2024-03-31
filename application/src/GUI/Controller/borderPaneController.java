package GUI.Controller;

import GUI.Model.ViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import util.HttpService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class borderPaneController implements Initializable {
    @FXML
    private BorderPane borderPane;
    ViewModel viewModel = ViewModel.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewModel.setBorderPane(borderPane);
        initCenter();
        initBanner();
    }

    public void initCenter() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/MainPage/mainView.fxml"));
            Parent parent = loader.load();
            borderPane.setCenter(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initBanner() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/MainPage/bannerView.fxml"));
            Parent parent = loader.load();
            borderPane.setTop(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
