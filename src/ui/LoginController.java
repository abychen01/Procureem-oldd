package ui;

import database.DatabaseAccess;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.User;
import session.Session;
import ui.utilities.MessageDialog;
import ui.utilities.PageDimensions;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML private AnchorPane root;
    @FXML private Button registerButton;
    @FXML private  Button loginButton;
    @FXML private TextField username;
    @FXML private TextField password;
    private DatabaseAccess da;

    private void navigate(String title, String path) throws  IOException{
        new PageLoader().LoadPage(title,path, PageDimensions.DASHBOARD_WIDTH, PageDimensions.DASHBOARD_HEIGHT,root);

    }


    @FXML
    public void login() {
        try {
            User user = da.userLogin(username.getText(), password.getText());
            if (user == null) {
                MessageDialog.ShowDialog("Login failed", "Username or password incorrect!", "error");
            }else {
                System.out.println("User  :" +user.getId());
                Session.user = user;
                if (user.getRole().equals("Seller")) {
                    navigate("Seller Dashboard", "seller-dashboard.fxml");
                }else {
                    navigate("Buyers Dashboard", "buyer-dashboard.fxml");
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML public  void registerPage() {

        try {
            new PageLoader().LoadPage(
                    "Register Page",
                    "register.fxml",
                    PageDimensions.RegisterPageWidth,
                    PageDimensions.RegisterPageHeight,
                    root
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        da = new DatabaseAccess();
    }
}
