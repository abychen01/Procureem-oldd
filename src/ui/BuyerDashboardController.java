package ui;

import database.DatabaseAccess;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import models.*;
import session.Session;
import ui.utilities.MessageDialog;
import ui.utilities.PageDimensions;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BuyerDashboardController implements Initializable {



    @FXML private HBox icons;
    @FXML private Node root;
    @FXML private TableView<Quote> rfqData;
    @FXML public TableColumn<Quote, Integer> idColumn;
    @FXML public TableColumn<Quote, String> nameColumn;
    @FXML public TableColumn<Quote, String> specificationsColumn;
    @FXML public TableColumn<Quote, String> deliveryDueColumn;

    //Suppliers page
    @FXML private TableView<Supplier> supplierTableView;
    @FXML public TableColumn<Supplier, String> tableColumnSupplierName;
    @FXML public TableColumn<Supplier, String> tableColumnSupplierUsername;
    @FXML public TableColumn<Supplier, Integer> tableColumnSupplierQuotation;
    @FXML public TableColumn<Supplier, Integer> tableColumnSupplierOrders;


    private DatabaseAccess da;

    //Dashboard controls
    @FXML private Hyperlink username;

    //Controls For Add rfq Page
    @FXML private TextField name;
    @FXML private TextField specification;
    @FXML private TextField quantity;
    @FXML private TextField maxUnitPrice;
    @FXML private TextField deliveryDue;
    @FXML private TextField range;
    @FXML private TextField supportPeriod;
    @FXML private TextArea additionalRequirements;

    //View Quotations
    @FXML private TableView<SupplierQuote> supplierQuoteTableView;
    @FXML private TableColumn<SupplierQuote, Double> supplierPriceColumn;
    @FXML public TableColumn<SupplierQuote, Double> maxUnitPriceColumn;

    //Post Orders
    @FXML public TableView<PostOrder> postOrdersTableView;
    public TableColumn<PostOrder, Integer> postOrderIdColumn;
    public TableColumn<PostOrder, String> postOrderItemColumn;
    public TableColumn<PostOrder, String> postOrderSpecificationsColumn;
    public TableColumn<PostOrder, Integer> postOrderQuantityColumn;
    public TableColumn<PostOrder, String> postOrderDeliveryDueColumn;
    public TableColumn<PostOrder, String> postOrderSupportPeriodColumn;
    public TableColumn<PostOrder, Double> postOrderUnitPriceColumn;


    private Quote createFromForm() {
        return  new Quote(
                0, name.getText(),
                specification.getText(), Integer.parseInt(quantity.getText()),
                Double.parseDouble(maxUnitPrice.getText()),
                deliveryDue.getText(),
                range.getText(),
                supportPeriod.getText(),
                additionalRequirements.getText(),
                Session.user.getId(),
                0
        );
    }

    @FXML public void home() {
        navigate("Buyer Dashboard", "buyer-dashboard.fxml");
    }

    @FXML public void createRFQ() {
        Quote quote = createFromForm();
        if (da.createRFQ(quote) ) {
            MessageDialog.ShowDialog("Success", "RFQ created successfully!", "success");
        }else {
            MessageDialog.ShowDialog("Error", "Failed to create RFQ!", "error");
        }
    }

    @FXML public  void createRFQPage() {
        navigate("Create RFQ", "create-rfq.fxml");
    }

    @FXML public void editRFQPage() {
       try {
           Quote selected = rfqData.getSelectionModel().getSelectedItem();
           if (selected != null) {
               Session.navData = selected;
               navigate("Edit RFQ", "edit-rfq.fxml");
           }else {
               MessageDialog.ShowDialog("Error", "Please select an item to edit!", "error");
           }
       }catch (Exception ex) {
            ex.printStackTrace();
       }

    }

    @FXML public void viewPostOrdersPage() {

    }

    @FXML public void suppliersPage() {
        navigate("All Suppliers", "suppliers.fxml");
    }

    @FXML public void quotationsPage() {
        try {
            Quote selected = rfqData.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Session.navData = selected;
                navigate("View Quotations", "view-quotations.fxml");
            }else {
                MessageDialog.ShowDialog("Error", "Please select an item to view it's submitted quotations!", "error");
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML public void postOrdersPage() {
        navigate("Post Orders", "buyer-post-orders.fxml");
    }

    @FXML public void confirmQuotation() {
        try {
            SupplierQuote selected = supplierQuoteTableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                da.confirmSupplierQuotation(selected);
                MessageDialog.ShowDialog("Success", "Quotation Confirmed!", "success");
                navigate("Post Orders", "buyer-post-orders.fxml");
            }else {
                MessageDialog.ShowDialog("Error", "Please select quotation to confirm!", "error");
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML public void logout() {
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

    @FXML public void updateRFQ() {
        Quote original = (Quote) Session.navData;
        Quote edited = createFromForm();
        edited.setId(original.getId());
        if (da.updateRFQ(edited)){
            MessageDialog.ShowDialog("Success", "RFQ updated sucessfully", "success");
            home();
        }else {
            MessageDialog.ShowDialog("Error", "Failed to update RFQ", "error");
        }
    }

    @FXML public void showNotifications() {
        try {
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);

            NotificationController controller = new NotificationController("B");
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
        username.setText(Session.user.getName() + "(buyer)");
        String page = Session.pageName;
        icons.getChildren().addAll( GlyphsDude.createIcon(FontAwesomeIcon.BELL, "15px"),
                new Label("("+da.getNotifications(Session.user.getId(), "B").size()+")")
        );
        switch (page) {
            case "edit-rfq":
                Quote quote = (Quote) Session.navData;
                name.setText(quote.getName());
                specification.setText(quote.getSpecification());
                quantity.setText(quote.getQuantity() + "");
                maxUnitPrice.setText(quote.getMaxUnitPrice() + "");
                deliveryDue.setText(quote.getDeliveryDue());
                range.setText(quote.getRange());
                supportPeriod.setText(quote.getSupportPeriod());
                additionalRequirements.setText(quote.getAdditionalRequirements());
                break;

            case "buyer-dashboard":

                idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
                specificationsColumn.setCellValueFactory(new PropertyValueFactory<>("Specification"));
                deliveryDueColumn.setCellValueFactory(new PropertyValueFactory<>("DeliveryDue"));

                ObservableList<Quote> quotesObservableList = FXCollections.observableArrayList(
                        da.getAllBuyersQuotes(Session.user.getId())
                );
                rfqData.setItems(quotesObservableList);
                break;

            case "suppliers":
                System.out.println("In suppliers page --");
                ObservableList<Supplier> supplierObservableList = FXCollections.observableList(
                        da.allSuppliers()
                );
                supplierTableView.setItems(supplierObservableList);
                tableColumnSupplierName.setCellValueFactory(new PropertyValueFactory<>("Name"));
                tableColumnSupplierUsername.setCellValueFactory(new PropertyValueFactory<>("Username"));
                tableColumnSupplierQuotation.setCellValueFactory(new PropertyValueFactory<>("TotalQuotations"));
                tableColumnSupplierOrders.setCellValueFactory(new PropertyValueFactory<>("TotalPostOrders"));

            case "view-quotations":
                idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
                maxUnitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("MaxUnitPrice"));
                supplierPriceColumn.setCellValueFactory(new PropertyValueFactory<>("SupplierPrice"));
                Quote quote1 = (Quote) Session.navData;
                ObservableList<SupplierQuote> supplierQuoteObservableList = FXCollections.observableArrayList(
                        da.getOrderQuotes( quote1.getId() )
                );
                supplierQuoteTableView.setItems(supplierQuoteObservableList);
                break;
            case "buyer-post-orders":
                //Post Orders
                postOrderIdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
                postOrderItemColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
                postOrderSpecificationsColumn.setCellValueFactory(new PropertyValueFactory<>("Specification"));
                postOrderQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
                postOrderDeliveryDueColumn.setCellValueFactory(new PropertyValueFactory<>("DeliveryDue"));
                postOrderSupportPeriodColumn.setCellValueFactory(new PropertyValueFactory<>("SupportPeriod"));
                postOrderUnitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
                ObservableList<PostOrder> postOrderObservableList = FXCollections.observableArrayList(
                        da.getUserPostOrders(Session.user.getId(), true)
                );
                postOrdersTableView.setItems(postOrderObservableList);
                break;

        }
    }

    @FXML public void loadBlank() {
        navigate("Blank Page", "buyer-blank-page.fxml");
    }
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
}
