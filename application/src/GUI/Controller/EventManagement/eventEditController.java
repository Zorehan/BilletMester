package GUI.Controller.EventManagement;

import BE.Events;
import BE.SpecialTickets;
import BE.Tickets;
import GUI.Model.EventModel;
import GUI.Model.TicketModel;
import GUI.Model.UserModel;
import GUI.Model.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class eventEditController implements Initializable {

    @FXML
    private ComboBox categoryComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Pane rootPane;
    @FXML
    private TextField titleField, locationField, notesField, descriptionField, previewImageField, bannerImageField, ticketsAmountField;
    @FXML
    private Button submitButton, cancelButton, chooseBannerBtn, choosePreviewBtn;

    private EventModel eventModel;
    private Events events;
    private Tickets tickets;
    private TicketModel ticketModel;
    private ViewModel viewModel;
    private UserModel userModel;
    private SpecialTickets specialTickets;

    private Events selectedEvent;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initModels();
        initCategories();
        initData(selectedEvent);
    }

    void initData(Events selectedEvent) {
        if (selectedEvent != null) {
            titleField.setText(selectedEvent.getEventName());
            locationField.setText(selectedEvent.getEventLocation());
            notesField.setText(selectedEvent.getEventNotes());
            descriptionField.setText(selectedEvent.getEventGuidance());
            previewImageField.setText(selectedEvent.getEventPreview());
            bannerImageField.setText(selectedEvent.getEventBanner());

            categoryComboBox.setValue(selectedEvent.getEventCategory());

            datePicker.setValue(selectedEvent.getEventStart().toLocalDate());

            ticketsAmountField.setText(String.valueOf(selectedEvent.getEventPrice()));
        }
    }


    private boolean validateInput() {
        if (titleField.getText().isEmpty() || locationField.getText().isEmpty() ||
                datePicker.getValue() == null || notesField.getText().isEmpty()) {
            showAlert("All fields marked with * are required.");
            return false;
        }
        return true;
    }

    // Alert Box
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText("Input Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void switchToMainView(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/MainPage/mainView.fxml"));
        Parent mainViewParent = loader.load();
        viewModel.getBorderPane().setCenter(mainViewParent);
    }

    private void initCategories() {
        for (Events.Categories category : Events.Categories.values()) {
            categoryComboBox.getItems().add(category);
        }
    }

    private void initModels() {
        eventModel = EventModel.getInstance();
        ticketModel = TicketModel.getInstance();
        viewModel = ViewModel.getInstance();
        userModel = UserModel.getInstance();
    }

    public void clickSubmit(ActionEvent actionEvent) {
        if (validateInput()) {
            // Update the selected event with the new data
            selectedEvent.setEventName(titleField.getText());
            selectedEvent.setEventLocation(locationField.getText());
            selectedEvent.setEventNotes(notesField.getText());
            selectedEvent.setEventGuidance(descriptionField.getText());
            selectedEvent.setEventPreview(previewImageField.getText());
            selectedEvent.setEventBanner(bannerImageField.getText());
            selectedEvent.setEventCategory(categoryComboBox.getValue().toString());
            selectedEvent.setEventStart(datePicker.getValue().atStartOfDay());
            selectedEvent.setEventPrice(Integer.parseInt(ticketsAmountField.getText()));

            // Set eventEnd to the end of the selected date
            LocalDate eventDate = datePicker.getValue();
            LocalDateTime eventEnd = eventDate.atTime(23, 59, 59);
            selectedEvent.setEventEnd(eventEnd);

            try {
                eventModel.updateEvent(selectedEvent);
                switchToMainView(actionEvent);
            } catch (Exception e) {
                showAlert("Failed to update event.");
                e.printStackTrace();
            }
        }
    }

    public void clickPreviewBtn() {
        previewImageField.setText(btnChoose());
    }

    public void clickBannerBtn() {
        bannerImageField.setText(btnChoose());
    }

    public String btnChoose() {   // Method to choose valid files
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show the file chooser dialog
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String originalPath = selectedFile.getAbsolutePath();
            String fileName = selectedFile.getName();
            String destination = "application/data/images";

            try {
                Path sourcePath = Paths.get(originalPath);
                Path destinationPath = Paths.get(destination, fileName);
                Files.copy(sourcePath, destinationPath);

                return "/images/" + fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return selectedFile.getAbsolutePath();  // Get the selected file path and save it
        }
        return "";
    }

    public void setSelectedEvent(Events event) {
        selectedEvent = event;
    }

}
