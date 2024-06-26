package GUI.Widgets;

import GUI.Controller.EventManagement.eventCreatorController;
import GUI.Controller.EventManagement.eventManagerController;
import GUI.Controller.adminViewController;
import GUI.Model.EventModel;
import GUI.Model.UserModel;
import GUI.Model.ViewModel;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

import java.io.IOException;

/**
 * This is the top right panel of both the banner on the main page
 * and the top bar on all the other pages.
 */
public class TopPanel extends HBox {
    private ViewModel viewModel = ViewModel.getInstance();
    private UserModel userModel = UserModel.getInstance();
    private EventModel eventModel = EventModel.getInstance();
    public TopPanel() {
        this.setId("topPanel");
        this.getStylesheets().add("/GUI/Styling/BannerAndTopBar.css");
        this.setWidth(1200);
        this.setAlignment(Pos.CENTER_RIGHT);

        HBox rightBox = initTopPanel();
        HBox leftBox = new HBox();
        leftBox.setPrefWidth(600);

        switch(userModel.getUser().getUserType()) {
            case ADMIN:
                initAdmin(rightBox);
                break;
            case EVENTCOORDINATOR:
                initEventManager(rightBox);
                break;
        }

        if(!viewModel.isMainWindow())
            initBackButton(leftBox);

        this.getChildren().addAll(leftBox, rightBox);
    }

    public HBox initTopPanel() {
        HBox box = new HBox(5);
        box.setPrefWidth(600);
        box.setAlignment(Pos.CENTER_RIGHT);

        //Setup images for buttons
        Image user = new Image(getClass().getResourceAsStream("/icons/userWhite.png"));
        ImageView userView = new ImageView(user);
        Button btnUser = new Button();

        btnUser.setGraphic(userView);
        btnUser.setOnAction(this::clickUser);
        btnUser.setId("btnUser");

        userView.setPreserveRatio(true);
        userView.setFitWidth(30);

        //Setup Search Bar
        HBox searchBarBox = new HBox();
        searchBarBox.setId("searchBarBox");
        searchBarBox.setPrefWidth(200);

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search");
        searchBar.setId("txtSearch");
        searchBarBox.setAlignment(Pos.CENTER_LEFT);

        Image search = new Image(getClass().getResourceAsStream("/icons/search.png"));
        ImageView imgSearch = new ImageView(search);
        imgSearch.setId("imgSearch");

        imgSearch.setPreserveRatio(true);
        imgSearch.setFitWidth(20);
        searchBarBox.getChildren().addAll(imgSearch, searchBar);

        box.getChildren().addAll(searchBarBox, btnUser);
        return box;
    }

    private void initEventManager(HBox box) {
        Button button = new Button("Event Manager");
        button.setId("userButton");
        button.setOnAction(this::clickEventManager);
        box.getChildren().addFirst(button);
    }


    private void initAdmin(HBox box) {
        Button button = new Button("Admin Panel");
        button.setId("userButton");
        button.setOnAction(this::clickAdminView);
        box.getChildren().addFirst(button);
    }

    public void clickUser(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/UserView/userView.fxml"));
            Parent userView = loader.load();
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/GUI/View/UserView/sideBarView.fxml"));
            Parent sideBarView = loader1.load();
            viewModel.unsetMainWindow();

            viewModel.getBorderPane().setCenter(userView);
            viewModel.getBorderPane().setLeft(sideBarView);

            viewModel.setTopBar();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickAdminView(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/adminView.fxml"));
            Parent userView = loader.load();
            viewModel.unsetMainWindow();

            viewModel.getBorderPane().setCenter(userView);
            viewModel.setTopBar();
            viewModel.disableSidePanel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickEventManager(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/EventManagement/eventManagerView.fxml"));
            Parent userView = loader.load();
            viewModel.unsetMainWindow();

            viewModel.getBorderPane().setCenter(userView);
            viewModel.setTopBar();
            viewModel.disableSidePanel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initBackButton(HBox box) {
        box.setAlignment(Pos.CENTER_LEFT);
        Button button = new Button("Back");
        button.setId("btnBack");
        button.setPrefWidth(70);
        button.setOnAction(this::clickBack);
        box.setPadding(new Insets(0, 0, 0, 10));
        box.getChildren().addFirst(button);
    }

    private void clickBack(ActionEvent actionEvent) {
        try {
            eventModel.setEvent(null);
            viewModel.setMainWindow();

            viewModel.disableSidePanel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
