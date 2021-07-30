package ui;

import database.DatabaseAccess;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.PostOrder;
import models.Quote;
import models.SupplierQuote;
import models.User;
import session.Session;
import ui.utilities.InputCancelledException;
import ui.utilities.MessageDialog;
import ui.utilities.PageDimensions;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SellerDashboardController implements Initializable {
    //Commons
    @FXML private HBox icons;
    @FXML private Node root;
    private DatabaseAccess da;
    private User user;
    //Main dashboard
    @FXML public TableView<Quote> rfqData;
    @FXML public TableColumn<Quote, Integer> idColumn;
    @FXML public TableColumn<Quote, String> nameColumn;
    @FXML public TableColumn<Quote, String>  specificationsColumn;
    @FXML public TableColumn<Quote, String>  deliveryDueColumn;
    @FXML public TableColumn<Quote, Double> priceColumn;
    @FXML public Hyperlink username;

    //Post Orders
    @FXML public TableView<PostOrder> postOrdersTableView;
    public TableColumn<PostOrder, Integer> postOrderIdColumn;
    public TableColumn<PostOrder, String> postOrderItemColumn;
    public TableColumn<PostOrder, String> postOrderSpecificationsColumn;
    public TableColumn<PostOrder, Integer> postOrderQuantityColumn;
    public TableColumn<PostOrder, String> postOrderDeliveryDueColumn;
    public TableColumn<PostOrder, String> postOrderSupportPeriodColumn;
    public TableColumn<PostOrder, Double> postOrderUnitPriceColumn;

    //submitted-quotes

    private void navigate(String title, String path) {
        try {
            new PageLoader().LoadPage(
                    title,
                    path,
                    PageDimensions.DASHBOARD_WIDTH,
                    PageDimensions.DASHBOARD_HEIGHT,
                    root
            );
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML public void showNotifications() {
        try {
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);

            NotificationController controller = new NotificationController("S");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("notifications.fxml"));
            loader.setController(controller);
            Parent root = loader.load();
            dialogStage.setTitle("Notifications");
            dialogStage.setScene(new Scene(root));
            dialogStage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        da = new DatabaseAccess();
        username.setText(Session.user.getName() + "(seller)");
        String page = Session.pageName;
        icons.getChildren().addAll( GlyphsDude.createIcon(FontAwesomeIcon.BELL, "15px"),
                new Label("("+da.getNotifications(Session.user.getId(), "S").size()+")")
        );
        switch (page){
            case "seller-dashboard":

                idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
                specificationsColumn.setCellValueFactory(new PropertyValueFactory<>("Specification"));
                priceColumn.setCellValueFactory(new PropertyValueFactory<>("MaxUnitPrice"));
                deliveryDueColumn.setCellValueFactory(new PropertyValueFactory<>("DeliveryDue"));

                ObservableList<Quote> quotesObservableList = FXCollections.observableArrayList(
                        da.getAllQuotes()
                );
                rfqData.setItems(quotesObservableList);
                break;

            case "submitted-quotes":
                idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
                specificationsColumn.setCellValueFactory(new PropertyValueFactory<>("Specification"));
                priceColumn.setCellValueFactory(new PropertyValueFactory<>("SupplierPrice"));
                deliveryDueColumn.setCellValueFactory(new PropertyValueFactory<>("DeliveryDue"));

                ObservableList<Quote> supplierQuoteObservableList = FXCollections.observableArrayList(
                        da.getSupplierQuotations(Session.user.getId())
                );
                rfqData.setItems(supplierQuoteObservableList);
                break;

            case "seller-post-orders":
                //Post Orders
                postOrderIdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
                postOrderItemColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
                postOrderSpecificationsColumn.setCellValueFactory(new PropertyValueFactory<>("Specification"));
                postOrderQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
                postOrderDeliveryDueColumn.setCellValueFactory(new PropertyValueFactory<>("DeliveryDue"));
                postOrderSupportPeriodColumn.setCellValueFactory(new PropertyValueFactory<>("SupportPeriod"));
                postOrderUnitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
                ObservableList<PostOrder> postOrderObservableList = FXCollections.observableArrayList(
                        da.getUserPostOrders(Session.user.getId(), false)
                );
                postOrdersTableView.setItems(postOrderObservableList);
                break;
        }
    }

    public void allQuotationsPage() {
        navigate("Seller Dashboard", "seller-dashboard.fxml");
    }

    public void submittedQuotationsPage(ActionEvent actionEvent) {
        navigate("Submitted Quotes", "submitted-quotes.fxml");
    }

    public void postOrdersPage(ActionEvent actionEvent) {
        navigate("Post Orders", "seller-post-orders.fxml");
    }

    public void inventoryManagementPage(ActionEvent actionEvent) {
        loadBlank();
    }

    public void historyPage(ActionEvent actionEvent) {
        loadBlank();
    }

    public void logout(ActionEvent actionEvent) {
        Session.clearAll();
        try {
            new PageLoader().LoadPage(
                    "Login",
                    "login.fxml",
                    PageDimensions.LoginPageWidth,
                    PageDimensions.LoginPageHeight,
                    root
            );
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void submitQuotePage(ActionEvent actionEvent) {
        try {
            Quote selected = rfqData.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String data = MessageDialog.showInputDialog(selected.getMaxUnitPrice()+"", "Your unit price :");
                if (da.submitQuote(selected, Session.user.getId(), Double.parseDouble(data) )) {
                    MessageDialog.ShowDialog("Success", "Quote submitted!", "success");
                }else {
                    MessageDialog.ShowDialog("Error", "Error submitting your quote!", "error");
                }
            }else {
                MessageDialog.ShowDialog("Error", "Please select an order first!", "error");
            }
        }catch (InputCancelledException ex) {
//            ex.printStackTrace();
        }catch (Exception e) {
            MessageDialog.ShowDialog("Error", "Error submitting your quote!", "error");
            e.printStackTrace();
        }
    }

    @FXML  public void loadBlank() {
        navigate("Blank-Page", "seller-blank-page.fxml");
    }

    @FXML public void home() {
        navigate("Seller dashboard", "seller-dashboard.fxml");
    }
}
