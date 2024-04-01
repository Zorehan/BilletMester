package GUI.Controller;

import BE.Events;
import BE.Users.User;
import BE.Users.UserEnum;
import GUI.Model.EventModel;
import GUI.Model.UserModel;
import GUI.Model.ViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.Optional;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class adminViewController implements Initializable {

    @FXML
    private TextField userSearchBar;
    @FXML
    private TextField eventSearchBar;
    @FXML
    private ListView<User> userListView;

    @FXML
    private ListView<Events> eventListView;

    UserModel userModel = UserModel.getInstance();
    EventModel eventModel = EventModel.getInstance();
    ViewModel viewModel = ViewModel.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<User> observableUsers = userModel.getObservableUsers();
        List<Events> observableEvents = eventModel.getObservableEvents();

        userListView.getItems().addAll(observableUsers);
        eventListView.getItems().addAll(observableEvents);

        userListView.setCellFactory(param -> new ListCell<User>() {
            private final ComboBox<UserEnum> comboBox = new ComboBox<>();

            {
                comboBox.setItems(FXCollections.observableArrayList(UserEnum.USER, UserEnum.EVENTCOORDINATOR, UserEnum.ADMIN));
                comboBox.getSelectionModel().selectFirst();
            }

            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);

                if (empty || user == null) {
                    setGraphic(null);
                    setText(null); // Clear the cell's text if the user is empty
                } else {
                    // Create a horizontal box to hold the username, ComboBox, and delete button
                    HBox hbox = new HBox(5); // Set spacing between elements
                    hbox.setAlignment(Pos.CENTER); // Align elements in the center

                    // Label for displaying the user's name
                    Label nameLabel = new Label(user.getFirstName() + " " + user.getLastName());
                    nameLabel.setPrefWidth(80); // Set preferred width for the name label
                    nameLabel.setAlignment(Pos.CENTER); // Align text in the center vertically
                    nameLabel.setMaxHeight(Double.MAX_VALUE); // Set maximum height to ensure vertical centering

                    // ComboBox for user role selection
                    ComboBox<UserEnum> comboBox = new ComboBox<>();
                    comboBox.setItems(FXCollections.observableArrayList(UserEnum.USER, UserEnum.EVENTCOORDINATOR, UserEnum.ADMIN));
                    comboBox.getSelectionModel().select(UserEnum.valueOf(user.getUserType().name())); // Set initial selection based on user's role
                    comboBox.setPrefWidth(100); // Set preferred width for the ComboBox
                    comboBox.setMaxHeight(Double.MAX_VALUE); // Set maximum height to ensure vertical centering
                    comboBox.setOnAction(event -> {
                        // Update the user's role based on the selected value in the ComboBox
                        UserEnum selectedRole = comboBox.getSelectionModel().getSelectedItem();
                        // Update the user's role in the model
                        userModel.updateUser(user, selectedRole);
                    });

                    // Button for deleting the user
                    Button deleteButton = new Button("Delete");
                    deleteButton.setOnAction(event -> {
                        // Show confirmation dialog before deleting the user
                        if (showConfirmationDialog("Are you sure you want to delete this user?")) {
                            // Implement your logic to delete the user here
                            userModel.deleteUser(user);
                            // Remove the user from the ListView
                            getListView().getItems().remove(user);
                        }
                    });
                    deleteButton.setMaxHeight(Double.MAX_VALUE); // Set maximum height to ensure vertical centering

                    hbox.getChildren().addAll(nameLabel, comboBox, deleteButton);

                    setGraphic(hbox);
                    setText(null);
                }
            }

        });

        eventListView.setCellFactory(param -> new ListCell<Events>() {
            @Override
            protected void updateItem(Events eventItem, boolean empty) {
                super.updateItem(eventItem, empty);

                if (empty || eventItem == null) {
                    setGraphic(null);
                    setText(null); // Clear the cell's text if the event is empty
                } else {
                    // Create a horizontal box to hold the event name and delete button
                    HBox hbox = new HBox(5); // Set spacing between elements
                    hbox.setAlignment(Pos.CENTER_LEFT); // Align elements to the left

                    // Label for displaying the event name
                    Label eventNameLabel = new Label(eventItem.getEventName());
                    eventNameLabel.setPrefWidth(150); // Set preferred width for the event name label
                    eventNameLabel.setAlignment(Pos.CENTER_LEFT); // Align text to the left vertically
                    eventNameLabel.setMaxHeight(Double.MAX_VALUE); // Set maximum height to ensure vertical centering

                    // Button for deleting the event
                    Button deleteButton = new Button("Delete");
                    deleteButton.setOnAction(event -> {
                        // Show confirmation dialog before deleting the event
                        if (showConfirmationDialog("Are you sure you want to delete this event?")) {
                            // Implement your logic to delete the event here
                            eventModel.deleteEvent(eventItem);
                            // Remove the event from the ListView
                            getListView().getItems().remove(eventItem);
                        }
                    });
                    deleteButton.setMaxHeight(Double.MAX_VALUE); // Set maximum height to ensure vertical centering

                    hbox.getChildren().addAll(eventNameLabel, deleteButton);

                    setGraphic(hbox);
                    setText(null);
                }
            }
        });

        // For Event Search Bar
        eventSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            // Filter the event list based on the search input
            ObservableList<Events> filteredEvents = FXCollections.observableArrayList();
            for (Events event : eventModel.getObservableEvents()) {
                if (event.getEventName().toLowerCase().contains(newValue.toLowerCase())) {
                    filteredEvents.add(event);
                }
            }
            // Update the event list view with the filtered list
            eventListView.setItems(filteredEvents);
        });

        // For User Search Bar
        userSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            // Filter the user list based on the search input
            ObservableList<User> filteredUsers = FXCollections.observableArrayList();
            for (User user : userModel.getObservableUsers()) {
                if (user.getFirstName().toLowerCase().contains(newValue.toLowerCase()) ||
                        user.getLastName().toLowerCase().contains(newValue.toLowerCase())) {
                    filteredUsers.add(user);
                }
            }
            // Update the user list view with the filtered list
            userListView.setItems(filteredUsers);
        });
    }

    public void clickBack(ActionEvent actionEvent) throws IOException {
        switchToMainView(actionEvent);

    }

    private void switchToMainView(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/MainPage/mainView.fxml"));
        Parent mainViewParent = loader.load();

        viewModel.getBorderPane().setCenter(mainViewParent);
    }

    private boolean showConfirmationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
