package GUI.Controller.EventManagement;

import BE.Events;
import BE.SpecialTickets;
import BE.Tickets;
import BE.Users.User;
import GUI.Model.EventModel;
import GUI.Model.TicketModel;
import GUI.Model.UserModel;
import GUI.Model.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class eventCreatorController {
    @FXML
    private ComboBox<Events.Categories>categoryComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField ticketsField, ticketsFoodAmountField, ticketsDrinkAmountField, ticketsDiscountsAmountField, ticketDrinkField, ticketDiscountField, ticketsFoodField, ticketsAmountField;
    @FXML
    private Pane rootPane;
    @FXML
    private TextField titleField, locationField, notesField, descriptionField, previewImageField, bannerImageField;
    @FXML
    private Button submitButton, cancelButton, chooseBannerBtn, choosePreviewBtn;

    private EventModel eventModel;
    private Events events;
    private Tickets tickets;
    private TicketModel ticketModel;
    private ViewModel viewModel;
    private UserModel userModel;
    private SpecialTickets specialTickets;

    public void initialize() {
        initModels();
        initCategories();
    }


    // TODO: Refactor + Lav nyt input validation


    public void createNewEvent() {
        User loggedInUser = userModel.getUser();

        // Retrieve input values from the GUI
        String title = titleField.getText();
        String hostName = loggedInUser.getFullName();
        String location = locationField.getText();
        LocalDate eventDate = datePicker.getValue(); // Get the date from the datePicker
        LocalDateTime eventStart = eventDate.atStartOfDay(); // Set the start time to the beginning of the selected date
        LocalDateTime eventEnd = eventDate.atTime(23, 59, 59); // Set the end time to the end of the selected date
        String notes = notesField.getText();
        String bannerImage = bannerImageField.getText();
        String previewImage = previewImageField.getText();
        String description = descriptionField.getText();
        Events.Categories eventCategory = categoryComboBox.getValue();
        Integer eventPrice = Integer.valueOf(ticketsAmountField.getText());

        // Create the event - -1 is a placeholder id, the actual id gets automatically set in the DB
        Events event = new Events(-1, title, hostName, eventStart, eventEnd, location, notes, description, bannerImage, previewImage, eventCategory, eventPrice);

        try {
            // Create the event in the database
            eventModel.createEvent(event);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // -----------------------------------------------------------------------------------
    //---------------------------------------BUTTONS--------------------------------------
    //------------------------------------------------------------------------------------

    public void clickSubmit(ActionEvent actionEvent) throws IOException {
        if (validateInput()) {
            createNewEvent();
            switchToMainView(actionEvent);
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

    // -----------------------------------------------------------------------------------
    //---------------------------------------MISC-----------------------------------------
    //------------------------------------------------------------------------------------

    // Validates input, so data only gets send when this is true.
    private boolean validateInput() {
        if (titleField.getText().isEmpty() || locationField.getText().isEmpty() ||
                datePicker.getValue() == null || notesField.getText().isEmpty() || ticketsAmountField.getText().isEmpty()){
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

}
