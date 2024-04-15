package GUI.Controller;

import GUI.Model.ViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class borderPaneController implements Initializable {
    @FXML
    private BorderPane borderPane;
    ViewModel viewModel = ViewModel.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // viewModel.setBorderPane(borderPane) kaster ikke en IOException, som før
        // så derfor flyttes try-catch blokken kun til at omgive initCenter metoden.
        viewModel.setBorderPane(borderPane);
        initCenter();
        try {
            viewModel.setBanner();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initCenter() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/MainPage/mainView.fxml"));
            // Bemærk: Ingen grund til at kalde getResourceAsStream og derefter sætte location separat.
            // FXMLLoader kan håndtere loading direkte fra en URL, som returneres af getResource.
            Parent parent = loader.load();
            borderPane.setCenter(parent); // Enkelt kald er tilstrækkeligt
        } catch (IOException e) {
            // En mere meningsfuld exception håndtering. I stedet for at kaste en runtime exception,
            // overvej at logge fejlen eller vise en dialogboks med en fejlmeddelelse til brugeren.
            System.err.println("Fejl ved indlæsning af MainView: " + e.getMessage());
            e.printStackTrace(); // Kun til debugging, overvej at fjerne i produktion eller logge på en mere passende måde
        }
    }
}
