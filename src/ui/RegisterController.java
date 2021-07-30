package ui;

import database.DatabaseAccess;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ui.utilities.MessageDialog;
import ui.utilities.PageDimensions;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML private AnchorPane root;
    @FXML private Hyperlink exit;

    @FXML private TextField name;
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private ComboBox<String> role;
    private DatabaseAccess da;

    @FXML public void loginPage() {
        try {
            new PageLoader().LoadPage("Login", "login.fxml", PageDimensions.LoginPageWidth, PageDimensions.LoginPageHeight, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML public void register() {
        try {
            if (!da.registerUser(name.getText(), username.getText(), password.getText(), role.getSelectionModel().getSelectedItem())) {
                MessageDialog.ShowDialog("Registration Failed", "Failed to register new user", "error");
            }else {
                loginPage();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        da = new DatabaseAccess();
    }
}
