package GUI.Controller;

import GUI.Model.ViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import util.HttpService;
import util.MailSender;
import util.PDFGenerator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class borderPaneController implements Initializable {
    @FXML
    private BorderPane borderPane;
    ViewModel viewModel = ViewModel.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            viewModel.setBorderPane(borderPane);
            initCenter();
            viewModel.setBanner();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
}
