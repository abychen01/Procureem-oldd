package ui;

import database.DatabaseAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import models.Notification;
import session.Session;

import java.net.URL;
import java.util.ResourceBundle;

public class NotificationController implements Initializable {
    @FXML public ListView<Notification> notificationListView;
    private DatabaseAccess da;
    private String role;
    public NotificationController(String role) {
        this.role = role;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        da = new DatabaseAccess();

        notificationListView.setCellFactory(new NotificationCellFactory());

        ObservableList<Notification> notificationObservableList = FXCollections.observableArrayList(
                da.getNotifications(Session.user.getId(), role)
        );
//        System.out.println("In init");
        notificationListView.setItems(notificationObservableList);
    }


}
