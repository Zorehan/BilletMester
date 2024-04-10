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
import java.time.LocalDateTime;

public class eventCreatorController {
    @FXML
    private ComboBox categoryComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField ticketsPriceField, ticketsFoodAmountField, ticketsDrinkAmountField, ticketsDiscountsAmountField, ticketDrinkField, ticketDiscountField, ticketsFoodField, ticketsAmountField;
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
        initInput();
        initCategories();
    }

    public void createNewEvent() {

        User loggedInUser = userModel.getUser();

        // Retrieve input values from the GUI
        String title = titleField.getText();
        String hostName = loggedInUser.getFullName(); // Tager det fulde navn af user - dette er for eventManager
        String location = locationField.getText();
        LocalDateTime eventStart = LocalDateTime.now();
        LocalDateTime eventEnd = LocalDateTime.now();
        String notes = notesField.getText();
        String bannerImage = bannerImageField.getText();
        String previewImage = previewImageField.getText();
        String description = descriptionField.getText();
        String eventCategory = categoryComboBox.getValue().toString();
        Integer eventPrice = Integer.valueOf(ticketsPriceField.getText());
        Integer ticketsField = Integer.valueOf(ticketsAmountField.getText());

        // Create the event - -1 is a placeholder id, the actual id gets automatically set in the DB
        Events event = new Events(-1, title, hostName, eventStart, eventEnd, location, description, notes, bannerImage, previewImage, eventCategory, eventPrice);

        try {
            // Create the event in the database
            eventModel.createEvent(event);

            // Create tickets for the event
            for (int i = 0; i < ticketsField; i++) {
                Tickets ticket = new Tickets(-1, title, null, null, null);
                ticketModel.createTicket(ticket);
            }

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

    // Restricts label input to number 0-9 and backspace
    private void restrictToNumericInput(TextField mainField, TextField amountField) {
        amountField.disableProperty().bind(mainField.textProperty().isEmpty());

        mainField.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9\b]")) {
                showAlert("Only numeric inputs are allowed!");
                event.consume();
            }
        });

        amountField.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9\b]")) {
                showAlert("Only numeric inputs are allowed!");
                event.consume();
            }
        });
    }

    private void switchToMainView(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/MainPage/mainView.fxml"));
        Parent mainViewParent = loader.load();
        viewModel.getBorderPane().setCenter(mainViewParent);
    }

    private void initInput() {
        restrictToNumericInput(ticketsAmountField, ticketsPriceField);
        restrictToNumericInput(ticketsFoodField, ticketsFoodAmountField);
        restrictToNumericInput(ticketDrinkField, ticketsDrinkAmountField);
        restrictToNumericInput(ticketDiscountField, ticketsDiscountsAmountField);
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
