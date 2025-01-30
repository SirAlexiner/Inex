package no.ntnu.idatg1002.grp8.inex.gui.stages;

import io.github.siralexiner.fxmanager.FxManager;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.experimental.UtilityClass;
import no.ntnu.idatg1002.grp8.inex.customers.Customer;
import no.ntnu.idatg1002.grp8.inex.customers.dao.CustomerRegistryDao;
import no.ntnu.idatg1002.grp8.inex.gui.controllers.InvoiceController;
import no.ntnu.idatg1002.grp8.inex.gui.controllers.util.ControllerUtility;
import no.ntnu.idatg1002.grp8.inex.utilities.AddressUtility;

/**
 * The CustomerSuggestionStage class opens a modal window displaying a table of customer suggestions
 * and allows the user to select a customer to populate fields in an invoice form.
 */
@UtilityClass
public class CustomerSuggestionStage {

  /**
   * The function opens a modal window displaying a table of customer suggestions
   * and allows the user to select a customer to populate fields in an invoice form.
   */
  public static void open() {
    Stage stage = new Stage();
    FxManager.setupCaptionBar(stage);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(MainPageStage.getPrimaryStage());

    TableView<Customer> suggestionsTable = createTableView();
    BorderPane borderPane = setupBorderPane(suggestionsTable);
    setMouseClickEvent(suggestionsTable, stage);

    Scene scene = new Scene(borderPane, 480, 270);
    stage.setTitle("Inex - Customer Suggestions");
    Image logo =
        new Image(String.valueOf(InvoiceStage.class.getResource("/images/logo.png")));
    stage.getIcons().addAll(logo);
    stage.setScene(scene);
    stage.show();
  }

  private static TableView<Customer> createTableView() {
    TableView<Customer> suggestionsTable = new TableView<>();
    List<TableColumn<Customer, String>> columns = createTableColumns();
    suggestionsTable.getColumns().setAll(columns);

    ObservableList<Customer> suggestions =
        FXCollections.observableArrayList(CustomerRegistryDao.loadCustomerRegister());
    suggestionsTable.setItems(suggestions);
    return suggestionsTable;
  }

  private static List<TableColumn<Customer, String>> createTableColumns() {
    TableColumn<Customer, String> streetNameCol = new TableColumn<>("Customer ID");
    streetNameCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

    TableColumn<Customer, String> houseNumberCol = new TableColumn<>("Customer Name");
    houseNumberCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));

    TableColumn<Customer, String> letterCol = new TableColumn<>("Customer Email");
    letterCol.setCellValueFactory(new PropertyValueFactory<>("email"));

    return Arrays.asList(streetNameCol, houseNumberCol, letterCol);
  }

  private static BorderPane setupBorderPane(TableView<Customer> suggestionsTable) {
    BorderPane borderPane = new BorderPane();
    VBox suggestionsvbox = new VBox(suggestionsTable);
    borderPane.setCenter(suggestionsvbox);
    return borderPane;
  }

  private static void setMouseClickEvent(TableView<Customer> suggestionsTable, Stage stage) {
    suggestionsTable.setOnMouseClicked(event -> {
      if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
        Customer customer = suggestionsTable.getSelectionModel().getSelectedItem();
        if (customer != null) {
          handleTableDoubleClick(customer, stage);
        }
      }
    });
  }

  private static void handleTableDoubleClick(Customer customer, Stage stage) {
    String customerId = customer.getCustomerId() != null ? customer.getCustomerId() : "";
    String customerName = customer.getFullName() != null ? customer.getFullName() : "";

    InvoiceController controller = InvoiceStage.getInvoiceController();
    controller.getCustomerId().setText(customerId);
    controller.getCustomerName().setText(customerName);

    Map<String, String> address = AddressUtility.getAddress(customer);

    ControllerUtility.setAddress(address, controller);
    stage.close();
  }
}
