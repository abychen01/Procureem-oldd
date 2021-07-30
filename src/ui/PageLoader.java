package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import session.Session;

import java.io.IOException;

public  class PageLoader {
    public   void LoadPage(String title, String fxmlPath, int width, int height, Node node) throws IOException {
        Session.pageName = fxmlPath.split("\\.")[0].toLowerCase().trim();
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root, width, height));
        stage.show();
        Stage old = (Stage) node.getScene().getWindow();
        old.close();
    }
}
