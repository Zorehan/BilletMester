package GUI.Controller;

import BE.Events;
import BE.SpecialTickets;
import BE.Tickets;
import GUI.Model.EventModel;
import GUI.Model.TicketModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class eventCreatorController {
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField eventHostField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField previewImageField;
    @FXML
    private TextField bannerImageField;
    @FXML
    private Button choosePreviewBtn;
    @FXML
    private Button chooseBannerBtn;
    @FXML
    private TextField ticketsAmountField;
    @FXML
    private TextField ticketsFoodAmountField;
    @FXML
    private TextField ticketsDrinkAmountField;
    @FXML
    private TextField ticketsDiscountsAmountField;
    @FXML
    private TextField ticketDrinkField;

    @FXML
    private TextField ticketDiscountField;

    @FXML
    private TextField ticketsFoodField;

    @FXML
    private TextField ticketsField;

    @FXML
    private Pane rootPane;

    @FXML
    private TextField titleField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField notesField;

    @FXML
    private Button submitButton;

    @FXML
    private Button cancelButton;

    private EventModel eventModel;
    private Events events;
    private Tickets tickets;
    private TicketModel ticketModel;
    private SpecialTickets specialTickets;

    public void initialize() {
        eventModel = new EventModel();
        ticketModel = new TicketModel();

        restrictToNumericInput(ticketsField, ticketsAmountField);
        restrictToNumericInput(ticketsFoodField, ticketsFoodAmountField);
        restrictToNumericInput(ticketDrinkField, ticketsDrinkAmountField);
        restrictToNumericInput(ticketDiscountField, ticketsDiscountsAmountField);
    }

    public void createNewEvent() {
        // Retrieve input values from the GUI
        String title = titleField.getText();
        String hostName = eventHostField.getText();
        String location = locationField.getText();
        LocalDateTime eventStart = LocalDateTime.now();
        LocalDateTime eventEnd = LocalDateTime.now();
        String notes = notesField.getText();
        String bannerImage = bannerImageField.getText();
        String previewImage = previewImageField.getText();
        String description = descriptionField.getText();
        String foodTicketsText = ticketsFoodField.getText();
        Integer foodTickets = foodTicketsText.isEmpty() ? 0 : Integer.valueOf(foodTicketsText);

        String drinkTicketsText = ticketDrinkField.getText();
        Integer drinkTickets = drinkTicketsText.isEmpty() ? 0 : Integer.valueOf(drinkTicketsText);

        String discountTicketsText = ticketDiscountField.getText();
        Integer discountTickets = discountTicketsText.isEmpty() ? 0 : Integer.valueOf(discountTicketsText);


        // Create the event - -1 is a placeholder id, the actual id gets automatically set in the DB
        Events event = new Events(-1, title, hostName, eventStart, eventEnd, location, description, notes, bannerImage, previewImage);

        // Create the ticket for the events
        Tickets ticket = new Tickets(-1, title, null, null, null);

        try {
            // Create the event in the database
            eventModel.createEvent(event);
            ticketModel.createTicket(ticket);
            Platform.exit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -----------------------------------------------------------------------------------
    //---------------------------------------BUTTONS--------------------------------------
    //------------------------------------------------------------------------------------
    public void clickCancel(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void clickSubmit(ActionEvent actionEvent) {
        if (validateInput()) {
            createNewEvent();
        }
    }

    public void clickPreviewBtn(ActionEvent actionEvent) {
        previewImageField.setText(btnChoose());
    }

    public void clickBannerBtn(javafx.event.ActionEvent actionEvent) {
        bannerImageField.setText(btnChoose());
    }

    public String btnChoose() {   // Method to choose valid files
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show the file chooser dialog
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
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
                datePicker.getValue() == null || notesField.getText().isEmpty() || ticketsField.getText().isEmpty()){
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

}
