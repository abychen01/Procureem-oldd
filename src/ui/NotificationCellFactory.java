package ui;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import models.Notification;

public class NotificationCellFactory implements Callback<ListView<Notification>, ListCell<Notification>> {

    @Override
    public ListCell<Notification> call(ListView<Notification> param) {
        return new NotificationListCell();
    }
}
