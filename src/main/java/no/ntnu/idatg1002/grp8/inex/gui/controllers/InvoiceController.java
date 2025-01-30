package no.ntnu.idatg1002.grp8.inex.gui.controllers;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import lombok.Getter;
import no.ntnu.idatg1002.grp8.inex.ErrorLogger;
import no.ntnu.idatg1002.grp8.inex.gui.controllers.util.AddressField;
import no.ntnu.idatg1002.grp8.inex.gui.controllers.util.ControllerUtility;
import no.ntnu.idatg1002.grp8.inex.gui.stages.CustomerSuggestionStage;
import no.ntnu.idatg1002.grp8.inex.invoice.InvoiceGenerator;
import no.ntnu.idatg1002.grp8.inex.invoice.Item;
import no.ntnu.idatg1002.grp8.inex.utilities.AddressUtility;
import no.ntnu.idatg1002.grp8.inex.utilities.InternetAccessChecker;

/**
 * The `InvoiceController` class in Java defines methods for initializing an invoice GUI, retrieving
 * customer and item information, and generating an invoice as a PDF file.
 */
public class InvoiceController implements AddressField {
  @FXML
  @Getter
  private TextField customerName;
  @FXML
  @Getter
  private TextField customerId;
  @FXML
  private TextField customerStreet;
  @FXML
  private TextField customerHouseNumber;
  @FXML
  private TextField customerHouseLetter;
  @FXML
  private TextField customerZipCode;
  @FXML
  private TextField customerCity;
  @FXML
  private TextField customerMunicipality;
  @FXML
  private TextField customerCounty;
  @FXML
  private TextField customerReference;
  @FXML
  private TextField companyReference;
  @FXML
  private VBox items;
  @FXML
  public DatePicker issueDate;
  @FXML
  public DatePicker dueDate;
  @FXML
  public Button validAddressBtn;

  private final ArrayList<Item> itemList = new ArrayList<>();

  /**
   * The function initializes various fields and sets up event filters for a form,
   * including disabling certain buttons if there is no internet access.
   */
  @FXML
  protected void initialize() {
    if (!InternetAccessChecker.checkInternetAccess()) {
      customerCity.setEditable(true);
      customerMunicipality.setEditable(true);
      customerCounty.setEditable(true);
      validAddressBtn.setDisable(true);
    }

    issueDate.setValue(LocalDate.now());
    issueDate.setDayCellFactory(picker -> new DateCell() {
      @Override
      public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        LocalDate today = LocalDate.now();

        setDisable(empty || date.isBefore(today));
      }
    });

    dueDate.setValue(LocalDate.now().plusDays(30));

    dueDate.setDayCellFactory(picker -> new DateCell() {
      @Override
      public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        LocalDate today = LocalDate.now();

        setDisable(empty || date.isBefore(today.plusDays(14)));
      }
    });
    customerZipCode.addEventFilter(KeyEvent.KEY_TYPED, event -> {
      if (!event.getCharacter().matches("\\d")) {
        event.consume();
      }
    });
  }

  @FXML
  protected void searchCustomerRegister() {
    CustomerSuggestionStage.open();
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
    ControllerUtility.validateAddress(address, true);
  }

  @FXML
  protected void addVbox() {
    VBox vbox = new VBox();
    vbox.setPrefHeight(200.0);
    vbox.setPrefWidth(100.0);
    vbox.setSpacing(10.0);

    Label productDescriptionLabel = new Label("Product Description");
    Font labelFont = new Font("Calibri Bold Italic", 14.0);
    productDescriptionLabel.setFont(labelFont);

    TextField productDescriptionField = new TextField();
    productDescriptionField.setPromptText("Product Description");
    productDescriptionField.setPrefWidth(220.0);

    Label quantityLabel = new Label("Quantity:");
    quantityLabel.setFont(labelFont);

    Spinner<Integer> productQuantitySpinner = new Spinner<>(0, 99999, 0);
    productQuantitySpinner.setEditable(true);
    productQuantitySpinner.setPrefHeight(25.0);
    productQuantitySpinner.setPrefWidth(220.0);

    Label priceLabel = new Label("Price/Unit");
    priceLabel.setFont(labelFont);

    TextField productPriceField = new TextField();
    productPriceField.setPromptText("100.00 (NOK)");
    productPriceField.setPrefHeight(25.0);
    productPriceField.setPrefWidth(220.0);

    vbox.getChildren().addAll(
        productDescriptionLabel,
        productDescriptionField,
        quantityLabel,
        productQuantitySpinner,
        priceLabel,
        productPriceField
    );
    vbox.setPadding(new Insets(15, 0, 0, 0));

    items.getChildren().addAll(vbox);
  }

  @FXML
  protected void removeVbox() {
    if (items.getChildren().size() > 1) {
      int size = items.getChildren().size();
      items.getChildren().remove(size - 1);
    }
  }

  @FXML
  protected boolean getItems() {
    itemList.clear();
    List<Node> nodes = items.getChildren();
    for (Node node : nodes) {
      try {
        VBox item = (VBox) node;
        TextField descriptionText = (TextField) item.getChildren().get(1);
        Node quantityNode = item.getChildren().get(3);
        TextField priceText = (TextField) item.getChildren().get(5);

        int quantity = 0;
        if (quantityNode instanceof Spinner<?> spinner && (spinner.getValue() instanceof Integer)) {
          quantity = (int) spinner.getValue();
        }

        Optional<Item> optionalProduct = parseProduct(descriptionText.getText().trim(),
            quantity,
            priceText.getText().trim());

        if (optionalProduct.isPresent()) {
          itemList.add(optionalProduct.get());
        } else {
          return false;
        }
      } catch (Exception e) {
        ErrorLogger.getLOGGER().log(Level.WARNING, "Unable to get items");
      }
    }
    return true;
  }

  /**
   * The function parses a product description, quantity, and price into an Item object and returns
   * it
   * as an Optional, or returns an empty Optional if the description or price is empty
   * or if the price
   * cannot be parsed as a double.
   *
   * @param description A string representing the description of a product.
   * @param quantity    The quantity parameter represents the number of items
   *                    of a particular product being purchased.
   * @param price       The price parameter is a String representing the price of an item.
   *                   It is later parsed
   *                    into a double value using Double.parseDouble() method.
   * @return The method `parseProduct` returns an `Optional` object
   *         that may contain an `Item` object
   *         if the `description` and `price` parameters are not empty
   *         and the `price` parameter can be parsed as a `double`.
   *         If either of these conditions is not met, an empty `Optional` object is returned.
   */
  private Optional<Item> parseProduct(String description, int quantity, String price) {
    if (description.isEmpty() || price.isEmpty()) {
      return Optional.empty();
    }

    try {
      double priceValue = Double.parseDouble(price);
      return Optional.of(new Item(description, quantity, priceValue));
    } catch (NumberFormatException ex) {
      return Optional.empty();
    }
  }

  /**
   * This function returns a string that represents the location based on the input city,
   * municipality, and county.
   *
   * @param city         The name of the city in which the address is located.
   * @param municipality The municipality parameter refers to the name of the municipality or town
   *                     where the address is located.
   * @param county       The parameter "county" is a String variable representing the name
   *                     of a county.
   * @return The method is returning a string that represents the location of an address
   *         based on the input parameters.
   *         The string contains the municipality and county separated by a comma,
   *         unless the city parameter is equal to the municipality parameter,
   *         in which case only the county is returned.
   *         If the city parameter is also equal to the county parameter,
   *         an empty string is returned.
   */
  public static String addressValidatorForMultipleNames(String city, String municipality,
                                                        String county) {
    String location2 = municipality + ", " + county;
    if (city.equals(municipality)) {
      location2 = county;
      if (city.equals(county)) {
        location2 = "";
      }
    }
    return location2;
  }

  /**
   * The function generates an invoice with customer and item information
   * and saves it as a PDF file.
   */
  @FXML
  protected void generateInvoice() {
    String name = customerName.getText();
    String id = customerId.getText();
    String address = customerStreet.getText();
    String houseNumber = customerHouseNumber.getText();
    String apartmentLetter = customerHouseLetter.getText();
    String fullAddress = address + " " + houseNumber + apartmentLetter;
    String zipCode = customerZipCode.getText();
    String zipLocation = customerCity.getText();
    String zipMunicipality = customerMunicipality.getText();
    String zipCounty = customerCounty.getText();
    String location = zipCode + " " + zipLocation;
    String location2 = addressValidatorForMultipleNames(zipLocation, zipMunicipality, zipCounty);
    String customerRef = customerReference.getText();
    String companyRef = companyReference.getText();
    String kid = "";
    String issue = issueDate.getValue().toString();
    String due = dueDate.getValue().toString();

    Path folderPath = Path.of("cfg/pdf/generated/");

    HashMap<String, String> invoiceInformation = new HashMap<>();
    invoiceInformation.put("name", name);
    invoiceInformation.put("id", id);
    invoiceInformation.put("address", fullAddress);
    invoiceInformation.put("location", location);
    invoiceInformation.put("location2", location2);
    invoiceInformation.put("customerRef", customerRef);
    invoiceInformation.put("companyRef", companyRef);
    invoiceInformation.put("kid", kid);
    invoiceInformation.put("issue", issue);
    invoiceInformation.put("due", due);

    if (getItems()) {
      try {
        InvoiceGenerator.generateInvoice(
            invoiceInformation,
            itemList,
            folderPath);
      } catch (Exception e) {
        ErrorLogger.getLOGGER().log(Level.SEVERE, "Unable to generate Invoice PDF");
      }
    }
  }
}