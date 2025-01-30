package no.ntnu.idatg1002.grp8.inex.gui.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import no.ntnu.idatg1002.grp8.inex.customers.Customer;
import no.ntnu.idatg1002.grp8.inex.customers.dao.CustomerRegistryDao;
import no.ntnu.idatg1002.grp8.inex.gui.controllers.util.AddressField;
import no.ntnu.idatg1002.grp8.inex.gui.controllers.util.ControllerUtility;
import no.ntnu.idatg1002.grp8.inex.utilities.AddressUtility;
import no.ntnu.idatg1002.grp8.inex.utilities.InternetAccessChecker;

/**
 * The CustomerController class is a JavaFX controller that handles user input and updates a list of
 * customers displayed in a ListView.
 */
public class CustomerController implements AddressField {
  @FXML
  public TextField customerId;
  @FXML
  public TextField customerName;
  @FXML
  public TextField customerEmail;
  @FXML
  public TextField customerZipCode;
  @FXML
  public TextField customerCity;
  @FXML
  public TextField customerStreet;
  @FXML
  public TextField customerHouseNumber;
  @FXML
  public TextField customerHouseLetter;
  @FXML
  public TextField customerCounty;
  @FXML
  public TextField customerMunicipality;
  @FXML
  public Button validAddressBtn;
  @FXML
  public Button addCustomerBtn;

  @FXML
  public ListView<Customer> listView;

  private List<Customer> customerList;

  @FXML
  protected void initialize() {
    // Checks the internet access
    if (!InternetAccessChecker.checkInternetAccess()) {
      customerCity.setEditable(true);
      customerMunicipality.setEditable(true);
      customerCounty.setEditable(true);
      validAddressBtn.setDisable(true);
    }

    customerList = CustomerRegistryDao.loadCustomerRegister();
    AtomicReference<ObservableList<Customer>> observableList =
        new AtomicReference<>(FXCollections.observableArrayList(customerList));
    listView.setItems(observableList.get());

    ContextMenu contextMenu = new ContextMenu();
    MenuItem deleteItem = new MenuItem("Delete");
    contextMenu.getItems().add(deleteItem);

    listView.setOnContextMenuRequested(event ->
        contextMenu.show(listView, event.getScreenX(), event.getScreenY()));

    deleteItem.setOnAction(event -> {
      Customer selectedItem = listView.getSelectionModel().getSelectedItem();
      if (selectedItem != null) {
        customerList.remove(selectedItem);
        observableList.set(FXCollections.observableArrayList(customerList));
        listView.setItems(observableList.get());
        CustomerRegistryDao.saveCustomersToFile();
      }
    });
  }

  @FXML
  protected void checkValidZipLookup() {
    ControllerUtility.checkValidZipLookup(customerZipCode, customerCity, customerMunicipality,
        customerCounty);
  }

  @Override
  public TextField getCustomerStreet() {
    return customerStreet;
  }

  @Override
  public TextField getCustomerHouseNumber() {
    return customerHouseNumber;
  }

  @Override
  public TextField getCustomerHouseLetter() {
    return customerHouseLetter;
  }

  @Override
  public TextField getCustomerZipCode() {
    return customerZipCode;
  }

  @Override
  public TextField getCustomerCity() {
    return customerCity;
  }

  @Override
  public TextField getCustomerCounty() {
    return customerCounty;
  }

  @Override
  public TextField getCustomerMunicipality() {
    return customerMunicipality;
  }

  @FXML
  @Override
  public void validateAddress() {
    Map<String, TextField> address =
        AddressUtility.getAddress(customerStreet, customerHouseNumber, customerHouseLetter,
            customerZipCode, customerCity, customerCounty, customerMunicipality);
    ControllerUtility.validateAddress(address, false);
  }

  @FXML
  protected void addCustomer() {
    Map<String, String> customer = new HashMap<>();
    customer.put("customerId", customerId.getText());
    customer.put("customerName", customerName.getText());
    customer.put("customerEmail", customerEmail.getText());
    customer.put("customerZipCode", customerZipCode.getText());
    customer.put("customerCity", customerCity.getText());
    customer.put("customerStreet", customerStreet.getText());
    customer.put("customerHouseNumber", customerHouseNumber.getText());
    customer.put("customerHouseLetter", customerHouseLetter.getText());
    customer.put("customerCounty", customerCounty.getText());
    customer.put("customerMunicipality", customerMunicipality.getText());
    customerList.add(new Customer(customer));
    CustomerRegistryDao.saveCustomersToFile();
    ObservableList<Customer> observableList =
        FXCollections.observableArrayList(customerList);
    listView.setItems(observableList);
  }
}
